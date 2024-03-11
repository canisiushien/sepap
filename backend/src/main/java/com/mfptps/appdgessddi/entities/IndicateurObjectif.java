package com.mfptps.appdgessddi.entities;

import com.mfptps.appdgessddi.enums.TypeIndicateurObjectif;
import com.mfptps.appdgessddi.enums.convertes.TypeIndicateurObjectifConverter;
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
@Table(name = "indicateur_objectif")
@SQLDelete(sql = "UPDATE indicateur_objectif SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
@FilterDef(
        name = "deletedFilter",
        parameters = @ParamDef(name = "isDeleted", type = "boolean")
)
@Filter(
        name = "deletedFilter",
        condition = "deleted = :isDeleted"
)
public class IndicateurObjectif extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long id;
    private String description;
    private String libelle;

    /**
     * Objectif STRATEGIQUE is linked at indicateur EFFET . Objectif
     * OPERATIONNEL is linked at indicateur IMPACT
     *
     */
    @Convert(converter = TypeIndicateurObjectifConverter.class)
    @Column(nullable = false, length = 1)
    private TypeIndicateurObjectif typeIndicateur;

    @Column(nullable = false)
    private Double valeur;

    @ManyToOne
    private Objectif objectif;

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

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public TypeIndicateurObjectif getTypeIndicateur() {
        return typeIndicateur;
    }

    public void setTypeIndicateur(TypeIndicateurObjectif typeIndicateur) {
        this.typeIndicateur = typeIndicateur;
    }

    public Double getValeur() {
        return valeur;
    }

    public void setValeur(Double valeur) {
        this.valeur = valeur;
    }

    public Objectif getObjectif() {
        return objectif;
    }

    public void setObjectif(Objectif objectif) {
        this.objectif = objectif;
    }

    @Override
    public String toString() {
        return "IndicateurObjectif [id=" + id + ", description=" + description + ", libelle=" + libelle
                + ", typeIndicateur=" + typeIndicateur + ", valeur=" + valeur + ", objectif=" + objectif
                + ", toString()=" + super.toString() + "]";
    }

}
