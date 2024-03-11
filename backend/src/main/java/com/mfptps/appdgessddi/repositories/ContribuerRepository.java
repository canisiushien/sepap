/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mfptps.appdgessddi.repositories;

import com.mfptps.appdgessddi.entities.Contribuer;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author aboubacary
 */
public interface ContribuerRepository extends JpaRepository<Contribuer, Long> {

    @Query("SELECT c FROM Contribuer c, ParametrerImpact p, Exercice e WHERE c.structure.id=?1 AND c.parametrerImpact.id=p.id AND p.exercice.id=?2 AND e.id=p.exercice.id AND c.nonapplicable=false")
    public Page<Contribuer> findStructureContribution(long structureId, long exerciceId, Pageable pageable);

    @Query("SELECT c FROM Contribuer c, ParametrerImpact p, Exercice e, Impact imp WHERE c.parametrerImpact.id=p.id AND imp.ministere.id=?1 AND imp.id=p.impact.id AND p.exercice.id=?2 AND e.id=p.exercice.id")
    public Page<Contribuer> findExerciceContribution(long ministereId, long exerciceId, Pageable pageable);

    @Query("SELECT c FROM Contribuer c, ParametrerImpact p, Exercice e WHERE c.structure.id=?1 AND c.parametrerImpact.id=p.id AND p.exercice.id=?2 AND e.id=p.exercice.id AND c.nonapplicable=false AND p.impact.statistique=false")
    public List<Contribuer> findAllStructureContribution(long structureId, long exerciceId);

}
