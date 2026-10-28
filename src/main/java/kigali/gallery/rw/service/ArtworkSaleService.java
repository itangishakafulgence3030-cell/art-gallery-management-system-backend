package kigali.gallery.rw.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import kigali.gallery.rw.domain.ArtworkSale;
import kigali.gallery.rw.domain.Exhibition;
import kigali.gallery.rw.domain.ExhibitionStatus;
import kigali.gallery.rw.repository.ArtworkSaleRepository;
import kigali.gallery.rw.repository.ExhibitionRepository;

@Service
public class ArtworkSaleService {

    private final ArtworkSaleRepository artworkSaleRepository;
    private final ExhibitionRepository exhibitionRepository;

    public ArtworkSaleService(ArtworkSaleRepository artworkSaleRepository, ExhibitionRepository exhibitionRepository) {
        this.artworkSaleRepository = artworkSaleRepository;
        this.exhibitionRepository = exhibitionRepository;
    }

    public String save(UUID exhibitionId, ArtworkSale artworkSale) {
        Optional<Exhibition> exhibitionOptional = exhibitionRepository.findById(exhibitionId);
        if (exhibitionOptional.isEmpty()) {
            return "The exhibition with that id does not exist";
        }
        Exhibition exhibition = exhibitionOptional.get();
        if (exhibition.getStatus() != ExhibitionStatus.CLOSED) {
            return "Artwork sales are only for CLOSED exhibitions";
        }
        artworkSale.setExhibition(exhibition);
        artworkSale.setSaleDate(LocalDate.now());
        artworkSaleRepository.save(artworkSale);
        return "Artwork sale saved successfully";
    }

    public List<ArtworkSale> findByArtistCode(String artistCode) {
        return artworkSaleRepository.findByArtistCodeOrderBySaleDateDesc(artistCode);
    }

    public String delete(UUID id) {
        if (!artworkSaleRepository.existsById(id)) {
            return "The artwork sale with that id does not exist";
        }
        artworkSaleRepository.deleteById(id);
        return "Artwork sale deleted successfully";
    }
}
