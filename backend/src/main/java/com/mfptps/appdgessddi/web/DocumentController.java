/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mfptps.appdgessddi.web;

import com.mfptps.appdgessddi.service.CustomException;
import com.mfptps.appdgessddi.service.DocumentService;
import com.mfptps.appdgessddi.service.dto.DocumentDTO;
import com.mfptps.appdgessddi.utils.AppUtil;
import com.mfptps.appdgessddi.utils.ResponseMessage;
import com.mfptps.appdgessddi.web.exceptions.BadRequestAlertException;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Slf4j
@RestController
@RequestMapping(path = "/api/documents")
public class DocumentController {

    private static final String ENTITY_NAME = "document";

    @Value("${application.name}")
    private String applicationName;

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    /**
     * Join a file to a Tache
     *
     * @param documentDTO
     * @param documentFile : file joined
     * @return
     */
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @PreAuthorize("hasAnyAuthority(\"" + AppUtil.FS + "\",\"" + AppUtil.RS + "\", \"" + AppUtil.ADMIN + "\")")
    public ResponseEntity<ResponseMessage> createDocument(@Valid @RequestPart DocumentDTO documentDTO,
            @RequestPart MultipartFile documentFile) {
        log.debug("Jointure d'un Document à une Tache : {}", documentDTO);
        if (documentDTO.getTacheId() == null) {
            throw new BadRequestAlertException("Aucune tache n'est associée. ", ENTITY_NAME, "idnull");
        }
        if (!documentFile.getOriginalFilename().endsWith(".pdf")) {
            throw new CustomException("Ce format de fichier n'est pas autorisé. Réessayez avec un .pdf svp !");
        }
        ResponseMessage message = documentService.create(documentDTO, documentFile);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    /**
     *
     * @param fileUri
     * @return
     */
    @GetMapping("/{fileUri}")
    public ResponseEntity<Resource> getFile(@PathVariable(name = "fileUri", required = true) String fileUri) {
        log.debug("Lecture d'un Document : {}", fileUri);
        Resource file = documentService.download(fileUri);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}
