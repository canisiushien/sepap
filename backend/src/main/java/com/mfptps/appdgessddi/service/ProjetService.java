/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mfptps.appdgessddi.service;

import com.mfptps.appdgessddi.entities.Projet;
import com.mfptps.appdgessddi.service.dto.ProjetDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public interface ProjetService {

    Projet create(ProjetDTO projetDTO);

    Projet update(Projet projet);

    Optional<Projet> get(Long id);

    Optional<Projet> get(String libelle);

    Page<Projet> findAll(Pageable pageable);

    void delete(Long id);
}
