/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mfptps.appdgessddi.web.vm;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Getter
@Setter
public class PrintGlobalVM {

    private long ministereId;

    private Long structureId;

    private Long exerciceId;

    private long currentStructureId;

    private String format;

    private Long periodeId;

}
