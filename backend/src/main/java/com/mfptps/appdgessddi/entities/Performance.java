package com.mfptps.appdgessddi.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.text.DecimalFormat;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@ToString
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "performance",
        uniqueConstraints = {
            @UniqueConstraint(name = "uniqueKeys_exercice_structure_ponderation", columnNames = {"exercice_id", "structure_id", "ponderation_id"})})
@SQLDelete(sql = "UPDATE performance SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
@FilterDef(name = "deletedFilter", parameters = @ParamDef(name = "isDeleted", type = "boolean"))
@Filter(name = "deletedFilter", condition = "deleted = :isDeleted")
public class Performance extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "efficacite")
    private double efficacite;

    @Column(name = "coefftemps")
    private double coefftemps;

    @Column(name = "tgro")
    private double tgro;

    @Column(name = "efficience")
    private double efficience;

    @Column(name = "gouvernance")
    private double gouvernance;

    @Column(name = "impact")
    private double impact;

    @Column(name = "pgs")
    private double pgs;

    @ManyToOne
    @JoinColumn(name = "exercice_id", nullable = false)
    private Exercice exercice;

    @JsonIgnoreProperties("parent")
    @ManyToOne
    @JoinColumn(name = "structure_id", nullable = false)
    private Structure structure;

    @ManyToOne
    @JoinColumn(name = "ponderation_id", nullable = false)
    private Ponderation ponderation;

    @Column(name = "appreciation", nullable = false)
    private String appreciation;

    public Performance() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getEfficacite() {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        return Double.valueOf(df.format(efficacite).replace(",", "."));
    }

    public void setEfficacite(double efficacite) {
        this.efficacite = efficacite;
    }

    public double getEfficience() {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        return Double.valueOf(df.format(efficience).replace(",", "."));
    }

    public void setEfficience(double efficience) {
        this.efficience = efficience;
    }

    public double getGouvernance() {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        return Double.valueOf(df.format(gouvernance).replace(",", "."));
    }

    public void setGouvernance(double gouvernance) {
        this.gouvernance = gouvernance;
    }

    public double getImpact() {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        return Double.valueOf(df.format(impact).replace(",", "."));
    }

    public void setImpact(double impact) {
        this.impact = impact;
    }

    public double getPgs() {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        return Double.valueOf(df.format(pgs).replace(",", "."));
    }

    public void setPgs(double pgs) {
        this.pgs = pgs;
    }

    public Exercice getExercice() {
        return exercice;
    }

    public void setExercice(Exercice exercice) {
        this.exercice = exercice;
    }

    public Structure getStructure() {
        return structure;
    }

    public void setStructure(Structure structure) {
        this.structure = structure;
    }

    public Ponderation getPonderation() {
        return ponderation;
    }

    public void setPonderation(Ponderation ponderation) {
        this.ponderation = ponderation;
    }

    public String getAppreciation() {
        return appreciation;
    }

    public void setAppreciation(String appreciation) {
        this.appreciation = appreciation;
    }

    public double getCoefftemps() {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        return Double.valueOf(df.format(coefftemps).replace(",", "."));
    }

    public void setCoefftemps(double coefftemps) {
        this.coefftemps = coefftemps;
    }

    public double getTgro() {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        return Double.valueOf(df.format(tgro).replace(",", "."));
    }

    public void setTgro(double tgro) {
        this.tgro = tgro;
    }

}
