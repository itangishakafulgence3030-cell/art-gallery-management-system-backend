package kigali.gallery.rw.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import kigali.gallery.rw.domain.Hall;
import kigali.gallery.rw.repository.HallRepository;

@Service
public class HallService {

    private final HallRepository hallRepository;

    public HallService(HallRepository hallRepository) {
        this.hallRepository = hallRepository;
    }

    public List<Hall> findAll() {
        return hallRepository.findAll();
    }

    public Optional<Hall> findOne(UUID id) {
        return hallRepository.findById(id);
    }

    public Optional<Hall> findByHallNumber(int hallNumber) {
        return hallRepository.findByHallNumber(hallNumber);
    }

    public String save(Hall hall) {
        hallRepository.save(hall);
        return "Hall saved successfully";
    }

    public String update(UUID id, Hall hall) {
        if (!hallRepository.existsById(id)) {
            return "The hall with that id does not exist";
        }
        hall.setId(id);
        hallRepository.save(hall);
        return "Hall updated successfully";
    }

    public String delete(UUID id) {
        if (!hallRepository.existsById(id)) {
            return "The hall with that id does not exist";
        }
        hallRepository.deleteById(id);
        return "Hall deleted successfully";
    }

    public List<Hall> findFree() {
        return hallRepository.findFreeHalls();
    }
}
