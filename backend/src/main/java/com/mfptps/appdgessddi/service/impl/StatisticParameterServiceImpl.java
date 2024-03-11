/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mfptps.appdgessddi.service.impl;

import com.mfptps.appdgessddi.entities.Exercice;
import com.mfptps.appdgessddi.entities.Ministere;
import com.mfptps.appdgessddi.entities.Periode;
import com.mfptps.appdgessddi.entities.Structure;
import com.mfptps.appdgessddi.enums.ExerciceStatus;
import com.mfptps.appdgessddi.enums.TypeStructure;
import com.mfptps.appdgessddi.repositories.EvaluationRepository;
import com.mfptps.appdgessddi.repositories.ExerciceRepository;
import com.mfptps.appdgessddi.repositories.GrillePerformanceRepository;
import com.mfptps.appdgessddi.repositories.MinistereRepository;
import com.mfptps.appdgessddi.repositories.MinistereStructureRepository;
import com.mfptps.appdgessddi.repositories.PerformanceRepository;
import com.mfptps.appdgessddi.repositories.PeriodeRepository;
import com.mfptps.appdgessddi.repositories.ProgrammationRepository;
import com.mfptps.appdgessddi.repositories.StructureRepository;
import com.mfptps.appdgessddi.service.StatisticParameterService;
import com.mfptps.appdgessddi.service.dto.statisticresponses.AllEvolutionData;
import com.mfptps.appdgessddi.service.dto.statisticresponses.CountStructureGroupByType;
import com.mfptps.appdgessddi.service.dto.statisticresponses.EvolutionParam;
import com.mfptps.appdgessddi.service.dto.statisticresponses.MinistereGlobalPerfData;
import com.mfptps.appdgessddi.service.dto.statisticresponses.MinistereGlobalStatsBundleData;
import com.mfptps.appdgessddi.service.dto.statisticresponses.ResumerActiviteData;
import com.mfptps.appdgessddi.service.dto.statisticresponses.ResumerDepenseData;
import com.mfptps.appdgessddi.service.dto.statisticresponses.ResumerPerfData;
import com.mfptps.appdgessddi.service.dto.statisticresponses.ResumerSectorielData;
import com.mfptps.appdgessddi.service.dto.statisticresponses.ResumerSectorielDepenseData;
import com.mfptps.appdgessddi.service.dto.statisticresponses.ResumerStructureData;
import com.mfptps.appdgessddi.utils.AppUtil;
import com.mfptps.appdgessddi.utils.MinistereEvolutionBundle;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import static java.util.Calendar.YEAR;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Slf4j
@Service
public class StatisticParameterServiceImpl implements StatisticParameterService {

    private final MinistereStructureRepository repository;
    private final ProgrammationRepository programmationRepository;
    private final PeriodeRepository periodeRepository;
    private final ExerciceRepository exerciceRepository;
    private final StructureRepository structureRepository;
    private final MinistereRepository ministereRepository;
    private final GrillePerformanceRepository grilleRepository;
    private final PerformanceRepository performanceRepository;
    private final EvaluationRepository evaluationRepository;

    public StatisticParameterServiceImpl(MinistereStructureRepository repository,
            ProgrammationRepository programmationRepository,
            PeriodeRepository periodeRepository,
            ExerciceRepository exerciceRepository,
            StructureRepository structureRepository,
            MinistereRepository ministereRepository,
            GrillePerformanceRepository grilleRepository,
            PerformanceRepository performanceRepository,
            EvaluationRepository evaluationRepository) {

        this.repository = repository;
        this.programmationRepository = programmationRepository;
        this.periodeRepository = periodeRepository;
        this.exerciceRepository = exerciceRepository;
        this.structureRepository = structureRepository;
        this.ministereRepository = ministereRepository;
        this.grilleRepository = grilleRepository;
        this.performanceRepository = performanceRepository;
        this.evaluationRepository = evaluationRepository;
    }

    @Override
    public long nbStructuresByMinistere(long ministereId) {
        return repository.countStructureByMinistere(ministereId);
    }

    @Override
    public List<CountStructureGroupByType> nbStructuresByGroupType(long ministereId) {
        return repository.countStructureByMinistereAndByGroupType(ministereId);
    }

