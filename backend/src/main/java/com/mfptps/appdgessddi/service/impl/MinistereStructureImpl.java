package com.mfptps.appdgessddi.service.impl;

import com.mfptps.appdgessddi.entities.MinistereStructure;
import com.mfptps.appdgessddi.repositories.MinistereStructureRepository;
import com.mfptps.appdgessddi.security.SecurityUtils;
import com.mfptps.appdgessddi.service.MinistereStructureService;
import com.mfptps.appdgessddi.service.dto.MinistereStructureDTO;
import com.mfptps.appdgessddi.service.dto.StructureDTO;
import com.mfptps.appdgessddi.service.mapper.MinistereStructureMapper;
import com.mfptps.appdgessddi.utils.AppUtil;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MinistereStructureImpl implements MinistereStructureService {

    private final MinistereStructureRepository ministereStructureRepository;
    private final MinistereStructureMapper ministereStructureMapper;

    public MinistereStructureImpl(MinistereStructureRepository ministereSRepository,
            MinistereStructureMapper ministereStructureMapper) {
        this.ministereStructureRepository = ministereSRepository;
        this.ministereStructureMapper = ministereStructureMapper;
    }

    @Override
    public MinistereStructure create(MinistereStructureDTO ministereSDTO) {
        MinistereStructure ministere_S = ministereStructureMapper.toEntity(ministereSDTO);
        return ministereStructureRepository.save(ministere_S);

    }

    @Override
    public MinistereStructure update(MinistereStructure ministereS) {

        return ministereStructureRepository.save(ministereS);

    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MinistereStructure> get(Long id) {

        return ministereStructureRepository.findById(id);

    }

    @Override
    public Page<MinistereStructure> findAll(Pageable pageable) {
        if (SecurityUtils.isCurrentUserInRole(AppUtil.ADMIN)) {
            return ministereStructureRepository.findAllByStatutIsTrue(pageable);
        } else {
            //Ne pas retourner le MinistereTest au cas où le client n'est pas ADMIN
            return ministereStructureRepository.findAllByStatutIsTrue(AppUtil.BASIC_MINISTERE_CODE, pageable);
        }

    }

    @Override
    public Page<StructureDTO> findAllBeta(Pageable pageable) {
        Page<StructureDTO> responseMapped = null;
        if (SecurityUtils.isCurrentUserInRole(AppUtil.ADMIN)) {
            responseMapped = ministereStructureRepository.findAllByStatutIsTrue(pageable).map(ministereStructureMapper::toStructureDTO);
        } else {
            //Ne pas retourner le StructureTest au cas où le client n'est pas ADMIN
            responseMapped = ministereStructureRepository.findAllByStatutIsTrue(AppUtil.BASIC_MINISTERE_CODE, pageable).map(ministereStructureMapper::toStructureDTO);
        }
        return responseMapped;
    }

    @Override
    public Page<StructureDTO> findAllStructureByMinistere(long ministereId, Pageable pageable) {
        Page<StructureDTO> responseMapped = ministereStructureRepository
                .findByMinistereIdAndStatutIsTrue(ministereId, pageable).map(ministereStructureMapper::toStructureDTO);
        return responseMapped;
    }

    @Override
    public void delete(Long code) {
        ministereStructureRepository.deleteById(code);
    }
}
