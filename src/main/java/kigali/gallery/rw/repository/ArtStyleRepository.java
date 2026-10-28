package kigali.gallery.rw.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kigali.gallery.rw.domain.ArtStyle;

@Repository
public interface ArtStyleRepository extends JpaRepository<ArtStyle, UUID> {

    @Query("SELECT a FROM ArtStyle a WHERE a.curators IS EMPTY")
    List<ArtStyle> findUnusedArtStyles();
}
