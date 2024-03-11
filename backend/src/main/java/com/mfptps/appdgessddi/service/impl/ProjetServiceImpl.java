/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mfptps.appdgessddi.service.impl;

import com.mfptps.appdgessddi.entities.Projet;
import com.mfptps.appdgessddi.repositories.ProjetRepository;
import com.mfptps.appdgessddi.service.ProjetService;
import com.mfptps.appdgessddi.service.dto.ProjetDTO;
import com.mfptps.appdgessddi.service.mapper.ProjetMapper;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Slf4j
@Service
@Transactional
public class ProjetServiceImpl implements ProjetService {

    private final ProjetRepository repository;
    private final ProjetMapper projetMapper;

    public ProjetServiceImpl(ProjetRepository repository, ProjetMapper projetMapper) {
        this.repository = repository;
        this.projetMapper = projetMapper;
    }

    /**
     *
     * @param projetDTO : object Projet
     * @return
     */
    @Override
    public Projet create(ProjetDTO projetDTO) {
        Projet mapped = projetMapper.toEntity(projetDTO);
        return repository.save(mapped);
    }

    /**
     *
     * @param projet : object Projet
     * @return
     */
    @Override
    public Projet update(Projet projet) {
        return repository.save(projet);
    }

    /**
     *
     * @param id : id of Projet
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Projet> get(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Projet> get(String libelle) {
        return repository.findByLibelle(libelle);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Projet> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

}
