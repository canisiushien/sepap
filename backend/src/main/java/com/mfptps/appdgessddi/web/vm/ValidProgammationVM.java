/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mfptps.appdgessddi.web.vm;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Getter
@Setter
public class ValidProgammationVM {

    /**
     * A FOURNIR OBLIGATOIREMENT
     */
    private Long structureId;

    //POUR LISTER: Si TRUE, retourne la liste des programmations non encore validees par RESP_STRUCT
    private boolean noValidatedBySTRUCT;

    /**
     * POUR LISTER: Si TRUE, retourne la liste des programmations validees par
     * RESP_STRUCT
     *
     *
     * POUR VALIDER: si TRUE, validation globale a faire par RESP_STRUCT.
     * validatedByDGESS et validatedByCASEM devront etre a FALSE
     */
    private boolean validatedBySTRUCT;

    /**
     * POUR LISTER: Si TRUE, retourne la liste des programmations validees par
     * RESP_DGESS
     *
     *
     * POUR VALIDER: si TRUE, validation globale a faire par RESP_DGESS.
     * validatedBySTRUCT et validatedByCASEM devront etre a FALSE
     */
    private boolean validatedByDGESS;

    /**
     * POUR LISTER: Si TRUE, retourne la liste des programmations validees par
     * CASEM
     *
     *
     * POUR VALIDER: si TRUE, validation globale a faire par CASEM (represente
     * par DGESS). validatedBySTRUCT et validatedByDGESS devront etre a FALSE
     */
    private boolean validatedByCASEM;
}
