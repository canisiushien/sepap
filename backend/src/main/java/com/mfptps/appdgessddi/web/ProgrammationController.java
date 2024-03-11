/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mfptps.appdgessddi.web;

import com.mfptps.appdgessddi.entities.Programmation;
import com.mfptps.appdgessddi.service.ProgrammationService;
import com.mfptps.appdgessddi.service.dto.CommentaireDTO;
import com.mfptps.appdgessddi.service.dto.ProgrammationDTO;
import com.mfptps.appdgessddi.service.dto.ProgrammationForEvaluationDTO;
import com.mfptps.appdgessddi.utils.*;
import com.mfptps.appdgessddi.web.exceptions.BadRequestAlertException;
import com.mfptps.appdgessddi.web.vm.PrintGlobalVM;
import com.mfptps.appdgessddi.web.vm.TauxExecutionVM;
import com.mfptps.appdgessddi.web.vm.ValidProgammationVM;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Slf4j
@RestController
@RequestMapping(path = "/api/programmations")
public class ProgrammationController {

    private static final String ENTITY_NAME = "programmation";

    @Value("${application.name}")
    private String applicationName;

    private final ProgrammationService programmationService;

    public ProgrammationController(ProgrammationService programmationService) {
        this.programmationService = programmationService;
    }

    /**
     * Access granted to FOCAL_STRUCT, RESP_STRUCT, RESP_DGESS, DIR_DGESS,
     * (ADMIN)
     *
     * @param programmationDTO
     * @return
     * @throws URISyntaxException
     */
    @PostMapping
    @PreAuthorize("hasAnyAuthority(\"" + AppUtil.FS + "\",\"" + AppUtil.RS + "\",\"" + AppUtil.RD + "\",\"" + AppUtil.DD + "\", \"" + AppUtil.ADMIN + "\")")
    public ResponseEntity<Programmation> createProgrammation(@Valid @RequestBody ProgrammationDTO programmationDTO) throws URISyntaxException {
        Programmation saved = programmationService.create(programmationDTO);
        log.debug("Programmation d'une activité : {}", programmationDTO);
        return ResponseEntity.created(new URI("/api/programmations/" + saved.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, saved.getId().toString()))
                .body(saved);
    }

