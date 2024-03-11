/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mfptps.appdgessddi.service.reportentities;

import java.io.InputStream;
import java.io.Serializable;

/**
 *
 * @author aboubacary
 */
public class RapportGardePage implements Serializable {

    private String ministere;

    private String structureParent;

    private String structure;

    private String titre;

    private String libelleStructure;

    private InputStream logo;

    public String getMinistere() {
        return ministere;
    }

    public void setMinistere(String ministere) {
        this.ministere = ministere;
    }

    public String getStructureParent() {
        return structureParent;
    }

    public void setStructureParent(String structureParent) {
        this.structureParent = structureParent;
    }

    public String getStructure() {
        return structure;
    }

    public void setStructure(String structure) {
        this.structure = structure;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getLibelleStructure() {
        return libelleStructure;
    }

    public void setLibelleStructure(String libelleStructure) {
        this.libelleStructure = libelleStructure;
    }

    public InputStream getLogo() {
        return logo;
    }

    public void setLogo(InputStream logo) {
        this.logo = logo;
    }

}
