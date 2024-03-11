package com.mfptps.appdgessddi.web.vm;

public class AffectationVM {
    
    private String username;
    private Long structureId;

    public AffectationVM() {
    }

    public Long getStructureId() {
        return structureId;
    }

    public void setStructureId(Long structureId) {
        this.structureId = structureId;
    }

    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
}
