package com.mfptps.appdgessddi.service.impl;

import com.mfptps.appdgessddi.entities.IndicateurObjectif;
import com.mfptps.appdgessddi.entities.Objectif;
import com.mfptps.appdgessddi.repositories.IndicateurObjectifRepository;
import com.mfptps.appdgessddi.repositories.ObjectifRepository;
import com.mfptps.appdgessddi.service.CustomException;
import com.mfptps.appdgessddi.service.IndicateurObjectifService;
import com.mfptps.appdgessddi.service.dto.IndicateurObjectifDTO;
import com.mfptps.appdgessddi.service.mapper.IndicateurObjectifMapper;
import com.mfptps.appdgessddi.utils.AppUtil;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class IndicateurObjectifServiceImpl implements IndicateurObjectifService {

    private final IndicateurObjectifRepository indicateurObjectifRepository;
    private final ObjectifRepository objectifRepository;
    private IndicateurObjectifMapper indicateurObjectifMapper;

    public IndicateurObjectifServiceImpl(IndicateurObjectifRepository indicateurObjectifRepository,
            ObjectifRepository objectifRepository,
            IndicateurObjectifMapper indicateurObjectifMapper) {
        this.indicateurObjectifRepository = indicateurObjectifRepository;
        this.objectifRepository = objectifRepository;
        this.indicateurObjectifMapper = indicateurObjectifMapper;
    }

    /**
     * Modify by Canisius 02122021
     *
     * @param indicateurObjectifDTO
     * @return
     */
    @Override
    public IndicateurObjectif create(IndicateurObjectifDTO indicateurObjectifDTO) {
        IndicateurObjectif indicateur = indicateurObjectifMapper.toEntity(indicateurObjectifDTO);
        this.checktypeObjectif(indicateur);
        return indicateurObjectifRepository.save(indicateur);
    }

    /**
     * Modifiy by Canisius 02122021
     *
     * @param indicateurObjectif
     * @return
     */
    @Override
    public IndicateurObjectif update(IndicateurObjectif indicateurObjectif) {
        this.checktypeObjectif(indicateurObjectif);
        return indicateurObjectifRepository.save(indicateurObjectif);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<IndicateurObjectif> get(Long id) {
        return indicateurObjectifRepository.findById(id);
    }

    @Override
    public Page<IndicateurObjectif> findAll(Pageable pageable) {
        return indicateurObjectifRepository.findAll(pageable);
    }

    @Override
    public void delete(Long id) {
        indicateurObjectifRepository.deleteById(id);

    }

    /**
     * Objectif STRATEGIQUE ================================> indicateur EFFET
     * Objectif OPERATIONNEL ================================> indicateur IMPACT
     * Added by Canisius 02122021
     *
     * @param indicateur
     */
    void checktypeObjectif(IndicateurObjectif indicateur) {
        Objectif objectif = objectifRepository.findById(indicateur.getObjectif().getId()).orElseThrow(() -> new CustomException("Objectif inexistant."));
        if ((indicateur.getTypeIndicateur().getLabel() == null ? AppUtil.INDICATEUR_EFFET == null : indicateur.getTypeIndicateur().getLabel().equals(AppUtil.INDICATEUR_EFFET))
                && !objectif.getType().getLabel().equals(AppUtil.OBJECTIF_STRATEGIQUE)) {
            throw new CustomException("L'indicateur d'effet doit correspondre à un objectif stratégique.");
        }
        if ((indicateur.getTypeIndicateur().getLabel() == null ? AppUtil.INDICATEUR_IMPACT == null : indicateur.getTypeIndicateur().getLabel().equals(AppUtil.INDICATEUR_IMPACT))
                && !objectif.getType().getLabel().equals(AppUtil.OBJECTIF_OPERATIONNEL)) {
            throw new CustomException("L'indicateur d'impact doit correspondre à un objectif opérationnel.");
        }
    }
}
