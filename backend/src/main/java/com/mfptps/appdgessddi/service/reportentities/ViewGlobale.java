/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mfptps.appdgessddi.service.reportentities;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
public class ViewGlobale {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;//referenced programmationId

    @Column(name = "codeprogrammation")
    private String codeProgrammation;

    @Column(name = "idactivite")
    private long idActivite;

    @Column(name = "libelleactivite")
    private String libelleActivite;

    //indicateurProgrammation 
    @Column(name = "cibleprogrammation")
    private String cibleProgrammation;

    @Column(name = "coutprevisionnel")
    private double coutPrevisionnel;

    @Column(name = "coutreel")
    private double coutReel;

    @Column(name = "resultatsattendus")
    private String resultatsAttendus;

    @Column(name = "resultatsatteints")
    private String resultatsAtteints;

    @Column(name = "indicateur")
    private String indicateur;

    @Column(name = "tauxprogrammation")
    private double tauxProgrammation;

    @Column(name = "idfinancement")
    private long idFinancement;

    @Column(name = "libellefinancement")
    private String libelleFinancement;

    @Column(name = "idobjectifope")
    private long idObjectifOpe;

    @Column(name = "codeobjectifope")
    private String codeObjectifOpe;

    @Column(name = "libelleobjectifope")
    private String libelleObjectifOpe;

    //indicateurObjectifOpe
    @Column(name = "idaction")
    private long idAction;

    @Column(name = "codeaction")
    private String codeAction;

    @Column(name = "libelleaction")
    private String libelleAction;

    @Column(name = "idobjectifstra")
    private long idObjectifStra;

    @Column(name = "codeobjectifstra")
    private String codeObjectifStra;

    @Column(name = "libelleobjectifstra")
    private String libelleObjectifStra;

    //indicateurObjectifStra
    @Column(name = "idprogramme")
    private long idProgramme;

    @Column(name = "codeprogramme")
    private String codeProgramme;

    @Column(name = "libelleprogramme")
    private String libelleProgramme;

    @Column(name = "idstructure")
    private long idStructure;

    @Column(name = "siglestructure")
    private String sigleStructure;

    @Column(name = "idministere")
    private long idMinistere;

    @Column(name = "idexercice")
    private long idExercice;
    
    // AJOUT D ATTRIBUTS
    
    @Column(name = "lastevaldate")
    @Temporal(TemporalType.DATE)
    private Date lastEvalDate; // date de la dernière évaluation

    @Column(name = "deadline")
    @Temporal(TemporalType.DATE)
    private Date deadLine; // date limite d'exécution des tâches de l'activités programmées
    
    
    /**
     * enlève les points du code et convertit en int
     * @return 
     */
    public Integer convertCodeToInteger(){
        return Integer.parseInt(codeProgrammation.replace(".", ""));
    }

}
