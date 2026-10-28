package kigali.gallery.rw.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import kigali.gallery.rw.domain.Exhibition;
import kigali.gallery.rw.domain.ExhibitionStatus;
import kigali.gallery.rw.repository.ExhibitionRepository;

@Service
public class ExhibitionService {

    private final ExhibitionRepository exhibitionRepository;

    public ExhibitionService(ExhibitionRepository exhibitionRepository) {
        this.exhibitionRepository = exhibitionRepository;
    }

    public List<Exhibition> findAll() {
        return exhibitionRepository.findAll();
    }

    public Optional<Exhibition> findOne(UUID id) {
        return exhibitionRepository.findById(id);
    }

    public String save(Exhibition exhibition) {
        if (exhibition.getOpeningDate() != null && exhibition.getOpeningDate().isBefore(LocalDate.now())) {
            return "Exhibition date cannot be in the past";
        }
        if (exhibition.getCurator() != null && exhibition.getCurator().getId() != null
                && exhibitionRepository.existsByCuratorIdAndOpeningDateAndStatusNot(
                        exhibition.getCurator().getId(), exhibition.getOpeningDate(), ExhibitionStatus.CANCELLED)) {
            return "Curator already opens an exhibition on that date";
        }
        exhibition.setStatus(ExhibitionStatus.PLANNED);
        exhibitionRepository.save(exhibition);
        return "Exhibition saved successfully";
    }

    public String update(UUID id, Exhibition exhibition) {
        if (!exhibitionRepository.existsById(id)) {
            return "The exhibition with that id does not exist";
        }
        Exhibition existing = exhibitionRepository.findById(id).orElse(null);
        if (existing != null) {
            exhibition.setStatus(existing.getStatus());
        }
        exhibition.setId(id);
        exhibitionRepository.save(exhibition);
        return "Exhibition updated successfully";
    }

    public String delete(UUID id) {
        if (!exhibitionRepository.existsById(id)) {
            return "The exhibition with that id does not exist";
        }
        exhibitionRepository.deleteById(id);
        return "Exhibition deleted successfully";
    }

    public List<Exhibition> findByStatus(ExhibitionStatus status) {
        return exhibitionRepository.findByStatusOrderByOpeningDateAsc(status);
    }

    public List<Exhibition> findBetween(LocalDate start, LocalDate end) {
        return exhibitionRepository.findByOpeningDateBetweenOrderByOpeningDateAsc(start, end);
    }

    public List<Object[]> findStatsByStatus() {
        return exhibitionRepository.countByStatusGroupBy();
    }

    public String changeStatus(UUID id, ExhibitionStatus status) {
        Optional<Exhibition> exhibitionOptional = exhibitionRepository.findById(id);
        if (exhibitionOptional.isEmpty()) {
            return "The exhibition with that id does not exist";
        }
        Exhibition exhibition = exhibitionOptional.get();
        ExhibitionStatus currentStatus = exhibition.getStatus();
        if (currentStatus == ExhibitionStatus.PLANNED && (status == ExhibitionStatus.OPEN || status == ExhibitionStatus.CANCELLED)) {
            exhibition.setStatus(status);
            exhibitionRepository.save(exhibition);
            return "Exhibition status updated successfully";
        }
        if (currentStatus == ExhibitionStatus.OPEN && (status == ExhibitionStatus.CLOSED || status == ExhibitionStatus.CANCELLED)) {
            exhibition.setStatus(status);
            exhibitionRepository.save(exhibition);
            return "Exhibition status updated successfully";
        }
        return "Cannot change status from " + currentStatus + " to " + status;
    }

    public int cancelDay(UUID curatorId, LocalDate date) {
        return exhibitionRepository.cancelExhibitionsForDate(curatorId, date, ExhibitionStatus.CANCELLED, ExhibitionStatus.CLOSED);
    }

    public Page<Exhibition> findPage(Pageable pageable) {
        return exhibitionRepository.findAll(pageable);
    }
}
