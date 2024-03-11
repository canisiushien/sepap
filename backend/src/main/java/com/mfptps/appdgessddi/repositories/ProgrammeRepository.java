/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mfptps.appdgessddi.repositories;

import com.mfptps.appdgessddi.entities.Programme;
import com.mfptps.appdgessddi.enums.BaseStatus;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public interface ProgrammeRepository extends JpaRepository<Programme, Long> {

    Page<Programme> findByCode(String code, Pageable pageable);//it return data where deleted = false

    Page<Programme> findByStatut(BaseStatus statut, Pageable pageable);

      
    @Query("SELECT p FROM Programme p, Objectif o, Action a "
            + "WHERE p.deleted = false "
            + "AND o.action.id = a.id AND a.id = :actionId "
            + "AND o.programme.id = p.id")
    List<Programme> findProgrammeByAction(long actionId);
}
