/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mfptps.appdgessddi.service.dto.statisticresponses;

/**
 *
 * @author aboubacary
 */
public class ResumerDepenseData {
    
    // libellé de la dépense (donnée facultative)
    private String libelle;
    
    // total des couts prévisionnels 
    private double previsionnel;
    
    // total des couts réels
    private double reel;

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public double getPrevisionnel() {
        return previsionnel;
    }

    public void setPrevisionnel(double previsionnel) {
        this.previsionnel = previsionnel;
    }

    public double getReel() {
        return reel;
    }

    public void setReel(double reel) {
        this.reel = reel;
    }
        
}
