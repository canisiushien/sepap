package com.mfptps.appdgessddi.service;

import com.mfptps.appdgessddi.entities.*;
import com.mfptps.appdgessddi.repositories.*;
import com.mfptps.appdgessddi.security.SecurityUtils;
import com.mfptps.appdgessddi.service.dto.AgentDTO;
import com.mfptps.appdgessddi.service.dto.AgentStructureDTO;
import com.mfptps.appdgessddi.utils.AppUtil;
import com.mfptps.appdgessddi.utils.RandomUtil;
import com.mfptps.appdgessddi.web.exceptions.CustomException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Bieve Service class for managing agents.
 */
@Service
@Transactional
public class AgentService {

    private final Logger log = LoggerFactory.getLogger(AgentService.class);

    private final AgentRepository agentRepository;

    private final PasswordEncoder passwordEncoder;

    private final ProfileRepository profileRepository;

    private final StructureRepository structureRepository;

    private final AgentStructureRepository agentStructureRepository;

    private final CacheManager cacheManager;

    public AgentService(AgentRepository agentRepository, PasswordEncoder passwordEncoder,
            ProfileRepository profileRepository, CacheManager cacheManager, StructureRepository structureRepository, AgentStructureRepository agentStructureRepository) {
        this.agentRepository = agentRepository;
        this.passwordEncoder = passwordEncoder;
        this.profileRepository = profileRepository;
        this.cacheManager = cacheManager;
        this.structureRepository = structureRepository;
        this.agentStructureRepository = agentStructureRepository;
    }

    public Optional<Agent> activateRegistration(String key, String password) {
        log.debug("Activating agent for activation key {}", key);
        return agentRepository.findOneByActivationKey(key)
                .map(agent -> {
                    // activate given agent for the registration key.
                    String encryptedPassword = passwordEncoder.encode(password);
                    agent.setPassword(encryptedPassword);
                    agent.setActif(true);
                    agent.setActivationKey(null);
                    this.clearAgentCaches(agent);
                    log.debug("Activated agent: {}", agent);
                    return agent;
                });
    }

    public Optional<Agent> completePasswordReset(String newPassword, String key) {
        log.debug("Reset agent password for reset key {}", key);
        return agentRepository.findOneByResetKey(key)
                .filter(agent -> agent.getResetDate().isAfter(Instant.now().minusSeconds(86400)))
                .map(agent -> {
                    agent.setPassword(passwordEncoder.encode(newPassword));
                    agent.setResetKey(null);
                    agent.setResetDate(null);
                    agent.setActif(true);
                    this.clearAgentCaches(agent);
                    return agent;
                });
    }

    public Optional<Agent> requestPasswordReset(String mail) {
        Optional<Agent> result = agentRepository.findOneByEmailIgnoreCase(mail)
                .filter(Agent::isActif)
                .map(agent -> {
                    agent.setPassword(passwordEncoder.encode(AppUtil.DEFAULT_PASSWORD));//===========
                    agent.setActivationKey(RandomUtil.generateActivationKey());//========
                    //agent.setResetKey(RandomUtil.generateResetKey());
                    agent.setResetDate(Instant.now());
                    //agent.setActif(false);//=======

                    this.clearAgentCaches(agent);
                    return agent;
                });

        if (!result.isPresent()) {//==============
            throw new CustomException("Agent d'email " + mail + " désactivé ou inexistant !");
        }
        return result;
    }

    public Agent registerAgent(AgentDTO agentDTO, String password) {
        agentRepository.findOneByMatricule(agentDTO.getMatricule().toLowerCase()).ifPresent(existingAgent -> {
            boolean removed = removeNonActivatedAgent(existingAgent);
            if (!removed) {
                throw new UsernameAlreadyUsedException();
            }
        });

        Agent newAgent = new Agent();
        String encryptedPassword = passwordEncoder.encode(password);
        newAgent.setMatricule(agentDTO.getMatricule().toLowerCase());
        newAgent.setPassword(encryptedPassword);
        newAgent.setNom(agentDTO.getNom());
        newAgent.setPrenom(agentDTO.getPrenom());
        newAgent.setTelephone(agentDTO.getTelephone());
        newAgent.setEmail(agentDTO.getEmail());
        // new agent is not active
        newAgent.setActif(false);
        // new agent gets registration key
        newAgent.setActivationKey(RandomUtil.generateActivationKey());
        Set<Profile> profiles = new HashSet<>();
        if (agentDTO.getProfiles() != null) {
            profiles = agentDTO.getProfiles().stream()
                    .map(profileRepository::findByName)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toSet());
            newAgent.setProfiles(profiles);
        } else {
            profiles.add(profileRepository.findByName("USER").get());
            newAgent.setProfiles(profiles);
        }

