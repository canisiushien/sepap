/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mfptps.appdgessddi.service;

import com.mfptps.appdgessddi.entities.Commentaire;
import com.mfptps.appdgessddi.service.dto.CommentaireDTO;
import java.util.List;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public interface CommentaireService {

    Commentaire create(CommentaireDTO commentaireDTO);

    Commentaire update(Commentaire commentaire);

    List<Commentaire> get(Long programmationId);

    void delete(Long id);
}
