package com.mfptps.appdgessddi.service;

import com.mfptps.appdgessddi.entities.Structure;
import com.mfptps.appdgessddi.service.dto.ChangeMinistereDTO;
import com.mfptps.appdgessddi.service.dto.StructureDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StructureService {

    Structure create(StructureDTO structure);

    Structure update(Structure structure);

    Optional<Structure> get(long id);

    Page<Structure> findAll(Pageable pageable);

    void delete(Long code);

    Structure changementMinistere(ChangeMinistereDTO changeMinistereDTO);
}
