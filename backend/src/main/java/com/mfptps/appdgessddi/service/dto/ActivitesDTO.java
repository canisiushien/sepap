package com.mfptps.appdgessddi.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mfptps.appdgessddi.entities.TypeActivites;

public class ActivitesDTO {

    private Long id;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String code;
    private String description;
    private String libelle;
    private String status;
    private TypeActivites typeActivites;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypeActivites getTypeActivites() {
        return typeActivites;
    }

    public void setTypeActivites(TypeActivites typeActivites) {
        this.typeActivites = typeActivites;
    }

    public ActivitesDTO() {

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ActivitesDTO [code=" + code + ", description=" + description + ", libelle=" + libelle + "]";
    }

}
