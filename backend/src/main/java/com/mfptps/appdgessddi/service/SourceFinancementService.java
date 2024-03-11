/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mfptps.appdgessddi.service;

import com.mfptps.appdgessddi.entities.SourceFinancement;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public interface SourceFinancementService {

    SourceFinancement create(SourceFinancement sourceFinancement);

    SourceFinancement update(SourceFinancement sourceFinancement);

    List<SourceFinancement> find(String libelle);

    Page<SourceFinancement> findAll(Pageable pageable);

    void delete(Long id);
}
