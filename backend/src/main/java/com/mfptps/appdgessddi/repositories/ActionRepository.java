package com.mfptps.appdgessddi.repositories;

import com.mfptps.appdgessddi.entities.Action;
import com.mfptps.appdgessddi.service.reportentities.ActionRE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface ActionRepository extends JpaRepository<Action,Long> {

    List<Action> findByLibelleContainingIgnoreCase(String libelle);
  
    
    @Query("SELECT a FROM Programmation p, Structure s, Exercice e, Objectif o, Action a "
            + "WHERE p.deleted = false "
            + "AND p.structure.id = s.id AND s.id = :structureId "
            + "AND p.exercice.id = e.id AND e.id = :exerciceId "
            + "AND p.objectif.action.id=a.id")
    List<Action> findActionsByStructureAndExercice(long structureId, long exerciceId);
    
    
//    @Query("SELECT new com.mfptps.appdgessddi.service.reportentities.ActionRE(a.id, a.code, a.libelle) "
//            + "FROM Objectif o, Action a WHERE a.objectif.id=o.id AND o.id=:objectifId AND a.deleted = false")
//    List<ActionRE> constructActionREREByObjectif(long objectifId);
}
