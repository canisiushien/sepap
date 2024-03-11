package com.mfptps.appdgessddi.repositories;

import com.mfptps.appdgessddi.entities.Performance;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PerformanceRepository extends JpaRepository<Performance, Long> {

    @Query("SELECT p FROM Performance p WHERE p.deleted = false AND p.structure.id=?1 AND p.exercice.id =?2 ")
    Optional<Performance> findCurrentStructurePerformance(long structureId, long exerciceId);

    @Query("SELECT p.pgs FROM Performance p WHERE p.deleted = false AND p.structure.id=?1 AND p.exercice.id =?2 ")
    Optional<Double> findCurrentStructurePerformanceValue(long structureId, long exerciceId);

    @Query("SELECT p FROM Performance p, MinistereStructure ms "
            + "WHERE p.deleted = false AND p.exercice.id = :exerciceId "
            + "AND ms.ministere.id = :ministereId AND ms.statut = true "
            + "AND ms.structure.id = p.structure.id")
    Page<Performance> findAllByMinistere(long ministereId, long exerciceId, Pageable pageable);
}