    /**
     * Resume les dépenses du ministere pour l'exercice en cours
     *
     * @param ministereId
     * @param exerciceId
     * @return
     */
    @Override
    public ResumerDepenseData resumerDepenseParMinistere(Long ministereId, Long exerciceId) {
        // initialisation de la donnée de retour
        ResumerDepenseData data = new ResumerDepenseData();
        data.setLibelle("-");

        // Somme des couts prévisionnels pour le ministere et l'exercice donné
        double previsionnel = programmationRepository.coutPrevisionnelMinistereProgrammation(ministereId, exerciceId).orElse(0d);
        data.setPrevisionnel(previsionnel);

        // Somme des couts réels pour le ministere et l'exercice donné
        double reel = programmationRepository.coutReelMinistereProgrammation(ministereId, exerciceId).orElse(0d);
        data.setReel(reel);

        return data;
    }

    @Override
    public ResumerActiviteData resumerActiviteParMinistere(Long ministereId, Long exerciceId) {
        // initialisation de la donnée de retour
        ResumerActiviteData data = new ResumerActiviteData();
        data.setLibelle("-");

        // termine
        long termine = programmationRepository.countMinistereTermine(ministereId, exerciceId);
        data.setTermine(termine);

        //encours
        long encours = programmationRepository.countMinistereEncours(ministereId, exerciceId);
        data.setEncours(encours);

        // en attente
        long enattente = programmationRepository.countMinistereAttente(ministereId, exerciceId);
        data.setEnattente(enattente);

        // total
        long total = termine + encours + enattente;
        data.setTotal(total);

        return data;
    }

    @Override
    public List<ResumerStructureData> resumerActiviteParStructure(Long ministereId, Long exerciceId) {

        // initialisation de la donnée de retour
        List<ResumerStructureData> data = new ArrayList<>();

        // liste des structures
        List<Structure> structures = repository.findAllStructuresByMinistere(ministereId);

        for (Structure struct : structures) {
            ResumerStructureData resume = new ResumerStructureData();
            resume.setLibelle("Evolution de l'exécution des activités");
            resume.setStructureCode(struct.getSigle());

            // termine
            long termine = programmationRepository.countStructureProgrammationTerminer(struct.getId(), exerciceId);
            resume.setTermine(termine);

            //encours
            long encours = programmationRepository.countStructureProgrammationEncours(struct.getId(), exerciceId);
            resume.setEncours(encours);

            // en attente
            long enattente = programmationRepository.countStructureProgrammationEnattente(struct.getId(), exerciceId);
            resume.setEnattente(enattente);

            // total
            long total = termine + encours + enattente;
            resume.setTotal(total);

            data.add(resume);

        }

        return data;
    }

    /**
     * Regroupe en une seule option les trois précédentes fonctions
     *
     * @param ministereId
     * @param exerciceId
     * @return
     */
    @Override
    public MinistereGlobalStatsBundleData resumerMinistere(Long ministereId, Long exerciceId) {

        MinistereGlobalStatsBundleData data = new MinistereGlobalStatsBundleData();

        List<ResumerStructureData> resumes = resumerActiviteParStructure(ministereId, exerciceId);
        data.setResumes(resumes);

        ResumerDepenseData depense = resumerDepenseParMinistere(ministereId, exerciceId);
        data.setDepense(depense);

        ResumerActiviteData activite = resumerActiviteParMinistere(ministereId, exerciceId);
        data.setActivite(activite);

        return data;
    }

    //
    @Override
    public MinistereGlobalPerfData resumerPerformance(Long ministereId, Long exerciceId) {

        MinistereGlobalPerfData value = new MinistereGlobalPerfData();

        List<ResumerPerfData> data = new ArrayList<>();

        //Chargement du ministère
        Ministere ministere = ministereRepository.findMinistereByID(ministereId);

        //Chargement des structures du ministère
        List<Structure> structures = repository.allNonInternalStructureByMinistere(ministereId, TypeStructure.INTERNE);

        for (Structure struct : structures) {
            ResumerPerfData resume = new ResumerPerfData();
            resume.setLibelle(struct.getLibelle());
            resume.setCode(struct.getSigle());

            double taux = performanceRepository.findCurrentStructurePerformanceValue(struct.getId(), exerciceId).orElse(-1d);
            resume.setTaux(taux);

            data.add(resume);

        }

        value.setData(data);
        value.setCode(ministere.getSigle());
        value.setLibelle(ministere.getLibelle());

        return value;
    }

