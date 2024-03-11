package com.mfptps.appdgessddi.web;

import com.mfptps.appdgessddi.entities.Exercice;
import com.mfptps.appdgessddi.enums.ExerciceStatus;
import com.mfptps.appdgessddi.service.ExerciceService;
import com.mfptps.appdgessddi.service.dto.ExerciceDTO;
import com.mfptps.appdgessddi.utils.AppUtil;
import com.mfptps.appdgessddi.utils.HeaderUtil;
import com.mfptps.appdgessddi.utils.PaginationUtil;
import com.mfptps.appdgessddi.utils.ResponseUtil;
import com.mfptps.appdgessddi.web.exceptions.BadRequestAlertException;
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
@RequestMapping(path = "/api/exercices")
public class ExerciceController {

    private static final String ENTITY_NAME = "Exercice";

    @Value("${application.name}")
    private String applicationName;
    /*
     */
    private final ExerciceService exerciceService;

    public ExerciceController(ExerciceService exerciceService) {
        this.exerciceService = exerciceService;
    }

//    @PostMapping
//    public ResponseEntity<Exercice> createExercice(@Valid @RequestBody ExerciceDTO exerciceDTO) throws URISyntaxException {
//        Exercice exercice = exerciceService.create(exerciceDTO);
//        log.debug("Création d'une exercice : {}", exerciceDTO);
//        return ResponseEntity.created(new URI("/api/exercices/" + exercice.getId()))
//                .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, exercice.getId().toString()))
//                .body(exercice);
//    }
    /**
     * Access granted to RESP_DGESS, (ADMIN)
     *
     * @param exercice
     * @return
     * @throws URISyntaxException
     */
    @PutMapping
    @PreAuthorize("hasAnyAuthority(\"" + AppUtil.RD + "\", \"" + AppUtil.ADMIN + "\")")
    public ResponseEntity<Exercice> updateExercice(@Valid @RequestBody ExerciceDTO exercice) throws URISyntaxException {
        log.debug("Mis à jour d un exercice : {}", exercice);
        if (exercice.getId() == null) {
            throw new BadRequestAlertException("Id invalide", ENTITY_NAME, "idnull");
        }
        Exercice resultE = exerciceService.update(exercice);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, exercice.getId().toString()))
                .body(resultE);
    }

    /**
     * Access granted to RESP_DGESS, (ADMIN)
     *
     * @return
     */
    @GetMapping(path = "/cloture")
    @PreAuthorize("hasAnyAuthority(\"" + AppUtil.RD + "\", \"" + AppUtil.ADMIN + "\")")
    public ResponseEntity<Void> cloture() {
        log.debug("Cloture de l'exercice en cours : {}");
        exerciceService.cloture();
        HttpHeaders headers = new HttpHeaders();
        headers.add("MESSAGE", "Exercice cloturé");
        return ResponseEntity.noContent().headers(headers).build();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Exercice> getExerciceById(@PathVariable Long id) {
        log.debug("Consultation d un exercice : {}", id);
        Optional<Exercice> actionFound = exerciceService.get(id);
        return ResponseUtil.wrapOrNotFound(actionFound);
    }

    /**
     *
     * @return
     */
    @GetMapping(path = "/statut/attente")
    public ResponseEntity<Exercice> getExerciceENATTENTE() {
        log.debug("Consultation de l'exercice en attente :");
        Optional<Exercice> response = exerciceService.getByStatutAttente();
        return ResponseUtil.wrapOrNotFound(response);
    }

    /**
     *
     * @param statut
     * @param pageable
     * @return
     */
    @GetMapping(path = "/statut/{statut}")
    public ResponseEntity<List<Exercice>> findExerciceByStatut(@PathVariable(name = "statut", required = true) String statut, Pageable pageable) {
        Page<Exercice> exercices = exerciceService.findByStatut(ExerciceStatus.valueOf(statut), pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), exercices);
        return ResponseEntity.ok().headers(headers).body(exercices.getContent());
    }

    @GetMapping
    public ResponseEntity<List<Exercice>> findAllExercice(Pageable pageable) {
        Page<Exercice> exercices = exerciceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), exercices);
        return ResponseEntity.ok().headers(headers).body(exercices.getContent());
    }
//
//    @DeleteMapping(path = "/{id}")
//    public ResponseEntity<Void> delete(@PathVariable Long id) {
//        log.debug("Suppression d un exercice : {}", id);
//        exerciceService.delete(id);
//        return ResponseEntity
//                .noContent()
//                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
//                .build();
//    }
}
