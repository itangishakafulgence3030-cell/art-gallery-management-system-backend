package kigali.gallery.rw.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

import kigali.gallery.rw.domain.Exhibition;
import kigali.gallery.rw.domain.ExhibitionStatus;
import kigali.gallery.rw.repository.ArtworkSaleRepository;
import kigali.gallery.rw.service.ExhibitionService;

@RestController
@RequestMapping("/api")
public class ExhibitionController {

    private final ExhibitionService exhibitionService;
    private final ArtworkSaleRepository artworkSaleRepository;

    public ExhibitionController(ExhibitionService exhibitionService, ArtworkSaleRepository artworkSaleRepository) {
        this.exhibitionService = exhibitionService;
        this.artworkSaleRepository = artworkSaleRepository;
    }

    @PostMapping("/exhibitions/save")
    public ResponseEntity<String> save(@RequestBody Exhibition exhibition) {
        String message = exhibitionService.save(exhibition);
        if (message.equals("Exhibition date cannot be in the past")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        if (message.equals("Curator already opens an exhibition on that date")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @GetMapping("/exhibitions/all")
    public List<Exhibition> findAll() {
        return exhibitionService.findAll();
    }

    @GetMapping("/exhibitions/{id}")
    public ResponseEntity<?> findOne(@PathVariable UUID id) {
        Optional<Exhibition> exhibition = exhibitionService.findOne(id);
        if (exhibition.isPresent()) {
            return ResponseEntity.ok(exhibition.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The exhibition with that id does not exist");
    }

    @PutMapping("/exhibitions/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody Exhibition exhibition) {
        String message = exhibitionService.update(id, exhibition);
        if (message.equals("The exhibition with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/exhibitions/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        if (artworkSaleRepository.existsByExhibitionId(id)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("That exhibition has artwork sales, delete them first");
        }
        String message = exhibitionService.delete(id);
        if (message.equals("The exhibition with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        return ResponseEntity.ok(message);
    }

    @GetMapping("/exhibitions/by-status")
    public List<Exhibition> findByStatus(@RequestParam ExhibitionStatus status) {
        return exhibitionService.findByStatus(status);
    }

    @GetMapping("/exhibitions/between")
    public List<Exhibition> findBetween(@RequestParam String start, @RequestParam String end) {
        return exhibitionService.findBetween(LocalDate.parse(start), LocalDate.parse(end));
    }

    @PatchMapping("/exhibitions/{id}/status")
    public ResponseEntity<String> changeStatus(@PathVariable UUID id, @RequestParam ExhibitionStatus status) {
        String message = exhibitionService.changeStatus(id, status);
        if (message.equals("The exhibition with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        if (message.startsWith("Cannot change status from")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        return ResponseEntity.ok(message);
    }

    @GetMapping("/exhibitions/stats/by-status")
    public List<Object[]> statsByStatus() {
        return exhibitionService.findStatsByStatus();
    }

    @PatchMapping("/exhibitions/cancel-day")
    public ResponseEntity<String> cancelDay(@RequestParam UUID curatorId, @RequestParam String date) {
        int count = exhibitionService.cancelDay(curatorId, LocalDate.parse(date));
        return ResponseEntity.ok(count + " exhibitions cancelled");
    }

    @GetMapping("/exhibitions/page")
    public Page<Exhibition> page(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "openingDate,desc") String sort) {
        String[] parts = sort.split(",");
        String property = parts.length > 0 ? parts[0] : "openingDate";
        String direction = parts.length > 1 ? parts[1] : "desc";
        Sort.Direction dir = direction.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(dir, property));
        return exhibitionService.findPage(pageable);
    }
}
