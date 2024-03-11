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
public class ResumerSectorielDepenseData {
    
    private String libelle; 
    
    private double avgreel;
    
    private double avgprevisionnel;
    
    private List<String> periodes; 
    
    private List<Double> previsionnels; 
    
    private List<Double> reels; 

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public double getAvgreel() {
        return avgreel;
    }

    public void setAvgreel(double avgreel) {
        this.avgreel = avgreel;
    }

    public double getAvgprevisionnel() {
        return avgprevisionnel;
    }

    public void setAvgprevisionnel(double avgprevisionnel) {
        this.avgprevisionnel = avgprevisionnel;
    }

    public List<String> getPeriodes() {
        return periodes;
    }

    public void setPeriodes(List<String> periodes) {
        this.periodes = periodes;
    }

    public List<Double> getPrevisionnels() {
        return previsionnels;
    }

    public void setPrevisionnels(List<Double> previsionnels) {
        this.previsionnels = previsionnels;
    }

    public List<Double> getReels() {
        return reels;
    }

    public void setReels(List<Double> reels) {
        this.reels = reels;
    }
       
}
