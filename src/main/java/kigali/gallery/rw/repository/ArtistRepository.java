package kigali.gallery.rw.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import kigali.gallery.rw.domain.Artist;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, UUID> {

    List<Artist> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);

    @Query("SELECT DISTINCT e.artist FROM Exhibition e WHERE e.curator.id = :curatorId")
    List<Artist> findArtistsOfCurator(@Param("curatorId") UUID curatorId);

    boolean existsByArtistCode(String artistCode);
}
