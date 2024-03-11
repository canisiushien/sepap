/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mfptps.appdgessddi.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;

/**
 * This entity is similar to Activite
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Entity
@Table(name = "programmation")
//@SQLDelete(sql = "UPDATE programmation SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
@FilterDef(name = "deletedFilter", parameters = @ParamDef(name = "isDeleted", type = "boolean"))
@Filter(name = "deletedFilter", condition = "deleted = :isDeleted")
public class Programmation extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(nullable = false, updatable = false)
    private String code;

    @Column(name = "cout_previsionnel")
    private double coutPrevisionnel;

    @Column(name = "cout_reel")
    private double coutReel;

    @Column(nullable = false)
    @Type(type = "yes_no")
    private boolean estPrioritaire = false;

    @Column(nullable = false)
    @Type(type = "yes_no")
    private boolean estProgramme = true;//If activite is programmed

    @Column(name = "singleton", updatable = false)
    @Type(type = "yes_no")
    private boolean singleton; //If this Programmation have just one Tache

    @Column(name = "cible")
    private double cible = 1;

    @Column(name = "taux")
    private double taux;// taux exécution

    @Column(name = "last_eval_date")
    @Temporal(TemporalType.DATE)
    private Date lastEvalDate; // date de la dernière évaluation

    @Column(name = "dead_line")
    @Temporal(TemporalType.DATE)
    private Date deadLine; // date limite d'exécution des tâches de l'activités programmées

    @Column(length = 1000)
    private String resultatsAttendus;

    @Column(length = 1000)
    private String resultatsAtteints;//Renseigne uniquement lors de l'evaluation de l'activite

    @Column(length = 1000)
    private String indicateur;//equivaut a indicateurDeResultat

    @Column(length = 1000)
    private String observations;

    @Column(name = "valid_initial")
    private boolean validationInitial;//For validation RESP_STRUC

    @Column(name = "valid_interne")
    private boolean validationInterne;//For validation RESP_DGESS

    @Column(name = "valid_final")
    private boolean validationFinal;//For validation CASEM

    //============= relationships 
    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    private SourceFinancement sourceFinancement;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "programmation"/*, cascade = CascadeType.PERSIST*/)
    private List<Tache> taches;

    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    private Activites activite;

    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    private Projet projet;

    @JsonIgnoreProperties(value = {"parent"})
    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    private Structure structure;

    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    private Exercice exercice;

    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    private Objectif objectif;//ObjectifOperationel

    //============== CONSTRUCTORS && GETTERS/SETTERS
    public Programmation() {
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
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        return Double.valueOf(df.format(taux).replace(",", "."));
    }

    public void setTaux(double taux) {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        this.taux = Double.valueOf(df.format(taux).replace(",", "."));
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

    public Objectif getObjectif() {
        return objectif;
    }

    public void setObjectif(Objectif objectif) {
        this.objectif = objectif;
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

    /**
     * Previous to check if sum of taches's ponderation equals 100%
     *
     * @return
     */
    public double checkPonderation() {
        double total = this.taches.stream().map(tache -> tache.getPonderation())
                .reduce(0D, (subtotal, element) -> subtotal + element);
        return total;
    }

    /**
     * Previous to check if sum of taches's valeur equals Porgrammation.cible
     *
     * @return
     */
    public double checkValeur() {
        double total = this.taches.stream().map(tache -> tache.getValeur())
                .reduce(0D, (subtotal, element) -> subtotal + element);
        return total;
    }

    public Date getLastEvalDate() {
        return lastEvalDate;
    }

    public void setLastEvalDate(Date lastEvalDate) {
        this.lastEvalDate = lastEvalDate;
    }

    public Date getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(Date deadLine) {
        this.deadLine = deadLine;
    }

}
