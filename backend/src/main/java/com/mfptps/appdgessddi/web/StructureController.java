package com.mfptps.appdgessddi.web;

import com.mfptps.appdgessddi.entities.Structure;
import com.mfptps.appdgessddi.service.MinistereStructureService;
import com.mfptps.appdgessddi.service.StructureService;
import com.mfptps.appdgessddi.service.dto.ChangeMinistereDTO;
import com.mfptps.appdgessddi.service.dto.StructureDTO;
import com.mfptps.appdgessddi.utils.*;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(path = "/api")
public class StructureController {

    private final Logger log = LoggerFactory.getLogger(MinistereController.class);

    private static final String ENTITY_NAME = "structure";

    @Value("${application.name}")
    private String applicationName;
    /*

     */
    private final StructureService structureService;

    private final MinistereStructureService ministereStructureService;

    public StructureController(StructureService structureService,
            MinistereStructureService ministereStructureService) {
        this.structureService = structureService;
        this.ministereStructureService = ministereStructureService;
    }

    /**
     * Access granted to RESP_DDII, DIR_DGESS, ADMIN
     *
     * @param structure
     * @return
     * @throws URISyntaxException
     */
    @PostMapping(path = "/structures")
    @PreAuthorize("hasAnyAuthority(\"" + AppUtil.RDDII + "\",\"" + AppUtil.DD + "\", \"" + AppUtil.ADMIN + "\")")
    public ResponseEntity<Structure> create(@Valid @RequestBody StructureDTO structure) throws URISyntaxException {

        Structure structu = structureService.create(structure);
        log.debug("Création de la structure : {}", structure);
        return ResponseEntity.created(new URI("/api/structures/" + structu.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, structu.getId().toString()))
                .body(structu);
    }

    @DeleteMapping(path = "/structures/{id}")
    @PreAuthorize("hasAnyAuthority(\"" + AppUtil.RDDII + "\",\"" + AppUtil.DD + "\", \"" + AppUtil.ADMIN + "\")")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("Suppression d'une structure : {}", id);
        structureService.delete(id);
        return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
                .build();
    }

    /**
     * Access granted to RESP_DDII, DIR_DGESS, ADMIN
     *
     * @param structure
     * @return
     * @throws URISyntaxException
     */
    @PutMapping(path = "/structures")
    @PreAuthorize("hasAnyAuthority(\"" + AppUtil.RDDII + "\",\"" + AppUtil.DD + "\", \"" + AppUtil.ADMIN + "\")")
    public ResponseEntity<Structure> updateStructure(@Valid @RequestBody Structure structure) throws URISyntaxException {
        log.debug("Mis à jour d'une structure : {}", structure);
        if (structure.getId() == null) {
            throw new BadRequestAlertException("Id invalide", ENTITY_NAME, "idnull");
        }
        Structure result = structureService.update(structure);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, structure.getId().toString()))
                .body(result);
    }

    @GetMapping(path = "/structures")
    public ResponseEntity<List<StructureDTO>> findAllStructure(Pageable pageable) {
        Page<StructureDTO> structure = ministereStructureService.findAllBeta(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), structure);
        return ResponseEntity.ok().headers(headers).body(structure.getContent());
    }

    /**
     *
     * @param id : id of ministere
     * @return
     */
    @GetMapping(path = "/structures/ministere/{id}")
    public ResponseEntity<List<StructureDTO>> findAllStructureByMinistere(@PathVariable Long id, Pageable pageable) {
        Page<StructureDTO> structure = ministereStructureService.findAllStructureByMinistere(id, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), structure);
        return ResponseEntity.ok().headers(headers).body(structure.getContent());
    }

    @GetMapping(path = "/structures/{id}")
    public ResponseEntity<Structure> getStructureById(@PathVariable Long id) {
        log.debug("Consultation d une structure : {}", id);
        Optional<Structure> structureFound = structureService.get(id);
        return ResponseUtil.wrapOrNotFound(structureFound);
    }

    /**
     * Access granted to RESP_DDII, DIR_DGESS, ADMIN
     *
     * @param changeMinistereDTO
     * @return
     * @throws URISyntaxException
     */
    @PostMapping(path = "/structures/change-ministere")
    @PreAuthorize("hasAnyAuthority(\"" + AppUtil.RDDII + "\",\"" + AppUtil.DD + "\", \"" + AppUtil.ADMIN + "\")")
    public ResponseEntity<Structure> changeMinistere(@Valid @RequestBody ChangeMinistereDTO changeMinistereDTO) throws URISyntaxException {
        log.debug("Changement de ministere : {}", changeMinistereDTO);
        Structure result = structureService.changementMinistere(changeMinistereDTO);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }
}
