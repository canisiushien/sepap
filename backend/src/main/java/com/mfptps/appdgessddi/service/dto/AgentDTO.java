package com.mfptps.appdgessddi.service.dto;

import com.mfptps.appdgessddi.config.Constants;
import com.mfptps.appdgessddi.entities.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.constraints.*;

/**
 * A DTO representing a agent, with his profiles.
 */
public class AgentDTO {

    private Long id;

    @NotBlank
    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1, max = 50)
    private String matricule;

    @Size(max = 50)
    private String nom;

    @Size(max = 50)
    private String prenom;

    private String telephone;

    @Email
    @Size(min = 5, max = 254)
    private String email;

    private boolean actif = false;

    private Long structureId;

    private String createdBy;

    private Instant createdDate;

    private String lastModifiedBy;

    private Instant lastModifiedDate;

    private Set<String> privileges;

    private Set<String> profiles;

    public AgentDTO() {
    }

    public AgentDTO(Agent agent) {
        this.id = agent.getId();
        this.matricule = agent.getMatricule();
        this.nom = agent.getNom();
        this.prenom = agent.getPrenom();
        this.telephone = agent.getTelephone();
        this.email = agent.getEmail();
        this.actif = agent.isActif();
        this.createdBy = agent.getCreatedBy();
        this.createdDate = agent.getCreatedDate();
        this.lastModifiedBy = agent.getLastModifiedBy();
        this.lastModifiedDate = agent.getLastModifiedDate();
        Set<Privilege> actions = new HashSet<>();
        Set<String> prof = new HashSet<>();
        agent.getProfiles().stream().forEach(r -> {
            prof.add(r.getName());
            actions.addAll(r.getPrivileges());
        });
        this.profiles = prof;
        this.privileges = actions.stream()
                .map(Privilege::getName)
                .collect(Collectors.toSet());

//        if(null != agent.getStructure() && null != agent.getStructure().getId()) {
//            this.structureId = agent.getStructure().getId();
//        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public boolean isActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Set<String> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(Set<String> privileges) {
        this.privileges = privileges;
    }

    public Set<String> getProfiles() {
        return profiles;
    }

    public void setProfiles(Set<String> profiles) {
        this.profiles = profiles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getStructureId() {
        return structureId;
    }

    public void setStructureId(Long structureId) {
        this.structureId = structureId;
    }

    @Override
    public String toString() {
        return "AgentDTO{"
                + "matricule='" + matricule + '\''
                + ", nom='" + nom + '\''
                + ", prenom='" + prenom + '\''
                + ", email='" + email + '\''
                + ", actif=" + actif
                + ", createdBy=" + createdBy
                + ", createdDate=" + createdDate
                + ", lastModifiedBy='" + lastModifiedBy + '\''
                + ", lastModifiedDate=" + lastModifiedDate
                + ", profiles=" + profiles
                + ", privileges=" + privileges
                + "}";
    }
}
