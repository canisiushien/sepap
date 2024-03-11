package com.mfptps.appdgessddi.entities;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;

@ToString
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "parametre")
@SQLDelete(sql = "UPDATE parametre SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
@FilterDef(name = "deletedFilter", parameters = @ParamDef(name = "isDeleted", type = "boolean"))
@Filter(name = "deletedFilter", condition = "deleted = :isDeleted")
public class Parametre extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long echeance;
    @Type(type = "yes_no")
    private boolean verrouille;
//    private Date dateDebutExercice;
//    private Date dateFinExercice;
    private Date dateDebutSaisit;
    private Date dateFinSaisit;

    public Parametre() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEcheance() {
        return echeance;
    }

    public void setEcheance(Long echeance) {
        this.echeance = echeance;
    }

    public boolean isVerrouille() {
        return verrouille;
    }

    public void setVerrouille(boolean verrouille) {
        this.verrouille = verrouille;
    }

    public Date getDateDebutSaisit() {
        return dateDebutSaisit;
    }

    public void setDateDebutSaisit(Date dateDebutSaisit) {
        this.dateDebutSaisit = dateDebutSaisit;
    }

    public Date getDateFinSaisit() {
        return dateFinSaisit;
    }

    public void setDateFinSaisit(Date dateFinSaisit) {
        this.dateFinSaisit = dateFinSaisit;
    }
}
