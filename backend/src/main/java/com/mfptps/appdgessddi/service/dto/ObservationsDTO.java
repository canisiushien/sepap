package com.mfptps.appdgessddi.service.dto;

import com.mfptps.appdgessddi.entities.Performer;

public class ObservationsDTO {

    private String libelle ;
    private String type ;
    private Performer performer ;

    public ObservationsDTO() {
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Performer getPerformer() {
        return performer;
    }

    public void setPerformer(Performer performer) {
        this.performer = performer;
    }
}
