/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mfptps.appdgessddi.service;

import com.mfptps.appdgessddi.entities.Programmation;
import com.mfptps.appdgessddi.entities.ProgrammationPhysique;
import com.mfptps.appdgessddi.service.dto.PeriodesDTO;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public interface ProgrammationPhysiqueService {

    ProgrammationPhysique create(ProgrammationPhysique programmationPhysique);

    void addProgrammationPhysique(List<PeriodesDTO> periodes, Programmation programmation);

    Page<ProgrammationPhysique> findAllByProgrammation(Long progammationId, Pageable pageable);
}
