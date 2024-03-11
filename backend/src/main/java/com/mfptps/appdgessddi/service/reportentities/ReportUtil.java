/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mfptps.appdgessddi.service.reportentities;

import com.mfptps.appdgessddi.entities.Exercice;
import com.mfptps.appdgessddi.entities.GrillePerformance;
import com.mfptps.appdgessddi.entities.Ministere;
import com.mfptps.appdgessddi.entities.Performance;
import com.mfptps.appdgessddi.entities.Ponderation;
import com.mfptps.appdgessddi.entities.Structure;
import com.mfptps.appdgessddi.enums.Nombre;
import com.mfptps.appdgessddi.repositories.QueryManagerRepository;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 *
 * @author aboubacary
 */
@Slf4j
@Service
public class ReportUtil {

    private static QueryManagerRepository query;

    public ReportUtil(QueryManagerRepository query) {
        this.query = query;
    }

    protected static boolean containsCodeProgramme(List<ProgrammeRE> programmes, final String codeProgramme) {
        return programmes.stream()
                .map(ProgrammeRE::getCodeProgramme)
                .filter(codeProgramme::equals).findFirst()
                .isPresent();
    }

    protected static boolean containsCodeObjectifStrategique(List<ObjectifStrategiqueRE> objectifsStra, final String codeObjectifStra) {
        return objectifsStra.stream()
                .map(ObjectifStrategiqueRE::getCodeObjectifStra)
                .filter(codeObjectifStra::equals).findFirst()
                .isPresent();
    }

    public static boolean containsCodeAction(List<ActionRE> actions, final String codeAction) {
        return actions.stream()
                .map(ActionRE::getCodeAction)
                .filter(codeAction::equals).findFirst()
                .isPresent();
    }

    public static boolean containsCodeObjectifOperational(List<ObjectifOperationnelRE> objectifsop, final String codeObjectifOp) {
        return objectifsop.stream()
                .map(ObjectifOperationnelRE::getCodeObjectifOp)
                .filter(codeObjectifOp::equals).findFirst()
                .isPresent();
    }

    public static boolean containsCodeActivite(List<ActiviteRE> activites, final String codeProgrammation) {
        return activites.stream()
                .map(ActiviteRE::getCodeActivite)
                .filter(codeProgrammation::equals).findFirst()
                .isPresent();
    }

    /**
     * extraction des programmes dans le globalView
     *
     * @param globalData
     * @return
     */
    protected static List<ProgrammeRE> extractPrograms(List<ViewGlobale> globalData) {
        List<ProgrammeRE> data = new ArrayList<>();

        globalData.stream()
                .map(elmnt -> new ProgrammeRE(elmnt.getCodeProgramme(), elmnt.getLibelleProgramme(), "", null))
                .filter(prog -> (!containsCodeProgramme(data, prog.getCodeProgramme()))).forEachOrdered(prog -> {
            data.add(prog);
        });

        sortProgrammeData(data);

        return data;
    }

    /**
     * extraction des objectifs stratégiques par programme
     *
     * @param program
     * @param globalData
     * @return
     */
    protected static List<ObjectifStrategiqueRE> extractStrategicPurpose(ProgrammeRE program, List<ViewGlobale> globalData) {
        List<ObjectifStrategiqueRE> data = new ArrayList<>();
        for (ViewGlobale elmt : globalData) {
            if (program.getCodeProgramme().equals(elmt.getCodeProgramme())) {
                ObjectifStrategiqueRE obj = new ObjectifStrategiqueRE(null, elmt.getCodeObjectifStra(), elmt.getLibelleObjectifStra(), "");
                if (!containsCodeObjectifStrategique(data, elmt.getCodeObjectifStra())) {
                    data.add(obj);
                }
            }
        }

        sortObStrageData(data);

        return data;
    }

