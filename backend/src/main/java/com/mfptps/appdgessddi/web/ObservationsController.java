package com.mfptps.appdgessddi.web;

import com.mfptps.appdgessddi.entities.Observations;
import com.mfptps.appdgessddi.service.ObservationsService;
import com.mfptps.appdgessddi.service.dto.ObservationsDTO;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Slf4j
@RestController
@RequestMapping(path = "/api/observations")
public class ObservationsController {

    private static final String ENTITY_NAME = "observations";

    @Value("${application.name}")
    private String applicationName;

    private final ObservationsService observationsService;

    public ObservationsController(ObservationsService observationsService) {
        this.observationsService = observationsService;
    }

    @PostMapping
    public ResponseEntity<Observations> createObservation(@Valid @RequestBody ObservationsDTO observationsDTO) throws URISyntaxException {
        Observations observationsSaved = observationsService.create(observationsDTO);
        log.debug("Création d une observation : {}", observationsDTO);
        return ResponseEntity.created(new URI("/api/observations/" + observationsSaved.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, observationsSaved.getId().toString()))
                .body(observationsSaved);
    }

    @PutMapping
    public ResponseEntity<Observations> updateObservations(@Valid @RequestBody Observations observations) throws URISyntaxException {
        log.debug("Mis à jour d'une observation : {}", observations);
        if (observations.getId() == null) {
            throw new BadRequestAlertException("Id invalide", ENTITY_NAME, "idnull");
        }
        Observations result = observationsService.update(observations);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, observations.getId().toString()))
                .body(result);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Observations> getObservationById(@PathVariable(name = "id") Long id) {
        log.debug("Consultation d'une observation: {}", id);
        Optional<Observations> observationsFound = observationsService.get(id);
        return ResponseUtil.wrapOrNotFound(observationsFound);
    }

    @GetMapping
    public ResponseEntity<List<Observations>> findAllObservations(Pageable pageable) {
        Page<Observations> observations = observationsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), observations);
        return ResponseEntity.ok().headers(headers).body(observations.getContent());
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("Suppression d'une observation : {}", id);
        observationsService.delete(id);
        return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
                .build();
    }

}
