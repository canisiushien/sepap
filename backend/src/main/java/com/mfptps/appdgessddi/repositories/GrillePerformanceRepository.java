package com.mfptps.appdgessddi.repositories;

import com.mfptps.appdgessddi.entities.GrillePerformance;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GrillePerformanceRepository extends JpaRepository<GrillePerformance, Long> {

    @Query("SELECT g FROM GrillePerformance g WHERE g.deleted = false  AND g.id =?1")
    Optional<GrillePerformance> findGrille(long dataId);

    @Query("SELECT g FROM GrillePerformance g WHERE g.deleted = false")
    List<GrillePerformance> findAllGrille();

    @Query("SELECT g.appreciation FROM GrillePerformance g WHERE g.deleted = false AND g.borneInferieur<=?1 AND g.borneSuperieur >=?1")
    Optional<String> findGrilleAppreciation(double value);
}