    /**
     * extraction des Action par objectif stratégique
     *
     * @param objectif
     * @param globalData
     * @return
     */
    protected static List<ActionRE> extractAction(ObjectifStrategiqueRE objectif, List<ViewGlobale> globalData) {
        List<ActionRE> data = new ArrayList<>();
        for (ViewGlobale elmt : globalData) {
            if (objectif.getCodeObjectifStra().equals(elmt.getCodeObjectifStra())) {
                ActionRE action = new ActionRE(null, elmt.getCodeAction(), elmt.getLibelleAction());
                if (!containsCodeAction(data, elmt.getCodeAction())) {
                    data.add(action);
                }
            }
        }

        sortActionData(data);

        return data;
    }

    /**
     * extraction des Objectifs opérationnels par action
     *
     * @param action
     * @param globalData
     * @return
     */
    protected static List<ObjectifOperationnelRE> extractOperationalPurpose(ActionRE action, List<ViewGlobale> globalData) {
        List<ObjectifOperationnelRE> data = new ArrayList<>();
        for (ViewGlobale elmt : globalData) {
            if (action.getCodeAction().equals(elmt.getCodeAction())) {
                ObjectifOperationnelRE obj = new ObjectifOperationnelRE(null, elmt.getCodeObjectifOpe(), elmt.getLibelleObjectifOpe());
                if (!containsCodeObjectifOperational(data, elmt.getCodeObjectifOpe())) {
                    data.add(obj);
                }
            }
        }

        sortObOperationnelData(data);

        return data;
    }

    /**
     * extraction des Activités / Programmations par objectifs opérationnels
     *
     * @param objectif
     * @param globalData
     * @return
     */
    protected static List<ActiviteRE> extractActivity(ObjectifOperationnelRE objectif, List<ViewGlobale> globalData) {
        List<ProgrammationPhysiqueRE> programmationPhysiqueData = ReportUtil.query.programmationPhysiqueList();

        List<ActiviteRE> data = new ArrayList<>();
        for (ViewGlobale elmt : globalData) {
            if (objectif.getCodeObjectifOp().equals(elmt.getCodeObjectifOpe())) {
                ActiviteRE activite = new ActiviteRE(
                        elmt.getCodeProgrammation(),
                        elmt.getLibelleActivite(),
                        elmt.getIndicateur(),
                        elmt.getCibleProgrammation(),
                        elmt.getCoutPrevisionnel() / 1000, /*En millier de FCFA*/
                        null, null, null, null, null, null,
                        elmt.getLibelleFinancement(),
                        elmt.getSigleStructure(),
                        null, null, 0, 0, "");
                for (ProgrammationPhysiqueRE physique : programmationPhysiqueData) {
                    if (elmt.getId() == physique.getIdProgrammation()) {
                        switch (physique.getLibellePeriode()) {
                            case "T1":
                                activite.setT1("X");
                                break;
                            case "T2":
                                activite.setT2("X");
                                break;
                            case "T3":
                                activite.setT3("X");
                                break;
                            case "T4":
                                activite.setT4("X");
                                break;
                            case "S1":
                                activite.setS1("X");
                                break;
                            case "S2":
                                activite.setS2("X");
                                break;
                            default:
                            //System.out.println("_______________________ default case...");
                        }
                    }
                }
                if (!containsCodeActivite(data, elmt.getCodeProgrammation())) {
                    data.add(activite);
                }
            }
        }

        sortActiviteData(data);

        return data;
    }

