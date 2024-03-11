/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mfptps.appdgessddi.service.dto.statisticresponses;

import com.mfptps.appdgessddi.utils.MinistereEvolutionBundle;
import java.util.List;

/**
 *
 * @author aboubacary
 */
public class AllEvolutionData {
    
    private String libelle;
    
    private List<MinistereEvolutionBundle> data;

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public List<MinistereEvolutionBundle> getData() {
        return data;
    }

    public void setData(List<MinistereEvolutionBundle> data) {
        this.data = data;
    }
    
    
}
