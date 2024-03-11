/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mfptps.appdgessddi.web;

import com.mfptps.appdgessddi.entities.Commentaire;
import com.mfptps.appdgessddi.service.CommentaireService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Slf4j
@RestController
@RequestMapping(path = "/api/commentaires")
public class CommentaireController {

    private static final String ENTITY_NAME = "commentaire";

    @Value("${application.name}")
    private String applicationName;

    private final CommentaireService commentaireService;

    public CommentaireController(CommentaireService commentaireService) {
        this.commentaireService = commentaireService;
    }

    /**
     *
     * @param commentaireDTO
     * @return
     * @throws URISyntaxException
     */
//    @PostMapping
//    public ResponseEntity<Commentaire> create(@Valid @RequestBody CommentaireDTO commentaireDTO) throws URISyntaxException {
//
//        Commentaire comment = commentaireService.create(commentaireDTO);
//        log.debug("Création de Commentaire : {}", commentaireDTO);
//        return ResponseEntity.created(new URI("/api/commentaires/" + comment.getId()))
//                .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, comment.getId().toString()))
//                .body(comment);
//    }
    /**
     *
     * @param commentaire
     * @return
     * @throws URISyntaxException
     */
//    @PutMapping
//    public ResponseEntity<Commentaire> update(@Valid @RequestBody Commentaire commentaire) throws URISyntaxException {
//        log.debug("Mis à jour du Commentaire : {}", commentaire);
//        if (commentaire.getId() == null) {
//            throw new BadRequestAlertException("Id invalide", ENTITY_NAME, "idnull");
//        }
//        Commentaire result = commentaireService.update(commentaire);
//        return ResponseEntity.ok()
//                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, commentaire.getId().toString()))
//                .body(result);
//    }
    /**
     *
     * @param id
     * @return
     */
    @GetMapping(path = "/{id}")
    public ResponseEntity<List<Commentaire>> getByProgrammationId(@PathVariable(name = "id") Long id) {
        log.debug("Consultation des Commentaires de Programmation : {}", id);
        List<Commentaire> comments = commentaireService.get(id);
        return ResponseEntity.ok().body(comments);
    }
}
