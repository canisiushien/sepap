/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mfptps.appdgessddi.service.reportentities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ObjectifOperationnelListVE {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;//referenced ObjectifOperationnelId
    @Column(name = "codeobjectifop")
    private String codeObjectifOp;
    @Column(name = "libelleobjectifop")
    private String libelleObjectifOp;
    @Column(name = "programmationid")
    private long programmationId;
    private String cible;
    private String code;
    @Column(name = "coutprevisionnel")
    private double coutPrevisionnel;
    @Column(name = "coutreel")
    private double coutReel;
    private String observations;
    private String taux;
    @Column(name = "activiteid")
    private long activiteId;
    @Column(name = "exerciceid")
    private long exerciceId;
    @Column(name = "projetid")
    private long projetId;
    @Column(name = "sourcefinancementid")
    private long sourceFinancementId;
    @Column(name = "structureid")
    private long structureId;
}
