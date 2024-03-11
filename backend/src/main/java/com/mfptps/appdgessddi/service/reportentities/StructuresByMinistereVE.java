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
 * StructuresByMinistere VIEW ENTITY
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StructuresByMinistereVE {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;//referenced structureId
    @Column(name = "ministereid")
    private long ministereId;
    @Column(name = "libellestructure")
    private String libelleStructure;
    @Column(name = "siglestructure")
    private String sigleStructure;
    @Column(name = "niveaustructure")
    private Integer niveauStructure;
    @Column(name = "statutstructure")
    private String statutStructure;
    @Column(name = "telephonestructure")
    private String telephoneStructure;
    @Column(name = "emailrespstructure")
    private String emailRespStructure;
    @Column(name = "emailstructstructure")
    private String emailStructStructure;
    @Column(name = "parentstructure")
    private Long parentStructure;

}
