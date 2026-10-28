package kigali.gallery.rw.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import kigali.gallery.rw.domain.Exhibition;
import kigali.gallery.rw.domain.ExhibitionStatus;

@Repository
public interface ExhibitionRepository extends JpaRepository<Exhibition, UUID> {

    List<Exhibition> findByStatusOrderByOpeningDateAsc(ExhibitionStatus status);

    List<Exhibition> findByOpeningDateBetweenOrderByOpeningDateAsc(LocalDate start, LocalDate end);

    boolean existsByCuratorIdAndOpeningDateAndStatusNot(UUID curatorId, LocalDate openingDate, ExhibitionStatus status);

    long countByStatus(ExhibitionStatus status);

    long countByOpeningDate(LocalDate openingDate);

    @Query("SELECT e.status, COUNT(e) FROM Exhibition e GROUP BY e.status")
    List<Object[]> countByStatusGroupBy();

    @Query("UPDATE Exhibition e SET e.status = :cancelled WHERE e.curator.id = :curatorId AND e.openingDate = :date AND e.status <> :closed")
    @Modifying
    @Transactional
    int cancelExhibitionsForDate(@Param("curatorId") UUID curatorId,
                                @Param("date") LocalDate date,
                                @Param("cancelled") ExhibitionStatus cancelled,
                                @Param("closed") ExhibitionStatus closed);

    Page<Exhibition> findAll(Pageable pageable);
}