    protected static List<ActiviteRE> extractActivityForRapport(ObjectifOperationnelRE objectif, List<ViewGlobale> globalData) {

        List<ActiviteRE> data = new ArrayList<>();
        for (ViewGlobale elmt : globalData) {
            if (objectif.getCodeObjectifOp().equals(elmt.getCodeObjectifOpe())) {
                ActiviteRE activite = new ActiviteRE(
                        elmt.getCodeProgrammation(),
                        elmt.getLibelleActivite(),
                        elmt.getIndicateur(),
                        elmt.getCibleProgrammation(),
                        elmt.getCoutPrevisionnel() / 1000, /*En millier de FCFA*/
                        null, null, null, null, null, null,/*Periodes*/
                        elmt.getLibelleFinancement(),
                        elmt.getSigleStructure(),
                        elmt.getResultatsAttendus(),
                        elmt.getResultatsAtteints(),
                        elmt.getTauxProgrammation(),
                        elmt.getCoutReel() / 1000, /*En millier de FCFA*/
                        "" /*observations*/
                );

                if (!containsCodeActivite(data, elmt.getCodeProgrammation())) {
                    data.add(activite);
                }
            }
        }

        sortActiviteData(data);

        return data;
    }

    public static List<ProgrammeDataRE> consctruct(String ministereLibelle, String structureParentLibelle, String currentStructureLibelle, String currentStructureTelephone, String titre, InputStream logoStream, List<ViewGlobale> globalData) {
        //ministere.getLibelle(), structureParent.getLibelle(), currentStructure.getLibelle(), currentStructure.getTelephone(), titre, logoStream

        // Conteneurs intermédiaires utilisés pour construire les données
        List<ProgrammeRE> allPrograms = extractPrograms(globalData);

        // conteneurs de données à imprimer
        List<ProgrammeDataRE> mainProgramData = new ArrayList<>();

        List<ProgrammeRE> programData = new ArrayList<>();

        // conteneur des structures
        List<Structure> allStructures = new ArrayList<>();

        // Construction des objet pour impression
        for (ProgrammeRE programmeRE : allPrograms) {
            programData = new ArrayList<>();
            // chercher tous les objectifs stratégiques 
            List<ObjectifStrategiqueRE> objectifStrategiqueData = new ArrayList<>();

            List<ObjectifOperationnelRE> objectifOperationnelData = new ArrayList<>();

            //List<ActiviteRE> activiteData = new ArrayList<>();
            List<ActionRE> actionData = new ArrayList<>();

            List<ObjectifStrategiqueRE> objStrData = extractStrategicPurpose(programmeRE, globalData);

            //programmeRE.setObjectifStrategiqueREs(objStrData);
            for (ObjectifStrategiqueRE ostr : objStrData) {
                List<ActionRE> actData = extractAction(ostr, globalData);

                for (ActionRE act : actData) {
                    List<ObjectifOperationnelRE> listObjOp = extractOperationalPurpose(act, globalData);

                    for (ObjectifOperationnelRE obOp : listObjOp) {
                        List<ActiviteRE> listActivite = extractActivity(obOp, globalData);

                        obOp.setActiviteREs(listActivite);
                        objectifOperationnelData.add(obOp);
                    }

                    act.setObjectifOperationnelREs(objectifOperationnelData);
                    actionData.add(act);
                }

                ostr.setActionREs(actionData);
                objectifStrategiqueData.add(ostr);
            }

            programmeRE.setObjectifStrategiqueREs(objectifStrategiqueData);
            programData.add(programmeRE);

            mainProgramData.add(new ProgrammeDataRE(ministereLibelle, structureParentLibelle, currentStructureLibelle, currentStructureTelephone, titre, logoStream, programData));

        }

        return mainProgramData;
    }

