package kigali.gallery.rw.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kigali.gallery.rw.domain.Curator;
import kigali.gallery.rw.service.CuratorService;

@RestController
@RequestMapping("/api")
public class CuratorController {

    private final CuratorService curatorService;

    public CuratorController(CuratorService curatorService) {
        this.curatorService = curatorService;
    }

    @PostMapping("/curators/save")
    public ResponseEntity<String> save(@RequestBody Curator curator) {
        return ResponseEntity.status(HttpStatus.CREATED).body(curatorService.save(curator));
    }

    @GetMapping("/curators/all")
    public List<Curator> findAll() {
        return curatorService.findAll();
    }

    @GetMapping("/curators/{id}")
    public ResponseEntity<?> findOne(@PathVariable UUID id) {
        Optional<Curator> curator = curatorService.findOne(id);
        if (curator.isPresent()) {
            return ResponseEntity.ok(curator.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The curator with that id does not exist");
    }

    @PutMapping("/curators/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody Curator curator) {
        String message = curatorService.update(id, curator);
        if (message.equals("The curator with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/curators/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = curatorService.delete(id);
        if (message.equals("The curator with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        return ResponseEntity.ok(message);
    }

    @GetMapping("/curators/by-art-style")
    public List<Curator> findByArtStyle(@RequestParam String name) {
        return curatorService.findByArtStyleName(name);
    }

    @PutMapping("/curators/{curatorId}/art-styles/{artStyleId}")
    public ResponseEntity<String> addArtStyle(@PathVariable UUID curatorId, @PathVariable UUID artStyleId) {
        String message = curatorService.addArtStyle(curatorId, artStyleId);
        if (message.equals("The curator with that id does not exist")
                || message.equals("That art style does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        if (message.equals("The curator already has that art style")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        }
        return ResponseEntity.ok(message);
    }

    @PatchMapping("/curators/{curatorId}/hall")
    public ResponseEntity<String> changeHall(@PathVariable UUID curatorId, @RequestParam int hallNumber) {
        String message = curatorService.changeHall(curatorId, hallNumber);
        if (message.equals("The curator with that id does not exist")
                || message.equals("The hall with that hallNumber does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        if (message.equals("That hall is taken by another curator")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        }
        return ResponseEntity.ok(message);
    }
}
