package com.mfptps.appdgessddi.entities;

import com.mfptps.appdgessddi.enums.BaseStatus;
import com.mfptps.appdgessddi.enums.convertes.BaseStatusConverter;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;

/**
 * This is the Projet budgetaire entity
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "projet")
public class Projet extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, updatable = true)
    private String libelle;

    @Column(nullable = true, updatable = true)
    private String description;

    @Column(nullable = false, length = 1)
    @Convert(converter = BaseStatusConverter.class)
    private BaseStatus statut;

    private Date debut;

    private Date fin;

    // ================= relationships
    @ManyToOne
    @JoinColumn(nullable = false)
    private Programme programme;

    //================== CONSTRUCTORS && GETTERS/SETTERS
    public Projet() {
    }

    public Projet(String libelle, String description, BaseStatus statut, Date debut, Date fin, Programme programme) {
        this.libelle = libelle;
        this.description = description;
        this.statut = statut;
        this.debut = debut;
        this.fin = fin;
        this.programme = programme;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BaseStatus getStatut() {
        return statut;
    }

    public void setStatut(BaseStatus statut) {
        this.statut = statut;
    }

    public Date getDebut() {
        return debut;
    }

    public void setDebut(Date debut) {
        this.debut = debut;
    }

    public Date getFin() {
        return fin;
    }

    public void setFin(Date fin) {
        this.fin = fin;
    }

    public Programme getProgramme() {
        return programme;
    }

    public void setProgramme(Programme programme) {
        this.programme = programme;
    }

    @Override
    public String toString() {
        return "Projet{" + "libelle=" + libelle + ", description=" + description + ", statut=" + statut + ", debut=" + debut + ", fin=" + fin + ", programme=" + programme + '}';
    }

}
