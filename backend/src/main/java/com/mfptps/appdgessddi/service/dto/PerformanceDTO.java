package com.mfptps.appdgessddi.service.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class PerformanceDTO {

    private double efficacite;
    private double efficience;
    private double gouvernance;
    private double impact;
    private double pgs;
    private double pgm;
    private String appreciation;
    private boolean global;

    public double getEfficacite() {
        return efficacite;
    }

    public void setEfficacite(double efficacite) {
        this.efficacite = efficacite;
    }

    public double getEfficience() {
        return efficience;
    }

    public void setEfficience(double efficience) {
        this.efficience = efficience;
    }

    public double getGouvernance() {
        return gouvernance;
    }

    public void setGouvernance(double gouvernance) {
        this.gouvernance = gouvernance;
    }

    public double getImpact() {
        return impact;
    }

    public void setImpact(double impact) {
        this.impact = impact;
    }

    public double getPgs() {
        return pgs;
    }

    public void setPgs(double pgs) {
        this.pgs = pgs;
    }

    public double getPgm() {
        return pgm;
    }

    public void setPgm(double pgm) {
        this.pgm = pgm;
    }

    public String getAppreciation() {
        return appreciation;
    }

    public void setAppreciation(String appreciation) {
        this.appreciation = appreciation;
    }

    public boolean isGlobal() {
        return global;
    }

    public void setGlobal(boolean global) {
        this.global = global;
    }

}
