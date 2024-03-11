/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mfptps.appdgessddi.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mfptps.appdgessddi.entities.Activites;
import com.mfptps.appdgessddi.entities.Objectif;
import com.mfptps.appdgessddi.entities.Projet;
import com.mfptps.appdgessddi.entities.SourceFinancement;
import com.mfptps.appdgessddi.entities.Structure;
import com.mfptps.appdgessddi.entities.Tache;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public class ProgrammationDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String code;

    private double coutPrevisionnel;

    private double coutReel;//Ne pas renseigner lors de la programmation

    private double cible;

    private String resultatsAttendus;

    private String resultatsAtteints;//Ne pas renseigner lors de la programmation

    private String indicateur;

    private String observations;

    private boolean estPrioritaire;

    private boolean singleton;

    private Structure structure;

    private Projet projet;

    private SourceFinancement sourceFinancement;

    private Activites activite;

    private Objectif objectif;//ObjectifOPeationel

    private List<Tache> taches = new ArrayList<>();

    /**
     * ex: [{"libelle":"T1", "valeur"=true}, {"libelle":"T2", "valeur"=false},
     * {"libelle":"T3", "valeur"=true}, {"libelle":"T4", "valeur"=false}]
     */
    private List<PeriodesDTO> periodes = new ArrayList<>();

    //======================= CONSTRUCTORS && GETTERS/SETTERS
    public ProgrammationDTO() {
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

    public double getCible() {
        return cible;
    }

    public void setCible(double cible) {
        this.cible = cible;
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

    public boolean isEstPrioritaire() {
        return estPrioritaire;
    }

    public void setEstPrioritaire(boolean estPrioritaire) {
        this.estPrioritaire = estPrioritaire;
    }

    public boolean isSingleton() {
        return singleton;
    }

    public void setSingleton(boolean singleton) {
        this.singleton = singleton;
    }

    public Activites getActivite() {
        return activite;
    }

    public void setActivite(Activites activite) {
        this.activite = activite;
    }

    public Structure getStructure() {
        return structure;
    }

    public void setStructure(Structure structure) {
        this.structure = structure;
    }

    public Projet getProjet() {
        return projet;
    }

    public void setProjet(Projet projet) {
        this.projet = projet;
    }

    public SourceFinancement getSourceFinancement() {
        return sourceFinancement;
    }

    public void setSourceFinancement(SourceFinancement sourceFinancement) {
        this.sourceFinancement = sourceFinancement;
    }

    public Objectif getObjectif() {
        return objectif;
    }

    public void setObjectif(Objectif objectif) {
        this.objectif = objectif;
    }

    public List<Tache> getTaches() {
        return taches;
    }

    public void setTaches(List<Tache> taches) {
        this.taches = taches;
    }

    public List<PeriodesDTO> getPeriodes() {
        return periodes;
    }

    public void setPeriodes(List<PeriodesDTO> periodes) {
        this.periodes = periodes;
    }

}
