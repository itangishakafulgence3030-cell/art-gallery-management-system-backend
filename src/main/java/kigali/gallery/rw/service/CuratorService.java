package kigali.gallery.rw.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import kigali.gallery.rw.domain.ArtStyle;
import kigali.gallery.rw.domain.Curator;
import kigali.gallery.rw.domain.Hall;
import kigali.gallery.rw.repository.ArtStyleRepository;
import kigali.gallery.rw.repository.CuratorRepository;
import kigali.gallery.rw.repository.HallRepository;

@Service
public class CuratorService {

    private final CuratorRepository curatorRepository;
    private final HallRepository hallRepository;
    private final ArtStyleRepository artStyleRepository;

    public CuratorService(CuratorRepository curatorRepository, HallRepository hallRepository,
            ArtStyleRepository artStyleRepository) {
        this.curatorRepository = curatorRepository;
        this.hallRepository = hallRepository;
        this.artStyleRepository = artStyleRepository;
    }

    public List<Curator> findAll() {
        return curatorRepository.findAll();
    }

    public Optional<Curator> findOne(UUID id) {
        return curatorRepository.findById(id);
    }

    public String save(Curator curator) {
        curatorRepository.save(curator);
        return "Curator saved successfully";
    }

    public String update(UUID id, Curator curator) {
        if (!curatorRepository.existsById(id)) {
            return "The curator with that id does not exist";
        }
        curator.setId(id);
        curatorRepository.save(curator);
        return "Curator updated successfully";
    }

    public String delete(UUID id) {
        if (!curatorRepository.existsById(id)) {
            return "The curator with that id does not exist";
        }
        curatorRepository.deleteById(id);
        return "Curator deleted successfully";
    }

    public List<Curator> findByArtStyleName(String name) {
        return curatorRepository.findByArtStyleNameIgnoreCase(name);
    }

    public List<Curator> findFreeHalls() {
        return curatorRepository.findByHallIsNull();
    }

    public String addArtStyle(UUID curatorId, UUID artStyleId) {
        Optional<Curator> curatorOptional = curatorRepository.findById(curatorId);
        if (curatorOptional.isEmpty()) {
            return "The curator with that id does not exist";
        }
        Optional<ArtStyle> artStyleOptional = artStyleRepository.findById(artStyleId);
        if (artStyleOptional.isEmpty()) {
            return "That art style does not exist";
        }
        Curator curator = curatorOptional.get();
        if (curator.getArtStyles().stream().anyMatch(style -> style.getId().equals(artStyleId))) {
            return "The curator already has that art style";
        }
        curator.getArtStyles().add(artStyleOptional.get());
        curatorRepository.save(curator);
        return "Art style added to the curator";
    }

    public String changeHall(UUID curatorId, int hallNumber) {
        Optional<Curator> curatorOptional = curatorRepository.findById(curatorId);
        if (curatorOptional.isEmpty()) {
            return "The curator with that id does not exist";
        }
        Optional<Hall> hallOptional = hallRepository.findByHallNumber(hallNumber);
        if (hallOptional.isEmpty()) {
            return "The hall with that hallNumber does not exist";
        }
        Curator curator = curatorOptional.get();
        Hall hall = hallOptional.get();
        if (curator.getHall() != null && curator.getHall().getId().equals(hall.getId())) {
            return "Hall changed successfully";
        }
        Optional<Curator> occupant = curatorRepository.findByHall(hall).stream()
                .filter(candidate -> !candidate.getId().equals(curatorId))
                .findFirst();
        if (occupant.isPresent()) {
            return "That hall is taken by another curator";
        }
        curator.setHall(hall);
        curatorRepository.save(curator);
        return "Hall changed successfully";
    }
}
