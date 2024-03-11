package com.mfptps.appdgessddi.web;

import com.mfptps.appdgessddi.entities.CritereGouvernance;
import com.mfptps.appdgessddi.service.CritereGouvernanceService;
import com.mfptps.appdgessddi.service.dto.CritereGouvernanceDTO;
import com.mfptps.appdgessddi.utils.HeaderUtil;
import com.mfptps.appdgessddi.utils.PaginationUtil;
import com.mfptps.appdgessddi.utils.ResponseUtil;
import com.mfptps.appdgessddi.web.exceptions.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(path = "/api/criteres")
public class CritereGouvernanceController {

    private final Logger log = LoggerFactory.getLogger(CritereGouvernanceController.class);

    private static final String ENTITY_NAME = "CritereGouvernance";

    @Value("${application.name}")
    private String applicationName;

    private final CritereGouvernanceService critereGouvernanceService;

    public CritereGouvernanceController(CritereGouvernanceService critereGouvernanceService) {
        this.critereGouvernanceService = critereGouvernanceService;
    }

    @PostMapping
    public ResponseEntity<CritereGouvernance> create(@Valid @RequestBody CritereGouvernanceDTO critereGouvernanceDTO) throws URISyntaxException {

        CritereGouvernance critereGouvernance = critereGouvernanceService.create(critereGouvernanceDTO);
        log.debug("Création du critere de gouvernance : {}", critereGouvernanceDTO);
        return ResponseEntity.created(new URI("/api/criteres/" + critereGouvernance.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, critereGouvernance.getId().toString()))
                .body(critereGouvernance);
    }

    @PutMapping
    public ResponseEntity<CritereGouvernance> updateCritere(@Valid @RequestBody CritereGouvernance critereGouvernance) throws URISyntaxException {
        log.debug("Mis à jour du critere gouvernance : {}", critereGouvernance);
        if (critereGouvernance.getId() == null) {
            throw new BadRequestAlertException("Id invalide", ENTITY_NAME, "idnull");
        }
        CritereGouvernance critereGouvernance1 = critereGouvernanceService.update(critereGouvernance);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, critereGouvernance.getId().toString()))
                .body(critereGouvernance1);
    }

    @GetMapping
    public ResponseEntity<List<CritereGouvernance>> findCriteres(Pageable pageable) {
        Page<CritereGouvernance> critereGouvernances = critereGouvernanceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), critereGouvernances);
        return ResponseEntity.ok().headers(headers).body(critereGouvernances.getContent());
    }

    @GetMapping("/actif")
    public ResponseEntity<List<CritereGouvernance>> findCriteresActifs(Pageable pageable) {
        Page<CritereGouvernance> critereGouvernances = critereGouvernanceService.findAllActifs(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), critereGouvernances);
        return ResponseEntity.ok().headers(headers).body(critereGouvernances.getContent());
    }

    @GetMapping(path = "/gouvernances/{id}")
    public ResponseEntity<CritereGouvernance> getCritereById(@PathVariable(name = "id") Long id) {
        log.debug("Consultation d un critere : {}", id);
        Optional<CritereGouvernance> critereGouvernance = critereGouvernanceService.get(id);
        return ResponseUtil.wrapOrNotFound(critereGouvernance);
    }
}
