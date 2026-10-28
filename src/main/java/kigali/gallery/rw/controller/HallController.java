package kigali.gallery.rw.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kigali.gallery.rw.domain.Hall;
import kigali.gallery.rw.service.HallService;

@RestController
@RequestMapping("/api")
public class HallController {

    private final HallService hallService;

    public HallController(HallService hallService) {
        this.hallService = hallService;
    }

    @PostMapping("/halls/save")
    public ResponseEntity<String> save(@RequestBody Hall hall) {
        return ResponseEntity.status(HttpStatus.CREATED).body(hallService.save(hall));
    }

    @GetMapping("/halls/all")
    public List<Hall> findAll() {
        return hallService.findAll();
    }

    @GetMapping("/halls/{id}")
    public ResponseEntity<?> findOne(@PathVariable UUID id) {
        Optional<Hall> hall = hallService.findOne(id);
        if (hall.isPresent()) {
            return ResponseEntity.ok(hall.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The hall with that id does not exist");
    }

    @PutMapping("/halls/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody Hall hall) {
        String message = hallService.update(id, hall);
        if (message.equals("The hall with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/halls/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = hallService.delete(id);
        if (message.equals("The hall with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        return ResponseEntity.ok(message);
    }

    @GetMapping("/halls/free")
    public List<Hall> findFree() {
        return hallService.findFree();
    }
}
