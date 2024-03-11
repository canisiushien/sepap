package com.mfptps.appdgessddi.service.impl;

import com.mfptps.appdgessddi.entities.Contribuer;
import com.mfptps.appdgessddi.entities.EvaluationGouvernance;
import com.mfptps.appdgessddi.entities.Exercice;
import com.mfptps.appdgessddi.entities.Performance;
import com.mfptps.appdgessddi.entities.Performer;
import com.mfptps.appdgessddi.entities.Ponderation;
import com.mfptps.appdgessddi.entities.Structure;
import com.mfptps.appdgessddi.enums.TypeStructure;
import com.mfptps.appdgessddi.repositories.ContribuerRepository;
import com.mfptps.appdgessddi.repositories.EvaluationGouvernanceRepository;
import com.mfptps.appdgessddi.repositories.EvaluationRepository;
import com.mfptps.appdgessddi.repositories.ExerciceRepository;
import com.mfptps.appdgessddi.repositories.GrillePerformanceRepository;
import com.mfptps.appdgessddi.repositories.PerformanceRepository;
import com.mfptps.appdgessddi.repositories.PerformerRepository;
import com.mfptps.appdgessddi.repositories.PonderationRepository;
import com.mfptps.appdgessddi.repositories.ProgrammationRepository;
import com.mfptps.appdgessddi.repositories.StructureRepository;
import com.mfptps.appdgessddi.service.PerformerService;
import com.mfptps.appdgessddi.service.dto.PerformanceDTO;
import com.mfptps.appdgessddi.service.dto.PerformerDTO;
import com.mfptps.appdgessddi.service.mapper.PerformerMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class PerformerServiceImpl implements PerformerService {

    private final PerformerRepository performerRepository;
    private final PerformerMapper performerMapper;
    private final GrillePerformanceRepository grilleRepository;
    private final StructureRepository structureRepository;
    private final ProgrammationRepository programmationRepository;
    private final PonderationRepository ponderationRepository;
    private final EvaluationGouvernanceRepository evaluationGouvernanceRepository;
    private final PerformanceRepository performanceRepository;
    private final EvaluationRepository evaluationRepository;
    private final ContribuerRepository contribuerRepository;
    private final ExerciceRepository exerciceRepository;

    public PerformerServiceImpl(PerformerRepository performerRepository, PerformerMapper performerMapper,
            GrillePerformanceRepository grilleRepository, StructureRepository structureRepository,
            ProgrammationRepository programmationRepository, PonderationRepository ponderationRepository,
            EvaluationGouvernanceRepository evaluationGouvernanceRepository, PerformanceRepository performanceRepository,
            EvaluationRepository evaluationRepository, ContribuerRepository contribuerRepository, ExerciceRepository exerciceRepository) {

        this.performerRepository = performerRepository;
        this.performerMapper = performerMapper;
        this.grilleRepository = grilleRepository;
        this.structureRepository = structureRepository;
        this.programmationRepository = programmationRepository;
        this.ponderationRepository = ponderationRepository;
        this.evaluationGouvernanceRepository = evaluationGouvernanceRepository;
        this.performanceRepository = performanceRepository;
        this.evaluationRepository = evaluationRepository;
        this.contribuerRepository = contribuerRepository;
        this.exerciceRepository = exerciceRepository;
    }

    @Override
    public Performer create(PerformerDTO performerDTO) {
        Performer performer = performerMapper.toEntity(performerDTO);
        return performerRepository.save(performer);
    }

    @Override
    public Performer update(Performer performer) {
        return performerRepository.save(performer);
    }

    @Override
    public Optional<Performer> get(Long id) {
        return performerRepository.findById(id);
    }

    @Override
    public Page<Performer> findAll(Pageable pageable) {
        return performerRepository.findAll(pageable);
    }

    @Override
    public void delete(Long id) {
        performerRepository.deleteById(id);
    }

    //======= section de calcul des performances
    @Override
    public PerformanceDTO calculatePerformance(Long ministerId, Long structureId, Long exerciceId, Long userId) {
        PerformanceDTO performance = new PerformanceDTO();

        // variables de stockage des éléments de performance
        double efficacite = 0;
        double efficience = 0;
        double gouvernance = 0;
        double impact = 0;

        double pgs = 0;
        String appreciation;
        Optional<Double> tmp;//variable temporaire utilisee pour controles et affectations

        // Déclaration de la liste des structures concernées
        List<Structure> mesStructures = new ArrayList<>();

        // vérification, savoir si c'est tout le ministère ou si c'est juste une structure 
        boolean many = ((ministerId != null) && (structureId == null));

        // récupération de la podération par défaut
        Ponderation ponderation = ponderationRepository.findActivePonderation().orElse(null);

        // récupération de l'exercice par défaut
        Exercice exercice = exerciceRepository.findById(exerciceId).orElse(null);

        if (many) {
            mesStructures = structureRepository.findMinistereStructure(ministerId, TypeStructure.INTERNE);
        } else {
            Structure singleStructure = new Structure();
            singleStructure.setId(structureId);
            mesStructures.add(singleStructure);
        }

        // compteur des structures
        int count = mesStructures.size();

        // Parcours des structures
        for (Structure structure : mesStructures) {

            Performance perf = performanceRepository.findCurrentStructurePerformance(structure.getId(), exercice.getId()).orElse(new Performance());
            perf.setPonderation(ponderation);
            perf.setExercice(exercice);
            perf.setStructure(structure);

            // récupération des évaluations(ou indicateurs) de gouvernance
            List<EvaluationGouvernance> evalGouv = evaluationGouvernanceRepository.findStructureEvaluation(structure.getId(), exercice.getId()); // changment du type

//            if (evalGouv.isEmpty()) {
//                throw new CustomException("Données de gouvernance non renseignées !");
//            }
            // nombre total d'activités programmées
            long nap = programmationRepository.countStructureProgrammation(structure.getId(), exercice.getId());
            // =+ taux global de réalisation des objectifs TGRO; 
            // ce taux vient de la somme des taux d'exécution par structure, cette valeur est stocquée dans la table évaluation
            tmp = evaluationRepository.findEvaluation(structure.getId(), exercice.getId());

            double tgro = tmp.isPresent() ? tmp.get() : 0;

            // nombre activités réalisées à temps
            long nart = programmationRepository.countActiviteRealiserATemps(structure.getId(), exercice.getId());
            // =+ coefficient temps CT
            double coeffTemps = ((nap > 0) ? nart / Double.valueOf(nap) : 0);
            // calcul de l'efficacité ea
            double ea = (tgro * 60 + coeffTemps * 40) / 100;
            ea = (ea >= 0) ? ea : 0;

            // AJout
            perf.setTgro(tgro);
            perf.setCoefftemps(coeffTemps);
            perf.setEfficacite(ea);
            efficacite = (ea >= 0) ? (efficacite + ea) : efficacite;
            // ====== + FIN CALCULS ea =====//
            // calcul de l'efficience ei
            double ei = 0;

            // =+ montant total dépensée
            tmp = programmationRepository.coutReelStructureProgrammation(structure.getId(), exercice.getId());
            double montant_total = tmp.isPresent() ? tmp.get() : 1;
            // =+ somme des couts prévisionnels des activités réalisées à 100%
            tmp = programmationRepository.coutPrevsionnelStructureProgrammation_(structure.getId(), exercice.getId());
            double cout_previsionnel = tmp.isPresent() ? tmp.get() : 0;
            // =+ somme des couts réels des activités réalisées à 100%
            tmp = programmationRepository.coutEffectifStructureProgrammation(structure.getId(), exercice.getId());
            double cout_effectif = tmp.isPresent() ? tmp.get() : 0;

            if (montant_total > 0) {
                ei = (cout_previsionnel - cout_effectif) / montant_total;
            }

            perf.setEfficience(ei);
            efficience = (ei >= 0) ? (efficience + ei) : efficience;

            // calcul de la gouvernance gouv
            double gouv = 0;

            if (!evalGouv.isEmpty()) {
                for (EvaluationGouvernance eval : evalGouv) {
                    gouv = gouv + (eval.getValeur() / eval.getValeurReference());
                }
                // après addition on fait la moyenne
                gouv = (gouv >= 0) ? (gouv / evalGouv.size()) : 0;
            }

            perf.setGouvernance(gouv);
            gouvernance = (gouv >= 0) ? (gouvernance + gouv) : gouvernance;

            // calcul de l'impact imp
            double imp = 0;

            if (ponderation.getImpact() > 0) {
                // traitement spécifique ici
                // list des impacts définis par la DGESS

                List<Contribuer> contribs = contribuerRepository.findAllStructureContribution(structure.getId(), exercice.getId());
                int nombre = contribs.size();

                for (Contribuer contrib : contribs) {
                    // I = (VA – VR) / (Cible-VR) x 100 où VA = valeur atteinte = Valeur dans Contribuer 
                    //et VR = Valeur référence i.e cible dans Contribuer 
                    //et Cible correspond à cible dans ParametrerImpact
                    double denominateur = (contrib.getParametrerImpact().getCible() - contrib.getCible());
                    if (denominateur != 0) {
                        imp = imp + (((contrib.getValeur() - contrib.getCible()) / denominateur) * 100);
                    }

                }
                if (nombre > 0) {
                    imp = imp / nombre;//(imp >= 0) ? (imp / nombre) : 0;
                }
            }
            perf.setImpact(imp);

            impact = impact + imp;//(imp >= 0) ? (impact + imp) : impact;

            // calcul de la performance globale de la structure
            double pg = ((ponderation.getEfficacite() * ea)
                    + (ponderation.getEfficience() * ei)
                    + (ponderation.getGouvernance() * gouv)
                    + (ponderation.getImpact() * imp)) / 100;

            perf.setPgs(pg);

            // Sauvegarde de la performance par structure
            appreciation = grilleRepository.findGrilleAppreciation(pg).orElse("");
            perf.setAppreciation(appreciation);
            performanceRepository.save(perf);

            pgs = (pg >= 0) ? (pgs + pg) : pgs;

        }

        // chargement des évaluations par structure
        // calcul de la moyenne si nécéssaire
        efficacite = efficacite / count;
        efficience = efficience / count;
        gouvernance = gouvernance / count;
        impact = impact / count;
        pgs = pgs / count;
        appreciation = grilleRepository.findGrilleAppreciation(pgs).orElse("");

        // dernières données
        performance.setGlobal(mesStructures.size() > 1);
        performance.setEfficacite(efficacite);
        performance.setEfficience(efficience);
        performance.setImpact(impact);
        performance.setGouvernance(gouvernance);
        performance.setPgm(pgs);
        performance.setPgs(pgs);
        performance.setAppreciation(appreciation);

        return performance;
    }
}
