/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mfptps.appdgessddi.utils;

import com.mfptps.appdgessddi.entities.Action;
import com.mfptps.appdgessddi.entities.Objectif;
import com.mfptps.appdgessddi.entities.Periode;
import com.mfptps.appdgessddi.entities.Programmation;
import com.mfptps.appdgessddi.entities.ProgrammationPhysique;
import com.mfptps.appdgessddi.entities.Programme;
import com.mfptps.appdgessddi.entities.Tache;
import com.mfptps.appdgessddi.entities.TacheEvaluer;
import com.mfptps.appdgessddi.enums.BaseStatus;
import com.mfptps.appdgessddi.enums.TypeObjectif;
import com.mfptps.appdgessddi.repositories.ActionRepository;
import com.mfptps.appdgessddi.repositories.ActivitesRepository;
import com.mfptps.appdgessddi.repositories.ObjectifRepository;
import com.mfptps.appdgessddi.repositories.ProgrammationPhysiqueRepository;
import com.mfptps.appdgessddi.repositories.ProgrammationRepository;
import com.mfptps.appdgessddi.repositories.ProgrammeRepository;
import com.mfptps.appdgessddi.repositories.TacheEvaluerRepository;
import com.mfptps.appdgessddi.service.CustomException;
import com.mfptps.appdgessddi.web.vm.ManagedAgentVM;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import static java.util.Calendar.YEAR;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.validation.constraints.Size;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Slf4j
public class AppUtil {

    private static InputStream logoStream;

    @Size(min = ManagedAgentVM.PASSWORD_MIN_LENGTH, max = ManagedAgentVM.PASSWORD_MAX_LENGTH)
    public static final String DEFAULT_PASSWORD = "12345678";

    /**
     * TYPE OF STRUCTURE
     */
    public static final String STRUCTURE_CENTRALE = "CENTRALE";
    public static final String STRUCTURE_DECONCENTREE = "DECONCENTREE";
    public static final String STRUCTURE_RATTACHEE = "RATTACHEE";
    public static final String STRUCTURE_DE_MISSION = "DE_MISSION";
    public static final String STRUCTURE_INTERNE = "INTERNE";

    /**
     * BASIC MINISTERE AND STRUCTURE DATAS
     */
    public static final String BASIC_MINISTERE_CODE = "00-TEST";
    public static final String BASIC_MINISTERE_LABEL = "Ministere en charge de la fonction publique - TEST";
    public static final String BASIC_MINISTERE_SIGLE = "MFP - TEST";

    public static final String BASIC_STRUCTURE_LABEL = "Structure de Test";
    public static final String BASIC_STRUCTURE_SIGLE = "STRUC - TEST";
    public static final String BASIC_STRUCTURE_TELEPHONE = "00.00.00.00";
    public static final String BASIC_STRUCTURE_EMAIL = "contact.test@fp.gov.bf";

    /**
     * TYPE OF OBJECTIF
     */
    public static final String OBJECTIF_STRATEGIQUE = "STRATEGIQUE";
    public static final String OBJECTIF_OPERATIONNEL = "OPERATIONNEL";

    /**
     * TYPE OF INDICATEUR
     */
    public static final String INDICATEUR_IMPACT = "IMPACT";
    public static final String INDICATEUR_EFFET = "EFFET";

    /**
     * TYPE OF EXERCICE OR BASE STATUS
     */
    public static final String EN_COURS = "EN_COURS";
    public static final String EN_ATTENTE = "EN_ATTENTE";
    public static final String CLOS = "CLOS";
    public static final String ANNULE = "ANNULE";
    public static final String TERMINEE = "TERMINEE";
    public static final String PAS_COMMENCEE = "PAS_COMMENCEE";

