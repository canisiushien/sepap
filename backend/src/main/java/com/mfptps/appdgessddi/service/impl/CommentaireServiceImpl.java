/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mfptps.appdgessddi.service.impl;

import com.mfptps.appdgessddi.entities.Commentaire;
import com.mfptps.appdgessddi.repositories.CommentaireRepository;
import com.mfptps.appdgessddi.service.CommentaireService;
import com.mfptps.appdgessddi.service.dto.CommentaireDTO;
import com.mfptps.appdgessddi.service.mapper.CommentaireMapper;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Service
public class CommentaireServiceImpl implements CommentaireService {

    private final CommentaireRepository commentaireRepository;
    private final CommentaireMapper commentaireMapper;

    public CommentaireServiceImpl(CommentaireRepository commentaireRepository, CommentaireMapper commentaireMapper) {
        this.commentaireRepository = commentaireRepository;
        this.commentaireMapper = commentaireMapper;
    }

    @Override
    @Transactional
    public Commentaire create(CommentaireDTO commentaireDTO) {
        Commentaire commentaire = commentaireMapper.toEntity(commentaireDTO);
        return commentaireRepository.save(commentaire);
    }

    @Override
    @Transactional
    public Commentaire update(Commentaire commentaire) {
        return commentaireRepository.save(commentaire);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Commentaire> get(Long programmationId) {
        List<Commentaire> commentaires = commentaireRepository.findByProgrammation(programmationId);
        return commentaires;
    }

    @Override
    public void delete(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
