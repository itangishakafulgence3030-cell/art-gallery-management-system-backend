package kigali.gallery.rw.config;

import java.time.LocalDate;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import kigali.gallery.rw.domain.ArtStyle;
import kigali.gallery.rw.domain.Artist;
import kigali.gallery.rw.domain.ArtworkSale;
import kigali.gallery.rw.domain.Curator;
import kigali.gallery.rw.domain.Exhibition;
import kigali.gallery.rw.domain.ExhibitionStatus;
import kigali.gallery.rw.domain.Hall;
import kigali.gallery.rw.repository.ArtStyleRepository;
import kigali.gallery.rw.repository.ArtistRepository;
import kigali.gallery.rw.repository.ArtworkSaleRepository;
import kigali.gallery.rw.repository.CuratorRepository;
import kigali.gallery.rw.repository.ExhibitionRepository;
import kigali.gallery.rw.repository.HallRepository;

@Configuration
public class DataSeeder {

    @Bean
    public ApplicationRunner seed(ArtStyleRepository artStyleRepository,
            HallRepository hallRepository,
            CuratorRepository curatorRepository,
            ArtistRepository artistRepository,
            ExhibitionRepository exhibitionRepository,
            ArtworkSaleRepository artworkSaleRepository) {
        return new ApplicationRunner() {
            @Override
            public void run(ApplicationArguments args) throws Exception {
                if (artStyleRepository.count() > 0) {
                    return;
                }

                ArtStyle painting = artStyleRepository.save(new ArtStyle("Painting"));
                ArtStyle sculpture = artStyleRepository.save(new ArtStyle("Sculpture"));
                ArtStyle photography = artStyleRepository.save(new ArtStyle("Photography"));
                ArtStyle digitalArt = artStyleRepository.save(new ArtStyle("Digital Art"));

                Hall hall1 = hallRepository.save(new Hall(1, "Hall 1"));
                Hall hall2 = hallRepository.save(new Hall(2, "Hall 2"));
                Hall hall3 = hallRepository.save(new Hall(3, "Hall 3"));
                Hall hall4 = hallRepository.save(new Hall(4, "Hall 4"));

                Curator curator1 = new Curator("Aline", "Mugisha", "aline@example.com");
                curator1.setHall(hall1);
                curator1.setArtStyles(java.util.List.of(painting, sculpture));
                curator1 = curatorRepository.save(curator1);

                Curator curator2 = new Curator("Eric", "Kamanzi", "eric@example.com");
                curator2.setHall(hall2);
                curator2.setArtStyles(java.util.List.of(photography));
                curator2 = curatorRepository.save(curator2);

                Curator curator3 = new Curator("Marie", "Uwineza", "marie@example.com");
                curator3.setHall(hall3);
                curator3.setArtStyles(java.util.List.of(painting));
                curator3 = curatorRepository.save(curator3);

                Artist artist1 = artistRepository.save(new Artist("Alice", "Niyonzima", "ART-2024-07", "alice@example.com", "+250780000001"));
                Artist artist2 = artistRepository.save(new Artist("Jonas", "Niyonzima", "ART-2024-08", "jonas@example.com", "+250780000002"));
                Artist artist3 = artistRepository.save(new Artist("Claire", "Uwimana", "ART-2024-09", "claire@example.com", "+250780000003"));
                Artist artist4 = artistRepository.save(new Artist("Samuel", "Habimana", "ART-2024-10", "samuel@example.com", "+250780000004"));
                Artist artist5 = artistRepository.save(new Artist("Dana", "Iradukunda", "ART-2024-11", "dana@example.com", "+250780000005"));

                Exhibition ex1 = new Exhibition(LocalDate.of(2026, 10, 5), ExhibitionStatus.PLANNED, "Lake Lights");
                ex1.setArtist(artist1); ex1.setCurator(curator1); exhibitionRepository.save(ex1);

                Exhibition ex2 = new Exhibition(LocalDate.of(2026, 10, 12), ExhibitionStatus.OPEN, "Stone Echoes");
                ex2.setArtist(artist2); ex2.setCurator(curator2); exhibitionRepository.save(ex2);

                Exhibition ex3 = new Exhibition(LocalDate.of(2026, 10, 20), ExhibitionStatus.CANCELLED, "Quiet Horizon");
                ex3.setArtist(artist3); ex3.setCurator(curator1); exhibitionRepository.save(ex3);

                Exhibition ex4 = new Exhibition(LocalDate.of(2026, 10, 20), ExhibitionStatus.PLANNED, "Night Garden");
                ex4.setArtist(artist4); ex4.setCurator(curator1); exhibitionRepository.save(ex4);

                Exhibition ex5 = new Exhibition(LocalDate.of(2026, 10, 27), ExhibitionStatus.CLOSED, "City in Motion");
                ex5.setArtist(artist2); ex5.setCurator(curator2); exhibitionRepository.save(ex5);

                Exhibition ex6 = new Exhibition(LocalDate.of(2026, 11, 2), ExhibitionStatus.OPEN, "Between Rain And Light");
                ex6.setArtist(artist5); ex6.setCurator(curator3); exhibitionRepository.save(ex6);

                Exhibition ex7 = new Exhibition(LocalDate.of(2026, 11, 8), ExhibitionStatus.CLOSED, "Sunset Over Kivu");
                ex7.setArtist(artist1); ex7.setCurator(curator1); exhibitionRepository.save(ex7);

                Exhibition ex8 = new Exhibition(LocalDate.of(2026, 11, 10), ExhibitionStatus.PLANNED, "Threads of Memory");
                ex8.setArtist(artist5); ex8.setCurator(curator2); exhibitionRepository.save(ex8);

                Exhibition ex9 = new Exhibition(LocalDate.of(2026, 11, 12), ExhibitionStatus.CANCELLED, "Urban Bloom");
                ex9.setArtist(artist4); ex9.setCurator(curator3); exhibitionRepository.save(ex9);

                Exhibition ex10 = new Exhibition(LocalDate.of(2026, 11, 15), ExhibitionStatus.CLOSED, "Glass Horizons");
                ex10.setArtist(artist5); ex10.setCurator(curator1); exhibitionRepository.save(ex10);

                ArtworkSale sale1 = new ArtworkSale("Look at the Lake", "Collected by a private buyer", 350000);
                sale1.setExhibition(ex5); sale1.setSaleDate(LocalDate.of(2026, 10, 30)); artworkSaleRepository.save(sale1);

                ArtworkSale sale2 = new ArtworkSale("Stone Echoes", "Gift for a family collection", 420000);
                sale2.setExhibition(ex7); sale2.setSaleDate(LocalDate.of(2026, 11, 9)); artworkSaleRepository.save(sale2);

                ArtworkSale sale3 = new ArtworkSale("Once in Morning Light", "Bought by a hotel chain", 510000);
                sale3.setExhibition(ex10); sale3.setSaleDate(LocalDate.of(2026, 11, 18)); artworkSaleRepository.save(sale3);

                ArtworkSale sale4 = new ArtworkSale("Echoes of the City", "Purchased by a collector", 470000);
                sale4.setExhibition(ex5); sale4.setSaleDate(LocalDate.of(2026, 10, 31)); artworkSaleRepository.save(sale4);

                ArtworkSale sale5 = new ArtworkSale("Kigali at Noon", "Collector request", 280000);
                sale5.setExhibition(ex7); sale5.setSaleDate(LocalDate.of(2026, 11, 12)); artworkSaleRepository.save(sale5);

                ArtworkSale sale6 = new ArtworkSale("A Path Through Memory", "Private collection", 365000);
                sale6.setExhibition(ex10); sale6.setSaleDate(LocalDate.of(2026, 11, 22)); artworkSaleRepository.save(sale6);
            }
        };
    }
}
