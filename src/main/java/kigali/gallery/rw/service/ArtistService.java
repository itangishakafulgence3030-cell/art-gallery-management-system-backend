package kigali.gallery.rw.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import kigali.gallery.rw.domain.Artist;
import kigali.gallery.rw.repository.ArtistRepository;

@Service
public class ArtistService {

    private final ArtistRepository artistRepository;

    public ArtistService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    public List<Artist> findAll() {
        return artistRepository.findAll();
    }

    public Optional<Artist> findOne(UUID id) {
        return artistRepository.findById(id);
    }

    public String save(Artist artist) {
        if (artistRepository.existsByArtistCode(artist.getArtistCode())) {
            return "The artist code already exists";
        }
        artistRepository.save(artist);
        return "Artist saved successfully";
    }

    public String update(UUID id, Artist artist) {
        if (!artistRepository.existsById(id)) {
            return "The artist with that id does not exist";
        }
        artist.setId(id);
        artistRepository.save(artist);
        return "Artist updated successfully";
    }

    public String delete(UUID id) {
        if (!artistRepository.existsById(id)) {
            return "The artist with that id does not exist";
        }
        artistRepository.deleteById(id);
        return "Artist deleted successfully";
    }

    public List<Artist> findByLastName(String lastName) {
        return artistRepository.findByLastNameIgnoreCaseOrderByFirstNameAsc(lastName);
    }

    public List<Artist> findByCurator(UUID curatorId) {
        return artistRepository.findArtistsOfCurator(curatorId);
    }
}
