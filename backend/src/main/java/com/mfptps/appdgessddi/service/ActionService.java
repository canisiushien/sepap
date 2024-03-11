package com.mfptps.appdgessddi.service;

import com.mfptps.appdgessddi.entities.Action;
import com.mfptps.appdgessddi.service.dto.ActionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ActionService {
    
    Action create(ActionDTO actionDTO);

    Action update(Action action);

    Optional<Action> get(Long id);

    List<Action> rechercheLibelle(String libelle);

    Page<Action> findAll(Pageable pageable);

    void delete(Long id);
}
