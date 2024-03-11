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
public class TauxExecutionVM {

    /**
     * IMPORTANT A LIRE
     *
     * CAS 1 : Si besoin de taux d'un ministere, renseigner: ministereId,
     * exerciceId/periodeId. NE PAS FOURNIR structureId
     *
     * CAS 2 : Si besoin de taux d'une structure, renseigner: structureId,
     * exerciceId/periodeId. NE PAS FOURNIR ministereId
     *
     * Dans les 2 cas, exerciceId doit etre obligatoirement renseigne. NE
     * FOURNIR periodeId QUE LORSQU'ON A BESOIN DE TAUX POUR UNE PERIODE DONNEE
     */
    private Long ministereId;

    private Long structureId;

    private long exerciceId;

    private Long periodeId;
}