    /**
     * ALL ROLE/PRIVILEGES OF USERS
     */
    public static final String FS = "ROLE_FOCAL_STRUCT";
    public static final String AGT_D = "ROLE_AGT_DGESS";
    public static final String DD = "ROLE_DIR_DGESS";
    public static final String RD = "ROLE_RESP_DGESS";
    public static final String RS = "ROLE_RESP_STRUCT";
    public static final String DM = "ROLE_DIRCAB_MIN";
    public static final String RM = "ROLE_RESP_MIN";
    public static final String SM = "ROLE_SG_MIN";
    public static final String AD = "ROLE_AGT_DDII";
    public static final String RDDII = "ROLE_RESP_DDII";
    public static final String ADMIN = "ROLE_ADMIN";
    public static final String DAF = "ROLE_DAF";
    public static final String DRH = "ROLE_DRH";
    public static final String USER = "ROLE_USER";

    /**
     *
     * @param debut : startDate
     * @param fin : endDate
     */
    public static void checkDebutBeforeFin(Date debut, Date fin) {
        if (debut.after(fin)) {
            throw new CustomException("Veuillez renseigner une date de debut antérieure à la date de fin.");
        }
    }

    /**
     * Generate a code of some activite
     *
     * @param repository
     * @return
     */
    public static String codeGeneratorActivite(ActivitesRepository repository) {
        return "" + (repository.count() + 1L);
    }

    /**
     * Generate a code of some programme budgetaire (which can extend over 10..4
     * years)
     *
     * @param repository
     * @return
     */
    public static String codeGeneratorProgramme(ProgrammeRepository repository) {
        return "" + (repository.count() + 1L);
    }

    /**
     * Generate a code of some objectif (strategique or operationnel)
     *
     * @param repository
     * @param programmeRepository
     * @param actionRepository
     * @param objectif
     * @return
     */
    public static String codeGeneratorObjectif(ObjectifRepository repository, ProgrammeRepository programmeRepository, ActionRepository actionRepository, Objectif objectif) {
        String codeGenerated = "";
        if (objectif.getType().getLabel().equals(OBJECTIF_STRATEGIQUE)) {
            Programme programme = programmeRepository.findById(objectif.getProgramme().getId())
                    .orElseThrow(() -> new CustomException("Veuillez rattacher le programme budgétaire approprié à cet objectif."));
            long count = repository.countObjectifStrategique(programme.getId(), BaseStatus.EN_COURS, TypeObjectif.STRATEGIQUE);
            codeGenerated = "" + programme.getCode() + "." + (count + 1L);
        } else if (objectif.getType().getLabel().equals(OBJECTIF_OPERATIONNEL)) {
            if (objectif.getParent() != null) {//subObjectifOperationnel
                Objectif parent = repository.findById(objectif.getParent().getId())
                        .orElseThrow(() -> new CustomException("Sous objectif mal référencé."));
                long count = repository.countObjectifSubOperationnel(parent.getId(), TypeObjectif.OPERATIONNEL);
                codeGenerated = "" + parent.getCode() + "." + (count + 1L);
                return codeGenerated;
            }
            Action action = actionRepository.findById(objectif.getAction().getId())
                    .orElseThrow(() -> new CustomException("Veuillez rattacher l'action appropriée à cet objectif."));
            long count = repository.countObjectifOperationnel(action.getId(), TypeObjectif.OPERATIONNEL);
            codeGenerated = "" + action.getCode() + "." + (count + 1L);
        }
        return codeGenerated;
    }

    /**
     * Generate a code of some Action
     *
     * @param repository
     * @param objectifRepository
     * @param action
     * @return
     */
    public static String codeGeneratorAction(ActionRepository repository, ObjectifRepository objectifRepository, Action action) {
        String codeGenerated = "";
        Objectif objectif = objectifRepository.findByIdAndType(action.getObjectif().getId(), TypeObjectif.STRATEGIQUE)
                .orElseThrow(() -> new CustomException("Veuillez rattacher l'objectif approprié à cette action."));
        codeGenerated = "" + objectif.getCode() + "." + (repository.count() + 1L);
        return codeGenerated;
    }

