package com.mfptps.appdgessddi.service.dto;

import com.mfptps.appdgessddi.entities.Profile;

public class ProfileDTO {

    private Long id;
    private String name;
    private boolean nativeProfile;

    public ProfileDTO() {
    }

    public ProfileDTO(Profile profile) {
        this.id = profile.getId();
        this.name = profile.getName();
        this.nativeProfile = profile.isNativeProfile();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isNativeProfile() {
        return nativeProfile;
    }

    public void setNativeProfile(boolean nativeProfile) {
        this.nativeProfile = nativeProfile;
    }

    @Override
    public String toString() {
        return "Profile {name=" + name + ", nativeProfile=" + nativeProfile + "}";
    }
}
