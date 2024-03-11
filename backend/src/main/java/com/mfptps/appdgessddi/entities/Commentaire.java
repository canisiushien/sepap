/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mfptps.appdgessddi.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * This entity is previous to manage comment when some programme is reject by
 * DGESS or at an other level
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Entity
@Table(name = "commentaire")
public class Commentaire extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String contenu;

    //================== Relationships
    @JsonIgnoreProperties(value = {"taches", "sourceFinancement", "activite", "projet", "structure"})
    @ManyToOne
    @JoinColumn(nullable = false)
    private Programmation programmation;

    //======================= CONSTRUCTORS && GETTERS/SETTERS
    public Commentaire() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Programmation getProgrammation() {
        return programmation;
    }

    public void setProgrammation(Programmation programmation) {
        this.programmation = programmation;
    }

}
