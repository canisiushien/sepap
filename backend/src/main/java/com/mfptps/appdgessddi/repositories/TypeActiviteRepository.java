package com.mfptps.appdgessddi.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mfptps.appdgessddi.entities.TypeActivites;




public interface TypeActiviteRepository extends JpaRepository<TypeActivites, Long>{

	Optional<TypeActivites> findById(Long id);
}
