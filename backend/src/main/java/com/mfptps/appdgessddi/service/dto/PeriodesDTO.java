/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mfptps.appdgessddi.service.dto;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public class PeriodesDTO {

    private String libelle;

    private boolean valeur;

    //=====================CONSTRUCTORS && GETTERS/SETTERS
    public PeriodesDTO() {
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public boolean isValeur() {
        return valeur;
    }

    public void setValeur(boolean valeur) {
        this.valeur = valeur;
    }

}
