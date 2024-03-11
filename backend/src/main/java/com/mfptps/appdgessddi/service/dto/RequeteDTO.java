/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mfptps.appdgessddi.service.dto;

/**
 *
 * @author aboubacary
 */
public class RequeteDTO {
    
    Long ministerId;
    
    Long structureId;
    
    Long exerciceId;
    
    Long userId;

    public Long getMinisterId() {
        return ministerId;
    }

    public void setMinisterId(Long ministerId) {
        this.ministerId = ministerId;
    }

    public Long getStructureId() {
        return structureId;
    }

    public void setStructureId(Long structureId) {
        this.structureId = structureId;
    }

    public Long getExerciceId() {
        return exerciceId;
    }

    public void setExerciceId(Long exerciceId) {
        this.exerciceId = exerciceId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
        
}
