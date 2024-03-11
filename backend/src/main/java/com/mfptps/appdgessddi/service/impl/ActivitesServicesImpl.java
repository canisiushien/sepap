package com.mfptps.appdgessddi.service.impl;

import com.mfptps.appdgessddi.entities.Activites;
import com.mfptps.appdgessddi.repositories.ActivitesRepository;
import com.mfptps.appdgessddi.service.ActivitesService;
import com.mfptps.appdgessddi.service.dto.ActivitesDTO;
import com.mfptps.appdgessddi.service.mapper.ActivitesMapper;
import com.mfptps.appdgessddi.utils.AppUtil;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ActivitesServicesImpl implements ActivitesService {

    private final ActivitesRepository activitesRepository;
    private final ActivitesMapper activiteMapper;
    
    public ActivitesServicesImpl(ActivitesRepository activitesRepositrory, ActivitesMapper activitesMapper) {        
        this.activitesRepository = activitesRepositrory;
        this.activiteMapper = activitesMapper;
    }
    
    @Override
    public Activites create(ActivitesDTO activites) {
        Activites activite = activiteMapper.toEntity(activites);
        activite.setCode(AppUtil.codeGeneratorActivite(activitesRepository));
        return activitesRepository.save(activite);
        
    }
    
    @Override
    public Activites update(Activites activite) {
        
        return activitesRepository.save(activite);
    }
    
    @Override
    public Optional<ActivitesDTO> get(Long id) {
        
        Optional<ActivitesDTO> responseMapped = activitesRepository.findById(id).map(activiteMapper::toDto);
        return responseMapped;
        
    }
    
    @Override
    public Page<ActivitesDTO> findAll(Pageable pageable) {
        Page<ActivitesDTO> responseMapped = activitesRepository.findAll(pageable).map(activiteMapper::toDto);
        return responseMapped;
    }
    
    @Override
    public void delete(Long id) {
        activitesRepository.deleteById(id);
        
    }
    
}