    /**
     * Generate a code of some Programmation
     *
     * @param repository
     * @param objectifRepository
     * @param programmation
     * @return
     */
    public static String codeGeneratorProgrammation(ProgrammationRepository repository, ObjectifRepository objectifRepository, Programmation programmation) {
        String codeGenerated = "";
        Objectif objectif = objectifRepository.findByIdAndType(programmation.getObjectif().getId(), TypeObjectif.OPERATIONNEL)
                .orElseThrow(() -> new CustomException("Veuillez rattacher l'objectif approprié à cette programmation."));
        long count = repository.countProgrammationByStrucutreAndObjectif(programmation.getStructure().getId(), objectif.getId());
        codeGenerated = "" + objectif.getCode() + "." + (count + 1L);
        return codeGenerated;
    }

    /**
     *
     * @return
     */
    public static InputStream getAppDefaultLogo() {
        try {
            logoStream = AppUtil.class.getResourceAsStream("/logo.png");
            return logoStream;
        } catch (Exception e) {
            throw new CustomException("Erreur lors du chargement du logo..." + e);
        }
    }

    /**
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date normaliserDate(Date date) throws ParseException {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String dateValue = df.format(date);
        Date toDay = new Date();
        String toDayValue = df.format(toDay);
        String value = dateValue
                .substring(0, dateValue.length() - 4).concat(toDayValue.substring(toDayValue.length() - 4, toDayValue.length()));
        return df.parse(value);
    }

    /**
     * Check if the current date is in the interval of the Activity realization
     * periods
     *
     * Return the Id of Periode come from ProgrammationPhysique ligne
     *
     * @param programmationId
     * @param repository
     * @return
     */
    public static ResponseCheckPeriode checkProgrammationPhysique(long programmationId, ProgrammationPhysiqueRepository repository) throws CustomException {
        Date toDay = new Date();
        ResponseCheckPeriode response = new ResponseCheckPeriode();
        List<ProgrammationPhysique> progsPhysiques = repository.findByProgrammationAndPeriode(programmationId);
        response.setPeriodes(progsPhysiques);

        for (ProgrammationPhysique pp : progsPhysiques) {
            try {
                Date dateDebut = AppUtil.normaliserDate(pp.getPeriode().getDebut());
                Date dateFin = AppUtil.normaliserDate(pp.getPeriode().getFin());
                response.setExists(response.isExists() || (toDay.after(dateDebut) && toDay.before(dateFin)));
                if (response.isExists()) {
                    response.setPeriode(pp.getPeriode().getId());
                    break;
                }
            } catch (ParseException ex) {
                log.error("Error when parsing data.");
            }
        }
        return response;
    }

    /**
     * SOUS FONCTION (TAUX PAR STRUCTURE): Taux d'execution par exercice/pph
     *
     * @param periodeId
     * @return
     */
    public static double tauxExecutionByExerciceOrPeriode(List<Programmation> programmations, Long periodeId, TacheEvaluerRepository tacheEvaluerRepository) {
        double taux = 0;//taux global recherche

        for (Programmation prog : programmations) {
            double tauxTaches = 0;//taux global de chaque programmation
            //on totalise les taux des taches de chaque programmation
            for (Tache tache : prog.getTaches()) {
                if (periodeId == null) {//============== CALCULS SANS TENIR COMPTE DE LA PEDIODE
                    //Optional<TacheEvaluer> tacheEvaluee = tacheEvaluerRepository.getByTacheAndActive(tache.getId());
                    tauxTaches = tauxByValueOfPeriode(prog, tache, null, tauxTaches);
                } else {//============== CALCULS EN FONCTION DE LA PERIODE
                    Optional<TacheEvaluer> tacheEvaluee = tacheEvaluerRepository.getByTacheAndPeriodeActive(tache.getId(), periodeId);
                    tauxTaches = tauxByValueOfPeriode(prog, tache, tacheEvaluee, tauxTaches);
                }
            }
            //on totalise le taux de chaque programmation 
            taux += tauxTaches;
        }
        return taux;
    }

