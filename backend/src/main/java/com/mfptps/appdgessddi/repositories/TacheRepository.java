/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mfptps.appdgessddi.repositories;

import com.mfptps.appdgessddi.entities.Tache;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public interface TacheRepository extends JpaRepository<Tache, Long> {

    /**
     *
     * @param id : programmationId referency id field of Programmation
     * @param pageable
     * @return
     */
    Page<Tache> findByDeletedFalseAndProgrammationId(Long id, Pageable pageable);

    List<Tache> findByDeletedFalseAndProgrammationId(Long id);

    /**
     *
     * @param libelle : libelle field of Tache
     * @param id : programmationId referency id field of Programmation
     * @param pageable
     * @return
     */
    @Query("SELECT t FROM Tache t, Programmation p "
            + "WHERE t.libelle =:libelle "
            + "AND t.programmation.id = p.id "
            + "AND p.id =:id "
            + "AND t.deleted = false")
    Page<Tache> findByAndLibelleAndProgrammationId(String libelle, Long id, Pageable pageable);
}
