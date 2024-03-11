/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mfptps.appdgessddi.service.impl;

import com.mfptps.appdgessddi.entities.NotificationAgent;
import com.mfptps.appdgessddi.repositories.NotificationAgentRepository;
import com.mfptps.appdgessddi.repositories.NotificationRepository;
import com.mfptps.appdgessddi.service.NotificationAgentService;
import com.mfptps.appdgessddi.service.dto.NotificationAgentDTO;
import com.mfptps.appdgessddi.service.mapper.NotificationAgentMapper;
import com.mfptps.appdgessddi.service.mapper.NotificationMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Fatogoma HEBIE <fat19ebie@gmail.com>
 */
@Service
@Transactional
public class NotificationAgentServiceImpl implements NotificationAgentService{
    
    private final NotificationAgentRepository notificationAgentRepository;
    private final NotificationAgentMapper notificationAgentMapper;
    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    public NotificationAgentServiceImpl(NotificationAgentRepository notificationAgentRepository, NotificationAgentMapper notificationAgentMapper, NotificationRepository notificationRepository, NotificationMapper notificationMapper) {
        this.notificationAgentRepository = notificationAgentRepository;
        this.notificationAgentMapper = notificationAgentMapper;
        this.notificationRepository = notificationRepository;
        this.notificationMapper = notificationMapper;
    }
    
    @Override
    public NotificationAgent create(NotificationAgentDTO notificationAgentDTO) {
        NotificationAgent notificationAgent = notificationAgentMapper.toEntity(notificationAgentDTO);
        return notificationAgentRepository.save(notificationAgent);
        
    }
    
    @Override
    public NotificationAgent update(NotificationAgent notificationAgent) {    
        return notificationAgentRepository.save(notificationAgent);
    }
    
    @Override
    public Page<NotificationAgentDTO> findAll(Pageable pageable) {
        Page<NotificationAgentDTO> responseMapped = notificationAgentRepository.findAll(pageable).map(notificationAgentMapper::toDto);
        return responseMapped;
    }
    
    @Override
    public void delete(Long id) {
        notificationAgentRepository.deleteById(id);
        
    }

    @Override
    public Page<NotificationAgent> get(String matricule,Pageable pageable) {
        Page<NotificationAgent> responseMapped = notificationAgentRepository.findAllNotificationAgent(matricule,pageable);
        return responseMapped;
        
    }

    @Override
    public Long getNonLu(String matricule) {
       return notificationAgentRepository.findAllByLuFalse(matricule);
    }
}
