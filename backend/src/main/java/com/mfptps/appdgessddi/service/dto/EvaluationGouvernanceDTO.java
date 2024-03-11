package com.mfptps.appdgessddi.service.dto;

import com.mfptps.appdgessddi.entities.Exercice;
import com.mfptps.appdgessddi.entities.Structure;
import java.util.List;

public class EvaluationGouvernanceDTO {

    private Long id;
    private double valeur;//A renseigner lors de l'evaluation des structures
    private List<CritereDTO> critereGouvernances;
    private Exercice exercice;
    private Structure structure;

    //========================================
    public EvaluationGouvernanceDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getValeur() {
        return valeur;
    }

    public void setValeur(double valeur) {
        this.valeur = valeur;
    }

    public Exercice getExercice() {
        return exercice;
    }

    public void setExercice(Exercice exercice) {
        this.exercice = exercice;
    }

    public List<CritereDTO> getCritereGouvernances() {
        return critereGouvernances;
    }

    public void setCritereGouvernances(List<CritereDTO> critereGouvernances) {
        this.critereGouvernances = critereGouvernances;
    }

    public Structure getStructure() {
        return structure;
    }

    public void setStructure(Structure structure) {
        this.structure = structure;
    }

}
