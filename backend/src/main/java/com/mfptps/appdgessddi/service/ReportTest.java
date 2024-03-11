///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.mfptps.appdgessddi.service;
//
//import com.mfptps.appdgessddi.repositories.QueryManagerRepository;
//import com.mfptps.appdgessddi.service.reportentities.ActionRE;
//import com.mfptps.appdgessddi.service.reportentities.ActiviteRE;
//import com.mfptps.appdgessddi.service.reportentities.ObjectifOperationnelListVE;
//import com.mfptps.appdgessddi.service.reportentities.ObjectifOperationnelRE;
//import com.mfptps.appdgessddi.service.reportentities.ObjectifStrategiqueRE;
//import com.mfptps.appdgessddi.service.reportentities.ProgrammationListVE;
//import com.mfptps.appdgessddi.service.reportentities.ProgrammeDataRE;
//import com.mfptps.appdgessddi.service.reportentities.ProgrammeRE;
//import com.mfptps.appdgessddi.service.reportentities.StructuresByMinistereVE;
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import lombok.extern.slf4j.Slf4j;
//import net.sf.jasperreports.engine.JRDataSource;
//import net.sf.jasperreports.engine.JasperExportManager;
//import net.sf.jasperreports.engine.JasperFillManager;
//import net.sf.jasperreports.engine.JasperPrint;
//import net.sf.jasperreports.engine.JasperReport;
//import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
//import net.sf.jasperreports.engine.util.JRLoader;
//import org.springframework.stereotype.Service;
//
///**
// *
// * @author Canisius <canisiushien@gmail.com>
// */
//@Slf4j
//@Service
//public class ReportTest {
//
//    private final QueryManagerRepository qmr;
//
//    public ReportTest(QueryManagerRepository qmr) {
//        this.qmr = qmr;
//    }
//
//    public void seviceTestReport() {
//        List<StructuresByMinistereVE> list = qmr.structuresList();
//        for (StructuresByMinistereVE l : list) {
//            System.out.println("_______ listeStruct : " + l.getSigleStructure() + " " + l.getSigleStructure());
//        }
//
//        for (ProgrammationListVE p : qmr.programmationList()) {
//            System.out.println("_______ listeProg : " + p.getCode() + " " + p.getProjetId());
//        }
//
//        for (ObjectifOperationnelListVE o : qmr.objectifOperationnelList()) {
//            System.out.println("_______ listeObjectifOp : " + o.getCodeObjectifOp() + " " + o.getLibelleObjectifOp());
//        }
//        ProgrammeDataRE programmeDataRE = new ProgrammeDataRE();
//        List<ProgrammeDataRE> programmeDataREs = new ArrayList<>();
//
//        ProgrammeRE programmeRE = new ProgrammeRE();
//        List<ProgrammeRE> programmeREs = new ArrayList<>();
//
//        ObjectifStrategiqueRE objectifStrategiqueRE = new ObjectifStrategiqueRE();
//        List<ObjectifStrategiqueRE> objectifStrategiqueREs = new ArrayList<>();
//
//        ActionRE actionRE = new ActionRE();
//        List<ActionRE> actionREs = new ArrayList<>();
//
//        ObjectifOperationnelRE objectifOperationnelRE = new ObjectifOperationnelRE();
//        List<ObjectifOperationnelRE> objectifOperationnelREs = new ArrayList<>();
//
//        ActiviteRE activiteRE = new ActiviteRE();
//        List<ActiviteRE> activiteREs = new ArrayList<>();
//
//        for (int i = 0; i < 10; i++) {
//            activiteREs.add(new ActiviteRE("" + (i + 1), "libelle" + i, "indicateur" + i, "" + i, (double) i, "fin" + i, "ST-GVAP" + i));
//        }
//
//        List<ActiviteRE> activiteREsBis = new ArrayList<>();
//
//        for (int i = 10; i < 23; i++) {
//            activiteREsBis.add(new ActiviteRE("" + (i + 1), "libelle" + i, "indicateur" + i, "" + i, (double) i, "fin" + i, "ST-GVAP" + i));
//        }
//
////        programmeREs.add(new ProgrammeRE("1.1", "Programme1",
////                "ST-GVAP",
////                Arrays.asList(
////                        new ObjectifStrategiqueRE("1.1.1",
////                                "OS1", "-",
////                                "-", Arrays.asList(
////                                        new ActionRE("1.1.1.1", "Action1",
////                                                "-", Arrays.asList(
////                                                        new ObjectifOperationnelRE("1.1.1.1.1", "OP1",
////                                                                "-", activiteREs))))))));
////        programmeREs.add(new ProgrammeRE("1.2", "Programme2",
////                "ST-GVAP",
////                Arrays.asList(
////                        new ObjectifStrategiqueRE("1.2.1",
////                                "OS2", "-",
////                                "-", Arrays.asList(
////                                        new ActionRE("1.2.1.1", "Action2",
////                                                "-", Arrays.asList(
////                                                        new ObjectifOperationnelRE("1.2.1.1.1", "OP2",
////                                                                "-"))))))));
//        programmeDataREs.add(new ProgrammeDataRE("MFTPS", "Cabinet", "ST-GVAP",
//                "20000000", "PROGRAMME D'ACTIVITES", null, programmeREs));
//
//        try {
//
//            InputStream reportStream = ReportTest.class.getResourceAsStream("/conteneur_principal.jasper");
//
//            Map<String, Object> parameters = new HashMap<>();
//
//            JRDataSource datasource = new JRBeanCollectionDataSource(programmeDataREs);
//            JasperReport japerReport = (JasperReport) JRLoader.loadObject(reportStream);
//
//            JasperPrint jasperPrint = JasperFillManager.fillReport(japerReport, parameters, datasource);
//
//            JasperExportManager.exportReportToPdfFile(jasperPrint, "C:\\Users\\Canisius\\Pictures\\test.pdf");//exportReportToPdfStream(jasperPrint, outStream);
//        } catch (Exception e) {
//            log.error("________", e);
//        }
//    }
//}
