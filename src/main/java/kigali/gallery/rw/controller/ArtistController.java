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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kigali.gallery.rw.domain.Artist;
import kigali.gallery.rw.repository.CuratorRepository;
import kigali.gallery.rw.service.ArtistService;

@RestController
@RequestMapping("/api")
public class ArtistController {

    private final ArtistService artistService;
    private final CuratorRepository curatorRepository;

    public ArtistController(ArtistService artistService, CuratorRepository curatorRepository) {
        this.artistService = artistService;
        this.curatorRepository = curatorRepository;
    }

    @PostMapping("/artists/save")
    public ResponseEntity<String> save(@RequestBody Artist artist) {
        return ResponseEntity.status(HttpStatus.CREATED).body(artistService.save(artist));
    }

    @GetMapping("/artists/all")
    public List<Artist> findAll() {
        return artistService.findAll();
    }

    @GetMapping("/artists/{id}")
    public ResponseEntity<?> findOne(@PathVariable UUID id) {
        Optional<Artist> artist = artistService.findOne(id);
        if (artist.isPresent()) {
            return ResponseEntity.ok(artist.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The artist with that id does not exist");
    }

    @PutMapping("/artists/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody Artist artist) {
        String message = artistService.update(id, artist);
        if (message.equals("The artist with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/artists/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = artistService.delete(id);
        if (message.equals("The artist with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        return ResponseEntity.ok(message);
    }

    @GetMapping("/artists/by-last-name")
    public List<Artist> findByLastName(@RequestParam String lastName) {
        return artistService.findByLastName(lastName);
    }

    @GetMapping("/artists/of-curator/{curatorId}")
    public ResponseEntity<?> findByCurator(@PathVariable UUID curatorId) {
        if (!curatorRepository.existsById(curatorId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The curator with that id does not exist");
        }
        return ResponseEntity.ok(artistService.findByCurator(curatorId));
    }
}
