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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Entity
@Table(name = "tache")
@SQLDelete(sql = "UPDATE tache SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
@FilterDef(name = "deletedFilter", parameters = @ParamDef(name = "isDeleted", type = "boolean"))
@Filter(name = "deletedFilter", condition = "deleted = :isDeleted")
public class Tache extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String libelle;

    private double ponderation;

    private double valeur = 1D;//this is valeurCible of the tache

    @Column(name = "est_execute", nullable = false)
    @Type(type = "yes_no")
    private boolean execute = false;//false means !execute.

    @Type(type = "yes_no")
    private boolean fichierJoint = false;//si fichier deja joint

    //================ relationships 
    //@JsonIgnore//to avoid an infinite loop when return Programmation object. COMMENTED 29112021 when implementing evaluerTache
    @JsonIgnoreProperties(value = {"sourceFinancement", "taches", "activite", "projet", "structure", "exercice", "objectif"})
    @ManyToOne
    private Programmation programmation;

    //================ CONSTRUCTOR && GETTERS/SETTERS 
    public Tache() {
        this.valeur = 1D;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public double getPonderation() {
        return ponderation;
    }

    public void setPonderation(double ponderation) {
        this.ponderation = ponderation;
    }

    public double getValeur() {
        return valeur;
    }

    public void setValeur(double valeur) {
        if (valeur == 0) {
            this.valeur = 1D;
        } else {
            this.valeur = valeur;
        }
    }

    public boolean isExecute() {
        return execute;
    }

    public void setExecute(boolean execute) {
        this.execute = execute;
    }

    public boolean isFichierJoint() {
        return fichierJoint;
    }

    public void setFichierJoint(boolean fichierJoint) {
        this.fichierJoint = fichierJoint;
    }

    public Programmation getProgrammation() {
        return programmation;
    }

    public void setProgrammation(Programmation programmation) {
        this.programmation = programmation;
    }

}
