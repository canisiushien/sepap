package com.mfptps.appdgessddi.repositories;

import com.mfptps.appdgessddi.entities.EvaluationGouvernance;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EvaluationGouvernanceRepository extends JpaRepository<EvaluationGouvernance, Long> {

    @Query("SELECT eg FROM EvaluationGouvernance eg, Structure s, Exercice e "
            + "WHERE eg.deleted = false "
            + "AND eg.structure.id = s.id "
            + "AND eg.exercice.id = e.id "
            + "AND s.id=?1 "
            + "AND e.id=?2 "
            + "AND eg.nonapplicable=false")
    List<EvaluationGouvernance> findStructureEvaluation(long structureId, long exerciceId);

}
