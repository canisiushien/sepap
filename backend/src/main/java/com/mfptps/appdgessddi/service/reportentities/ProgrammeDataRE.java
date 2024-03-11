/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mfptps.appdgessddi.service.reportentities;

import java.io.InputStream;
import java.io.Serializable;
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
public class ProgrammeDataRE implements Serializable {

    /**
     * HEADER P.A.
     */
    private String ministereLibelle;

    private String structureParentLibelle;

    private String structureLibelle;

    private String telephone;

    private String titre;

    private InputStream logo;

    private List<ProgrammeRE> programmeREs;

//    /**
//     * PROGRAMME LEVEL
//     */
//    private String codeProgramme;
//
//    private String libelleProgramme;
//
//    private String structureProgramme;
//    /**
//     * OBJECTIF STRATEGIQUE LEVEL
//     */
//    private String codeObjectifStra;
//
//    private String libelleObjectifStra;
//
//    private String indicateurObjectifStra;
//
//    private String structureObjectifStra;
//    /**
//     * ACTION LEVEL
//     */
//    private String codeAction;
//
//    private String libelleAction;
//
//    private String structureAction;
//    /**
//     * OBJECTIF OPERATIONNEL LEVEL
//     */
//    private String codeObjectifOp;
//
//    private String libelleObjectifOp;
//
//    private String structureObjectifOp;
//    /**
//     * ACTIVITE LEVEL
//     */
//    private String codeActivite;
//
//    private String libelleActivite;
//
//    private String indicateurActivite;
//
//    private String cibleActivite;
//
//    private double coutPrevisionnel;
//
//    private String financementLibelle;
//
//    private String structureActivite;
}
