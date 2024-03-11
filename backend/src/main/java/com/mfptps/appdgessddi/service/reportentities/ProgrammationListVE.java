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
public class ProgrammationListVE {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;//referenced programmationId
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
    @Column(name = "objectifid")
    private long objectifId;
    @Column(name = "projetid")
    private long projetId;
    @Column(name = "sourcefinancementid")
    private long sourceFinancementId;
    @Column(name = "structureid")
    private long structureId;
    @Column(name = "exerciceid")
    private long exerciceId;

}
