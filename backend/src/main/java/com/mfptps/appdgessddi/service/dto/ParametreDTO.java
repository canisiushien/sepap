package com.mfptps.appdgessddi.service.dto;

import java.util.Date;

public class ParametreDTO {

    private Long echeance;
    private boolean verrouille;
//    private Date dateDebutExercice ;
//    private Date dateFinExercice ;
    private Date dateDebutSaisit;
    private Date dateFinSaisit;

    public ParametreDTO() {
    }

    public Long getEcheance() {
        return echeance;
    }

    public void setEcheance(Long echeance) {
        this.echeance = echeance;
    }

    public boolean isVerrouille() {
        return verrouille;
    }

    public void setVerrouille(boolean verrouille) {
        this.verrouille = verrouille;
    }

    public Date getDateDebutSaisit() {
        return dateDebutSaisit;
    }

    public void setDateDebutSaisit(Date dateDebutSaisit) {
        this.dateDebutSaisit = dateDebutSaisit;
    }

    public Date getDateFinSaisit() {
        return dateFinSaisit;
    }

    public void setDateFinSaisit(Date dateFinSaisit) {
        this.dateFinSaisit = dateFinSaisit;
    }
}
