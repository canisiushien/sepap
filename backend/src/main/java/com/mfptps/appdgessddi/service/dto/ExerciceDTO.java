package com.mfptps.appdgessddi.service.dto;

import java.time.LocalDate;

public class ExerciceDTO {

    private Long id;
    private String description;
    private LocalDate debut;
    private LocalDate fin;

    public ExerciceDTO() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

}
