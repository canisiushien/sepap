package com.mfptps.appdgessddi.web;

import com.mfptps.appdgessddi.entities.TypeActivites;
import com.mfptps.appdgessddi.service.TypeActivitesService;
import com.mfptps.appdgessddi.service.dto.TypeActiviteDTO;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(path = "/api/activites/type")
public class TypeActiviteController {

    private final Logger log = LoggerFactory.getLogger(TypeActiviteController.class);

    private static final String ENTITY_NAME = "typeActivite";

    @Value("${application.name}")
    private String applicationName;

    private final TypeActivitesService typeActiviteService;

    public TypeActiviteController(TypeActivitesService typeActiviteService) {
        this.typeActiviteService = typeActiviteService;
    }

    /**
     * Access granted to ADMIN
     *
     * @param typeActivite
     * @return
     * @throws URISyntaxException
     */
    @PostMapping
    @PreAuthorize("hasAnyAuthority(\"" + AppUtil.ADMIN + "\")")
    public ResponseEntity<TypeActivites> create(@Valid @RequestBody TypeActiviteDTO typeActivite) throws URISyntaxException {

        TypeActivites type = typeActiviteService.create(typeActivite);
        log.debug("Création du Type d'activité : {}", typeActivite);
        return ResponseEntity.created(new URI("/api/ministeres/" + type.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, type.getId().toString()))
                .body(type);
    }

    /**
     * Access granted to ADMIN
     *
     * @param typeActivites
     * @return
     * @throws URISyntaxException
     */
    @PutMapping
    @PreAuthorize("hasAnyAuthority(\"" + AppUtil.ADMIN + "\")")
    public ResponseEntity<TypeActivites> updateTypeActivites(@Valid @RequestBody TypeActivites typeActivites) throws URISyntaxException {
        log.debug("Mis à jour du type de l'activité : {}", typeActivites);
        if (typeActivites.getId() == null) {
            throw new BadRequestAlertException("Id invalide", ENTITY_NAME, "idnull");
        }

        TypeActivites result = typeActiviteService.update(typeActivites);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, typeActivites.getId().toString()))
                .body(result);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<TypeActivites> getTypeActivites(@PathVariable(name = "id") Long id) {
        log.debug("Consultation du Type d'activités : {}", id);
        Optional<TypeActivites> typeActivites = typeActiviteService.get(id);
        return ResponseUtil.wrapOrNotFound(typeActivites);
    }

    @GetMapping
    public ResponseEntity<List<TypeActivites>> findAllType(Pageable pageable) {
        Page<TypeActivites> typeActivite = typeActiviteService.findPage(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), typeActivite);
        return ResponseEntity.ok().headers(headers).body(typeActivite.getContent());
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
        log.debug("Suppression du Ministere : {}", id);
        typeActiviteService.delete(id);
        return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
                .build();
    }

}
