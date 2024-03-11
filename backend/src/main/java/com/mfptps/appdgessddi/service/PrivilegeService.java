package com.mfptps.appdgessddi.service;


import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import com.mfptps.appdgessddi.entities.Privilege;

/**
 * Service Interface for managing {@link Privilege}.
 */
public interface PrivilegeService {

    /**
     * Save a permission.
     *
     * @param permission the entity to save.
     * @return the persisted entity.
     */
    Privilege save(Privilege permission);

    /**
     * Get all the permission.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Privilege> findAll(Pageable pageable);
    
    /**
     * Get all the permission.
     *
     * @return the list of entities.
     */
    List<Privilege> getList();


    /**
     * Get the "id" permission.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Privilege> findOne(Long id);

    /**
     * Delete the "id" permission.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
