/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mfptps.appdgessddi.repositories;

import com.mfptps.appdgessddi.entities.Evaluation;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {

    @Query("SELECT e.valeur FROM Evaluation e WHERE e.structure.id=?1 AND e.exercice.id=?2")
    Optional<Double> findEvaluation(long structureId, long exerciceId);
    
    @Query("SELECT e FROM Evaluation e WHERE e.deleted = false AND e.structure.id=?1 AND e.exercice.id =?2 ")
    Optional<Evaluation> findStructureEvaluation(long structureId, long exerciceId);

    @Query("SELECT e.valeur FROM Evaluation e WHERE e.deleted = false AND e.structure.id=?1 AND e.exercice.id =?2 ")
    Optional<Double> findStructureEvaluationValue(long structureId, long exerciceId);

    @Query("SELECT e FROM Evaluation e, MinistereStructure ms "
            + "WHERE e.deleted = false AND e.exercice.id = :exerciceId "
            + "AND ms.ministere.id = :ministereId AND ms.statut = true "
            + "AND ms.structure.id = e.structure.id")
    Page<Evaluation> findAllByMinistereEvaluation(long ministereId, long exerciceId, Pageable pageable);
}
