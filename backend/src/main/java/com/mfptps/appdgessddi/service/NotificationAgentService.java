/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mfptps.appdgessddi.service;

import com.mfptps.appdgessddi.entities.NotificationAgent;
import com.mfptps.appdgessddi.service.dto.NotificationAgentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author Fatogoma HEBIE <fat19ebie@gmail.com>
 */
public interface NotificationAgentService {
    
    NotificationAgent create(NotificationAgentDTO notificationAgentDTO);

    NotificationAgent update(NotificationAgent notificationAgent);

    Page<NotificationAgent> get(String matricule,Pageable pageable);
    
    Long getNonLu(String matricule);

    Page<NotificationAgentDTO> findAll(Pageable pageable);

    void delete(Long id);
}
