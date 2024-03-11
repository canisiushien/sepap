/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mfptps.appdgessddi.repositories;

import com.mfptps.appdgessddi.entities.Agent;
import com.mfptps.appdgessddi.service.dto.AgentStructureDTO;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author bieve
 */
public interface AgentRepository extends JpaRepository<Agent, Long> {

    String USERS_BY_LOGIN_CACHE = "usersByMatricule";
    String USERS_BY_EMAIL_CACHE = "usersByEmail";

    Optional<Agent> findOneByActivationKey(String activationKey);

    List<Agent> findAllByActifIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(Instant dateTime);

    Optional<Agent> findOneByResetKey(String resetKey);

    Optional<Agent> findOneByMatricule(String matricule);

    Optional<Agent> findOneByEmailIgnoreCase(String email);

    @EntityGraph(attributePaths = "profiles")
    @Cacheable(cacheNames = USERS_BY_LOGIN_CACHE)
    Optional<Agent> findOneWithProfilesByMatricule(String matricule);

    @EntityGraph(attributePaths = "profiles")
    @Cacheable(cacheNames = USERS_BY_EMAIL_CACHE)
    Optional<Agent> findOneWithProfilesByEmailIgnoreCase(String email);

    Page<Agent> findAllByIdNotNullAndActifIsTrue(Pageable pageable);

    Optional<Agent> findOneByMatriculeOrEmail(String matricule, String email);

    /**
     * return user created by system. 08122021
     *
     * @param matricule
     * @return
     */
    @Query("SELECT a FROM Agent a "
            + "WHERE a.actif = true "
            + "AND a.matricule = :matricule "
            + "AND a.createdBy = 'system' ")
    Agent findAgentSystemByMatricule(@Param("matricule") String matricule);

    @Query(value = "SELECT a.* FROM agent a, agent_profile g, profile p "
            + "WHERE a.actif = true "
            + "AND a.id = g.agent_id "
            + "AND g.profile_id = p.id "
            + "AND (p.name = 'FOCAL_STRUCT' "
            + "OR p.name = 'RESP_STRUCT') ", nativeQuery = true)
    List<Agent> findAllAgentByProfiles();

    @Query("SELECT a FROM Agent a, AgentStructure ast "
            + "WHERE a.actif = true AND a.deleted = false "
            + "AND ast.structure.id = :structureId AND a.id = ast.agent.id AND ast.actif = true")
    Page<Agent> findAllByStructure(@Param("structureId") long structureId, Pageable pageable);

    @Query(value = "SELECT new com.mfptps.appdgessddi.service.dto.AgentStructureDTO (a.id, a.matricule,a.nom,a.prenom,a.telephone,a.email,a.actif, s.libelle) "
            + "FROM Agent a, AgentStructure ags, Structure s "
            + "WHERE ags.structure.id = s.id "
            + "AND a.id = :id "
            + "AND ags.agent.id = a.id ")
    AgentStructureDTO findAgentById(@Param("id") long id);
}
