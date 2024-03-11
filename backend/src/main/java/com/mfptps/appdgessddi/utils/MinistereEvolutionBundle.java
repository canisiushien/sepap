/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mfptps.appdgessddi.utils;

import java.util.List;

/**
 *
 * @author aboubacary
 */
public class MinistereEvolutionBundle {
    
    private String id;
    
    private String exercice;
    
    private String ministere;
    
    private String structure;
    
    private List<Double> taux;
    
    private List<String> exercices;
    
    private String appreciation;
    
    private Double moyenne;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExercice() {
        return exercice;
    }

    public void setExercice(String exercice) {
        this.exercice = exercice;
    }

    public String getMinistere() {
        return ministere;
    }

    public void setMinistere(String ministere) {
        this.ministere = ministere;
    }

    public String getStructure() {
        return structure;
    }

    public void setStructure(String structure) {
        this.structure = structure;
    } 

    public String getAppreciation() {
        return appreciation;
    }

    public void setAppreciation(String appreciation) {
        this.appreciation = appreciation;
    }

    public Double getMoyenne() {
        return moyenne;
    }

    public void setMoyenne(Double moyenne) {
        this.moyenne = moyenne;
    }

    public List<Double> getTaux() {
        return taux;
    }

    public void setTaux(List<Double> taux) {
        this.taux = taux;
    }

    public List<String> getExercices() {
        return exercices;
    }

    public void setExercices(List<String> exercices) {
        this.exercices = exercices;
    }
       
}
