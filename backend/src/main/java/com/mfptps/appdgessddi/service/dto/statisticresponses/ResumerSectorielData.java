/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mfptps.appdgessddi.service.dto.statisticresponses;

import java.util.List;

/**
 *
 * @author aboubacary
 */
public class ResumerSectorielData {
    
    private String libelle;
    
    private List<String> periodes;
    
    private List<Double> physiques;
    
    private List<Double> finances;

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public List<String> getPeriodes() {
        return periodes;
    }

    public void setPeriodes(List<String> periodes) {
        this.periodes = periodes;
    }

    public List<Double> getPhysiques() {
        return physiques;
    }

    public void setPhysiques(List<Double> physiques) {
        this.physiques = physiques;
    }

    public List<Double> getFinances() {
        return finances;
    }

    public void setFinances(List<Double> finances) {
        this.finances = finances;
    }
    
}
