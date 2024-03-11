/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mfptps.appdgessddi.service.dto.statisticresponses;

/**
 * entité utilisée dans l'affichage des données statistiques
 * @author aboubacary
 */
public class MinistereBundleData {
    
    private String ministereCode;
    
    private String ministereLibelle;
    
    private long ministerId;
    
    private String structureCode;
    
    private String structureLibelle;
    
    private long structureId;
    
    private String periode;
    
    private String periodicite;
    
    private String dateJour;

    public String getMinistereLibelle() {
        return ministereLibelle;
    }

    public void setMinistereLibelle(String ministereLibelle) {
        this.ministereLibelle = ministereLibelle;
    }

    public long getMinisterId() {
        return ministerId;
    }

    public void setMinisterId(long ministerId) {
        this.ministerId = ministerId;
    }

    public String getStructureLibelle() {
        return structureLibelle;
    }

    public void setStructureLibelle(String structureLibelle) {
        this.structureLibelle = structureLibelle;
    }

    public long getStructureId() {
        return structureId;
    }

    public void setStructureId(long structureId) {
        this.structureId = structureId;
    }

    public String getPeriode() {
        return periode;
    }

    public void setPeriode(String periode) {
        this.periode = periode;
    }

    public String getPeriodicite() {
        return periodicite;
    }

    public void setPeriodicite(String periodicite) {
        this.periodicite = periodicite;
    }

    public String getMinistereCode() {
        return ministereCode;
    }

    public void setMinistereCode(String ministereCode) {
        this.ministereCode = ministereCode;
    }

    public String getStructureCode() {
        return structureCode;
    }

    public void setStructureCode(String structureCode) {
        this.structureCode = structureCode;
    }

    public String getDateJour() {
        return dateJour;
    }

    public void setDateJour(String dateJour) {
        this.dateJour = dateJour;
    } 
    
}
