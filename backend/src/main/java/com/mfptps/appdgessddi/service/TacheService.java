/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mfptps.appdgessddi.service;

import com.mfptps.appdgessddi.entities.Tache;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public interface TacheService {

    /**
     * Do not modify ponderation field
     *
     * @param tache
     * @return
     */
    Tache update(Tache tache);

    /**
     * THIS FUNCTION ACTS AS DELETION AND UPDATE. Here, ponderations fields can
     * be update after checking these sum 100%
     *
     * @param taches
     * @return
     */
    List<Tache> update(List<Tache> taches);

    /**
     * Task evaluation: only the execute and valeur fields can be modified. The
     * pondeation field is calculated automatically after updating the valeur
     * field
     *
     * @param taches
     * @return
     */
    List<Tache> evaluer(List<Tache> taches);

    /**
     * return a Tache (by libelle) not deleted of a Programmation
     *
     * @param libelle
     * @param programmationId
     * @param pageable
     * @return
     */
    Page<Tache> get(String libelle, Long programmationId, Pageable pageable);

    /**
     * return all Taches (not deleted) of a Programmation
     *
     * @param programmationId
     * @param pageable
     * @return
     */
    Page<Tache> get(Long programmationId, Pageable pageable);

    List<Tache> get(Long programmationId);

}
