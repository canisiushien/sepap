/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mfptps.appdgessddi.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mfptps.appdgessddi.enums.BaseStatus;
import com.mfptps.appdgessddi.enums.convertes.BaseStatusConverter;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Convert;
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
import org.hibernate.annotations.Where;

/**
 * This is the Programme budgetaire entity
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@ToString
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "programme")
@SQLDelete(sql = "UPDATE programme SET deleted = true WHERE id=?") // to manage softDeletion
@Where(clause = "deleted = false")
@FilterDef(name = "deletedFilter", parameters = @ParamDef(name = "isDeleted", type = "boolean"))
@Filter(name = "deletedFilter", condition = "deleted = :isDeleted")
public class Programme extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(nullable = false, updatable = false)
    private String code;

    @Column(nullable = false, updatable = true)
    private String libelle;

    @Column(nullable = false, length = 1)
    @Convert(converter = BaseStatusConverter.class)
    private BaseStatus statut;

    private Date debut;

    private Date fin;

    public Programme() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
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

}
