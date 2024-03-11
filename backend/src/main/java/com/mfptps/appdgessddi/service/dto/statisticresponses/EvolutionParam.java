/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mfptps.appdgessddi.service.dto.statisticresponses;

/**
 *
 * @author aboubacary
 */
public class EvolutionParam {
    
    private Long ministereId;
    
    private Long structureId;
    
    private Long exerciceId;
    
    private int couverture; // nombre d'exercice à couvrir par les recherches

    public Long getMinistereId() {
        return ministereId;
    }

    public void setMinistereId(Long ministereId) {
        this.ministereId = ministereId;
    }

    public Long getStructureId() {
        return structureId;
    }

    public void setStructureId(Long structureId) {
        this.structureId = structureId;
    }

    public int getCouverture() {
        return couverture;
    }

    public void setCouverture(int couverture) {
        this.couverture = couverture;
    }

    public Long getExerciceId() {
        return exerciceId;
    }

    public void setExerciceId(Long exerciceId) {
        this.exerciceId = exerciceId;
    }
       
}
