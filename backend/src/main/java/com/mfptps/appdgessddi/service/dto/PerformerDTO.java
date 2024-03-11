package com.mfptps.appdgessddi.service.dto;

import com.mfptps.appdgessddi.entities.Ministere;
import com.mfptps.appdgessddi.entities.Observations;
import com.mfptps.appdgessddi.entities.Performance;
import com.mfptps.appdgessddi.entities.Structure;



public class PerformerDTO {

    private String libelle ;
    private double moyenneEfficacite ;
    private  double moyenneEfficience ;
    private double moyenneImpact ;
    private double moyenneGouvernance ;
    private double moyennePGS ;
    private double PGM ;


    private Performance performance;

    public PerformerDTO() {
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
