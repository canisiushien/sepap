/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mfptps.appdgessddi.repositories;
 
import com.mfptps.appdgessddi.entities.ParametrerImpact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author aboubacary
 */
public interface ParametrerImpactRepository extends JpaRepository<ParametrerImpact, Long> {
    
    @Query("SELECT p FROM ParametrerImpact p, Impact im WHERE p.impact.id=im.id AND im.ministere.id=?1 AND im.inactif=false AND p.exercice.id=?2 AND p.deleted=false")
    public Page<ParametrerImpact> findExerciceImpact(long ministereId, long exerciceId, Pageable pageable);
    
    @Query("SELECT p FROM ParametrerImpact p, Impact im WHERE p.impact.id=im.id AND im.ministere.id=?1 AND im.id=?2 AND im.inactif=false AND p.deleted=false")
    public Page<ParametrerImpact> findAllMinisterImpact(long ministereId,long impactId, Pageable pageable);
}
