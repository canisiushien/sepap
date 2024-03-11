/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mfptps.appdgessddi.entities;

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
@Table(name = "parametrer_impact", uniqueConstraints = {@UniqueConstraint(columnNames = {"exercice_id","impact_id"})})
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
public class ParametrerImpact extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) 
    private Long id;
    
    private double cible;
    
    private double valeurReference;
    
    private double valeurAtteinte;
    
    @ManyToOne
    @JoinColumn(name = "exercice_id", nullable = false)
    private Exercice exercice;
    
    @ManyToOne
    @JoinColumn(name = "impact_id", nullable = false)
    private Impact impact;

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

    public double getValeurReference() {
        return valeurReference;
    }

    public void setValeurReference(double valeurReference) {
        this.valeurReference = valeurReference;
    }

    public double getValeurAtteinte() {
        return valeurAtteinte;
    }

    public void setValeurAtteinte(double valeurAtteinte) {
        this.valeurAtteinte = valeurAtteinte;
    }

    public Exercice getExercice() {
        return exercice;
    }

    public void setExercice(Exercice exercice) {
        this.exercice = exercice;
    }

    public Impact getImpact() {
        return impact;
    }

    public void setImpact(Impact impact) {
        this.impact = impact;
    }   
    
}
