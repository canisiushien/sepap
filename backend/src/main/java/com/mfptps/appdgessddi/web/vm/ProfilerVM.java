/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mfptps.appdgessddi.web.vm;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public class ProfilerVM {

    private String matricule;

    private Set<String> profiles = new HashSet<>();

    //=========================
    public ProfilerVM() {
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public Set<String> getProfiles() {
        return profiles;
    }

    public void setProfiles(Set<String> profiles) {
        this.profiles = profiles;
    }

}
