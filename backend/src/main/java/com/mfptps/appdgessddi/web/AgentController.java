package com.mfptps.appdgessddi.web;

import com.mfptps.appdgessddi.config.Constants;
import com.mfptps.appdgessddi.entities.Agent;
import com.mfptps.appdgessddi.repositories.AgentRepository;
import com.mfptps.appdgessddi.service.AgentService;
import com.mfptps.appdgessddi.service.dto.AgentDTO;
import com.mfptps.appdgessddi.service.dto.AgentStructureDTO;
import com.mfptps.appdgessddi.utils.*;
import com.mfptps.appdgessddi.web.exceptions.*;
import com.mfptps.appdgessddi.web.vm.AffectationVM;
import com.mfptps.appdgessddi.web.vm.ManagedAgentVM;
import com.mfptps.appdgessddi.web.vm.ProfilerVM;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import javax.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * REST controller for managing agents.
 * <p>
 * This class accesses the {@link Agent} entity, and needs to fetch its
 * collection of profiles.
 * <p>
 * For a normal use-case, it would be better to have an eager relationship
 * between Agent and Authority, and send everything to the client side: there
 * would be no View Model and DTO, a lot less code, and an outer-join which
 * would be good for performance.
 * <p>
 * We use a View Model and a DTO for 3 reasons:
 * <ul>
 * <li>We want to keep a lazy association between the agent and the profiles,
 * because people will quite often do relationships with the agent, and we don't
 * want them to get the profiles all the time for nothing (for performance
 * reasons). This is the #1 goal: we should not impact our agents' application
 * because of this use-case.</li>
 * <li> Not having an outer join causes n+1 requests to the database. This is
 * not a real issue as we have by default a second-level cache. This means on
 * the first HTTP call we do the n+1 requests, but then all profiles come from
 * the cache, so in fact it's much better than doing an outer join (which will
 * get lots of data from the database, for each HTTP call).</li>
 * <li> As this manages agents, for security reasons, we'd rather have a DTO
 * layer.</li>
 * </ul>
 * <p>
 * Another option would be to have a specific JPA entity graph to handle this
 * case.
 */
@RestController
@RequestMapping("/api")
public class AgentController {

    private final Logger log = LoggerFactory.getLogger(AgentController.class);

    @Value("${application.name}")
    private String applicationName;

    private static final String ENTITY_NAME = "agent";

    private final AgentService agentService;

    private final AgentRepository agentRepository;

    public AgentController(AgentService agentService, AgentRepository agentRepository) {
        this.agentService = agentService;
        this.agentRepository = agentRepository;
    }

    /**
     * {@code POST  /agents} : Creates a new agent.
     * <p>
     * The agent needs to be activated on creation.
     *
     * @param agentDTO the agent to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and
     * with body the new agent, or with status {@code 400 (Bad Request)} if the
     * login or matricule is already in use.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     * @throws BadRequestAlertException {@code 400 (Bad Request)} if the login
     * is already in use.
     */
    @PostMapping("/agents")
    //@PreAuthorize("hasAnyAuthority(\"ROLE_ADMIN\")")
    public ResponseEntity<Agent> createAgent(@Valid @RequestBody ManagedAgentVM agentDTO) throws URISyntaxException {
        log.debug("REST request to save Agent : {}", agentDTO);

        if (!checkPasswordLength(agentDTO.getPassword())) {
            throw new InvalidPasswordException();
        }

        if (agentDTO.getId() != null) {
            throw new BadRequestAlertException("A new agent cannot already have an ID", "agentManagement", "idexists");
            // Lowercase the agent login before comparing with database
        }

        if (agentRepository.findOneByMatricule(agentDTO.getMatricule().toLowerCase()).isPresent()) {
            throw new LoginAlreadyUsedException();
        }

        Agent newAgent = agentService.createAgent(agentDTO, agentDTO.getPassword());
        return ResponseEntity.created(new URI("/api/agents/" + newAgent.getMatricule()))
                .headers(HeaderUtil.createAlert(applicationName, "agentManagement.created", newAgent.getMatricule()))
                .body(newAgent);
    }

    @PostMapping("/agents/affectation")
    public ResponseEntity<Agent> affecterAgent(@Valid @RequestBody AffectationVM affectationVM) throws URISyntaxException {
        log.debug("REST request to affect Agent : {}", affectationVM.getUsername());

        Agent newAgent = agentService.affectationAgent(affectationVM.getUsername(), affectationVM.getStructureId());
        return ResponseEntity.created(new URI("/api/agents/" + newAgent.getMatricule()))
                .headers(HeaderUtil.createAlert(applicationName, "Affectation.created", newAgent.getMatricule()))
                .body(newAgent);
    }

