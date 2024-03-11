/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mfptps.appdgessddi.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;

/**
 * Records the valeur cible updates of taches during their evaluation.
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tache_evaluer")
public class TacheEvaluer extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private double valeurAtteinte;//this is valeurCibleAtteinte

    private double valeurCumulee;//sum of previous valeurAtteinte saved of some tache

    @Type(type = "yes_no")
    private boolean cumuleeActive;//mark the last row of the calculated valeurCumulee 

    //sert a comparer la periode d'evalution pour mettre à jour ou ajouter une evaluation
    //ce champ est fourni a partir de la Periode provenant de l'Evaluation
    private long idPeriode;

    @ManyToOne
    private Tache tache;//Tache referency
}
