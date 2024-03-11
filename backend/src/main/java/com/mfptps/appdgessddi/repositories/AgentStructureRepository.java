package com.mfptps.appdgessddi.repositories;

import com.mfptps.appdgessddi.entities.AgentStructure;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AgentStructureRepository extends JpaRepository<AgentStructure, Long> {

    Optional<AgentStructure> findByAgentIdAndActifTrue(Long id);

    /**
     * return the actif structure of the user. 08122021
     *
     * @param matricule : matricule or login of user
     * @param email :
     * @return
     */
    @Query("SELECT ast FROM AgentStructure ast, Agent a "
            + "WHERE ast.agent.id = a.id AND ast.actif = true "
            + "AND a.matricule = :matricule OR a.email = :email")
    AgentStructure findOneByAgentMatriculeOrAgentEmail(String matricule, String email);

}
