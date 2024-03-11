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
public class TacheDTO {

    private String libelle;

    private double ponderation;

    private Long programmation;

    public TacheDTO() {
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

    public Long getProgrammation() {
        return programmation;
    }

    public void setProgrammation(Long programmation) {
        this.programmation = programmation;
    }

}