    @Override
    public MinistereGlobalPerfData resumerGlobalPerformance(Long exerciceId) {

        MinistereGlobalPerfData value = new MinistereGlobalPerfData();

        List<ResumerPerfData> data = new ArrayList<>();

        //Chargement des ministères
        List<Ministere> ministeres = ministereRepository.findAll();

        // parcours des ministères
        for (Ministere ministere : ministeres) {

            //Chargement des structures du ministère
            List<Structure> structures = repository.allNonInternalStructureByMinistere(ministere.getId(), TypeStructure.INTERNE);

            ResumerPerfData resume = new ResumerPerfData();
            resume.setLibelle(ministere.getLibelle());
            resume.setCode(ministere.getSigle());

            double taux = 0;
            int taille = structures.size();

            for (Structure struct : structures) {
                taux = taux + performanceRepository.findCurrentStructurePerformanceValue(struct.getId(), exerciceId).orElse(0d);
            }

            taux = taux / taille;

            resume.setTaux(taux);
            data.add(resume);
        }

        value.setCode("-");
        value.setLibelle("-");
        value.setData(data);

        return value;
    }

    @Override
    public ResumerSectorielData resumerSectoriel(Long ministereId, Long exerciceId) {

        // Initialisation des données
        ResumerSectorielData data = new ResumerSectorielData();

        List<String> periodes = new ArrayList<>();

        List<Double> physiques = new ArrayList<>();

        List<Double> finances = new ArrayList<>();

        // Chargement de l'exercice (pour récuperer l'année)
        Exercice exercice = exerciceRepository.findById(exerciceId).get();

        // Chargement des périodes 
        List<Periode> periods = periodeRepository.findByPeriodiciteActif();

        // Calendrier pour extraction de l'année 
        Calendar calendrier = Calendar.getInstance();
        calendrier.setTime(java.util.Date.from(exercice.getDebut().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));

        for (Periode period : periods) {

            // Correction de la date de début de la période pour la conformité avec l'année réelle de l'exercice
            Date debut = AppUtil.repairDate(period.getDebut(), calendrier.get(YEAR));

            // Correction de la date de fin de la période pour la conformité avec l'année réelle de l'exercice
            Date fin = AppUtil.repairDate(period.getFin(), calendrier.get(YEAR));

            // Recherche du nombre d'activités programmées pour l'exercice pour la période
            long nbActivites = programmationRepository.countActivitesMinistereParPeriode(ministereId, exerciceId, debut, fin).orElse(0l);

            // recherche de la somme des taux d'exécution des activités programmées sur la période
            double taux = programmationRepository.additionnerTauxActivitesMinistereParPeriode(ministereId, exerciceId, debut, fin).orElse(0d);

            // recherche de la somme des couts réels d'exécution des activités programmées sur la période
            double cout = programmationRepository.additionnerCoutActivitesMinistereParPeriode(ministereId, exerciceId, debut, fin).orElse(0d);

            periodes.add(period.getLibelle());

            if (nbActivites != 0) {
                physiques.add(taux / nbActivites);
            }

            finances.add(cout);
        }

        data.setFinances(finances);
        data.setLibelle("-");
        data.setPeriodes(periodes);
        data.setPhysiques(physiques);

        return data;
    }

