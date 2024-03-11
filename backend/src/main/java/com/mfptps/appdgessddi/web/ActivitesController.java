package com.mfptps.appdgessddi.web;

import com.mfptps.appdgessddi.entities.Activites;
import com.mfptps.appdgessddi.service.ActivitesService;
import com.mfptps.appdgessddi.service.dto.ActivitesDTO;
import com.mfptps.appdgessddi.utils.*;
import com.mfptps.appdgessddi.web.exceptions.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(path = "/api/activites")
public class ActivitesController {

    private final Logger log = LoggerFactory.getLogger(ActivitesController.class);

    private static final String ENTITY_NAME = "activites";

    @Value("${application.name}")
    private String applicationName;

    private final ActivitesService activiteService;

    public ActivitesController(ActivitesService activiteService) {

        this.activiteService = activiteService;
    }

    /**
     * Access granted to DIR_DGESS, RESP_DGESS, FOCAL_STRUCT, (ADMIN)
     *
     * @param activites
     * @return
     * @throws URISyntaxException
     */
    @PostMapping
    @PreAuthorize("hasAnyAuthority(\"" + AppUtil.DD + "\",\"" + AppUtil.FS + "\",\"" + AppUtil.RD + "\", \"" + AppUtil.ADMIN + "\")")
    public ResponseEntity<Activites> create(@Valid @RequestBody ActivitesDTO activites) throws URISyntaxException {

        Activites activite = activiteService.create(activites);
        log.debug("Création de l'activité : {}", activite);
        return ResponseEntity.created(new URI("/api/activites" + activite.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, activite.getId().toString()))
                .body(activite);
    }

    /**
     * Access granted to DIR_DGESS, RESP_DGESS, FOCAL_STRUCT, (ADMIN)
     *
     * @param activites
     * @return
     * @throws URISyntaxException
     */
    @PutMapping
    @PreAuthorize("hasAnyAuthority(\"" + AppUtil.DD + "\",\"" + AppUtil.FS + "\",\"" + AppUtil.RD + "\", \"" + AppUtil.ADMIN + "\")")
    public ResponseEntity<Activites> updateActivites(@Valid @RequestBody Activites activites) throws URISyntaxException {
        log.debug("Mis à jour de l'activité : {}", activites);
        if (activites.getId() == null) {
            throw new BadRequestAlertException("Id invalide", ENTITY_NAME, "idnull");
        }
        Activites result = activiteService.update(activites);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, activites.getId().toString()))
                .body(result);
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
        log.debug("Suppression de l'activité : {}", id);
        activiteService.delete(id);
        return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
                .build();
    }

    @GetMapping
    public ResponseEntity<List<ActivitesDTO>> findAll(Pageable pageable) {
        Page<ActivitesDTO> activites = activiteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), activites);
        return ResponseEntity.ok().headers(headers).body(activites.getContent());

    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ActivitesDTO> getMinistereById(@PathVariable(name = "id") Long id) {
        log.debug("Consultation de l'activité : {}", id);
        Optional<ActivitesDTO> activitesDTO = activiteService.get(id);
        return ResponseUtil.wrapOrNotFound(activitesDTO);
    }

}