    //SOUS FONCTION DE tauxExecutionByExerciceOrPeriode(...)
    private static double tauxByValueOfPeriode(Programmation prog, Tache tache, Optional<TacheEvaluer> tacheEvaluee, double tauxTaches) {
        if ((tache.getValeur() == 1D) && tache.isExecute()) {//tache sans cible(cible = 1)  deja execute
            tauxTaches += tache.getPonderation();
        } else if ((tache.getValeur() != 1D) && tache.isExecute()) {//tache a cible deja execute(meme au dela de la cible prevue)
            tauxTaches += tache.getPonderation();
        } else if ((tache.getValeur() != 1D) && !tache.isExecute() && tacheEvaluee != null) {//tache a cible execute partiellement
            tauxTaches = tauxByValueOfProgrammationCible(prog, tache, tacheEvaluee, tauxTaches);
        }

        return tauxTaches;
    }

    //SOUS FONCTION DE tauxByValueOfPeriode(...)
    private static double tauxByValueOfProgrammationCible(Programmation prog, Tache tache, Optional<TacheEvaluer> tacheEvaluee, double tauxTaches) {
        if (tacheEvaluee.isPresent()) {
            if (prog.getCible() != 1D) {//programmation a cible 
                tauxTaches += (tacheEvaluee.get().getValeurCumulee() / prog.getCible()) * tache.getPonderation();
            } else {//programmation sans cible (cible = 1) 
                tauxTaches += (tacheEvaluee.get().getValeurCumulee() / tache.getValeur()) * tache.getPonderation();
            }
        }
        return tauxTaches;
    }

    public static Periode checkExactPeriode(List<Periode> periodes, Date currentDate) {
        Periode foundOne = null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);

        int year = calendar.get(YEAR);
        for (Periode per : periodes) {
            Date start = repairDate(per.getDebut(), year);
            Date end = repairDate(per.getFin(), year);

            if (currentDate.compareTo(start) >= 0 && end.compareTo(currentDate) >= 0) {
                foundOne = per;
                break;
            }
        }

        return foundOne;
    }

    public static Date repairDate(Date givenDate, int year) {
        try {
            DateFormat dateFormant = new SimpleDateFormat("dd-MM-yyyy");
            Date repaired;

            String dateString = dateFormant.format(givenDate);
            //cutting dateString
            dateString = dateString.substring(0, dateString.length() - 4);
            //concat the new year
            dateString = dateString + String.valueOf(year);

            repaired = dateFormant.parse(dateString);

            return repaired;
        } catch (ParseException ex) {
            return null;
        }
    }

    public static String convertToShortDate(Date date) {
        DateFormat dateFormant = new SimpleDateFormat("dd-MM-yyyy");
        String value = dateFormant.format(date);
        return value;
    }

    public static String convertAndConcatDates(LocalDate startDate, LocalDate endDate) {
        String dateStrValue = startDate.getYear() + "-" + endDate.getYear();
        return dateStrValue;
    }

    /**
     * Fonction qui renvoie l'extension du futur ficher à créeer et le format du
     * contenu du fichier à renvoyer
     *
     * @param extension
     * @return
     */
    public static String[] constructFormatAndExtension(String extension) {
        String[] result = new String[2];

        switch (extension) {
            case "PDF":
                result[0] = "application/pdf";
                result[1] = ".pdf";
                break;
            case "Excel":
                result[0] = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
                result[1] = ".xlsx";
                break;
            case "Word":
                result[0] = "application/ms-word";
                result[1] = ".docx";
                break;
            default:
                result[0] = "application/pdf";
                result[1] = ".pdf";
                break;
        }

        return result;
    }

    public static double arrondirDecimal(double valeur) {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        return Double.valueOf(df.format(valeur).replace(",", "."));
    }
}
