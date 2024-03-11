package com.mfptps.appdgessddi.service.dto;

import javax.validation.constraints.*;

/**
 * A DTO representing a password change required data - current and new password.
 */
public class ActivatedPassword {

    @NotBlank
    @NotNull
    @NotEmpty
    private String key;

    @NotBlank
    @NotNull
    @NotEmpty
    private String password;

    public ActivatedPassword() {
    }

    public ActivatedPassword(@NotBlank @NotNull @NotEmpty String password, @NotBlank @NotNull @NotEmpty String key) {
        this.password = password;
        this.key = key;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
