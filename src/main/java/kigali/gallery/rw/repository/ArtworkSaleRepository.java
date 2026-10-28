package kigali.gallery.rw.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import kigali.gallery.rw.domain.ArtworkSale;

@Repository
public interface ArtworkSaleRepository extends JpaRepository<ArtworkSale, UUID> {

    boolean existsByExhibitionId(UUID exhibitionId);

    @Query("SELECT a FROM ArtworkSale a JOIN a.exhibition e WHERE e.artist.artistCode = :artistCode ORDER BY a.saleDate DESC")
    List<ArtworkSale> findByArtistCodeOrderBySaleDateDesc(@Param("artistCode") String artistCode);
}
