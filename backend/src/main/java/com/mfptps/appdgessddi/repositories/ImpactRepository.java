/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mfptps.appdgessddi.repositories;
 
import com.mfptps.appdgessddi.entities.Impact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author aboubacary
 */
public interface ImpactRepository extends JpaRepository<Impact, Long> {
    
    @Query("SELECT im FROM Impact im WHERE im.ministere.id=?1 AND im.statistique=?2 AND im.deleted=false")
    public Page<Impact> findMinisterImpact(long ministereId, boolean statistique, Pageable pageable);
    
    @Query("SELECT imp FROM Impact imp WHERE imp.ministere.id=?1 AND imp.deleted=false")
    public Page<Impact> findAllMinisterImpact(long ministereId, Pageable pageable);
    
}
