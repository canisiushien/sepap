package com.mfptps.appdgessddi.service.dto;

import com.mfptps.appdgessddi.entities.Objectif;
import com.mfptps.appdgessddi.enums.TypeIndicateurObjectif;
import javax.persistence.Column;

public class IndicateurObjectifDTO {

    private String description;
    private String libelle;
    private TypeIndicateurObjectif typeIndicateur;

    @Column(nullable = false)
    private Double valeur;
    private Objectif objectif;

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

    public TypeIndicateurObjectif getTypeIndicateur() {
        return typeIndicateur;
    }

    public void setTypeIndicateur(TypeIndicateurObjectif typeIndicateur) {
        this.typeIndicateur = typeIndicateur;
    }

    public Double getValeur() {
        return valeur;
    }

    public void setValeur(Double valeur) {
        this.valeur = valeur;
    }

    public Objectif getObjectif() {
        return objectif;
    }

    public void setObjectif(Objectif objectif) {
        this.objectif = objectif;
    }

    @Override
    public String toString() {
        return "IndicateurObjectifDTO [description=" + description + ", libelle=" + libelle + ", typeIndicateur="
                + typeIndicateur + ", valeur=" + valeur + ", objectif=" + objectif + ", toString()=" + super.toString()
                + "]";
    }

}
