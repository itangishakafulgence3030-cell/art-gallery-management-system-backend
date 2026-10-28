package kigali.gallery.rw.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import kigali.gallery.rw.domain.ArtStyle;
import kigali.gallery.rw.repository.ArtStyleRepository;

@Service
public class ArtStyleService {

    private final ArtStyleRepository artStyleRepository;

    public ArtStyleService(ArtStyleRepository artStyleRepository) {
        this.artStyleRepository = artStyleRepository;
    }

    public List<ArtStyle> findAll() {
        return artStyleRepository.findAll();
    }

    public Optional<ArtStyle> findOne(UUID id) {
        return artStyleRepository.findById(id);
    }

    public String save(ArtStyle artStyle) {
        artStyleRepository.save(artStyle);
        return "Art style saved successfully";
    }

    public String update(UUID id, ArtStyle artStyle) {
        if (!artStyleRepository.existsById(id)) {
            return "The art style with that id does not exist";
        }
        artStyle.setId(id);
        artStyleRepository.save(artStyle);
        return "Art style updated successfully";
    }

    public String delete(UUID id) {
        if (!artStyleRepository.existsById(id)) {
            return "The art style with that id does not exist";
        }
        artStyleRepository.deleteById(id);
        return "Art style deleted successfully";
    }

    public List<ArtStyle> findUnused() {
        return artStyleRepository.findUnusedArtStyles();
    }
}
