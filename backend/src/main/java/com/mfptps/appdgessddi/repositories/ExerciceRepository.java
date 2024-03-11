package com.mfptps.appdgessddi.repositories;

import com.mfptps.appdgessddi.entities.Exercice;
import com.mfptps.appdgessddi.enums.ExerciceStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciceRepository extends JpaRepository<Exercice, Long> {

    Optional<Exercice> findByStatut(ExerciceStatus statut);

    Page<Exercice> findByStatut(ExerciceStatus exerciceStatus, Pageable pageable);

    Page<Exercice> findAllByOrderByDebutDesc(Pageable pageable);

    @Query("SELECT e FROM Exercice e WHERE e.id=:idExercice")
    Exercice findExerciceById(long idExercice);

    @Query("SELECT e FROM Exercice e WHERE e.statut<>:status ORDER BY e.fin DESC ")
    List<Exercice> checkXfirtsElement(ExerciceStatus status, Pageable pageable);
}
