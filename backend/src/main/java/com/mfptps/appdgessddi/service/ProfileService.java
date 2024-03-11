package com.mfptps.appdgessddi.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import com.mfptps.appdgessddi.entities.Profile;
import com.mfptps.appdgessddi.service.dto.ProfileDTO;

/**
 * Service Interface for managing {@link ProfileService}.
 */
public interface ProfileService {

    /**
     * Save a profile.
     *
     * @param profile the entity to save.
     * @return the persisted entity.
     */
    Profile save(Profile profile);

    /**
     * Get all the profiles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProfileDTO> findAll(Pageable pageable);


    /**
     * Get the "id" profile.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Profile> findOne(Long id);

    /**
     * Delete the "id" profile.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
    
    Optional<Profile> getProfileWithPrivilegesByName(String name);
}
