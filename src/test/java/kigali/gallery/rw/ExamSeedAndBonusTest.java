package kigali.gallery.rw;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import kigali.gallery.rw.domain.Exhibition;
import kigali.gallery.rw.domain.ExhibitionStatus;
import kigali.gallery.rw.repository.ArtStyleRepository;
import kigali.gallery.rw.repository.ArtistRepository;
import kigali.gallery.rw.repository.ArtworkSaleRepository;
import kigali.gallery.rw.repository.CuratorRepository;
import kigali.gallery.rw.repository.ExhibitionRepository;
import kigali.gallery.rw.repository.HallRepository;
import kigali.gallery.rw.service.CuratorService;
import kigali.gallery.rw.service.DashboardService;

@SpringBootTest
@Transactional
class ExamSeedAndBonusTest {

    @Autowired
    private ArtStyleRepository artStyleRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private ArtworkSaleRepository artworkSaleRepository;

    @Autowired
    private CuratorRepository curatorRepository;

    @Autowired
    private ExhibitionRepository exhibitionRepository;

    @Autowired
    private HallRepository hallRepository;

    @Autowired
    private DashboardService dashboardService;

    @Autowired
    private CuratorService curatorService;

    @Test
    void seededDataCoversTheExamQueries() {
        List<Exhibition> exhibitions = exhibitionRepository.findAll();
        Map<UUID, Long> exhibitionsPerArtist = exhibitions.stream()
                .collect(Collectors.groupingBy(exhibition -> exhibition.getArtist().getId(), Collectors.counting()));
        UUID curatorIdOnOctober20 = exhibitions.stream()
                .filter(exhibition -> exhibition.getOpeningDate().equals(LocalDate.of(2026, 10, 20)))
                .findFirst().orElseThrow().getCurator().getId();
        List<Exhibition> curatorExhibitionsOnOctober20 = exhibitions.stream()
                .filter(exhibition -> exhibition.getOpeningDate().equals(LocalDate.of(2026, 10, 20)))
                .filter(exhibition -> exhibition.getCurator().getId().equals(curatorIdOnOctober20))
                .toList();

        assertEquals(4, artStyleRepository.count());
        assertEquals(4, hallRepository.count());
        assertEquals(3, curatorRepository.count());
        assertEquals(5, artistRepository.count());
        assertEquals(10, exhibitions.size());
        assertEquals(6, artworkSaleRepository.count());
        assertTrue(exhibitionsPerArtist.values().stream().anyMatch(count -> count >= 3));
        assertEquals(1, hallRepository.findFreeHalls().size());
        assertEquals("Hall 4", hallRepository.findFreeHalls().get(0).getName());
        assertEquals(List.of("Digital Art"),
                artStyleRepository.findUnusedArtStyles().stream().map(style -> style.getName()).toList());
        assertTrue(curatorExhibitionsOnOctober20.stream()
                .anyMatch(exhibition -> exhibition.getStatus() == ExhibitionStatus.CANCELLED));
        assertTrue(curatorExhibitionsOnOctober20.stream()
                .anyMatch(exhibition -> exhibition.getStatus() != ExhibitionStatus.CANCELLED));
    }

    @Test
    void dashboardBonusReturnsTheSpecifiedKeysInOrder() {
        Map<String, Long> summary = dashboardService.summary();

        assertEquals(List.of("artists", "curators", "freeHalls", "exhibitionsToday", "planned", "artworkSales"),
                List.copyOf(summary.keySet()));
        assertEquals(5L, summary.get("artists"));
        assertEquals(3L, summary.get("curators"));
        assertEquals(1L, summary.get("freeHalls"));
        assertEquals(6L, summary.get("artworkSales"));
    }

    @Test
    void curatorCannotTakeAnOccupiedHallAndCanMoveToTheFreeHall() {
        UUID curatorId = curatorRepository.findAll().stream()
                .filter(curator -> curator.getFirstName().equals("Aline"))
                .findFirst().orElseThrow().getId();

        assertEquals("That hall is taken by another curator", curatorService.changeHall(curatorId, 2));
        assertEquals("Hall changed successfully", curatorService.changeHall(curatorId, 4));
        assertEquals(1, hallRepository.findFreeHalls().size());
        assertEquals(1, hallRepository.findFreeHalls().get(0).getHallNumber());
    }
}
