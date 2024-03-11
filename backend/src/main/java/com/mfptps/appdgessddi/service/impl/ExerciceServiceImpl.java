package com.mfptps.appdgessddi.service.impl;

import com.mfptps.appdgessddi.entities.Exercice;
import com.mfptps.appdgessddi.enums.ExerciceStatus;
import com.mfptps.appdgessddi.repositories.ExerciceRepository;
import com.mfptps.appdgessddi.service.CustomException;
import com.mfptps.appdgessddi.service.ExerciceService;
import com.mfptps.appdgessddi.service.dto.ExerciceDTO;
import com.mfptps.appdgessddi.service.mapper.ExerciceMapper;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class ExerciceServiceImpl implements ExerciceService {

    private final ExerciceRepository exerciceRepository;
    private final ExerciceMapper exerciceMapper;

    public ExerciceServiceImpl(ExerciceRepository exerciceRepository, ExerciceMapper exerciceMapper) {
        this.exerciceRepository = exerciceRepository;
        this.exerciceMapper = exerciceMapper;
    }

    @Override
    public Exercice update(ExerciceDTO exercice) {
        Exercice data = exerciceRepository.findById(exercice.getId()).orElseThrow(() -> new CustomException("Exercice inexistant"));
//        if (data.getStatut().equals(AppUtil.CLOS)) {
//            throw new CustomException("Modification interdite !");
//        }
        if (exercice.getDebut() != null) {
            data.setDebut(exercice.getDebut());
        }
        if (exercice.getFin() != null) {
            data.setFin(exercice.getFin());
        }
        if (exercice.getDescription() != null && !exercice.getDescription().isEmpty()) {
            data.setDescription(exercice.getDescription());
        }
        return data;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Exercice> get(Long id) {
        return exerciceRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Exercice> findAll(Pageable pageable) {
        return exerciceRepository.findAllByOrderByDebutDesc(pageable);//findAll(pageable);
    }

    @Override
    public void delete(Long id) {
        exerciceRepository.deleteById(id);
    }

    /**
     * The closing of an exercise implies the creation of a pending exercise
     */
    @Override
    public void cloture() {
        Exercice exerciceToCloture = exerciceRepository.findByStatut(ExerciceStatus.EN_COURS).orElseThrow(() -> new CustomException("Aucun exercice en attente."));
        Exercice exerciceToEncours = exerciceRepository.findByStatut(ExerciceStatus.EN_ATTENTE).orElseThrow(() -> new CustomException("Aucun exercice en attente."));

        exerciceToCloture.setStatut(ExerciceStatus.CLOS);
        exerciceToEncours.setStatut(ExerciceStatus.EN_COURS);

        Exercice exerciceAVenir = new Exercice();
        exerciceAVenir.setDebut(exerciceToEncours.getDebut().plusYears(1));
        exerciceAVenir.setFin(exerciceToEncours.getFin().plusYears(1));
        exerciceAVenir.setStatut(ExerciceStatus.EN_ATTENTE);
        exerciceRepository.save(exerciceAVenir);
    }

    /**
     *
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Exercice> getByStatutAttente() {
        return exerciceRepository.findByStatut(ExerciceStatus.EN_ATTENTE);
    }

    /**
     *
     * @param statut
     * @param pageable
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Exercice> findByStatut(ExerciceStatus statut, Pageable pageable) {
        return exerciceRepository.findByStatut(statut, pageable);
    }
}
