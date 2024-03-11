package com.mfptps.appdgessddi.repositories;

import com.mfptps.appdgessddi.entities.Ponderation;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PonderationRepository extends JpaRepository<Ponderation,Long> {
    
    @Query("SELECT p FROM Ponderation p WHERE p.deleted = false  AND p.actif = true ")
    Optional<Ponderation> findActivePonderation();
}
