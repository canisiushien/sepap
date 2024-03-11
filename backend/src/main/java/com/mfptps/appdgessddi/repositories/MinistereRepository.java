package com.mfptps.appdgessddi.repositories;

import com.mfptps.appdgessddi.entities.Ministere;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MinistereRepository extends JpaRepository<Ministere, Long> {

    Optional<Ministere> findByCode(String code);

    @Query("SELECT m FROM Ministere m, MinistereStructure ms, Structure s "
            + "WHERE ms.ministere.id = m.id "
            + "AND ms.structure.id = s.id "
            + "AND s.id = :structureId "
            + "AND ms.statut IS TRUE")
    Optional<Ministere> findMinistereStructureCode(Long structureId);

    @Query("SELECT m FROM Ministere m "
            + "WHERE m.id=:ministereID "
            + "AND m.deleted=false")
    Ministere findMinistereByID(Long ministereID);

    @Query("SELECT m FROM Ministere m "//Exclure le ministereTest
            + "WHERE m.code != :codeMinistere "
            + "AND m.deleted = false")
    Page<Ministere> findAll(String codeMinistere, Pageable pageable);

}
