/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mfptps.appdgessddi.service.dto.statisticresponses;

import java.util.List;

/**
 *
 * @author aboubacary
 */
public class MinistereGlobalStatsBundleData {
    
    private ResumerDepenseData depense;
    
    private ResumerActiviteData activite;
    
    private List<ResumerStructureData> resumes;

    public ResumerDepenseData getDepense() {
        return depense;
    }

    public void setDepense(ResumerDepenseData depense) {
        this.depense = depense;
    }

    public ResumerActiviteData getActivite() {
        return activite;
    }

    public void setActivite(ResumerActiviteData activite) {
        this.activite = activite;
    }

    public List<ResumerStructureData> getResumes() {
        return resumes;
    }

    public void setResumes(List<ResumerStructureData> resumes) {
        this.resumes = resumes;
    } 
    
}