    /**
     * {@code PUT /agents} : Updates an existing Agent.
     *
     * @param agentDTO the agent to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with
     * body the updated agent.
     *
     * @throws LoginAlreadyUsedException {@code 400 (Bad Request)} if the login
     * is already in use.
     */
    @PutMapping("/agents")
    // @PreAuthorize("hasAnyAuthority(\"ROLE_ADMIN\")")
    public ResponseEntity<AgentDTO> updateAgent(@Valid @RequestBody AgentDTO agentDTO) {
        log.debug("REST request to update Agent : {}", agentDTO);

        Optional<Agent> existingAgent = agentRepository.findOneByMatricule(agentDTO.getMatricule().toLowerCase());
        if (existingAgent.isPresent() && (!existingAgent.get().getId().equals(agentDTO.getId()))) {
            throw new LoginAlreadyUsedException();
        }
        Optional<AgentDTO> updatedAgent = agentService.updateAgent(agentDTO);

        return ResponseUtil.wrapOrNotFound(updatedAgent,
                HeaderUtil.createAlert(applicationName, "agentManagement.updated", agentDTO.getMatricule()));
    }

    /**
     * {@code GET /agents} : get all agents.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with
     * body all agents.
     */
    @GetMapping("/agents")
    public ResponseEntity<List<AgentDTO>> getAllAgents(Pageable pageable) {
        final Page<AgentDTO> page = agentService.getAllManagedAgents(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * Liste tous les agents avec controle ADMIN
     *
     * @param structureId
     * @param pageable
     * @return
     */
    @GetMapping("/agents/{ids}")
    public ResponseEntity<List<AgentDTO>> getAllAgentsByStructure(@PathVariable(name = "ids", required = true) Long structureId, Pageable pageable) {
        if (structureId == null) {
            throw new BadRequestAlertException("Veuillez renseigner le paramètre", applicationName, "idnull");
        }
        final Page<AgentDTO> page = agentService.getAllManagedAgentsByStructure(structureId, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /*
    * Liste l'agent avec sa structure
     */
    @GetMapping("/agents/struct/{id}")
    public ResponseEntity<AgentStructureDTO> getAgentWithStructure(@PathVariable(name = "id", required = true) Long id) {
        if (id == null) {
            throw new BadRequestAlertException("Veuillez renseigner le paramètre", applicationName, "idnull");
        }
        AgentStructureDTO page = agentService.getAgentsWithStructure(id);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    /**
     * Gets a list of all profiles.
     *
     * @return a string list of all profiles.
     */
    @GetMapping("/agents/profiles")
    // @PreAuthorize("hasAnyAuthority(\"ROLE_ADMIN\")")
    public List<String> getProfiles() {
        return agentService.getProfiles();
    }

    /**
     * {@code GET /agents/:login} : get the "login" agent.
     *
     * @param login the login of the agent to find.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with
     * body the "login" agent, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/agents/login/{login:" + Constants.LOGIN_REGEX + "}")
    // @PreAuthorize("#login == principal.username || hasAnyAuthority(\"ROLE_ADMIN\")")
    public ResponseEntity<AgentDTO> getAgent(@PathVariable String login) {
        log.debug("REST request to get Agent : {}", login);
        return ResponseUtil.wrapOrNotFound(
                agentService.getAgentWithProfilesByMatricule(login)
        );
    }

    /**
     * MODIFIE LE(S) PROFILE(S) D'UN AGENT
     *
     * @param profilerVM
     * @return
     */
    @PutMapping("/agents/profiles")
    public ResponseEntity<AgentDTO> attribuerProfiles(@Valid @RequestBody ProfilerVM profilerVM) {
        if (profilerVM.getMatricule() == null) {
            throw new BadRequestAlertException("Id invalide", ENTITY_NAME, "matriculenull");
        }

        Optional<AgentDTO> updatedAgent = agentService.attribuerProfiles(profilerVM.getMatricule(), profilerVM.getProfiles());

        if (!updatedAgent.isPresent()) {
            throw new CustomException("Agent " + profilerVM.getMatricule() + " introuvable !");
        }

        return ResponseUtil.wrapOrNotFound(updatedAgent,
                HeaderUtil.createAlert(applicationName, "agentManagement.updated", profilerVM.getMatricule()));
    }

    /**
     * {@code DELETE /agents/:login} : delete the "login" Agent.
     *
     * @param login the login of the agent to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/agents/{login:" + Constants.LOGIN_REGEX + "}")
    @PreAuthorize("hasAnyAuthority(\"ROLE_ADMIN\")")
    public ResponseEntity<Void> deleteAgent(@PathVariable String login) {
        log.debug("REST request to delete Agent: {}", login);
        agentService.deleteAgent(login);
        return ResponseEntity.noContent().headers(HeaderUtil.createAlert(applicationName, "agentManagement.deleted", login)).build();
    }

    private static boolean checkPasswordLength(String password) {
        return !StringUtils.isEmpty(password)
                && password.length() >= ManagedAgentVM.PASSWORD_MIN_LENGTH
                && password.length() <= ManagedAgentVM.PASSWORD_MAX_LENGTH;
    }
}
