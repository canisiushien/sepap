/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mfptps.appdgessddi.service.dto.statisticresponses;

/**
 *
 * @author aboubacary
 */
public class ResumerStructureData {
    
    private String libelle;
    
    private String structureCode;
    
     // nombre d'activités avec un taux d'évaluation à 100%
    private long termine ;
    
    // nombre d'activités dont le taux est strcitement compris entre 0 et 100 %
    private long encours;
    
    // nombre des activités à 0%
    private long enattente;
    
    // nombre total des activités
    private long total;

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getStructureCode() {
        return structureCode;
    }

    public void setStructureCode(String structureCode) {
        this.structureCode = structureCode;
    }

    public long getTermine() {
        return termine;
    }

    public void setTermine(long termine) {
        this.termine = termine;
    }

    public long getEncours() {
        return encours;
    }

    public void setEncours(long encours) {
        this.encours = encours;
    }

    public long getEnattente() {
        return enattente;
    }

    public void setEnattente(long enattente) {
        this.enattente = enattente;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
       
}
