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
public class ObjectifOperationnelRE implements Serializable {

    /**
     * OBJECTIF OPERATIONNEL LEVEL
     */
    
    private Long id;
    
    private String codeObjectifOp;

    private String libelleObjectifOp;

    private String structureObjectifOp;

    private List<ActiviteRE> activiteREs;

    public ObjectifOperationnelRE(Long id, String code, String libelle){
        this.id = id;
        this.codeObjectifOp = code;
        this.libelleObjectifOp = libelle;
        this.structureObjectifOp = "";
        this.activiteREs = new ArrayList<>();
    }
    
     /**
     * enlève les points du code et convertit en int
     * @return 
     */
    public Integer convertCodeToInteger(){
        return Integer.parseInt(codeObjectifOp.replace(".", ""));
    }
}
