package com.mfptps.appdgessddi.entities;

import com.mfptps.appdgessddi.enums.ExerciceStatus;
import com.mfptps.appdgessddi.enums.convertes.ExerciceStatusConverter;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "exercice")
@SQLDelete(sql = "UPDATE exercice SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
@FilterDef(name = "deletedFilter", parameters = @ParamDef(name = "isDeleted", type = "boolean"))
@Filter(name = "deletedFilter", condition = "deleted = :isDeleted")
public class Exercice extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String description;

    @Convert(converter = ExerciceStatusConverter.class)
    @Column(nullable = false, length = 1)
    private ExerciceStatus statut;

    @Column(nullable = false)
    private LocalDate debut;

    @Column(nullable = false)
    private LocalDate fin;

    @ManyToOne
    private Observations observation;

    @ManyToOne
    private Ponderation ponderation;
//===============================

    public Exercice() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ExerciceStatus getStatut() {
        return statut;
    }

    public void setStatut(ExerciceStatus statut) {
        this.statut = statut;
    }

    public LocalDate getDebut() {
        return debut;
    }

    public void setDebut(LocalDate debut) {
        this.debut = debut;
    }

    public LocalDate getFin() {
        return fin;
    }

    public void setFin(LocalDate fin) {
        this.fin = fin;
    }

    public Observations getObservation() {
        return observation;
    }

    public void setObservations(Observations observation) {
        this.observation = observation;
    }

    public Ponderation getPonderation() {
        return ponderation;
    }

    public void setPonderation(Ponderation ponderation) {
        this.ponderation = ponderation;
    }

}
