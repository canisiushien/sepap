/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mfptps.appdgessddi.service.dto;

import com.mfptps.appdgessddi.entities.CritereGouvernance;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public class CritereDTO {

    private boolean nonapplicable;//Si cocher (true), cela implique que la structure exclut cet critere de sa gouvernance
    private double valeurReference;//Nullable et concerne les criteres dont le mode=false (pas par OUI/NON)
    private CritereGouvernance critereGouvernance;

    //=====================================
    public CritereDTO() {
    }

    public boolean isNonapplicable() {
        return nonapplicable;
    }

    public void setNonapplicable(boolean nonapplicable) {
        this.nonapplicable = nonapplicable;
    }

    public double getValeurReference() {
        return valeurReference;
    }

    public void setValeurReference(double valeurReference) {
        this.valeurReference = valeurReference;
    }

    public CritereGouvernance getCritereGouvernance() {
        return critereGouvernance;
    }

    public void setCritereGouvernance(CritereGouvernance critereGouvernance) {
        this.critereGouvernance = critereGouvernance;
    }

}
