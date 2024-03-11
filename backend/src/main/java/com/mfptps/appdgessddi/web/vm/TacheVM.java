/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mfptps.appdgessddi.web.vm;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public class TacheVM {

    @NotNull
    @NotBlank
    private String libelle;

    @NotNull
    @NotBlank
    private Long programmationId;

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Long getProgrammationId() {
        return programmationId;
    }

    public void setProgrammationId(Long programmationId) {
        this.programmationId = programmationId;
    }

}