    public static List<ProgrammeDataRE> consctructRapport(String ministereLibelle, String structureParentLibelle, String currentStructureLibelle, String currentStructureTelephone, String titre, InputStream logoStream, List<ViewGlobale> globalData) {
        //ministere.getLibelle(), structureParent.getLibelle(), currentStructure.getLibelle(), currentStructure.getTelephone(), titre, logoStream

        // Conteneurs intermédiaires utilisés pour construire les données
        List<ProgrammeRE> allPrograms = extractPrograms(globalData);

        // conteneurs de données à imprimer
        List<ProgrammeDataRE> mainProgramData = new ArrayList<>();

        List<ProgrammeRE> programData = new ArrayList<>();

        // conteneur des structures
        List<Structure> allStructures = new ArrayList<>();

        // Construction des objet pour impression
        for (ProgrammeRE programmeRE : allPrograms) {
            programData = new ArrayList<>();
            // chercher tous les objectifs stratégiques 
            List<ObjectifStrategiqueRE> objectifStrategiqueData = new ArrayList<>();

            List<ObjectifOperationnelRE> objectifOperationnelData = new ArrayList<>();

            //List<ActiviteRE> activiteData = new ArrayList<>();
            List<ActionRE> actionData = new ArrayList<>();

            List<ObjectifStrategiqueRE> objStrData = extractStrategicPurpose(programmeRE, globalData);

            //programmeRE.setObjectifStrategiqueREs(objStrData);
            for (ObjectifStrategiqueRE ostr : objStrData) {
                List<ActionRE> actData = extractAction(ostr, globalData);

                for (ActionRE act : actData) {
                    List<ObjectifOperationnelRE> listObjOp = extractOperationalPurpose(act, globalData);

                    for (ObjectifOperationnelRE obOp : listObjOp) {
                        List<ActiviteRE> listActivite = extractActivityForRapport(obOp, globalData);

                        obOp.setActiviteREs(listActivite);
                        objectifOperationnelData.add(obOp);
                    }

                    act.setObjectifOperationnelREs(objectifOperationnelData);
                    actionData.add(act);
                }

                ostr.setActionREs(actionData);
                objectifStrategiqueData.add(ostr);
            }

            programmeRE.setObjectifStrategiqueREs(objectifStrategiqueData);
            programData.add(programmeRE);

            mainProgramData.add(new ProgrammeDataRE(ministereLibelle, structureParentLibelle, currentStructureLibelle, currentStructureTelephone, titre, logoStream, programData));

        }

        return mainProgramData;
    }

    public static void sortViewGloablData(List<ViewGlobale> data) {
        Collections.sort(data, new Comparator<ViewGlobale>() {
            @Override
            public int compare(ViewGlobale vg1, ViewGlobale vg2) {
                return vg1.convertCodeToInteger().compareTo(vg2.convertCodeToInteger());
            }
        });
    }

    protected static void sortObStrageData(List<ObjectifStrategiqueRE> data) {
        Collections.sort(data, new Comparator<ObjectifStrategiqueRE>() {
            @Override
            public int compare(ObjectifStrategiqueRE vg1, ObjectifStrategiqueRE vg2) {
                return vg1.convertCodeToInteger().compareTo(vg2.convertCodeToInteger());
            }
        });
    }

    //
    protected static void sortProgrammeData(List<ProgrammeRE> data) {
        Collections.sort(data, new Comparator<ProgrammeRE>() {
            @Override
            public int compare(ProgrammeRE vg1, ProgrammeRE vg2) {
                return vg1.convertCodeToInteger().compareTo(vg2.convertCodeToInteger());
            }
        });
    }

    protected static void sortActionData(List<ActionRE> data) {
        Collections.sort(data, new Comparator<ActionRE>() {
            @Override
            public int compare(ActionRE vg1, ActionRE vg2) {
                return vg1.convertCodeToInteger().compareTo(vg2.convertCodeToInteger());
            }
        });
    }

    //
    protected static void sortObOperationnelData(List<ObjectifOperationnelRE> data) {
        Collections.sort(data, new Comparator<ObjectifOperationnelRE>() {
            @Override
            public int compare(ObjectifOperationnelRE vg1, ObjectifOperationnelRE vg2) {
                return vg1.convertCodeToInteger().compareTo(vg2.convertCodeToInteger());
            }
        });
    }

