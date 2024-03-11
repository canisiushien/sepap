/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mfptps.appdgessddi.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;

/**
 *
 * @author aboubacary
 */
@Entity
@Table(name = "contribuer", uniqueConstraints = {@UniqueConstraint(columnNames = {"paramatrer_id","structure_id"})})
@SQLDelete(sql = "UPDATE parametrer_impact SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
@FilterDef(
        name = "deletedFilter",
        parameters = @ParamDef(name = "isDeleted", type = "boolean")
)
@Filter(
        name = "deletedFilter",
        condition = "deleted = :isDeleted"
)
public class Contribuer extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) 
    private Long id;
    
    private double cible;
    
    private double valeur; // valeur atteinte par la structure
    
    @Type(type = "yes_no")
    private boolean nonapplicable;
    
    //@JsonIgnoreProperties(value = {"parent"})
    @ManyToOne
    @JoinColumn(name = "paramatrer_id", nullable = false)
    private ParametrerImpact parametrerImpact;
    
    @JsonIgnoreProperties(value = {"parent"})
    @ManyToOne
    @JoinColumn(name = "structure_id", nullable = false)
    private Structure structure;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getCible() {
        return cible;
    }

    public void setCible(double cible) {
        this.cible = cible;
    }

    public double getValeur() {
        return valeur;
    }

    public void setValeur(double valeur) {
        this.valeur = valeur;
    }

    public ParametrerImpact getParametrerImpact() {
        return parametrerImpact;
    }

    public void setParametrerImpact(ParametrerImpact parametrerImpact) {
        this.parametrerImpact = parametrerImpact;
    }

    public Structure getStructure() {
        return structure;
    }

    public void setStructure(Structure structure) {
        this.structure = structure;
    }

    public boolean isNonapplicable() {
        return nonapplicable;
    }

    public void setNonapplicable(boolean nonapplicable) {
        this.nonapplicable = nonapplicable;
    }
       
}
