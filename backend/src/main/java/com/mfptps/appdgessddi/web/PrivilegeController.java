package com.mfptps.appdgessddi.web;

import com.mfptps.appdgessddi.entities.Privilege;
import com.mfptps.appdgessddi.service.PrivilegeService;
import com.mfptps.appdgessddi.utils.*;
import com.mfptps.appdgessddi.web.exceptions.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api")
public class PrivilegeController {

    private final Logger log = LoggerFactory.getLogger(PrivilegeController.class);

    private static final String ENTITY_NAME = "privilege";

    @Value("${application.name}")
    private String applicationName;

    private final PrivilegeService privilegeService;

    public PrivilegeController(PrivilegeService privilegeService) {
        this.privilegeService = privilegeService;
    }

    /**
     * Access granted to ADMIN
     *
     * {@code POST  /privileges} : Create a new privilege.
     *
     * @param privilege the privilege to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and
     * with body the new privilege, or with status {@code 400 (Bad Request)} if
     * the privilege has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/privileges")
    @PreAuthorize("hasAnyAuthority(\"" + AppUtil.ADMIN + "\")")
    public ResponseEntity<Privilege> createPrivilege(@RequestBody Privilege privilege) throws URISyntaxException {
        log.debug("REST request to save Privilege : {}", privilege);
        if (privilege.getId() != null) {
            throw new BadRequestAlertException("A new privilege cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Privilege result = privilegeService.save(privilege);
        return ResponseEntity.created(new URI("/api/privileges/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * Access granted to ADMIN
     *
     * {@code PUT  /privileges} : Updates an existing privilege.
     *
     * @param privilege the privilege to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with
     * body the updated privilege, or with status {@code 400 (Bad Request)} if
     * the privilege is not valid, or with status
     * {@code 500 (Internal Server Error)} if the privilege couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/privileges")
    @PreAuthorize("hasAnyAuthority(\"" + AppUtil.ADMIN + "\")")
    public ResponseEntity<Privilege> updatePrivilege(@RequestBody Privilege privilege) throws URISyntaxException {
        log.debug("REST request to update Privilege : {}", privilege);
        if (privilege.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Privilege result = privilegeService.save(privilege);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, privilege.getId().toString()))
                .body(result);
    }

    /**
     * {@code GET  /privileges} : get all the privilege.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the
     * list of privilege in body.
     */
    @GetMapping("/privileges")
    public ResponseEntity<List<Privilege>> getAllPrivileges(Pageable pageable) {
        log.debug("REST request to get a page of Privilege");
        Page<Privilege> page = privilegeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/privileges/list")
    public ResponseEntity<List<Privilege>> getPrivilegeList() {
        log.debug("REST request to get a list of Privilege");
        List<Privilege> list = privilegeService.getList();
        return ResponseEntity.ok().body(list);
    }

    /**
     * {@code GET  /privileges/:id} : get the "id" privilege.
     *
     * @param id the id of the privilege to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with
     * body the privilege, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/privileges/{id}")
    public ResponseEntity<Privilege> getPrivilege(@PathVariable Long id) {
        log.debug("REST request to get Privilege : {}", id);
        Optional<Privilege> privilege = privilegeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(privilege);
    }

    /**
     * Access granted to ADMIN
     *
     * {@code DELETE  /privileges/:id} : delete the "id" privilege.
     *
     * @param id the id of the privilege to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/privileges/{id}")
    @PreAuthorize("hasAnyAuthority(\"" + AppUtil.ADMIN + "\")")
    public ResponseEntity<Void> deletePrivilege(@PathVariable Long id) {
        log.debug("REST request to delete Privilege : {}", id);
        privilegeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
