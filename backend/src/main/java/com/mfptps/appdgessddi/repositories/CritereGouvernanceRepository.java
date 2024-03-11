package com.mfptps.appdgessddi.repositories;

import com.mfptps.appdgessddi.entities.CritereGouvernance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CritereGouvernanceRepository extends JpaRepository<CritereGouvernance, Long> {

    Page<CritereGouvernance> findByActifTrue(Pageable pageable);
}