        agentRepository.save(newAgent);
        if (null != agentDTO.getStructureId()) {
            Structure structure = structureRepository.findById(agentDTO.getStructureId()).orElseThrow(() -> new CustomException("Structure with id = " + agentDTO.getStructureId() + " does not exist !"));
//            newAgent.setStructure(structure);
            AgentStructure agentStructure = new AgentStructure();
            agentStructure.setAgent(newAgent);
            agentStructure.setStructure(structure);
            agentStructureRepository.save(agentStructure);
        }
        this.clearAgentCaches(newAgent);
        log.debug("Created Information for Agent: {}", newAgent);
        return newAgent;
    }

    public Optional<AgentDTO> attribuerProfiles(String matricule, Set<String> profiles) {
        return Optional.of(agentRepository
                .findOneByMatricule(matricule))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(agent -> {
                    this.clearAgentCaches(agent);

                    Set<Profile> managedProfiles = agent.getProfiles();
                    managedProfiles.clear();
                    profiles.stream()
                            .map(profileRepository::findByName)
                            .filter(Optional::isPresent)
                            .map(Optional::get)
                            .forEach(managedProfiles::add);

                    this.clearAgentCaches(agent);

                    log.debug("Changed Profiles for Agent: {}", agent);

                    return agent;
                })
                .map(AgentDTO::new);

    }

    public Agent affectationAgent(String matriculeOrMail, Long structureID) {
        Structure structure = structureRepository.findById(structureID).orElseThrow(() -> new CustomException("Structure with id = " + structureID + " does not exist !"));
        Agent agent = agentRepository.findOneByMatriculeOrEmail(matriculeOrMail, matriculeOrMail).orElseThrow(() -> new CustomException("Agent with matricule or email " + matriculeOrMail + " does not exist !"));
//        agent.setStructure(structure);
        AgentStructure entity = new AgentStructure();
        entity.setAgent(agent);
        entity.setStructure(structure);
        agentStructureRepository.findByAgentIdAndActifTrue(agent.getId()).ifPresent(oldAffectation -> oldAffectation.setActif(false));;
        agentStructureRepository.save(entity);
        return agent;
    }

    private boolean removeNonActivatedAgent(Agent existingAgent) {
        if (existingAgent.isActif()) {
            return false;
        }
        agentRepository.delete(existingAgent);
        agentRepository.flush();
        this.clearAgentCaches(existingAgent);
        return true;
    }

    public Agent createAgent(AgentDTO agentDTO, String password) {
        Agent agent = new Agent();
        agent.setMatricule(agentDTO.getMatricule().toLowerCase());
        agent.setNom(agentDTO.getNom());
        agent.setPrenom(agentDTO.getPrenom());
        agent.setCreatedBy(agentDTO.getCreatedBy());
        agent.setTelephone(agentDTO.getTelephone());
        String encryptedPassword = passwordEncoder.encode(password);
        agent.setPassword(encryptedPassword);
        agent.setResetKey(RandomUtil.generateResetKey());
        agent.setResetDate(Instant.now());
        agent.setActif(agentDTO.isActif());
        if (agentDTO.getProfiles() != null) {
            Set<Profile> profiles = agentDTO.getProfiles().stream()
                    .map(profileRepository::findByName)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toSet());
            agent.setProfiles(profiles);
        }
        agentRepository.save(agent);
        this.clearAgentCaches(agent);
        log.debug("Created Information for Agent: {}", agent);
        return agent;
    }

    /**
     * Update all information for a specific agent, and return the modified
     * agent.
     *
     * @param agentDTO agent to update.
     * @return updated agent.
     */
    public Optional<AgentDTO> updateAgent(AgentDTO agentDTO) {

        return Optional.of(agentRepository
                .findById(agentDTO.getId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(agent -> {
                    this.clearAgentCaches(agent);
                    agent.setMatricule(agentDTO.getMatricule().toLowerCase());
                    agent.setNom(agentDTO.getNom());
                    agent.setPrenom(agentDTO.getPrenom());
                    agent.setTelephone(agentDTO.getTelephone());
                    agent.setEmail(agentDTO.getEmail());
                    agent.setActif(agentDTO.isActif());
                    Set<Profile> managedProfiles = agent.getProfiles();
                    managedProfiles.clear();
                    agentDTO.getProfiles().stream()
                            .map(profileRepository::findByName)
                            .filter(Optional::isPresent)
                            .map(Optional::get)
                            .forEach(managedProfiles::add);
                    this.clearAgentCaches(agent);
                    log.debug("Changed Information for Agent: {}", agent);
                    return agent;
                })
                .map(AgentDTO::new);
    }

    public void deleteAgent(String matricule) {
        agentRepository.findOneByMatricule(matricule).ifPresent(agent -> {
            agentRepository.deleteById(agent.getId());
            this.clearAgentCaches(agent);
            log.debug("Deleted Agent: {}", agent);
        });
    }

    /**
     * Update basic information (nom, prenom, email) for the current agent.
     *
     * @param nom nom of agent.
     * @param prenom prenom of agent.
     * @param email email of agent.
     */
    public void updateAgent(String nom, String prenom, String email) {
        SecurityUtils.getCurrentUserMatricule()
                .flatMap(agentRepository::findOneByMatricule)
                .ifPresent(agent -> {
                    agent.setNom(nom);
                    agent.setPrenom(prenom);
                    if (email != null) {
                        agent.setEmail(email.toLowerCase());
                    }
                    this.clearAgentCaches(agent);
                    log.debug("Changed Information for Agent: {}", agent);
                });
    }

    @Transactional
    public void changePassword(String currentClearTextPassword, String newPassword) {
        SecurityUtils.getCurrentUserMatricule()
                .flatMap(agentRepository::findOneByMatricule)
                .ifPresent(agent -> {
                    String currentEncryptedPassword = agent.getPassword();
                    if (!passwordEncoder.matches(currentClearTextPassword, currentEncryptedPassword)) {
                        throw new InvalidPasswordException();
                    }
                    String encryptedPassword = passwordEncoder.encode(newPassword);
                    agent.setPassword(encryptedPassword);
                    this.clearAgentCaches(agent);
                    log.debug("Changed password for Agent: {}", agent);
                });
    }

    @Transactional(readOnly = true)
    public Page<AgentDTO> getAllManagedAgents(Pageable pageable) {
        return agentRepository.findAll(pageable).map(AgentDTO::new);
    }

    /**
     * Liste les agents par structure
     *
     * @param structureId
     * @param pageable
     * @return
     */
    @Transactional(readOnly = true)
    public Page<AgentDTO> getAllManagedAgentsByStructure(long structureId, Pageable pageable) {
        Page<AgentDTO> result;

        if (SecurityUtils.isCurrentUserInRole(AppUtil.ADMIN)) {//S'il sagit d'un admin, on renvoie tous les agents sans exception
            result = agentRepository.findAll(pageable).map(AgentDTO::new);
        } else {
            result = agentRepository.findAllByStructure(structureId, pageable).map(AgentDTO::new);
        }
        return result;
    }

    /*
    * liste l'agent avec la structure
     */
    @Transactional(readOnly = true)
    public AgentStructureDTO getAgentsWithStructure(long id) {
        return agentRepository.findAgentById(id);
    }

    @Transactional(readOnly = true)
    public Optional<AgentDTO> getAgentWithProfilesByMatricule(String matricule) {
        return agentRepository.findOneWithProfilesByMatricule(matricule).map(AgentDTO::new);
    }

    /* @Transactional(readOnly = true)
    public Optional<Agent> getAgentWithProfiles() {
        return SecurityUtils.getCurrentUserMatricule().flatMap(agentRepository::findOneWithProfilesByMatricule);
    } */
    @Transactional(readOnly = true)
    public Optional<AgentDTO> getAgentWithProfiles() {
        return SecurityUtils.getCurrentUserMatricule().flatMap(agentRepository::findOneWithProfilesByMatricule).map(AgentDTO::new);
    }

    /**
     * Not activated agents should be automatically deleted after 5 months.
     * <p>
     * This is scheduled to get fired everyday, at 01:00 (am).
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void removeNotActivatedAgents() {
        agentRepository
                .findAllByActifIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(Instant.now().minus(5, ChronoUnit.MONTHS))
                .forEach(agent -> {
                    log.debug("Deleting not activated agent {}", agent.getMatricule());
                    agentRepository.delete(agent);
                    this.clearAgentCaches(agent);
                });
    }

    /**
     * Gets a list of all the profiles.
     *
     * @return a list of all the profiles.
     */
    @Transactional(readOnly = true)
    public List<String> getProfiles() {
        return profileRepository.findAll().stream().map(Profile::getName).collect(Collectors.toList());
    }

    private void clearAgentCaches(Agent agent) {
        Objects.requireNonNull(cacheManager.getCache(AgentRepository.USERS_BY_LOGIN_CACHE)).evict(agent.getMatricule());
    }

    /**
     * Return the Structure of the agent who wants to connect. Added 08122021
     *
     * @param matriculeOrLogin
     * @return
     */
    public Long getStructureOfAgent(String matriculeOrLogin) {
        Long response = -2L;//agent attached to no structure. This is an exception in the process of saving agent
        Optional<AgentStructure> as = Optional.ofNullable(agentStructureRepository.findOneByAgentMatriculeOrAgentEmail(matriculeOrLogin, matriculeOrLogin));
        if (as.isPresent()) {
            response = as.get().getStructure().getId();
        } else {
            Optional<Agent> a = Optional.ofNullable(agentRepository.findAgentSystemByMatricule(matriculeOrLogin));
            if (a.isPresent()) {
                response = -1L;//if is it an agent system no joined to structure
            }
        }

        return response;
    }
}
