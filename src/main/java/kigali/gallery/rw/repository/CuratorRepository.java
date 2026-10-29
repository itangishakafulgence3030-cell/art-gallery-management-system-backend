package kigali.gallery.rw.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import kigali.gallery.rw.domain.Curator;
import kigali.gallery.rw.domain.Hall;

@Repository
public interface CuratorRepository extends JpaRepository<Curator, UUID> {

    @Query("SELECT c FROM Curator c JOIN c.artStyles a WHERE lower(a.name) = lower(:name) ORDER BY c.lastName ASC")
    List<Curator> findByArtStyleNameIgnoreCase(@Param("name") String name);

    @Query("SELECT c FROM Curator c WHERE c.hall IS NULL")
    List<Curator> findByHallIsNull();

    boolean existsByHall(Hall hall);
}
