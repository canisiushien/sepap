/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mfptps.appdgessddi.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

/**
 *
 * @author Fatogoma HEBIE <fat19ebie@gmail.com>
 */
@Entity
@Table(name = "notification_agent")
@SQLDelete(sql = "UPDATE notification_agent SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
@FilterDef(
        name = "deletedFilter",
        parameters = @ParamDef(name = "isDeleted", type = "boolean")
)
@Filter(
        name = "deletedFilter",
        condition = "deleted = :isDeleted"
)
public class NotificationAgent extends CommonEntity{
     
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long id;
   
   @ManyToOne
   @JoinColumn(name = "agent_id", nullable = false, updatable = false)
   private Agent agent;
	
   @ManyToOne
   @JoinColumn(name = "notification_id", nullable = false, updatable = false)
   private Notification notification;
	
   @Column(name = "lu", nullable = false) // lecture de la notification
   private boolean lu;

   
    public NotificationAgent() {
    }

    public NotificationAgent(Long id, Agent agent, Notification notification, boolean lu, boolean emailSent) {
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
