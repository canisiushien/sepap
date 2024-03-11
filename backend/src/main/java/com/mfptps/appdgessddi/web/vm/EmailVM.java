/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mfptps.appdgessddi.web.vm;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public class EmailVM {

    @Email(message = "La valeur saisie ne ressemble pas à un email !")
    @NotBlank(message = "La valeur saisie ne ressemble pas à un email !")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
