package com.mfptps.appdgessddi.service.impl;

import com.mfptps.appdgessddi.entities.Parametre;
import com.mfptps.appdgessddi.repositories.ParametreRepository;
import com.mfptps.appdgessddi.service.ParametreService;
import com.mfptps.appdgessddi.service.dto.ParametreDTO;
import com.mfptps.appdgessddi.service.mapper.ParametreMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class ParametreServiceImpl  implements ParametreService {

  private final ParametreRepository parametreRepository ;
  private final ParametreMapper parametreMapper ;

    public ParametreServiceImpl(ParametreRepository parametreRepository, ParametreMapper parametreMapper) {
        this.parametreRepository = parametreRepository;
        this.parametreMapper = parametreMapper;
    }

    @Override
    public Parametre create(ParametreDTO parametreDTO) {
        return parametreRepository.save(parametreMapper.toEntity(parametreDTO));
    }

    @Override
    public Parametre update(Parametre parametre) {
        return parametreRepository.save(parametre);
    }

    @Override
    public Page<Parametre> findAll(Pageable pageable) {
        return parametreRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Parametre> get(Long id) {
        return parametreRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
parametreRepository.deleteById(id);
    }
}
