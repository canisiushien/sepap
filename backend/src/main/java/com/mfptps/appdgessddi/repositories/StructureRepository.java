package com.mfptps.appdgessddi.repositories;

import com.mfptps.appdgessddi.entities.Structure;
import com.mfptps.appdgessddi.enums.TypeStructure;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StructureRepository extends JpaRepository<Structure, Long> {

    @Query("SELECT s FROM Structure s, Programmation p, Tache t "
            + "WHERE t.id = :tacheId "
            + "AND t.deleted = false "
            + "AND t.programmation.id = p.id "
            + "AND p.structure.id = s.id "
            + "AND p.deleted = false "
            + "AND s.deleted = false")
    Optional<Structure> findStructureById(Long tacheId);

    List<Structure> findByParentId(Long id);
    
    @Query("SELECT s FROM Structure s, MinistereStructure ms, Ministere m "
            + "WHERE ms.ministere.id = m.id "
            + "AND m.id =:ministerId "
            + "AND ms.structure.id = s.id "
            + "AND ms.statut = true "    
            + "AND s.type <>:excludeType "
            + "AND s.deleted = false")
    List<Structure> findMinistereStructure(Long ministerId, TypeStructure excludeType);
}
