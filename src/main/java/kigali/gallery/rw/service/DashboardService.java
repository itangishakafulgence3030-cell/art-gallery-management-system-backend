package kigali.gallery.rw.service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import kigali.gallery.rw.domain.ExhibitionStatus;
import kigali.gallery.rw.repository.ArtistRepository;
import kigali.gallery.rw.repository.ArtworkSaleRepository;
import kigali.gallery.rw.repository.CuratorRepository;
import kigali.gallery.rw.repository.ExhibitionRepository;
import kigali.gallery.rw.repository.HallRepository;

@Service
public class DashboardService {

    private final ArtistRepository artistRepository;
    private final CuratorRepository curatorRepository;
    private final HallRepository hallRepository;
    private final ExhibitionRepository exhibitionRepository;
    private final ArtworkSaleRepository artworkSaleRepository;

    public DashboardService(ArtistRepository artistRepository, CuratorRepository curatorRepository,
            HallRepository hallRepository, ExhibitionRepository exhibitionRepository,
            ArtworkSaleRepository artworkSaleRepository) {
        this.artistRepository = artistRepository;
        this.curatorRepository = curatorRepository;
        this.hallRepository = hallRepository;
        this.exhibitionRepository = exhibitionRepository;
        this.artworkSaleRepository = artworkSaleRepository;
    }

    public Map<String, Long> summary() {
        Map<String, Long> summary = new LinkedHashMap<>();
        summary.put("artists", artistRepository.count());
        summary.put("curators", curatorRepository.count());
        long occupiedHallCount = curatorRepository.findAll().stream()
                .filter(curator -> curator.getHall() != null)
                .map(curator -> curator.getHall().getId())
                .distinct()
                .count();
        summary.put("freeHalls", hallRepository.count() - occupiedHallCount);
        summary.put("exhibitionsToday", exhibitionRepository.countByOpeningDate(LocalDate.now()));
        summary.put("planned", exhibitionRepository.countByStatus(ExhibitionStatus.PLANNED));
        summary.put("artworkSales", artworkSaleRepository.count());
        return summary;
    }
}