    /**
     *
     * @param programmation
     * @return
     * @throws URISyntaxException
     */
    @PutMapping
    @PreAuthorize("hasAnyAuthority(\"" + AppUtil.FS + "\",\"" + AppUtil.RS + "\",\"" + AppUtil.RD + "\",\"" + AppUtil.DD + "\", \"" + AppUtil.ADMIN + "\")")
    public ResponseEntity<Programmation> updateProgrammation(@Valid @RequestBody Programmation programmation) throws URISyntaxException {
        log.debug("Mis à jour de la Programmation : {}", programmation);
        if (programmation.getId() == null) {
            throw new BadRequestAlertException("Id invalide", ENTITY_NAME, "idnull");
        }
        Programmation result = programmationService.update(programmation);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, programmation.getId().toString()))
                .body(result);
    }

    /**
     * Liste les programmations de cette structure
     *
     * @param structureId : id of Structure referency by ids in path
     * @param pageable
     * @return
     */
    @GetMapping(path = "/all/{ids}")
    public ResponseEntity<List<Programmation>> findAllProgrammations(@PathVariable(name = "ids", required = true) Long structureId, Pageable pageable) {
        Page<Programmation> programmations = programmationService.findAll(structureId, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), programmations);
        return ResponseEntity.ok().headers(headers).body(programmations.getContent());
    }

    /**
     * Liste les programmations ENCOURS de cette structure
     *
     * @param structureId : id of Structure referency by ids in path
     * @param pageable
     * @return
     */
    @GetMapping(path = "/all/exercice-encours/{ids}")
    public ResponseEntity<List<Programmation>> findProgrammationsByExerciceENCOURS(@PathVariable(name = "ids", required = true) Long structureId, Pageable pageable) {
        Page<Programmation> programmations = programmationService.findAllENCOURS(structureId, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), programmations);
        return ResponseEntity.ok().headers(headers).body(programmations.getContent());
    }

    /**
     * Liste les programmations ENATTENTE de cette structure
     *
     * @param structureId : id of Structure referency by ids in path
     * @param pageable
     * @return
     */
    @GetMapping(path = "/all/exercice-enattente/{ids}")
    public ResponseEntity<List<Programmation>> findProgrammationsByExerciceENATTENTE(@PathVariable(name = "ids", required = true) Long structureId, Pageable pageable) {
        Page<Programmation> programmations = programmationService.findAllENATTENTE(structureId, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), programmations);
        return ResponseEntity.ok().headers(headers).body(programmations.getContent());
    }

    /**
     * list of Programmation for Evaluation
     *
     * @param structureId
     * @param pageable
     * @return
     */
    @GetMapping(path = "/all/valide/{ids}")
    public ResponseEntity<List<Programmation>> findAllProgrammationsValided(@PathVariable(name = "ids", required = true) Long structureId, Pageable pageable) {
        Page<Programmation> programmations = programmationService.findAllValided(structureId, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), programmations);
        return ResponseEntity.ok().headers(headers).body(programmations.getContent());
    }

    /**
     * Liste les programmations contenant tous les champs apres evaluation
     *
     * @param structureId: id de la structure representee par ids
     * @param exerciceId: id de l'exercice representee par ide
     * @return
     */
    @GetMapping(path = "/programmations-evaluees/{ids}/{ide}")
    public ResponseEntity<List<ProgrammationForEvaluationDTO>> findAllProgrammationsEvaluees(
            @PathVariable(name = "ids", required = true) Long structureId,
            @PathVariable(name = "ide", required = true) Long exerciceId) {
        log.debug("List des programmations evaluees");
        if (structureId == null || exerciceId == null) {
            throw new BadRequestAlertException("Structure ou Exercice non renseigné.", ENTITY_NAME, "idnull");
        }
        List<ProgrammationForEvaluationDTO> programmations = programmationService.findAllAfterEvaluation(structureId, exerciceId);
        return ResponseEntity.ok().body(programmations);
    }

    @GetMapping(path = "/libelle/{ids}")
    public ResponseEntity<List<Programmation>> findAllProgrammationsByLibelle(
            @PathVariable(name = "ids", required = true) Long structureId,
            @RequestParam(required = true, name = "libelle") String libelle, Pageable pageable) {
        Page<Programmation> programmations = programmationService.get(structureId, libelle, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), programmations);
        return ResponseEntity.ok().headers(headers).body(programmations.getContent());
    }

    /**
     *
     * @param structureId : id of Structure referency by ids in path
     * @param id : id of Programmation referency by idp in path
     * @return
     */
    @GetMapping(path = "/{ids}/{idp}")
    public ResponseEntity<Programmation> getProgrammationById(@PathVariable(name = "ids", required = true) Long structureId, @PathVariable(name = "idp", required = true) Long id) {
        log.debug("Consultation de la Programmation : {}", id);
        Optional<Programmation> programmation = programmationService.get(structureId, id);
        return ResponseUtil.wrapOrNotFound(programmation);
    }

    /**
     * Presente une programmation pour son evaluation
     *
     * @param programmationId
     * @return
     */
    @GetMapping(path = "/detail/{idp}")
    public ResponseEntity<ProgrammationForEvaluationDTO> getProgrammationForEvaluation(@PathVariable(name = "idp", required = true) Long programmationId) {
        log.debug("Consultation de la Programmation {} pour l'evaluation", programmationId);
        ProgrammationForEvaluationDTO programmation = programmationService.getForEvaluation(programmationId);

        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, programmation.getId().toString()))
                .body(programmation);
    }

    /**
     * Access granted to RESP_STRUCT, RESP_DGESS, (ADMIN)
     *
     * @param structureId : id of Structure of validator referency by ids in
     * path
     * @param programmationId: id of Programmation referency by idp in path
     * @return
     */
    @PutMapping(path = "/validation/{ids}/{idp}")
    @PreAuthorize("hasAnyAuthority(\"" + AppUtil.RS + "\",\"" + AppUtil.RD + "\", \"" + AppUtil.ADMIN + "\")")
    public ResponseEntity<Programmation> validationProgrammation(
            @PathVariable(name = "ids", required = true) Long structureId,
            @PathVariable(name = "idp", required = true) Long programmationId) {
        log.debug("Validation initiale ou interne de la Programmation : {}", programmationId);
        Optional<Programmation> programmation = programmationService.validationInitialeOrInterne(structureId, programmationId);
        return ResponseUtil.wrapOrNotFound(programmation);
    }

    /**
     *
     * @param params
     * @return
     */
    @PutMapping(path = "/validation-all")
    @PreAuthorize("hasAnyAuthority(\"" + AppUtil.RS + "\",\"" + AppUtil.RD + "\", \"" + AppUtil.ADMIN + "\")")
    public ResponseEntity<String> allValidationProgrammation(@RequestBody ValidProgammationVM params) {
        log.debug("Validation globale initiale/interne/finale de Programmations");
        String message = "";
        if (params.isValidatedBySTRUCT() && !params.isValidatedByDGESS() && !params.isValidatedByCASEM()) {//do all initial validation of RESP_STRUCT
            message = programmationService.allValidationInitiale(params.getStructureId());
        } else if (params.isValidatedByDGESS() && !params.isValidatedBySTRUCT() && !params.isValidatedByCASEM()) {//do all interne validation of RESP_DGESS
            message = programmationService.allValidationInterne(params.getStructureId());
        } else if (params.isValidatedByCASEM() && !params.isValidatedByDGESS() && !params.isValidatedBySTRUCT()) {//do all CASEM validation of CASEM
            message = programmationService.allValidationCASEM(params.getStructureId());
        } else {
            throw new BadRequestAlertException("Paramètres mal renseignés !", ENTITY_NAME, "idincorrects");
        }

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /**
     * Access granted to DIR_DGESS, RESP_DGESS, (ADMIN)
     *
     * @param commentaireDTO: motif of rejection
     * @return
     */
    @PutMapping(path = "/rejet")
    @PreAuthorize("hasAnyAuthority(\"" + AppUtil.RD + "\", \"" + AppUtil.DD + "\", \"" + AppUtil.ADMIN + "\")")
    public ResponseEntity<String> rejetProgrammation(@Valid @RequestBody CommentaireDTO commentaireDTO) {
        log.debug("Rejet de la Programmation : {}", commentaireDTO.getProgrammationId());
        programmationService.rejetDgessOrCasem(commentaireDTO);
        return ResponseEntity.ok().body("Programmation rejetée");
    }

    /**
     * Access granted to FOCAL_STRUCT, RESP_DGESS, DIR_DGESS, (ADMIN)
     *
     * @param structureId
     * @param programmationId
     * @return
     */
    @DeleteMapping(path = "/{ids}/{idp}")
    @PreAuthorize("hasAnyAuthority(\"" + AppUtil.FS + "\", \"" + AppUtil.RD + "\", \"" + AppUtil.DD + "\", \"" + AppUtil.ADMIN + "\")")
    public ResponseEntity<Void> delete(
            @PathVariable(name = "ids", required = true) Long structureId,
            @PathVariable(name = "idp", required = true) Long programmationId) {
        log.debug("Suppression de la Programmation : {}", programmationId);
        programmationService.delete(structureId, programmationId);
        return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, programmationId.toString()))
                .build();
    }

    /**
     *
     * @param response
     * @param structureId
     * @param exerciceId
     * @throws IOException
     */
    @PostMapping(value = "/print/programme-activites")
    public void imprimerPAGlobal(HttpServletResponse response, @RequestBody PrintGlobalVM printGlobalVM) throws IOException {
        if (printGlobalVM.getExerciceId() == null) {
            throw new BadRequestAlertException("Exercice non renseigné.", ENTITY_NAME, "idnull");
        }
        String[] tab = AppUtil.constructFormatAndExtension(printGlobalVM.getFormat());
        response.setContentType(tab[0]);
        response.setHeader("Content-Disposition", String.format("attachment; filename=\"Programme_activites_" + printGlobalVM.getMinistereId() + tab[1] + "\""));
        OutputStream outStream = response.getOutputStream();
        programmationService.printProgrammeActivites(printGlobalVM.getMinistereId(),
                printGlobalVM.getStructureId(), printGlobalVM.getExerciceId(),
                printGlobalVM.getCurrentStructureId(), printGlobalVM.getFormat(), outStream);
    }

    /**
     *
     * @param response
     * @param printGlobalVM
     * @throws IOException
     */
    @PostMapping(value = "/print/rapport-activites")
    public void imprimerRAGlobal(HttpServletResponse response, @RequestBody PrintGlobalVM printGlobalVM) throws IOException {
        if (printGlobalVM.getExerciceId() == null) {
            throw new BadRequestAlertException("Exercice non renseigné. ", ENTITY_NAME, "idnull");
        }

        String[] tab = AppUtil.constructFormatAndExtension(printGlobalVM.getFormat());
        response.setContentType(tab[0]);
        response.setHeader("Content-Disposition", String.format("attachment; filename=\"Rapport_activites_" + printGlobalVM.getMinistereId() + tab[1] + "\""));
        OutputStream outStream = response.getOutputStream();

        if (printGlobalVM.getPeriodeId() == null) {
            printGlobalVM.setPeriodeId(0L);
        }

        programmationService.printRapportActivites(printGlobalVM.getMinistereId(),
                printGlobalVM.getStructureId(), printGlobalVM.getExerciceId(),
                printGlobalVM.getCurrentStructureId(), printGlobalVM.getPeriodeId(), printGlobalVM.getFormat(), outStream);
    }

    /**
     *
     * @param response
     * @param printGlobalVM
     * @throws IOException
     */
    @PostMapping(value = "/print/rapport-performance")
    public void imprimerRapportPerformance(HttpServletResponse response, @RequestBody PrintGlobalVM printGlobalVM) throws IOException, Exception {
        if (printGlobalVM.getExerciceId() == null) {
            throw new BadRequestAlertException("Exercice non renseigné. ", ENTITY_NAME, "idnull");
        }

        String[] tab = AppUtil.constructFormatAndExtension(printGlobalVM.getFormat());
        response.setContentType(tab[0]);
        response.setHeader("Content-Disposition", String.format("attachment; filename=\"Rapport_evaluation_performance_" + printGlobalVM.getMinistereId() + tab[1] + "\""));
        OutputStream outStream = response.getOutputStream();

        if (printGlobalVM.getPeriodeId() == null) {
            printGlobalVM.setPeriodeId(0L);
        }

        programmationService.imprimerRapportPerformance(printGlobalVM.getMinistereId(),
                printGlobalVM.getStructureId(), printGlobalVM.getExerciceId(),
                printGlobalVM.getFormat(), outStream);
    }

    /**
     *
     * @param params
     * @return
     */
    @GetMapping(path = "/taux-execution")
    public ResponseEntity<Double> tauxExecution(@RequestBody TauxExecutionVM params) {
        log.debug("Taux d'execution des activités(Ministere/Structure) par Exercice : {}", params.getExerciceId());
        double taux = 0;
        if ((params.getStructureId() == null) && (params.getMinistereId() != null)) { //TAUX PAR MINISTERE
            taux = programmationService
                    .tauxExecutionGlobal(params.getMinistereId(), params.getExerciceId(), params.getPeriodeId());
        } else if ((params.getMinistereId() == null) && (params.getStructureId() != null)) { //TAUX PAR STRUCTURE
            taux = programmationService
                    .tauxExecution(params.getStructureId(), params.getExerciceId(), params.getPeriodeId());
        } else {
            throw new BadRequestAlertException("Paramètres mal renseignés.", ENTITY_NAME, "idincorrects");
        }

        return new ResponseEntity<>(taux, HttpStatus.OK);
    }
}
