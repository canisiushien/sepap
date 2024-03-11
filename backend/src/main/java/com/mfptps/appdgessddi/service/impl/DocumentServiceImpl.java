/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mfptps.appdgessddi.service.impl;

import static com.google.common.io.Files.getFileExtension;
import com.mfptps.appdgessddi.entities.Document;
import com.mfptps.appdgessddi.entities.Structure;
import com.mfptps.appdgessddi.entities.Tache;
import com.mfptps.appdgessddi.repositories.DocumentRepository;
import com.mfptps.appdgessddi.repositories.ProgrammationRepository;
import com.mfptps.appdgessddi.repositories.StructureRepository;
import com.mfptps.appdgessddi.repositories.TacheRepository;
import com.mfptps.appdgessddi.service.CustomException;
import com.mfptps.appdgessddi.service.DocumentService;
import com.mfptps.appdgessddi.service.dto.DocumentDTO;
import com.mfptps.appdgessddi.service.mapper.DocumentMapper;
import com.mfptps.appdgessddi.utils.ResponseMessage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Slf4j
@Service
@Transactional
public class DocumentServiceImpl implements DocumentService {

    private final String appStoreRootPath = System.getProperty("user.home")
            + File.separatorChar + "dgess-files"
            + File.separatorChar;

    private final DocumentRepository documentRepository;
    private final TacheRepository tacheRepository;
    private final StructureRepository structureRepository;
    private final ProgrammationRepository programmationRepository;
    private final DocumentMapper documentMapper;

    public DocumentServiceImpl(DocumentRepository documentRepository,
            TacheRepository tacheRepository,
            StructureRepository structureRepository,
            ProgrammationRepository programmationRepository,
            DocumentMapper documentMapper) {

        this.documentRepository = documentRepository;
        this.tacheRepository = tacheRepository;
        this.structureRepository = structureRepository;
        this.programmationRepository = programmationRepository;
        this.documentMapper = documentMapper;
    }

    /**
     * creating folder for storage
     *
     * @param directoryRef
     * @return
     */
    private Path initStoragePath(String directoryRef) {
        Path rootPath = Paths.get(this.appStoreRootPath + directoryRef + File.separatorChar);
        try {
            Files.createDirectories(rootPath);
            return rootPath;
        } catch (IOException e) {
            throw new CustomException("\n Echec de création du dossier de stockage des fichiers.");
        }
    }

    /**
     *
     * @param documentDTO
     * @param documentFile
     * @return
     */
    @Override
    @Transactional
    public ResponseMessage create(DocumentDTO documentDTO, MultipartFile documentFile) {

        // Initialisation de la réponse
        ResponseMessage response = new ResponseMessage();
        if (documentFile.isEmpty()) {
            response.setCode(500);
            response.setMessage("Données initiales incomplètes");
            response.setDetails(
                    "Echec de la prise en compte de la requête, veuillez vous assurer d'avoir fourni tous les élements");
            return response;
        }

        Document document = new Document();
        document = documentMapper.toEntity(documentDTO);

        Tache tache = tacheRepository.findById(document.getTache().getId())
                .orElseThrow(() -> new CustomException("Tache inexistante"));

        //Create a subFolder in user.home directory as name Structure ID and SIGLE fields
        Structure structure = structureRepository.findStructureById(tache.getId()).orElseThrow(() -> new CustomException("Structure inexistante"));

        Path path = this.initStoragePath(structure.getId().toString() + "_" + structure.getSigle().replaceAll("[^a-zA-Z0-9\\S+]", "_"));
        // Initialization and saving data+files
        try {
            String filename = tache.getId() + "_" + StringUtils.cleanPath(documentFile.getOriginalFilename());// Normalize filename
            Files.copy(documentFile.getInputStream(),
                    path.resolve(filename), StandardCopyOption.REPLACE_EXISTING);// Copy file to the target location (Replacing existing file with the same name)

            document.setChemin(ServletUriComponentsBuilder.fromCurrentContextPath().path(filename).toUriString());
            document.setFormat(getFileExtension(filename));

            tache.setFichierJoint(true);//indiquer qu'un fichier vient d'etre joint a cette tache

            //if some file with the same path exists in bd, we delete it
            Optional<Document> existingDocument = Optional.ofNullable(documentRepository.findByChemin(document.getChemin()));

            if (existingDocument.isPresent()) {
                this.delete(existingDocument.get().getId());
            }
            tacheRepository.save(tache);
            documentRepository.save(document);

            response.setCode(HttpStatus.CREATED.value());
            response.setMessage("Document joint.");
            response.setDetails("Succès de la requête de jointure du document à la tache.");
        } catch (IOException e) {
            log.error("Erreur lors de l'enregistrement du fichier", e);
        }
        return response;
    }

    /**
     *
     * @param id : id field of Document
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Document get(Long id) {
        return documentRepository.findById(id)
                .orElseThrow(() -> new CustomException("Document d'id [" + id + "] introuvable"));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Document> findAll(Pageable pageable) {
        return documentRepository.findAll(pageable);
    }

    @Override
    public void delete(Long id) {
        documentRepository.deleteById(id);
    }

    /**
     *
     * @param fileUri
     * @return
     */
    @Override
    public Resource download(String fileUri) {
//        try {
//            Document document = new Document();
//            document = documentRepository.findByChemin(fileUri).orElseThrow(
//                    () -> new CustomException("Document introuvable."));
//
//            if (document.isEstConfidentiel()) {
//                throw new CustomException("Ce document est confidentiel.");
//            }
//
//            String url = document.getChemin();
//            url = url.contains("C:") ? "file:///" + url.replace("\'", "/") : url;
//            Resource resource = null;
//            String operatingSystem = System.getProperty("os.name").toLowerCase();
//
//            if (operatingSystem.indexOf("win") >= 0) {
//                resource = new UrlResource(url);
//            } else if (operatingSystem.indexOf("nux") >= 0) {
//                resource = new UrlResource("file:" + url);
//            } else if (operatingSystem.indexOf("mac") >= 0) {
//                resource = new UrlResource("file:" + url);
//            }
//
//            if (resource.exists() || resource.isReadable()) {
//                return resource;
//            } else {
//                throw new RuntimeException("Impossible de lire le fichier.");
//            }
//        } catch (MalformedURLException e) {
//            throw new RuntimeException("Erreur : " + e.getMessage());
//        }
        throw new CustomException("Fonctionnalité en cours d'implementation ...");
    }

}
