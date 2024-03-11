/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mfptps.appdgessddi.repositories;

import com.mfptps.appdgessddi.service.reportentities.ObjectifOperationnelListVE;
import com.mfptps.appdgessddi.service.reportentities.ProgrammationListVE;
import com.mfptps.appdgessddi.service.reportentities.ProgrammationPhysiqueRE;
import com.mfptps.appdgessddi.service.reportentities.StructuresByMinistereVE;
import com.mfptps.appdgessddi.service.reportentities.ViewGlobale;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Component;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Component
public class QueryManagerRepository {

    @PersistenceContext
    private EntityManager em;

    public List<StructuresByMinistereVE> structuresList() {
        Query q = em.createNativeQuery("select * from structuresmyministere", StructuresByMinistereVE.class);
        List<StructuresByMinistereVE> result = q.getResultList();
        return result;
    }

    public List<ProgrammationListVE> programmationList() {
        Query q = em.createNativeQuery("select * from programmationlist", ProgrammationListVE.class);
        List<ProgrammationListVE> result = q.getResultList();
        return result;
    }

    public List<ObjectifOperationnelListVE> objectifOperationnelList() {
        Query q = em.createNativeQuery("select * from objectifoperationnellist", ObjectifOperationnelListVE.class);
        List<ObjectifOperationnelListVE> result = q.getResultList();
        return result;
    }

    public List<ViewGlobale> globalDataMinistere(long exerciceId, long ministereId) {
        Query q = em.createNativeQuery("select * from dgessreportview where idexercice = :exercice and idministere = :ministere", ViewGlobale.class);
        q.setParameter("exercice", exerciceId);
        q.setParameter("ministere", ministereId);
        List<ViewGlobale> result = q.getResultList();
        return result;
    }

    public List<ViewGlobale> globalDataMinistere(long exerciceId, Date deadline, long ministereId) {
        Query q = em.createNativeQuery("select * from dgessreportview where idexercice = :exercice and lastevaldate <= :deadline and idministere = :ministere", ViewGlobale.class);
        q.setParameter("exercice", exerciceId);
        q.setParameter("deadline", deadline);
        q.setParameter("ministere", ministereId);
        List<ViewGlobale> result = q.getResultList();
        return result;
    }

    public List<ViewGlobale> globalDataStructure(long exerciceId, long structureId, Date deadline) {
        Query q = em.createNativeQuery("select * from dgessreportview where idexercice = :exercice and idstructure = :structure and lastevaldate <= :deadline", ViewGlobale.class);
        q.setParameter("exercice", exerciceId);
        q.setParameter("structure", structureId);
        q.setParameter("deadline", deadline);
        List<ViewGlobale> result = q.getResultList();
        return result;
    }

    public List<ViewGlobale> globalDataStructure(long exerciceId, long structureId) {
        Query q = em.createNativeQuery("select * from dgessreportview where idexercice = :exercice and idstructure = :structure", ViewGlobale.class);
        q.setParameter("exercice", exerciceId);
        q.setParameter("structure", structureId);
        List<ViewGlobale> result = q.getResultList();
        return result;
    }

    public List<ProgrammationPhysiqueRE> programmationPhysiqueList() {
        Query q = em.createNativeQuery("select * from programmationphysique", ProgrammationPhysiqueRE.class);
        List<ProgrammationPhysiqueRE> result = q.getResultList();
        return result;
    }
}
