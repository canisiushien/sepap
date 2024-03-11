package com.mfptps.appdgessddi.repositories;

import com.mfptps.appdgessddi.entities.MinistereStructure;
import com.mfptps.appdgessddi.entities.Structure;
import com.mfptps.appdgessddi.enums.TypeStructure;
import com.mfptps.appdgessddi.service.dto.statisticresponses.CountStructureGroupByType;
import java.util.List;
import java.util.Optional;
import javax.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MinistereStructureRepository extends JpaRepository<MinistereStructure, Long> {

    Optional<MinistereStructure> findByStructureIdAndStatutIsTrue(@NotNull long structureId);

    Page<MinistereStructure> findAllByStatutIsTrue(Pageable pageable);

    @Query("SELECT ms FROM MinistereStructure ms "
            + "WHERE ms.statut IS TRUE "
            + "AND ms.ministere.code != :codeMinistere")
    Page<MinistereStructure> findAllByStatutIsTrue(String codeMinistere, Pageable pageable);

    Page<MinistereStructure> findByMinistereIdAndStatutIsTrue(long ministereId, Pageable pageable);

    @Query("SELECT ms.structure FROM MinistereStructure ms "
            + "WHERE ms.ministere.id = :ministereId "
            // + "AND ms.structure.niveau = 1 "
            + "AND ms.statut IS TRUE")
    List<Structure> findAllStructuresByMinistere(long ministereId);

    @Query("SELECT COUNT(*) FROM MinistereStructure ms "
            + "WHERE ms.ministere.id = :id "
            + "AND ms.structure.niveau = 1 "
            + "AND ms.statut IS TRUE")
    long countStructureByMinistere(long id);

    @Query("SELECT new com.mfptps.appdgessddi.service.dto.statisticresponses.CountStructureGroupByType(s.type, COUNT(s.type)) "
            + "FROM MinistereStructure ms, Structure s "
            + "WHERE ms.ministere.id = :id "
            + "AND ms.structure.niveau = 1 AND ms.structure.id = s.id "
            + "AND ms.statut IS TRUE "
            + "GROUP BY s.type ORDER BY s.type ASC")
    List<CountStructureGroupByType> countStructureByMinistereAndByGroupType(long id);

    @Query("SELECT s FROM MinistereStructure ms, Structure s "
            + "WHERE ms.ministere.id =:ministereId "
            + "AND ms.structure.id=s.id "
            + "AND ms.statut IS TRUE AND s.type<>:type")
    List<Structure> allNonInternalStructureByMinistere(long ministereId, TypeStructure type);

    @Query("SELECT ms.structure FROM MinistereStructure ms, Agent a, AgentStructure ags "
            + "WHERE a.deleted = false AND a.matricule = :matricule "
            + "AND a.id = ags.agent.id AND ags.actif = true "
            + "AND ags.structure.id = ms.structure.id AND ms.statut = true")
    Optional<Structure> findByAgentMatricule(String matricule);
}
