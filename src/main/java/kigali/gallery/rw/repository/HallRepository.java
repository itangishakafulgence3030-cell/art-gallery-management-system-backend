package kigali.gallery.rw.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kigali.gallery.rw.domain.Hall;

@Repository
public interface HallRepository extends JpaRepository<Hall, UUID> {

    Optional<Hall> findByHallNumber(int hallNumber);

    boolean existsByHallNumber(int hallNumber);

    @Query("SELECT h FROM Hall h LEFT JOIN Curator c ON c.hall = h WHERE c.id IS NULL ORDER BY h.hallNumber ASC")
    List<Hall> findFreeHalls();
}
