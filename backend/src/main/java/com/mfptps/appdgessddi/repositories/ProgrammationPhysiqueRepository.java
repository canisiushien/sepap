/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mfptps.appdgessddi.repositories;

import com.mfptps.appdgessddi.entities.Periode;
import com.mfptps.appdgessddi.entities.ProgrammationPhysique;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public interface ProgrammationPhysiqueRepository extends JpaRepository<ProgrammationPhysique, Long> {

    @Query("SELECT pph FROM ProgrammationPhysique pph, Periode p, Programmation pro "
            + "WHERE pph.programmation.id = pro.id AND pro.id = :id "
            + "AND pph.periode.id = p.id "
            + "AND :date BETWEEN p.debut AND p.fin")
    List<ProgrammationPhysique> findByProgrammationAndPeriode(Long id, Date date);

    @Query("SELECT pph FROM ProgrammationPhysique pph, Periode p, Programmation pro "
            + "WHERE pph.programmation.id = pro.id AND pro.id = :id "
            + "AND pph.periode.id = p.id ")
    List<ProgrammationPhysique> findByProgrammationAndPeriode(Long id);

    Page<ProgrammationPhysique> findByProgrammationId(long id, Pageable pageable);

    @Query("SELECT per FROM ProgrammationPhysique pph, Periode per, Programmation p "
            + "WHERE pph.periode.id = per.id AND per.id = :periodeId "
            + "AND pph.programmation.id = p.id AND p.id = :programmationId")
    Optional<Periode> findByPeriodeAndProgrammation(long periodeId, long programmationId);

    @Query("SELECT MAX(per.fin) AS max_fin FROM Periode per, Programmation prog, ProgrammationPhysique pp WHERE prog.id = pp.programmation.id AND per.id = pp.periode.id AND prog.id = ?1")
    Date findPeriodeMaxDateForProgrammation(long programmationId);

}
