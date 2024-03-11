/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mfptps.appdgessddi.service.reportentities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ObjectifStrategiqueRE implements Serializable {

    /**
     * OBJECTIF STRATEGIQUE LEVEL
     */
    
    private Long id;
    
    private String codeObjectifStra;

    private String libelleObjectifStra;

    private String indicateurObjectifStra;

    private String structureObjectifStra;

    private List<ActionRE> actionREs;
    
    public ObjectifStrategiqueRE(Long id, String code, String libelle, String indicateur){
        this.id = id;
        this.codeObjectifStra = code;
        this.libelleObjectifStra = libelle;
        this.indicateurObjectifStra = indicateur;
        this.structureObjectifStra = "";
        this.actionREs = new ArrayList<>();
    }
    
    /**
     * enlève les points du code et convertit en int
     * @return 
     */
    public Integer convertCodeToInteger(){
        return Integer.parseInt(codeObjectifStra.replace(".", ""));
    }
}