    @Override
    public ResumerSectorielDepenseData resumerSectorielDepense(Long ministereId, Long exerciceId) {

        // Initialisation des données de retour
        ResumerSectorielDepenseData data = new ResumerSectorielDepenseData();

        // total des couts réel
        double reel = 0;

        // Total des couts prévisionnels
        double previsionnel = 0;

        // Liste des périodes (trimestres ou semestres ...)
        List<String> periodes = new ArrayList<>();

        // liste de couts de réalisation par période
        List<Double> reels = new ArrayList<>();

        // liste de couts prévisionnels par prériodes
        List<Double> previsionnels = new ArrayList<>();

        // Chargement de l'exercice (pour récuperer l'année)
        Exercice exercice = exerciceRepository.findById(exerciceId).get();

        // Chargement des périodes 
        List<Periode> periods = periodeRepository.findByPeriodiciteActif();

        // Calendrier pour extraction de l'année 
        Calendar calendrier = Calendar.getInstance();
        calendrier.setTime(java.util.Date.from(exercice.getDebut().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));

        for (Periode period : periods) {

            // Correction de la date de début de la période pour la conformité avec l'année réelle de l'exercice
            Date debut = AppUtil.repairDate(period.getDebut(), calendrier.get(YEAR));

            // Correction de la date de fin de la période pour la conformité avec l'année réelle de l'exercice
            Date fin = AppUtil.repairDate(period.getFin(), calendrier.get(YEAR));

            // recherche de la somme des taux d'exécution des activités programmées sur la période
            double _prev = programmationRepository.additionnerPrevisionnelActivitesMinistereParPeriode(ministereId, exerciceId, debut, fin).orElse(0d);

            // recherche de la somme des couts réels d'exécution des activités programmées sur la période
            double _reel = programmationRepository.additionnerCoutActivitesMinistereParPeriode(ministereId, exerciceId, debut, fin).orElse(0d);

            // Ajout des éléments dans les listes
            periodes.add(period.getLibelle());
            reels.add(_reel);
            previsionnels.add(_prev);

            reel = reel + _reel;
            previsionnel = previsionnel + _prev;
        }

        // Chargement des données dans l'objet à retourner
        data.setAvgprevisionnel(previsionnel);
        data.setAvgreel(reel);
        data.setLibelle("-");
        data.setPeriodes(periodes);
        data.setPrevisionnels(previsionnels);
        data.setReels(reels);

        return data;
    }

    @Override
    public AllEvolutionData resumerEvolution(EvolutionParam params) {

        // Données initiales
        AllEvolutionData data = new AllEvolutionData();

        String libelle = "";

        List<MinistereEvolutionBundle> liste = new ArrayList<>();
        List<Exercice> exercices = new ArrayList<>();

        // Déclaration de la liste des structures concernées
        List<Structure> mesStructures = new ArrayList<>();

        boolean reduced = (params.getExerciceId() != null && params.getExerciceId() != 0);

        // vérification, savoir si c'est tout le ministère ou si c'est juste une structure 
        boolean many = ((params.getMinistereId() != null) && (params.getStructureId() == null));

        // Chargement du ministère
        Ministere ministere = ministereRepository.findMinistereByID(params.getMinistereId());

        if (many) {
            mesStructures = structureRepository.findMinistereStructure(ministere.getId(), TypeStructure.INTERNE);
        } else {
            Structure singleStructure = structureRepository.findById(params.getStructureId()).get();
            mesStructures.add(singleStructure);
        }

        // Chargement de la liste des exercices en tenant en compte la couverture
        if (reduced) {
            Exercice exo = exerciceRepository.findExerciceById(params.getExerciceId());
            exercices.add(exo);
        } else {
            exercices = exerciceRepository.checkXfirtsElement(ExerciceStatus.EN_ATTENTE, PageRequest.of(0, params.getCouverture()));
        }
        // Parcours des structure
        for (Structure structure : mesStructures) {

            List<Double> taux = new ArrayList<>();
            List<String> exos = new ArrayList<>();
            String idStr = ministere.getId() + "" + structure.getId();

            double moyenne = 0d;

            String exoStr = "[";

            // Parcours de la liste des exercices
            for (Exercice exo : exercices) {
                double valeur = evaluationRepository.findStructureEvaluationValue(structure.getId(), exo.getId()).orElse(0d);
                taux.add(valeur);
                exos.add(AppUtil.convertAndConcatDates(exo.getDebut(), exo.getFin()));
                moyenne = moyenne + valeur;
            }

            if (!exercices.isEmpty()) {
                moyenne = moyenne / exercices.size();
                int size = exercices.size();
                exoStr = exoStr + exercices.get(0).getDebut().getYear() + " - " + exercices.get(size - 1).getDebut().getYear() + "]";
            }

            String appreciation = grilleRepository.findGrilleAppreciation(moyenne).orElse("-");

            // Construction
            MinistereEvolutionBundle evolution = new MinistereEvolutionBundle();
            evolution.setMinistere(ministere.getCode());
            evolution.setStructure(structure.getSigle());

            evolution.setAppreciation(appreciation);
            evolution.setExercices(exos);
            evolution.setTaux(taux);

            evolution.setId(idStr);
            evolution.setExercice(exoStr);
            evolution.setMoyenne(moyenne);

            liste.add(evolution);
        }

        libelle = libelle + " Résumé des données sur : " + ministere.getSigle() + " avec sa/ses " + mesStructures.size() + " structure-s centrale-s, rattachée-s et/ou déconcentrée-s";

        data.setLibelle(libelle);
        data.setData(liste);

        return data;
    }

