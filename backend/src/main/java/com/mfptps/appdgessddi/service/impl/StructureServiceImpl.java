package com.mfptps.appdgessddi.service.impl;

import com.mfptps.appdgessddi.entities.Ministere;
import com.mfptps.appdgessddi.entities.MinistereStructure;
import com.mfptps.appdgessddi.entities.Structure;
import com.mfptps.appdgessddi.repositories.MinistereRepository;
import com.mfptps.appdgessddi.repositories.MinistereStructureRepository;
import com.mfptps.appdgessddi.repositories.StructureRepository;
import com.mfptps.appdgessddi.service.CustomException;
import com.mfptps.appdgessddi.service.StructureService;
import com.mfptps.appdgessddi.service.dto.ChangeMinistereDTO;
import com.mfptps.appdgessddi.service.dto.StructureDTO;
import com.mfptps.appdgessddi.service.mapper.StructureMapper;
import java.util.Date;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StructureServiceImpl implements StructureService {

    private final StructureRepository structureRepository;
    private final MinistereStructureRepository ministereStructureRepository;
    private final MinistereRepository ministereRepository;
    private final StructureMapper structureMapper;

    public StructureServiceImpl(StructureRepository structureRepository,
            MinistereStructureRepository ministereStructureRepository,
            MinistereRepository ministereRepository,
            StructureMapper structureMapper) {
        this.structureRepository = structureRepository;
        this.ministereStructureRepository = ministereStructureRepository;
        this.ministereRepository = ministereRepository;
        this.structureMapper = structureMapper;
    }

    /**
     * ModifyBY Canisius . Add constraintes 14122021
     *
     * @param structureDTO
     * @return
     */
    @Override
    public Structure create(StructureDTO structureDTO) {
        Structure structure = structureMapper.toEntity(structureDTO);
        Ministere ministere = ministereRepository
                .findById(structureDTO.getMinistere().getId())
                .orElseThrow(() -> new CustomException("Le Ministère d'id " + structureDTO.getMinistere().getId() + " est inexistant."));

        if (structureDTO.getParent() != null) {
            MinistereStructure ministereStructureChecked = ministereStructureRepository
                    .findByStructureIdAndStatutIsTrue(structureDTO.getParent().getId())
                    .orElseThrow(() -> new CustomException("La structure parente n'est pas rattachée à un ministère."));
            if (ministere.getId() != ministereStructureChecked.getMinistere().getId()) {
                throw new CustomException("Veuillez selectionner le ministère approprié (" + structureDTO.getParent().getSigle() + " => " + ministereStructureChecked.getMinistere().getSigle() + ").");
            }
        }
        Structure structureSaved = structureRepository.save(structure);
        MinistereStructure ministereStructure = new MinistereStructure();
        ministereStructure.setMinistere(ministere);
        ministereStructure.setStructure(structureSaved);
        ministereStructure.setDateDebut(new Date());
        ministereStructureRepository.save(ministereStructure);
        return structureSaved;
    }

    @Override
    public Structure update(Structure structure) {
        if (structure.getParent() != null) {
            ministereStructureRepository
                    .findByStructureIdAndStatutIsTrue(structure.getParent().getId())
                    .orElseThrow(() -> new CustomException("La structure parente n'est pas rattachée à un ministère."));
        }
        return structureRepository.save(structure);
    }

    @Override
    public Optional<Structure> get(long id) {
        return structureRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Structure> findAll(Pageable pageable) {
        return structureRepository.findAll(pageable);

    }

    @Override
    public void delete(Long id) {
        structureRepository.deleteById(id);
    }

    /**
     * Modify by Canisius. 14122021
     *
     * @param structureId
     * @param ministereId
     * @return
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Structure changementMinistere(ChangeMinistereDTO changeMinistereDTO) {
        //Check if exists Objects Ministere and Structure
        Optional<Structure> existStructure = structureRepository.findById(changeMinistereDTO.getStructureId())
                .map(s -> {
                    if (changeMinistereDTO.getStructureParentId() != null) {//tenir compte des sous structures
                        s.setParent(structureRepository.findById(changeMinistereDTO.getStructureParentId()).get());
                    }
                    return s;
                });
        if (!existStructure.isPresent()) {
            throw new CustomException("La Structure d'id " + changeMinistereDTO.getStructureId() + " est inexistante.");
        }
        Ministere existMinistere = ministereRepository.findById(changeMinistereDTO.getMinistereId())
                .orElseThrow(() -> new CustomException("Ministere inexistant."));

        //find MinistereStructure element from bd
        MinistereStructure existMinistereStructure = ministereStructureRepository
                .findByStructureIdAndStatutIsTrue(existStructure.get().getId()).get();

        //New line to be add as MinistereStructure object
        MinistereStructure ministereStructure = new MinistereStructure();
        Date date = new Date();
        ministereStructure.setMinistere(existMinistere);
        ministereStructure.setStructure(existStructure.get());
        ministereStructure.setDateDebut(date);

        //update (set to disable) old MinistereStructure object
        existMinistereStructure.setStatut(false);
        existMinistereStructure.setDateFin(date);

        ministereStructureRepository.save(existMinistereStructure);
        MinistereStructure response = ministereStructureRepository.save(ministereStructure);

        //this.updateSubStructures(existStructure.get(), existMinistere);//change also all subStructures
        return structureRepository.findById(response.getStructure().getId()).get();
    }

    /**
     * Recursivity. added 14122021
     *
     * @param structureParent
     * @param ministere
     */
//    void updateSubStructures(Structure structureParent, Ministere ministere) {
//        List<Structure> subStructures = structureRepository.findByParentId(structureParent.getId());
//        for (Structure s : subStructures) {
//            this.changementMinistere(s.getId(), ministere.getId());
//        }
//    }
}
