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
public class DocumentDTO {

    private Long tacheId;

    private String libelle;

    private String description;

    private boolean estConfidentiel;

    //==================================
    public DocumentDTO() {
    }

    public Long getTacheId() {
        return tacheId;
    }

    public void setTacheId(Long tacheId) {
        this.tacheId = tacheId;
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

    public boolean isEstConfidentiel() {
        return estConfidentiel;
    }

    public void setEstConfidentiel(boolean estConfidentiel) {
        this.estConfidentiel = estConfidentiel;
    }

}
