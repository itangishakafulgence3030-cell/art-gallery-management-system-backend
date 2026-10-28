package kigali.gallery.rw.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kigali.gallery.rw.domain.ArtworkSale;
import kigali.gallery.rw.service.ArtworkSaleService;

@RestController
@RequestMapping("/api")
public class ArtworkSaleController {

    private final ArtworkSaleService artworkSaleService;

    public ArtworkSaleController(ArtworkSaleService artworkSaleService) {
        this.artworkSaleService = artworkSaleService;
    }

    @PostMapping("/artwork-sales/save")
    public ResponseEntity<String> save(@RequestParam UUID exhibitionId, @RequestBody ArtworkSale artworkSale) {
        String message = artworkSaleService.save(exhibitionId, artworkSale);
        if (message.equals("The exhibition with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        if (message.equals("Artwork sales are only for CLOSED exhibitions")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @GetMapping("/artwork-sales/artist/{artistCode}")
    public List<ArtworkSale> findByArtistCode(@PathVariable String artistCode) {
        return artworkSaleService.findByArtistCode(artistCode);
    }

    @DeleteMapping("/artwork-sales/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = artworkSaleService.delete(id);
        if (message.equals("The artwork sale with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        return ResponseEntity.ok(message);
    }
}
