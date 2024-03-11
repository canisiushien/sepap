/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mfptps.appdgessddi.config;

import com.mfptps.appdgessddi.entities.Exercice;
import com.mfptps.appdgessddi.entities.Ministere;
import com.mfptps.appdgessddi.entities.MinistereStructure;
import com.mfptps.appdgessddi.entities.Structure;
import com.mfptps.appdgessddi.enums.ExerciceStatus;
import com.mfptps.appdgessddi.enums.TypeStructure;
import com.mfptps.appdgessddi.repositories.ExerciceRepository;
import com.mfptps.appdgessddi.repositories.MinistereRepository;
import com.mfptps.appdgessddi.repositories.MinistereStructureRepository;
import com.mfptps.appdgessddi.repositories.StructureRepository;
import com.mfptps.appdgessddi.service.AgentService;
import com.mfptps.appdgessddi.service.CustomException;
import com.mfptps.appdgessddi.utils.AppUtil;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import javax.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Configuration
public class AppDataInitializer {

    private final ExerciceRepository exerciceRepository;

    private final MinistereRepository ministereRepository;

    private final StructureRepository structureRepository;

    private final MinistereStructureRepository ministereStructureRepository;

    private final AgentService agentService;

    public AppDataInitializer(ExerciceRepository exerciceRepository,
            MinistereRepository ministereRepository,
            StructureRepository structureRepository,
            MinistereStructureRepository ministereStructureRepository,
            AgentService agentService) {
        this.exerciceRepository = exerciceRepository;
        this.ministereRepository = ministereRepository;
        this.structureRepository = structureRepository;
        this.ministereStructureRepository = ministereStructureRepository;
        this.agentService = agentService;
    }

    @PostConstruct
    public void initialization() {
        //save Exercices
        if (0 == exerciceRepository.count()
                || !exerciceRepository.findByStatut(ExerciceStatus.EN_COURS).isPresent()
                || !exerciceRepository.findByStatut(ExerciceStatus.EN_ATTENTE).isPresent()) {
            LocalDate dateDebut = LocalDate.of(LocalDate.now().getYear(), Month.JANUARY, 1);
            LocalDate dateFin = LocalDate.of(LocalDate.now().getYear(), Month.DECEMBER, 31);
            Exercice exerciceCourant = new Exercice();
            Exercice exerciceAVenir = new Exercice();

            exerciceCourant.setDebut(dateDebut);
            exerciceCourant.setFin(dateFin);
            exerciceCourant.setStatut(ExerciceStatus.EN_COURS);

            exerciceAVenir.setDebut(dateDebut.plusYears(1));
            exerciceAVenir.setFin(dateFin.plusYears(1));
            exerciceAVenir.setStatut(ExerciceStatus.EN_ATTENTE);

            exerciceRepository.saveAll(Arrays.asList(exerciceCourant, exerciceAVenir));
        }

        //save Basic Ministere and Structure
        Optional<Ministere> basicExisting = ministereRepository.findByCode(AppUtil.BASIC_MINISTERE_CODE);
        if (!basicExisting.isPresent()) {
            this.recordBasicMinistereAndStrucuture();
        }

    }

    /**
     *
     */
    void recordBasicMinistereAndStrucuture() {
        try {
            Ministere ministere = new Ministere();
            Structure structure = new Structure();
            MinistereStructure ministereStructure = new MinistereStructure();

            ministere.setCode(AppUtil.BASIC_MINISTERE_CODE);
            ministere.setLibelle(AppUtil.BASIC_MINISTERE_LABEL);
            ministere.setSigle(AppUtil.BASIC_MINISTERE_SIGLE);
            ministereRepository.save(ministere);

            structure.setLibelle(AppUtil.BASIC_STRUCTURE_LABEL);
            structure.setSigle(AppUtil.BASIC_STRUCTURE_SIGLE);
            structure.setNiveau(1);
            structure.setType(TypeStructure.CENTRALE);
            structure.setTelephone(AppUtil.BASIC_STRUCTURE_TELEPHONE);
            structure.setEmailResp(AppUtil.BASIC_STRUCTURE_EMAIL);
            structure.setEmailStruct(AppUtil.BASIC_STRUCTURE_EMAIL);
            structureRepository.save(structure);

            ministereStructure.setMinistere(ministere);
            ministereStructure.setStructure(structure);
            ministereStructure.setDateDebut(new Date());
            ministereStructureRepository.save(ministereStructure);

            //Join system users to new basic structure
            agentService.affectationAgent("admin", structure.getId());
            agentService.affectationAgent("user", structure.getId());
        } catch (Exception e) {
            throw new CustomException("Une erreur s'est produite lors de l'initialisation des ministère et structure de base.");
        }
    }

}