    @Override
    public AllEvolutionData resumerEvolutionPerformance(EvolutionParam params) {

        // Données initiales
        AllEvolutionData data = new AllEvolutionData();

        String libelle = "";

        List<MinistereEvolutionBundle> liste = new ArrayList<>();
        List<Exercice> exercices = new ArrayList<>();

        // Déclaration de la liste des structures concernées
        List<Structure> mesStructures = new ArrayList<>();

        boolean reduced = (params.getExerciceId() != null && params.getExerciceId() != 0);

        // vérification, savoir si c'est tout le ministère ou si c'est juste une structure 
        boolean many = ((params.getMinistereId() != null) && (params.getStructureId() == null));

        // Chargement du ministère
        Ministere ministere = ministereRepository.findMinistereByID(params.getMinistereId());

        if (many) {
            mesStructures = structureRepository.findMinistereStructure(ministere.getId(), TypeStructure.INTERNE);
        } else {
            Structure singleStructure = structureRepository.findById(params.getStructureId()).get();
            mesStructures.add(singleStructure);
        }

        // Chargement de la liste des exercices en tenant en compte la couverture
        if (reduced) {
            Exercice exo = exerciceRepository.findExerciceById(params.getExerciceId());
            exercices.add(exo);
        } else {
            exercices = exerciceRepository.checkXfirtsElement(ExerciceStatus.EN_ATTENTE, PageRequest.of(0, params.getCouverture()));
        }
        // Parcours des structure
        for (Structure structure : mesStructures) {

            List<Double> taux = new ArrayList<>();
            List<String> exos = new ArrayList<>();
            String idStr = ministere.getId() + "" + structure.getId();

            double moyenne = 0d;

            String exoStr = "[";

            // Parcours de la liste des exercices
            for (Exercice exo : exercices) {
                double valeur = performanceRepository.findCurrentStructurePerformanceValue(structure.getId(), exo.getId()).orElse(0d);
                taux.add(valeur);
                exos.add(AppUtil.convertAndConcatDates(exo.getDebut(), exo.getFin()));
                moyenne = moyenne + valeur;
            }

            if (!exercices.isEmpty()) {
                moyenne = moyenne / exercices.size();
                int size = exercices.size();
                exoStr = exoStr + exercices.get(0).getDebut().getYear() + " - " + exercices.get(size - 1).getDebut().getYear() + "]";
            }

            String appreciation = grilleRepository.findGrilleAppreciation(moyenne).orElse("-");

            // Construction
            MinistereEvolutionBundle evolution = new MinistereEvolutionBundle();
            evolution.setMinistere(ministere.getCode());
            evolution.setStructure(structure.getSigle());

            evolution.setAppreciation(appreciation);
            evolution.setExercices(exos);
            evolution.setTaux(taux);

            evolution.setId(idStr);
            evolution.setExercice(exoStr);
            evolution.setMoyenne(moyenne);

            liste.add(evolution);
        }

        libelle = libelle + " Résumé des données sur : " + ministere.getSigle() + " avec sa/ses " + mesStructures.size() + " structure-s centrale-s, rattachée-s et/ou déconcentrée-s";

        data.setLibelle(libelle);
        data.setData(liste);

        return data;
    }

}
