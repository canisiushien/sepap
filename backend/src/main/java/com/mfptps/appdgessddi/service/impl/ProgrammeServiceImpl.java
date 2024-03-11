/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mfptps.appdgessddi.service.impl;

import com.mfptps.appdgessddi.entities.Programme;
import com.mfptps.appdgessddi.enums.BaseStatus;
import com.mfptps.appdgessddi.repositories.ProgrammeRepository;
import com.mfptps.appdgessddi.service.ProgrammeService;
import com.mfptps.appdgessddi.service.dto.ProgrammeDTO;
import com.mfptps.appdgessddi.service.mapper.ProgrammeMapper;
import com.mfptps.appdgessddi.utils.AppUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Service
@Transactional
public class ProgrammeServiceImpl implements ProgrammeService {

    private final ProgrammeRepository repository;

    private final ProgrammeMapper programmeMapper;

    public ProgrammeServiceImpl(ProgrammeRepository repository,
            ProgrammeMapper programmeMapper) {
        this.repository = repository;
        this.programmeMapper = programmeMapper;
    }

    @Override
    public Programme create(ProgrammeDTO programmeDTO) {
        Programme programme = programmeMapper.toEntity(programmeDTO);
        programme.setCode(AppUtil.codeGeneratorProgramme(repository));
        return repository.save(programme);
    }

    @Override
    public Programme update(Programme programme) {
        return repository.save(programme);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Programme> get(String code, Pageable pageable) {
        return repository.findByCode(code, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Programme> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Page<Programme> getENCOURS(Pageable pageable) {
        return repository.findByStatut(BaseStatus.EN_COURS, pageable);
    }

}
