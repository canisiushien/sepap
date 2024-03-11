/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mfptps.appdgessddi.utils;

import com.mfptps.appdgessddi.entities.ProgrammationPhysique;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Getter
@Setter
public class ResponseCheckPeriode {

    private long periode;//id de la periodeActuelle

    private boolean exists = false;

    private List<ProgrammationPhysique> periodes = new ArrayList<>();
}
