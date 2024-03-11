/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mfptps.appdgessddi.service.dto;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public class CommentaireDTO {

    private Long programmationId;

    private String contenu;

    //==========================
    public CommentaireDTO() {
    }

    public Long getProgrammationId() {
        return programmationId;
    }

    public void setProgrammationId(Long programmationId) {
        this.programmationId = programmationId;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

}
