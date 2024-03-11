package com.mfptps.appdgessddi.service.dto;

import com.mfptps.appdgessddi.entities.Ministere;
import com.mfptps.appdgessddi.entities.Structure;

public class StructureDTO {

    private Long id;
    private String libelle;
    private String sigle;
    private Integer niveau;
    private String description;
    private String type;
    private boolean active;
    private String telephone;
    private String emailResp;
    private String emailStruct;

    private Structure parent;

    private Ministere ministere;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSigle() {
        return sigle;
    }

    public void setSigle(String sigle) {
        this.sigle = sigle;
    }

    public Integer getNiveau() {
        return niveau;
    }

    public void setNiveau(Integer niveau) {
        this.niveau = niveau;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmailResp() {
        return emailResp;
    }

    public void setEmailResp(String emailResp) {
        this.emailResp = emailResp;
    }

    public String getEmailStruct() {
        return emailStruct;
    }

    public void setEmailStruct(String emailStruct) {
        this.emailStruct = emailStruct;
    }

    public Structure getParent() {
        return parent;
    }

    public void setParent(Structure parent) {
        this.parent = parent;
    }

    public Ministere getMinistere() {
        return ministere;
    }

    public void setMinistere(Ministere ministere) {
        this.ministere = ministere;
    }

}
