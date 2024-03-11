/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mfptps.appdgessddi.repositories;

import com.mfptps.appdgessddi.entities.Periodicite;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public interface PeriodiciteRepository extends JpaRepository<Periodicite, Long> {

    @Query("SELECT pi FROM Periodicite pi "
            + "WHERE pi.actif = true "
            + "AND pi.deleted = false")
    Optional<Periodicite> findByActifTrue();

}
