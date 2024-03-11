/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mfptps.appdgessddi.service.reportentities;

import java.io.Serializable;

/**
 *
 * @author aboubacary
 */
public class RapportPageOne implements Serializable {
    
    private String titreEvaluation; // titre annonçant l'évaluation de la performance de la structure
    
    private String texteEvaluation; // texte en bas du titre de l'évaluation de la performance
    
    private String seuilPerformance; // élement du tableau correspondant au libellé du seuil de performance
    
    private String appreciation; // élement du tableau correspondant au libellé de l'appréciation de chaque seuil de performance
    
    private String texteEfficacite; // texte descriptif de l'efficacité
    
    private String formuleEfficacite;
    
    private String resultatEfficacite;
    
    private String texteEfficience; // texte descriptif de l'efficience
    
    private String formuleEfficience;
    
    private String resultatEfficience;
    
    private String texteGouvernance; // texte descriptif de la gourvernance
    
    private String texteImpact; // // texte descriptif de l'impact

    public String getTitreEvaluation() {
        return titreEvaluation;
    }

    public void setTitreEvaluation(String titreEvaluation) {
        this.titreEvaluation = titreEvaluation;
    }

    public String getSeuilPerformance() {
        return seuilPerformance;
    }

    public void setSeuilPerformance(String seuilPerformance) {
        this.seuilPerformance = seuilPerformance;
    }

    public String getAppreciation() {
        return appreciation;
    }

    public void setAppreciation(String appreciation) {
        this.appreciation = appreciation;
    }

    public String getTexteEfficacite() {
        return texteEfficacite;
    }

    public void setTexteEfficacite(String texteEfficacite) {
        this.texteEfficacite = texteEfficacite;
    }

    public String getFormuleEfficacite() {
        return formuleEfficacite;
    }

    public void setFormuleEfficacite(String formuleEfficacite) {
        this.formuleEfficacite = formuleEfficacite;
    }

    public String getResultatEfficacite() {
        return resultatEfficacite;
    }

    public void setResultatEfficacite(String resultatEfficacite) {
        this.resultatEfficacite = resultatEfficacite;
    }

    public String getTexteEfficience() {
        return texteEfficience;
    }

    public void setTexteEfficience(String texteEfficience) {
        this.texteEfficience = texteEfficience;
    }

    public String getFormuleEfficience() {
        return formuleEfficience;
    }

    public void setFormuleEfficience(String formuleEfficience) {
        this.formuleEfficience = formuleEfficience;
    }

    public String getResultatEfficience() {
        return resultatEfficience;
    }

    public void setResultatEfficience(String resultatEfficience) {
        this.resultatEfficience = resultatEfficience;
    }

    public String getTexteGouvernance() {
        return texteGouvernance;
    }

    public void setTexteGouvernance(String texteGouvernance) {
        this.texteGouvernance = texteGouvernance;
    }

    public String getTexteImpact() {
        return texteImpact;
    }

    public void setTexteImpact(String texteImpact) {
        this.texteImpact = texteImpact;
    } 

    public String getTexteEvaluation() {
        return texteEvaluation;
    }

    public void setTextEvaluation(String texteEvaluation) {
        this.texteEvaluation = texteEvaluation;
    }
}
