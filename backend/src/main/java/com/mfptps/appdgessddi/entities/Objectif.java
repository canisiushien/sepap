/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mfptps.appdgessddi.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mfptps.appdgessddi.enums.TypeObjectif;
import com.mfptps.appdgessddi.enums.convertes.TypeObjectifConverter;
import javax.persistence.*;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Entity
@Table(name = "objectif")
@SQLDelete(sql = "UPDATE objectif SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
@FilterDef(name = "deletedFilter", parameters = @ParamDef(name = "isDeleted", type = "boolean"))
@Filter(name = "deletedFilter", condition = "deleted = :isDeleted")
public class Objectif extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(nullable = false, updatable = false)
    private String code;

    @Column(nullable = false)
    private String libelle;

    @Convert(converter = TypeObjectifConverter.class)
    @Column(nullable = false, length = 1)
    private TypeObjectif type;

    @ManyToOne
    @JoinColumn(name = "objectif_id")
    private Objectif parent;//to manage SousObjectif notion

    //@JsonIgnoreProperties(value = {"objectif"})
    @ManyToOne
    private Action action;//ObjectifOperationnel to Action

    @ManyToOne
    private Programme programme;//If is it an ObjectifStrategique

//    @OneToMany
//    private Set<Action> actions;//this relationship can be move to entity Action
    public Objectif() {

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

    public TypeObjectif getType() {
        return type;
    }

    public void setType(TypeObjectif type) {
        this.type = type;
    }

    public Objectif getParent() {
        return parent;
    }

    public void setParent(Objectif parent) {
        this.parent = parent;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Programme getProgramme() {
        return programme;
    }

    public void setProgramme(Programme programme) {
        this.programme = programme;
    }

}
