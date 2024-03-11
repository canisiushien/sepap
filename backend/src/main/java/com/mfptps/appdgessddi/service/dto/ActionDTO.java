package com.mfptps.appdgessddi.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mfptps.appdgessddi.entities.Objectif;

public class ActionDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String code;
    private String libelle;
    private String description;

    private Objectif objectif;

    public ActionDTO() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public Objectif getObjectif() {
        return objectif;
    }

    public void setObjectif(Objectif objectif) {
        this.objectif = objectif;
    }
}
