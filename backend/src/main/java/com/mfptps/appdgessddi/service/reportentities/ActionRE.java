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
public class ActionRE implements Serializable {

    /**
     * ACTION LEVEL
     */
    
    private Long id;
    
    private String codeAction;

    private String libelleAction;

    private String structureAction;

    private List<ObjectifOperationnelRE> objectifOperationnelREs;
    
    public ActionRE(Long id, String code, String libelle){
        this.id = id;
        this.codeAction = code;
        this.libelleAction = libelle;
        this.structureAction = "";
        this.objectifOperationnelREs = new ArrayList<>();
    }
    
    /**
    * enlève les points du code et convertit en int
    * @return 
    */
    public Integer convertCodeToInteger(){
        return Integer.parseInt(codeAction.replace(".", ""));
    }
}
