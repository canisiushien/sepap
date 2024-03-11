package com.mfptps.appdgessddi.repositories;

import com.mfptps.appdgessddi.entities.Observations;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ObservationRepository extends JpaRepository<Observations,Long> {
}
