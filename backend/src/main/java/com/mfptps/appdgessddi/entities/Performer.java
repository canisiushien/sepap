package com.mfptps.appdgessddi.entities;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

@ToString
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "performer")
@SQLDelete(sql = "UPDATE performer SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
@FilterDef(name = "deletedFilter", parameters = @ParamDef(name = "isDeleted", type = "boolean"))
@Filter(name = "deletedFilter", condition = "deleted = :isDeleted")
public class Performer extends CommonEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String libelle ;
    private double moyenneEfficacite ;
    private  double moyenneEfficience ;
    private double moyenneImpact ;
    private double moyenneGouvernance ;
    private double moyennePGS ;
    private double PGM ;

    @ManyToOne
    private Performance performance;


    public Performer() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public double getMoyenneEfficacite() {
        return moyenneEfficacite;
    }

    public void setMoyenneEfficacite(double moyenneEfficacite) {
        this.moyenneEfficacite = moyenneEfficacite;
    }

    public double getMoyenneEfficience() {
        return moyenneEfficience;
    }

    public void setMoyenneEfficience(double moyenneEfficience) {
        this.moyenneEfficience = moyenneEfficience;
    }

    public double getMoyenneImpact() {
        return moyenneImpact;
    }

    public void setMoyenneImpact(double moyenneImpact) {
        this.moyenneImpact = moyenneImpact;
    }

    public double getMoyenneGouvernance() {
        return moyenneGouvernance;
    }

    public void setMoyenneGouvernance(double moyenneGouvernance) {
        this.moyenneGouvernance = moyenneGouvernance;
    }

    public double getMoyennePGS() {
        return moyennePGS;
    }

    public void setMoyennePGS(double moyennePGS) {
        this.moyennePGS = moyennePGS;
    }

    public double getPGM() {
        return PGM;
    }

    public void setPGM(double PGM) {
        this.PGM = PGM;
    }

    public Performance getPerformance() {
        return performance;
    }

    public void setPerformance(Performance performance) {
        this.performance = performance;
    }
}