    //
    protected static void sortActiviteData(List<ActiviteRE> data) {
        Collections.sort(data, new Comparator<ActiviteRE>() {
            @Override
            public int compare(ActiviteRE vg1, ActiviteRE vg2) {
                return vg1.convertCodeToInteger().compareTo(vg2.convertCodeToInteger());
            }
        });
    }

    public static List<RapportGardePage> constructGardePage(Ministere ministere, Structure structure, Exercice exercice, InputStream logo) {

        List<RapportGardePage> donnees = new ArrayList<>();

        RapportGardePage page = new RapportGardePage();

        String ministereValue = ministere.getLibelle().toUpperCase();
        page.setMinistere(ministereValue);
        String parentValue = (structure.getParent() != null) ? structure.getParent().getLibelle().toUpperCase() : null;
        page.setStructureParent(parentValue);
        String structureValue = structure.getLibelle().toUpperCase();
        page.setStructure(structureValue);
        String titreValue = "<b>RAPPORT D'EVALUATION DES PERFORMANCES " + exercice.getDebut().getYear() + "</b>";
        page.setTitre(titreValue);
        String libelle = "<u>STRUCTURE :</u> " + structure.getLibelle().toUpperCase();
        page.setLibelleStructure(libelle);
        page.setLogo(logo);

        donnees.add(page);

        return donnees;
    }

    public static List<RapportPageOne> constructRapportPageOne(Structure structure, int annee, List<GrillePerformance> grilles, Performance performance, Ponderation ponderation, long totalActivites, long activiteATemps) throws Exception {

        List<RapportPageOne> donnees = new ArrayList<>();

        //double ct = (Math.round(performance.getCoefftemps() * 100) / 100);
        double ct = performance.getCoefftemps();
        double tgro = performance.getTgro();
        double ei = performance.getEfficience();
        double effic = performance.getEfficacite();
        double gouv = performance.getGouvernance();
        double impact = performance.getImpact();

        String structLibelle = structure.getSigle().toUpperCase();

        String titre = "<b>EVALUATION DE LA PERFORMANCE DE " + structLibelle + "</b>";
        String texte = "La performance de " + structLibelle + " repose principalement sur la détermination de : (i) "
                + "l'efficacité, (ii) l'efficience, (iii) l'impact et (iv) la gouvernance, éléments essentiels du calcul de la Performance Globale (PG)";

        String texteEfficacite = structLibelle + " a un niveau d'efficacité de <b>" + effic + "% </b>."
                + " En effet, sur " + Nombre.CALCULATE.getValue(totalActivites) + " (" + totalActivites + ") activités programmées, " + Nombre.CALCULATE.getValue(activiteATemps) + " (" + activiteATemps + ") ont "
                + "été réalisées dans les délais; soit un coefficient temps de " + ct + "%. Pour ce qui est du taux global de réalisation des objectifs (TGRO), qui est la"
                + " moyenne des taux de réalisation des " + Nombre.CALCULATE.getValue(totalActivites) + " (" + totalActivites + ") activités programmées, il s'établit à " + tgro + "%";

        String formuleEfficacite = "<b>Ea = (60% x " + tgro + ") + (40% x " + ct + ")</b>";

        String resultatEfficacite = "<b>Ea = " + effic + "%</b>";

        String texteEfficience;

        if (ei > 0) {
            texteEfficience = structLibelle + " a un niveau de performance de <b>" + ei + "%</b> sur le plan de l'efficience. Ce qui représente le rapport du coût prévisionnel des activités réalisées à 100% moins le oût effectif des activités réalisées à 100% par le montant total dépensé.";
        } else {
            texteEfficience = "Dans l'impossibilité d'avoir des données pour le calcul de l'efficience pour toutes structures, le comité a décidé d'affecter 0% comme valeur.";
        }

        String texteGouvernance = structLibelle + " a un niveau de performance de <b>" + gouv + "%</b> sur le plan de la gouvernance.";

        String texteImpact;

        if (ponderation.getImpact() > 0) {
            texteImpact = structLibelle + " a un niveau de performance de <b>" + impact + "%</b> sur le plan de l'impact.";
        } else {
            texteImpact = "L'indicateur d'impact n'a pas été pris en compte pour toutes les structures pour l'évaluation " + annee + ". Les données de l'impact sont en effet extraites du "
                    + " document du rapport annuel de performance (RAP) du budget Programme du ministère. Le rapport de l'année " + annee + " du ministère n'est pas disponible, par conséquent "
                    + "la deuxième formule de calcul de la performance globale a été utilisée.";
        }

        for (GrillePerformance grille : grilles) {
            RapportPageOne page = new RapportPageOne();

            page.setTitreEvaluation(titre);
            page.setTextEvaluation(texte);

            page.setTexteEfficacite(texteEfficacite);
            page.setFormuleEfficacite(formuleEfficacite);
            page.setResultatEfficacite(resultatEfficacite);

            page.setTexteEfficience(texteEfficience);
            page.setFormuleEfficience(null);
            page.setResultatEfficience(null);

            page.setTexteGouvernance(texteGouvernance);
            page.setTexteImpact(texteImpact);

            String seuilPerformance = "[" + grille.getBorneInferieur() + " - " + grille.getBorneSuperieur() + (grille.getBorneInferieur() < 90 ? "[" : "]");
            String appreciation = grille.getAppreciation();

            page.setAppreciation(appreciation);
            page.setSeuilPerformance(seuilPerformance);

            donnees.add(page);
        }

        return donnees;
    }

