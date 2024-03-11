///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.mfptps.appdgessddi.service.reportentities;
//
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.Arrays;
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
//public class JasperTest {
//
////    @PersistenceContext
////    private static EntityManager em;
////
////    public JasperTest(EntityManager em) {
////        this.em = em;
////        Query l = this.em.createNativeQuery("select * from structuresmyministere ");
////        log.info("__________________ l " + l.toString());
////
////        List<Object[]> list = l.getResultList();
////        log.info("__________________ taille " + list.size());
////        for (Object[] o : list) {
////            System.out.println("---" + Arrays.toString(o));
////        }
////    }
//    public static void main(String[] args) {
////        Query l = em.createNativeQuery("select * from structuresmyministere ");
////        log.info("__________________ l " + l.toString());
////
////        List<Object[]> list = l.getResultList();
////        log.info("__________________ taille " + list.size());
////        for (Object[] o : list) {
////            System.out.println("---" + Arrays.toString(o));
////        }
//
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
//        programmeREs.add(new ProgrammeRE("1.1", "Programme1",
//                "ST-GVAP",
//                Arrays.asList(
//                        new ObjectifStrategiqueRE(null, "1.1.1",
//                                "OS1", "-",
//                                "-", Arrays.asList(
//                                        new ActionRE(null, "1.1.1.1", "Action1",
//                                                "-", Arrays.asList(
//                                                        new ObjectifOperationnelRE(null, "1.1.1.1.1", "OP1",
//                                                                "-", activiteREs))))))));
//
//        programmeREs.add(new ProgrammeRE("1.2", "Programme2",
//                "ST-GVAP",
//                Arrays.asList(
//                        new ObjectifStrategiqueRE(null, "1.2.1",
//                                "OS2", "-",
//                                "-", Arrays.asList(
//                                        new ActionRE(null, "1.2.1.1", "Action2",
//                                                "-", Arrays.asList(
//                                                        new ObjectifOperationnelRE(null, "1.2.1.1.1", "OP2",
//                                                                "-", activiteREsBis))))))));
//        programmeDataREs.add(new ProgrammeDataRE("MFTPS", "Cabinet", "ST-GVAP",
//                "20000000", "PROGRAMME D'ACTIVITES", null, programmeREs));
//
//        try {
//
//            InputStream reportStream = JasperTest.class.getResourceAsStream("/conteneur_principal.jasper");
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
//
//}
