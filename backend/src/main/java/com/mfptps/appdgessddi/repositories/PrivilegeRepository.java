/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mfptps.appdgessddi.repositories;

import java.util.stream.Stream;

import com.mfptps.appdgessddi.entities.Privilege;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author bieve
 */
public interface PrivilegeRepository extends JpaRepository<Privilege, Long>{
    
    @Query("SELECT u FROM Privilege u")
    Stream<Privilege> streamAll();
    
}
