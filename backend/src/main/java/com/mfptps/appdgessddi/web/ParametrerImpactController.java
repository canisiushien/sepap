/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mfptps.appdgessddi.web;

import com.mfptps.appdgessddi.entities.ParametrerImpact;
import com.mfptps.appdgessddi.service.ParametrerImpactService;
import com.mfptps.appdgessddi.utils.AppUtil;
import com.mfptps.appdgessddi.utils.HeaderUtil;
import com.mfptps.appdgessddi.utils.PaginationUtil;
import com.mfptps.appdgessddi.utils.ResponseUtil;
import com.mfptps.appdgessddi.web.exceptions.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 *
 * @author aboubacary
 */
@Slf4j
@RestController
@RequestMapping(path = "/api/parametrer")
public class ParametrerImpactController {
    
    private static final String ENTITY_NAME = "ParametrerImpact";

    @Value("${application.name}")
    private String applicationName;
    
    private final ParametrerImpactService service;
    
    public ParametrerImpactController(ParametrerImpactService service){
        this.service = service;
    }
    
    @PostMapping
    @PreAuthorize("hasAnyAuthority(\"" + AppUtil.DD + "\",\"" + AppUtil.RD + "\", \"" + AppUtil.ADMIN + "\")")
    public ResponseEntity<ParametrerImpact> create(@Valid @RequestBody ParametrerImpact data) throws URISyntaxException {
        ParametrerImpact parametre = service.create(data); 
        return ResponseEntity.created(new URI("/api/parametrer/" + parametre.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, parametre.getId().toString()))
                .body(parametre);
    }

    /**
     * Access granted to DIR_DGESS, RESP_DGESS, (ADMIN)
     *
     * @param data
     * @return
     * @throws URISyntaxException
     */
    @PutMapping
    @PreAuthorize("hasAnyAuthority(\"" + AppUtil.DD + "\",\"" + AppUtil.RD + "\", \"" + AppUtil.ADMIN + "\")")
    public ResponseEntity<ParametrerImpact> update(@Valid @RequestBody ParametrerImpact data) throws URISyntaxException {
        if (data.getId() == null) {
            throw new BadRequestAlertException("Id invalide", ENTITY_NAME, "idnull");
        }
        ParametrerImpact result = service.update(data);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    @GetMapping(path = "/get/{id}")
    public ResponseEntity<ParametrerImpact> getById(@PathVariable Long id) { 
        Optional<ParametrerImpact> result = service.get(id);
        return ResponseUtil.wrapOrNotFound(result);
    }

    @GetMapping(path = "/exercice/{ministerId}/{exerciceId}")
    public ResponseEntity<List<ParametrerImpact>> findAllByExercice(@PathVariable Long ministerId, @PathVariable Long exerciceId,Pageable pageable) {
        Page<ParametrerImpact> results = service.findExerciceParameter(ministerId, exerciceId, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), results);
        return ResponseEntity.ok().headers(headers).body(results.getContent());
    }
    
    @GetMapping(path = "/impact/{ministerId}/{impactId}")
    public ResponseEntity<List<ParametrerImpact>> findAllSpecified(@PathVariable Long ministerId, @PathVariable Long impactId,Pageable pageable) {
        Page<ParametrerImpact> results = service.findImpactParameter(ministerId, impactId, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), results);
        return ResponseEntity.ok().headers(headers).body(results.getContent());
    }

    /**
     * Access granted to ADMIN
     *
     * @param id
     * @return
     */
    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasAnyAuthority(\"" + AppUtil.ADMIN + "\")")
    public ResponseEntity<Void> delete(@PathVariable Long id) { 
        service.delete(id);
        return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
                .build();
    }
}
