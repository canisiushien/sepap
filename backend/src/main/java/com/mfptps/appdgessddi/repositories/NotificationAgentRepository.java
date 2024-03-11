/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mfptps.appdgessddi.repositories;

import com.mfptps.appdgessddi.entities.NotificationAgent;
import com.mfptps.appdgessddi.service.dto.NotificationAgentDTO;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Fatogoma HEBIE <fat19ebie@gmail.com>
 */
public interface NotificationAgentRepository extends JpaRepository<NotificationAgent, Long>{
    
    List<NotificationAgent> findByLuFalse();
    
    @Query(value = "SELECT COUNT(n) FROM notification_agent n, agent ag "
           + " WHERE n.lu = false "
           + " AND n.agent_id = ag.id "
           + " AND ag.matricule =:matricule ",nativeQuery = true)
    Long findAllByLuFalse(String matricule);
    
    @Query("SELECT n FROM NotificationAgent n "
        + " WHERE n.agent.matricule =:matricule ")
    Page<NotificationAgent> findAllNotificationAgent(@Param("matricule") String matricule, Pageable pageable);
}
