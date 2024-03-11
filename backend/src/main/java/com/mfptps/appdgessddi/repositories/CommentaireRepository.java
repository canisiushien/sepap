/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mfptps.appdgessddi.repositories;

import com.mfptps.appdgessddi.entities.Commentaire;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public interface CommentaireRepository extends JpaRepository<Commentaire, Long> {

    @Query("SELECT c FROM Commentaire c, Programmation p "
            + "WHERE c.programmation.id = p.id "
            + "AND p.id = :programmationId "
            + "AND p.deleted = false")
    List<Commentaire> findByProgrammation(Long programmationId);
}