    public static List<RapportPageTwo> constructRapportPageTwo(Structure structure, Performance performance, Ponderation ponderation) throws Exception {

        List<RapportPageTwo> donnees = new ArrayList<>();

        double pg = performance.getPgs();//(Math.round(performance.getPgs() * 100) / 100);

        double ei = performance.getEfficience();
        double effic = performance.getEfficacite();
        double gouv = performance.getGouvernance();
        double impact = performance.getImpact();

        String structLibelle = structure.getSigle().toUpperCase();

        String titrePerformance = "<b>Performance globale de " + structLibelle + "</b>";

        String textePerformance = "La performance de " + structLibelle + " se situe à <b>" + pg + "%</b>. Elle est obtenue à partir du calcul de la moyenne pondérée des différents critères.";

        String formulePerformance = "<b>PG = (" + ponderation.getEfficacite() + "% x " + effic + ") + (" + ponderation.getEfficience() + "% x " + ei + ") + (" + ponderation.getGouvernance() + "% x " + gouv + ")";

        if (ponderation.getImpact() > 0) {
            formulePerformance = formulePerformance + " + (" + ponderation.getImpact() + "% x " + impact + ") </b>";
        } else {
            formulePerformance = formulePerformance + "</b>";
        }

        String resultatPerformance = "<b>PG = " + pg + "%</b>";

        // Construction de l'objet
        RapportPageTwo page = new RapportPageTwo();
        page.setTitrePerformance(titrePerformance);
        page.setTextePerformance(textePerformance);
        page.setFormulePerformance(formulePerformance);
        page.setResultatPerformance(resultatPerformance);

        page.setLibelleEfficacite("Efficacité");
        page.setValeurEfficacite(effic + "%");

        page.setLibelleEfficience("Efficience");
        page.setValeurEfficience(ei + "%");

        page.setLibelleGouvernance("Gouvernance");
        page.setValeurGouvernance(gouv + "%");

        if (ponderation.getImpact() > 0) {
            page.setLibelleImpact("Impact");
            page.setValeurImpact(impact + "%");
        }

        page.setLibellePerformance("Performance globale");
        page.setValeurPerformance(pg + "%");

        donnees.add(page);

        return donnees;
    }

    public static void main(String[] args) throws Exception {
        String lettre = Nombre.CALCULATE.getValue(5);

        System.err.println(" ==================+> " + lettre);
    }
}
