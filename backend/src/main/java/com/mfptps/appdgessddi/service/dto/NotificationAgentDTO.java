/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mfptps.appdgessddi.service.dto;

import com.mfptps.appdgessddi.entities.Agent;
import com.mfptps.appdgessddi.entities.Notification;
import javax.persistence.Column;

/**
 *
 * @author Fatogoma HEBIE <fat19ebie@gmail.com>
 */
public class NotificationAgentDTO {
    
   private Long id;
   
   private Agent agent;
   
   private Notification notification;
   
   @Column(nullable = false) // lecture de la notification
   private boolean lu;

   public NotificationAgentDTO() {
    }

   public NotificationAgentDTO(Long id, Agent agent, Notification notification, boolean lu) {
        this.id = id;
        this.agent = agent;
        this.notification = notification;
        this.lu = lu;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public boolean isLu() {
        return lu;
    }

    public void setLu(boolean lu) {
        this.lu = lu;
    }
   
}
