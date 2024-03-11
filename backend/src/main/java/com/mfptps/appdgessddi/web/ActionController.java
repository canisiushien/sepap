package com.mfptps.appdgessddi.web;

import com.mfptps.appdgessddi.entities.Action;
import com.mfptps.appdgessddi.service.ActionService;
import com.mfptps.appdgessddi.service.dto.ActionDTO;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Slf4j
@RestController
@RequestMapping(path = "/api/actions")
public class ActionController {

    private static final String ENTITY_NAME = "Action";

    @Value("${application.name}")
    private String applicationName;

    private final ActionService actionService;

    public ActionController(ActionService actionService) {
        this.actionService = actionService;
    }

    /**
     * Access granted to DIR_DGESS, RESP_DGESS, (ADMIN)
     *
     * @param actionDTO
     * @return
     * @throws URISyntaxException
     */
    @PostMapping
    @PreAuthorize("hasAnyAuthority(\"" + AppUtil.DD + "\",\"" + AppUtil.RD + "\", \"" + AppUtil.ADMIN + "\")")
    public ResponseEntity<Action> createAction(@Valid @RequestBody ActionDTO actionDTO) throws URISyntaxException {
        Action action = actionService.create(actionDTO);
        log.debug("Création d une action : {}", actionDTO);
        return ResponseEntity.created(new URI("/api/actions/" + action.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, action.getId().toString()))
                .body(action);
    }

    /**
     * Access granted to DIR_DGESS, RESP_DGESS, (ADMIN)
     *
     * @param action
     * @return
     * @throws URISyntaxException
     */
    @PutMapping
    @PreAuthorize("hasAnyAuthority(\"" + AppUtil.DD + "\",\"" + AppUtil.RD + "\", \"" + AppUtil.ADMIN + "\")")
    public ResponseEntity<Action> updateAction(@Valid @RequestBody Action action) throws URISyntaxException {
        log.debug("Mis à jour d une action : {}", action);
        if (action.getId() == null) {
            throw new BadRequestAlertException("Id invalide", ENTITY_NAME, "idnull");
        }
        Action result = actionService.update(action);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, action.getId().toString()))
                .body(result);
    }

    @GetMapping(path = "/search")
    public ResponseEntity<List<Action>> getActionByLibelle(@RequestParam(required = true) String libelle) {
        log.debug("Recherche d'une action : {}", libelle);
        List<Action> foundAction = actionService.rechercheLibelle(libelle);
        return ResponseEntity.ok().body(foundAction);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Action> getActionById(@PathVariable Long id) {
        log.debug("Consultation d une action : {}", id);
        Optional<Action> actionFound = actionService.get(id);
        return ResponseUtil.wrapOrNotFound(actionFound);
    }

    @GetMapping
    public ResponseEntity<List<Action>> findAllActions(Pageable pageable) {
        Page<Action> actions = actionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), actions);
        return ResponseEntity.ok().headers(headers).body(actions.getContent());
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
        log.debug("Suppression d une action : {}", id);
        actionService.delete(id);
        return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
                .build();
    }
}
