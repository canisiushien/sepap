/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mfptps.appdgessddi.web;

import com.mfptps.appdgessddi.entities.Tache;
import com.mfptps.appdgessddi.service.TacheService;
import com.mfptps.appdgessddi.web.vm.TacheVM;
import com.mfptps.appdgessddi.utils.AppUtil;
import com.mfptps.appdgessddi.utils.PaginationUtil;
import java.util.List;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Slf4j
@RestController
@RequestMapping(path = "/api/taches")
public class TacheController {

    private static final String ENTITY_NAME = "tache";

    @Value("${application.name}")
    private String applicationName;

    private final TacheService tacheService;

    public TacheController(TacheService tacheService) {
        this.tacheService = tacheService;
    }

    /**
     *
     * @param id : id of Programmation
     * @param pageable
     * @return
     */
    @GetMapping(path = "/{id}")
    public ResponseEntity<List<Tache>> findTacheByProgrammation(@PathVariable(name = "id", required = true) Long id, Pageable pageable) {
        Page<Tache> taches = tacheService.get(id, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), taches);
        return ResponseEntity.ok().headers(headers).body(taches.getContent());
    }

    /**
     *
     * @param tache : VM object (fields libelle of tache and id of
     * programmation)
     * @param pageable
     * @return
     */
    @GetMapping(path = "/libelle")
    public ResponseEntity<List<Tache>> findTacheByLibelleAndProgrammation(@Valid @RequestBody TacheVM tache, Pageable pageable) {
        Page<Tache> taches = tacheService.get(tache.getLibelle(), tache.getProgrammationId(), pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), taches);
        return ResponseEntity.ok().headers(headers).body(taches.getContent());
    }

    /**
     * Access granted to FOCAL_STRUCT, RESP_DGESS, (ADMIN)
     *
     * @param taches : list of tache
     * @return
     */
    @PostMapping
    @PreAuthorize("hasAnyAuthority(\"" + AppUtil.FS + "\", \"" + AppUtil.RD + "\", \"" + AppUtil.ADMIN + "\")")
    public ResponseEntity<List<Tache>> evaluerTaches(@RequestBody List<Tache> taches) {
        List<Tache> response = tacheService.evaluer(taches);
        return ResponseEntity.ok().body(response);
    }
}
