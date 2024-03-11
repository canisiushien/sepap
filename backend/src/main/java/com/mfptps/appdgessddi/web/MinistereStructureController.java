package com.mfptps.appdgessddi.web;

import com.mfptps.appdgessddi.entities.MinistereStructure;
import com.mfptps.appdgessddi.service.MinistereStructureService;
import com.mfptps.appdgessddi.utils.*;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(path = "/api")
public class MinistereStructureController {

    private final Logger log = LoggerFactory.getLogger(MinistereController.class);

    private static final String ENTITY_NAME = "ministereStructure";

    @Value("${application.name}")
    private String applicationName;

    private final MinistereStructureService ministereStructureService;

    public MinistereStructureController(MinistereStructureService ministereStructureService) {
        this.ministereStructureService = ministereStructureService;
    }

    @GetMapping(path = "/ministere-structures")
    public ResponseEntity<List<MinistereStructure>> findAllMinisteresStrucre(Pageable pageable) {
        Page<MinistereStructure> minsiteresStructure = ministereStructureService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), minsiteresStructure);
        return ResponseEntity.ok().headers(headers).body(minsiteresStructure.getContent());
    }
}
