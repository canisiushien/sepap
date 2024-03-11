/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mfptps.appdgessddi.repositories;

import com.mfptps.appdgessddi.entities.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public interface DocumentRepository extends JpaRepository<Document, Long> {

    @Query("SELECT d FROM Document d "
            + "WHERE d.chemin = :chemin "
            + "AND d.deleted = false")
    Document findByChemin(@Param("chemin") String chemin);
}
