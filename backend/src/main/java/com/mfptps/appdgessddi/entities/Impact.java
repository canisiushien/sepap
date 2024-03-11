/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mfptps.appdgessddi.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

/**
 *
 * @author aboubacary
 */
@Entity
@Table(name = "impact", uniqueConstraints = {@UniqueConstraint(columnNames = {"libelle","ministere_id"})})
@SQLDelete(sql = "UPDATE impact SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
@FilterDef(
        name = "deletedFilter",
        parameters = @ParamDef(name = "isDeleted", type = "boolean")
)
@Filter(
        name = "deletedFilter",
        condition = "deleted = :isDeleted"
)
public class Impact extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) 
    private Long id;
    
    @Column(name = "libelle", nullable = false)
    private String libelle;
    
    private String description;
    
    private boolean inactif;
    
    private boolean statistique;
    
    @ManyToOne
    @JoinColumn(name = "ministere_id", nullable = false)
    private Ministere ministere;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isInactif() {
        return inactif;
    }

    public void setInactif(boolean inactif) {
        this.inactif = inactif;
    }

    public boolean isStatistique() {
        return statistique;
    }

    public void setStatistique(boolean statistique) {
        this.statistique = statistique;
    }

    public Ministere getMinistere() {
        return ministere;
    }

    public void setMinistere(Ministere ministere) {
        this.ministere = ministere;
    } 
    
}
