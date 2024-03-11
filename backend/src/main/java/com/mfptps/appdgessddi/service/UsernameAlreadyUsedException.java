package com.mfptps.appdgessddi.service;

public class UsernameAlreadyUsedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UsernameAlreadyUsedException() {
        super("Votre compte a déjà été activé, veuillez-vous connecter avec vos identifiants!");
    }
}
