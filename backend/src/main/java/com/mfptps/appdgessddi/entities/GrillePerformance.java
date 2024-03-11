package com.mfptps.appdgessddi.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "grille_performance",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"BORNE_INF","BORNE_SUP", "APPRECIATION"})})
public class GrillePerformance extends CommonEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name = "BORNE_INF", nullable = false)
    private Double borneInferieur;
    
    @Column(name = "BORNE_SUP", nullable = false)
    private Double borneSuperieur;
    
    @Column(name = "APPRECIATION", nullable = false)
    private String appreciation;
    
    public GrillePerformance() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getBorneInferieur() {
        return borneInferieur;
    }

    public void setBorneInferieur(Double borneInferieur) {
        this.borneInferieur = borneInferieur;
    }

    public Double getBorneSuperieur() {
        return borneSuperieur;
    }

    public void setBorneSuperieur(Double borneSuperieur) {
        this.borneSuperieur = borneSuperieur;
    }

    public String getAppreciation() {
        return appreciation;
    }

    public void setAppreciation(String appreciation) {
        this.appreciation = appreciation;
    }

    public GrillePerformance(Double borneInferieur, Double borneSuperieur, String appreciation) {
        this.borneInferieur = borneInferieur;
        this.borneSuperieur = borneSuperieur;
        this.appreciation = appreciation;
    }
}
