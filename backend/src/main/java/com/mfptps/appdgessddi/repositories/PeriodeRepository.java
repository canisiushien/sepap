/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mfptps.appdgessddi.repositories;

import com.mfptps.appdgessddi.entities.Periode;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public interface PeriodeRepository extends JpaRepository<Periode, Long> {

    @Query("SELECT pe FROM Periode pe, Periodicite pi "
            + "WHERE pe.periodicite.id = pi.id "
            + "AND pi.actif = true "
            + "AND pi.deleted = false")
    List<Periode> findByPeriodiciteActif();

    @Query("SELECT pe FROM Periode pe, Periodicite pi "
            + "WHERE pe.periodicite.id = pi.id "
            + "AND :date BETWEEN pe.debut AND pe.fin "
            + "AND pi.actif = true "
            + "AND pi.deleted = false")
    Optional<Periode> findByDatePeriodiciteActif(@NotBlank @NotNull Date date);
}
