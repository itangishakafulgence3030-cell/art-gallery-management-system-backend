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

import kigali.gallery.rw.domain.ArtStyle;
import kigali.gallery.rw.service.ArtStyleService;

@RestController
@RequestMapping("/api")
public class ArtStyleController {

    private final ArtStyleService artStyleService;

    public ArtStyleController(ArtStyleService artStyleService) {
        this.artStyleService = artStyleService;
    }

    @PostMapping("/art-styles/save")
    public ResponseEntity<String> save(@RequestBody ArtStyle artStyle) {
        return ResponseEntity.status(HttpStatus.CREATED).body(artStyleService.save(artStyle));
    }

    @GetMapping("/art-styles/all")
    public List<ArtStyle> findAll() {
        return artStyleService.findAll();
    }

    @GetMapping("/art-styles/{id}")
    public ResponseEntity<?> findOne(@PathVariable UUID id) {
        Optional<ArtStyle> artStyle = artStyleService.findOne(id);
        if (artStyle.isPresent()) {
            return ResponseEntity.ok(artStyle.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The art style with that id does not exist");
    }

    @PutMapping("/art-styles/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody ArtStyle artStyle) {
        String message = artStyleService.update(id, artStyle);
        if (message.equals("The art style with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/art-styles/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = artStyleService.delete(id);
        if (message.equals("The art style with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        return ResponseEntity.ok(message);
    }

    @GetMapping("/art-styles/unused")
    public List<ArtStyle> findUnused() {
        return artStyleService.findUnused();
    }
}
