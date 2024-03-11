/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mfptps.appdgessddi.service.dto;

import javax.validation.constraints.NotNull;

/**
 * POJO
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public class ChangeMinistereDTO {

    @NotNull
    private Long structureId;

    @NotNull
    private Long structureParentId;

    @NotNull
    private Long ministereId;

    public ChangeMinistereDTO() {
    }

    public long getStructureId() {
        return structureId;
    }

    public void setStructureId(long structureId) {
        this.structureId = structureId;
    }

    public Long getStructureParentId() {
        return structureParentId;
    }

    public void setStructureParentId(Long structureParentId) {
        this.structureParentId = structureParentId;
    }

    public long getMinistereId() {
        return ministereId;
    }

    public void setMinistereId(long ministereId) {
        this.ministereId = ministereId;
    }

}
