package com.mfptps.appdgessddi.web;

import com.mfptps.appdgessddi.entities.Profile;
import com.mfptps.appdgessddi.service.ProfileService;
import com.mfptps.appdgessddi.service.dto.ProfileDTO;
import com.mfptps.appdgessddi.utils.*;
import com.mfptps.appdgessddi.web.exceptions.*;
import java.net.*;
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
public class ProfileController {

    private final Logger log = LoggerFactory.getLogger(ProfileController.class);

    private static final String ENTITY_NAME = "profile";

    @Value("${application.name}")
    private String applicationName;

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    /**
     * Access granted to ADMIN
     *
     * {@code POST  /profiles} : Create a new profile.
     *
     * @param profile the profile to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and
     * with body the new profile, or with status {@code 400 (Bad Request)} if
     * the profile has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    //@PreAuthorize("hasAnyAuthority(\"ROLE_ADMIN\", \"ROLE_MANAGER\", \"ROLE_CREATE_ROLE\")")
    @PostMapping("/profiles")
    @PreAuthorize("hasAnyAuthority(\"" + AppUtil.ADMIN + "\")")
    public ResponseEntity<Profile> createProfile(@RequestBody Profile profile) throws URISyntaxException {
        log.debug("REST request to save Profile : {}", profile);
        if (profile.getId() != null) {
            throw new BadRequestAlertException("A new profile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Profile result = profileService.save(profile);
        return ResponseEntity.created(new URI("/api/profiles/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * Access granted to ADMIN
     *
     * {@code PUT  /profiles} : Updates an existing profile.
     *
     * @param profile the profile to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with
     * body the updated profile, or with status {@code 400 (Bad Request)} if the
     * profile is not valid, or with status {@code 500 (Internal Server Error)}
     * if the profile couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/profiles")
    @PreAuthorize("hasAnyAuthority(\"" + AppUtil.ADMIN + "\")")
    public ResponseEntity<Profile> updateProfile(@RequestBody Profile profile) throws URISyntaxException {
        log.debug("REST request to update Profile : {}", profile);
        if (profile.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Profile result = profileService.save(profile);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, profile.getId().toString()))
                .body(result);
    }

    /**
     * {@code GET  /profiles} : get all the profiles.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the
     * list of profiles in body.
     */
    @GetMapping("/profiles")
    public ResponseEntity<List<ProfileDTO>> getAllProfiles(Pageable pageable) {
        log.debug("REST request to get a page of Profiles");
        Page<ProfileDTO> page = profileService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /profiles/:name} : get the "name" profile.
     *
     * @param name the name of the profile to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with
     * body the profile, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/profiles/{name}")
    public ResponseEntity<Profile> getProfile(@PathVariable String name) {
        log.debug("REST request to get Profile : {}", name);
        Optional<Profile> profile = profileService.getProfileWithPrivilegesByName(name);
        return ResponseUtil.wrapOrNotFound(profile);
    }

    /**
     * Access granted to ADMIN
     *
     * {@code DELETE  /profiles/:id} : delete the "id" profile.
     *
     * @param id the id of the profile to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/profiles/{id}")
    @PreAuthorize("hasAnyAuthority(\"" + AppUtil.ADMIN + "\")")
    public ResponseEntity<Void> deleteProfile(@PathVariable Long id) {
        log.debug("REST request to delete Profile : {}", id);
        if (id < 3) {
            throw new RuntimeException("Ce profile ne peut être supprimé.");
        }
        profileService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
