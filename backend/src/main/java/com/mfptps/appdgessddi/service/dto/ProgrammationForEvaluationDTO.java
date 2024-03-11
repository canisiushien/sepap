/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mfptps.appdgessddi.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mfptps.appdgessddi.entities.Activites;
import com.mfptps.appdgessddi.entities.Exercice;
import com.mfptps.appdgessddi.entities.Objectif;
import com.mfptps.appdgessddi.entities.Projet;
import com.mfptps.appdgessddi.entities.SourceFinancement;
import com.mfptps.appdgessddi.entities.Structure;
import com.mfptps.appdgessddi.entities.Tache;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Ce DTO est construit et renvoye pour l'evaluation d'une programmation
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public class ProgrammationForEvaluationDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String code;

    private double coutPrevisionnel;

    private double coutReel;

    private boolean estPrioritaire;

    private boolean estProgramme;

    private boolean singleton;

    private double cible;

    private double taux;

    private String periodeActuelle = "";

    private String periodes = "";

    private double valeurActuelle = 0;

    private double tauxActuel = 0;

    private String resultatsAttendus;

    private String resultatsAtteints;

    private String indicateur;

    private String observations;

    private boolean validationInitial;

    private boolean validationInterne;

    private boolean validationFinal;

    private SourceFinancement sourceFinancement;

    private List<Tache> taches = new ArrayList<>();

    private Activites activite;

    private Projet projet;

    @JsonIgnoreProperties(value = {"parent"})
    private Structure structure;

    private Exercice exercice;

    @JsonIgnoreProperties(value = {"parent"})//Ne pas remonter avec l'objectif operationnel parent
    private Objectif objectif;

    //=========================================
    public ProgrammationForEvaluationDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getCoutPrevisionnel() {
        return coutPrevisionnel;
    }

    public void setCoutPrevisionnel(double coutPrevisionnel) {
        this.coutPrevisionnel = coutPrevisionnel;
    }

    public double getCoutReel() {
        return coutReel;
    }

    public void setCoutReel(double coutReel) {
        this.coutReel = coutReel;
    }

    public boolean isEstPrioritaire() {
        return estPrioritaire;
    }

    public void setEstPrioritaire(boolean estPrioritaire) {
        this.estPrioritaire = estPrioritaire;
    }

    public boolean isEstProgramme() {
        return estProgramme;
    }

    public void setEstProgramme(boolean estProgramme) {
        this.estProgramme = estProgramme;
    }

    public boolean isSingleton() {
        return singleton;
    }

    public void setSingleton(boolean singleton) {
        this.singleton = singleton;
    }

    public double getCible() {
        return cible;
    }

    public void setCible(double cible) {
        this.cible = cible;
    }

    public double getTaux() {
        return taux;
    }

    public void setTaux(double taux) {
        this.taux = taux;
    }

    public double getValeurActuelle() {
        return valeurActuelle;
    }

    public void setValeurActuelle(double valeurActuelle) {
        this.valeurActuelle = valeurActuelle;
    }

    public String getPeriodes() {
        return periodes;
    }

    public void setPeriodes(String periodes) {
        this.periodes = periodes;
    }

    public double getTauxActuel() {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        return Double.valueOf(df.format(tauxActuel).replace(",", "."));
    }

    public void setTauxActuel(double tauxActuel) {
        this.tauxActuel = tauxActuel;
    }

    public String getPeriodeActuelle() {
        return periodeActuelle;
    }

    public void setPeriodeActuelle(String periodeActuelle) {
        this.periodeActuelle = periodeActuelle;
    }

    public String getResultatsAttendus() {
        return resultatsAttendus;
    }

    public void setResultatsAttendus(String resultatsAttendus) {
        this.resultatsAttendus = resultatsAttendus;
    }

    public String getResultatsAtteints() {
        return resultatsAtteints;
    }

    public void setResultatsAtteints(String resultatsAtteints) {
        this.resultatsAtteints = resultatsAtteints;
    }

    public String getIndicateur() {
        return indicateur;
    }

    public void setIndicateur(String indicateur) {
        this.indicateur = indicateur;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public boolean isValidationInitial() {
        return validationInitial;
    }

    public void setValidationInitial(boolean validationInitial) {
        this.validationInitial = validationInitial;
    }

    public boolean isValidationInterne() {
        return validationInterne;
    }

    public void setValidationInterne(boolean validationInterne) {
        this.validationInterne = validationInterne;
    }

    public boolean isValidationFinal() {
        return validationFinal;
    }

    public void setValidationFinal(boolean validationFinal) {
        this.validationFinal = validationFinal;
    }

    public SourceFinancement getSourceFinancement() {
        return sourceFinancement;
    }

    public void setSourceFinancement(SourceFinancement sourceFinancement) {
        this.sourceFinancement = sourceFinancement;
    }

    public List<Tache> getTaches() {
        return taches;
    }

    public void setTaches(List<Tache> taches) {
        this.taches = taches;
    }

    public Activites getActivite() {
        return activite;
    }

    public void setActivite(Activites activite) {
        this.activite = activite;
    }

    public Projet getProjet() {
        return projet;
    }

    public void setProjet(Projet projet) {
        this.projet = projet;
    }

    public Structure getStructure() {
        return structure;
    }

    public void setStructure(Structure structure) {
        this.structure = structure;
    }

    public Exercice getExercice() {
        return exercice;
    }

    public void setExercice(Exercice exercice) {
        this.exercice = exercice;
    }

    public Objectif getObjectif() {
        return objectif;
    }

    public void setObjectif(Objectif objectif) {
        this.objectif = objectif;
    }

}
