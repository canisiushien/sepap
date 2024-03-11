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
public class MinistereGlobalPerfData {
    
    private String libelle;
    
    private String code;
    
    private List<ResumerPerfData> data;

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<ResumerPerfData> getData() {
        return data;
    }

    public void setData(List<ResumerPerfData> data) {
        this.data = data;
    }
       
}
