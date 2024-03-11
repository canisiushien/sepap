package com.mfptps.appdgessddi.web;

import com.mfptps.appdgessddi.entities.Parametre;
import com.mfptps.appdgessddi.service.ParametreService;
import com.mfptps.appdgessddi.service.dto.ParametreDTO;
import com.mfptps.appdgessddi.utils.*;
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
@RequestMapping(path = "/api/parametres")
public class ParametreController {

    private static final String ENTITY_NAME = "parametre";

    @Value("${application.name}")
    private String applicationName;
    private final ParametreService parametreService;

    public ParametreController(ParametreService parametreService) {
        this.parametreService = parametreService;
    }

    @PostMapping
    public ResponseEntity<Parametre> createParametre(@Valid @RequestBody ParametreDTO parametreDTO) throws URISyntaxException {
        AppUtil.checkDebutBeforeFin(parametreDTO.getDateDebutSaisit(), parametreDTO.getDateFinSaisit());
        Parametre parametre = parametreService.create(parametreDTO);
        log.debug("Création d un parametre : {}", parametreDTO);
        return ResponseEntity.created(new URI("/api/parametres/" + parametre.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, parametre.getId().toString()))
                .body(parametre);
    }

    @PutMapping
    public ResponseEntity<Parametre> updateParametre(@Valid @RequestBody Parametre parametre) throws URISyntaxException {
        log.debug("Mis à jour d un parametre : {}", parametre);
        if (parametre.getId() == null) {
            throw new BadRequestAlertException("Id invalide", ENTITY_NAME, "idnull");
        }
        AppUtil.checkDebutBeforeFin(parametre.getDateDebutSaisit(), parametre.getDateFinSaisit());
        Parametre result = parametreService.update(parametre);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, parametre.getId().toString()))
                .body(result);
    }

    @GetMapping
    public ResponseEntity<List<Parametre>> findAllParametres(Pageable pageable) {
        Page<Parametre> parametres = parametreService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), parametres);
        return ResponseEntity.ok().headers(headers).body(parametres.getContent());
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("Suppression d un parametre : {}", id);
        parametreService.delete(id);
        return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
                .build();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Parametre> getParametreById(@PathVariable Long id) {
        log.debug("Consultation d un parametre : {}", id);
        Optional<Parametre> parametreFound = parametreService.get(id);
        return ResponseUtil.wrapOrNotFound(parametreFound);
    }
}
