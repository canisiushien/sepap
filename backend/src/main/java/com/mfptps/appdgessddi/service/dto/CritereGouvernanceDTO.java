package com.mfptps.appdgessddi.service.dto;

import org.hibernate.annotations.Type;

public class CritereGouvernanceDTO {

    private Long id;
    private String indicateur ;
    private boolean mode ;
    private boolean actif;

    public CritereGouvernanceDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIndicateur() {
        return indicateur;
    }

    public void setIndicateur(String indicateur) {
        this.indicateur = indicateur;
    }

    public boolean isMode() {
        return mode;
    }

    public void setMode(boolean mode) {
        this.mode = mode;
    }

    public boolean isActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }
}
