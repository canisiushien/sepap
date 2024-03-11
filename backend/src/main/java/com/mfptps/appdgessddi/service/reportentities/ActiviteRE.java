/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mfptps.appdgessddi.service.reportentities;

import java.io.Serializable;
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
@NoArgsConstructor
@AllArgsConstructor
public class ActiviteRE implements Serializable {

    /**
     * ACTIVITE LEVEL
     */
    private String codeActivite;

    private String libelleActivite;

    private String indicateurActivite;

    private String cibleActivite;

    private double coutPrevisionnel;

    /**
     * WHEN PERIODICITE PARAMETER IS QUARTERLY
     */
    private String t1;

    private String t2;

    private String t3;

    private String t4;

    /**
     * WHEN PERIODICITE PARAMETER IS HALF-YEARLY
     */
    private String s1;

    private String s2;

    private String financementLibelle;

    private String structureActivite;

    /**
     * WHEN CONSTRUCT OBJECT FOR RAPPORT_ACTIVITES
     */
    private String resultatsAttendus;

    private String resultatsAtteints;

    private double tauxRealisation;

    private double coutReel;

    private String observationsActivite;
    
     /**
     * enlève les points du code et convertit en int
     * @return 
     */
    public Integer convertCodeToInteger(){
        return Integer.parseInt(codeActivite.replace(".", ""));
    }
}
