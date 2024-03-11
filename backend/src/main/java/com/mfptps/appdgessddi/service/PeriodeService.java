/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mfptps.appdgessddi.service;

import com.mfptps.appdgessddi.entities.Periode;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public interface PeriodeService {

    Periode create(Periode periode);

    Periode update(Periode periode);

    List<Periode> findByPeriodiciteActif();

    Optional<Periode> findByDateAndPeriodiciteActif(Date date);

}
