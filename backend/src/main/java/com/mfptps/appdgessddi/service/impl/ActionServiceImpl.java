package com.mfptps.appdgessddi.service.impl;

import com.mfptps.appdgessddi.entities.Action;
import com.mfptps.appdgessddi.repositories.ActionRepository;
import com.mfptps.appdgessddi.repositories.ObjectifRepository;
import com.mfptps.appdgessddi.service.ActionService;
import com.mfptps.appdgessddi.service.dto.ActionDTO;
import com.mfptps.appdgessddi.service.mapper.ActionMapper;
import com.mfptps.appdgessddi.utils.AppUtil;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class ActionServiceImpl implements ActionService {

    private final ActionRepository actionRepository;
    private final ActionMapper actionMapper;
    private final ObjectifRepository objectifRepository;

    public ActionServiceImpl(ActionRepository actionRepository,
            ActionMapper actionMapper,
            ObjectifRepository objectifRepository) {
        this.actionRepository = actionRepository;
        this.objectifRepository = objectifRepository;
        this.actionMapper = actionMapper;
    }

    @Override
    public Action create(ActionDTO actionDTO) {
        Action action = actionMapper.toEntity(actionDTO);
        action.setCode(AppUtil.codeGeneratorAction(actionRepository, objectifRepository, action));
        return actionRepository.save(action);
    }

    @Override
    public Action update(Action action) {
        return actionRepository.save(action);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Action> get(Long id) { 
        return actionRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Action> rechercheLibelle(String libelle) {
        return actionRepository.findByLibelleContainingIgnoreCase(libelle);
    }

    @Override
    public Page<Action> findAll(Pageable pageable) {
        return actionRepository.findAll(pageable);
    }

    @Override
    public void delete(Long id) {
        actionRepository.deleteById(id);
    }
}
