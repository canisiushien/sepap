/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mfptps.appdgessddi.web;

import com.mfptps.appdgessddi.entities.ProgrammationPhysique;
import com.mfptps.appdgessddi.service.ProgrammationPhysiqueService;
import com.mfptps.appdgessddi.utils.PaginationUtil;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Slf4j
@RestController
@RequestMapping(path = "/api/programmation-physique")
public class ProgrammationPhysiqueController {

    private final ProgrammationPhysiqueService programmationPhysiqueService;

    public ProgrammationPhysiqueController(ProgrammationPhysiqueService programmationPhysiqueService) {
        this.programmationPhysiqueService = programmationPhysiqueService;
    }

    /**
     *
     * @param programmationId : id of programmation
     * @param pageable
     * @return
     */
    @GetMapping(path = "/programmation/{id}")
    public ResponseEntity<List<ProgrammationPhysique>> findAllProgrammation(@PathVariable(name = "id", required = true) Long programmationId, Pageable pageable) {
        log.debug("Consultation des periodes de la Programmation : {}", programmationId);
        Page<ProgrammationPhysique> progsPhysiques = programmationPhysiqueService.findAllByProgrammation(programmationId, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), progsPhysiques);
        return ResponseEntity.ok().headers(headers).body(progsPhysiques.getContent());
    }
}
