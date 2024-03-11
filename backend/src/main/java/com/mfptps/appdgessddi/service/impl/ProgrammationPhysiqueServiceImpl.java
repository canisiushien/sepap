/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mfptps.appdgessddi.service.impl;

import com.mfptps.appdgessddi.entities.Periode;
import com.mfptps.appdgessddi.entities.Programmation;
import com.mfptps.appdgessddi.entities.ProgrammationPhysique;
import com.mfptps.appdgessddi.repositories.PeriodeRepository;
import com.mfptps.appdgessddi.repositories.ProgrammationPhysiqueRepository;
import com.mfptps.appdgessddi.service.CustomException;
import com.mfptps.appdgessddi.service.ProgrammationPhysiqueService;
import com.mfptps.appdgessddi.service.dto.PeriodesDTO;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Slf4j
@Service
public class ProgrammationPhysiqueServiceImpl implements ProgrammationPhysiqueService {

    private final ProgrammationPhysiqueRepository programmationPhysiqueRepository;

    private final PeriodeRepository periodeRepository;

    public ProgrammationPhysiqueServiceImpl(ProgrammationPhysiqueRepository programmationPhysiqueRepository,
            PeriodeRepository periodeRepository) {
        this.programmationPhysiqueRepository = programmationPhysiqueRepository;
        this.periodeRepository = periodeRepository;
    }

    @Override
    public ProgrammationPhysique create(ProgrammationPhysique programmationPhysique) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * automatically adds the number of lines corresponding to the number of
     * periods checked (true)
     *
     * @param periodes : list of periodes(from ProgrammationDTO) and its value
     * checked
     * @param programmation : Object Programmation saving
     */
    public void addProgrammationPhysique(List<PeriodesDTO> periodes, Programmation programmation) {
        List<Periode> periodesParametrees = periodeRepository.findByPeriodiciteActif();
        List<ProgrammationPhysique> programmationPhysiques = new ArrayList<>();

        if (periodes.size() != periodesParametrees.size()) {
            throw new CustomException("Périodes non conformes à celles parametrées.");
        }

        periodes.forEach(p -> {
            periodesParametrees.stream().filter(pp -> ((p.getLibelle().trim().charAt(0) == pp.getLibelle().charAt(0))
                    && (p.getLibelle().trim().charAt(p.getLibelle().length() - 1)
                    == pp.getLibelle().charAt(pp.getLibelle().length() - 1))
                    && p.isValeur())).map(pp -> {
                //if elem of periodesDTO is true and fisrtIndex/lastIndex of libelles are equals
                ProgrammationPhysique progPhysique = new ProgrammationPhysique();
                progPhysique.setPeriode(pp);
                return progPhysique;
            }).map(progPhysique -> {
                progPhysique.setProgrammation(programmation);
                return progPhysique;
            }).forEachOrdered(progPhysique -> {
                programmationPhysiques.add(progPhysique);
            });
        });
// above similars loops
//
//        for (PeriodesDTO p : periodes) {
//            for (Periode pp : periodesParametrees) {
//                if ((p.getLibelle().trim().charAt(0) == pp.getLibelle().charAt(0))
//                        && (p.getLibelle().trim().charAt(p.getLibelle().length() - 1)
//                        == pp.getLibelle().charAt(pp.getLibelle().length() - 1))
//                        && p.isValeur()) {//if elem of periodesDTO is true and fisrtIndex/lastIndex of libelles are equals
//                    ProgrammationPhysique progPhysique = new ProgrammationPhysique();
//                    progPhysique.setPeriode(pp);
//                    progPhysique.setProgrammation(programmation);
//                    programmationPhysiques.add(progPhysique);
//                }
//            }
//        }
        programmationPhysiqueRepository.saveAll(programmationPhysiques);
    }

    @Override
    public Page<ProgrammationPhysique> findAllByProgrammation(Long progammationId, Pageable pageable) {
        return programmationPhysiqueRepository.findByProgrammationId(progammationId, pageable);
    }
}
