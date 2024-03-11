/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mfptps.appdgessddi.service.reportentities;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProgrammeRE implements Serializable {

    private static final long serialVersionID = 1L;

    /**
     * PROGRAMME LEVEL
     */
    private String codeProgramme;

    private String libelleProgramme;

    private String structureProgramme;

    private List<ObjectifStrategiqueRE> objectifStrategiqueREs;
    
     /**
     * enlève les points du code et convertit en int
     * @return 
     */
    public Integer convertCodeToInteger(){
        return Integer.parseInt(codeProgramme.replace(".", ""));
    }
}
