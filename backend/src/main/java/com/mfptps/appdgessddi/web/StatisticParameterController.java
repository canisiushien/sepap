/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mfptps.appdgessddi.web;

import com.mfptps.appdgessddi.service.StatisticParameterService;
import com.mfptps.appdgessddi.service.dto.statisticresponses.AllEvolutionData;
import com.mfptps.appdgessddi.service.dto.statisticresponses.CountStructureGroupByType;
import com.mfptps.appdgessddi.service.dto.statisticresponses.EvolutionParam;
import com.mfptps.appdgessddi.service.dto.statisticresponses.MinistereGlobalPerfData;
import com.mfptps.appdgessddi.service.dto.statisticresponses.MinistereGlobalStatsBundleData;
import com.mfptps.appdgessddi.service.dto.statisticresponses.ResumerActiviteData;
import com.mfptps.appdgessddi.service.dto.statisticresponses.ResumerDepenseData;
import com.mfptps.appdgessddi.service.dto.statisticresponses.ResumerSectorielData;
import com.mfptps.appdgessddi.service.dto.statisticresponses.ResumerSectorielDepenseData;
import com.mfptps.appdgessddi.service.dto.statisticresponses.ResumerStructureData;
import java.util.List;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Slf4j
@RestController
@RequestMapping(path = "/api/stats")
public class StatisticParameterController {

    private final StatisticParameterService service;

    public StatisticParameterController(StatisticParameterService statisticParameterService) {
        this.service = statisticParameterService;
    }
    
    @GetMapping(path = "/depense/{ministereId}/{exerciceId}")
    public ResponseEntity<ResumerDepenseData> resumerDepenseParMinistere(@PathVariable(name = "ministereId") Long ministereId, @PathVariable(name = "exerciceId") Long exerciceId) { 
        ResumerDepenseData data = service.resumerDepenseParMinistere(ministereId,exerciceId);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    
    @GetMapping(path = "/activite/{ministereId}/{exerciceId}")
    public ResponseEntity<ResumerActiviteData> resumerActiviteParMinistere(@PathVariable(name = "ministereId") Long ministereId, @PathVariable(name = "exerciceId") Long exerciceId) { 
        ResumerActiviteData data = service.resumerActiviteParMinistere(ministereId,exerciceId);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    
    @GetMapping(path = "/structure/{ministereId}/{exerciceId}")
    public ResponseEntity<List<ResumerStructureData>> resumerActiviteParStructure(@PathVariable(name = "ministereId") Long ministereId, @PathVariable(name = "exerciceId") Long exerciceId) { 
        List<ResumerStructureData> data = service.resumerActiviteParStructure(ministereId,exerciceId);// 
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    
    @GetMapping(path = "/resumer/{ministereId}/{exerciceId}")
    public ResponseEntity<MinistereGlobalStatsBundleData> resumerMinistere(@PathVariable(name = "ministereId") Long ministereId, @PathVariable(name = "exerciceId") Long exerciceId) { 
        MinistereGlobalStatsBundleData data = service.resumerMinistere(ministereId,exerciceId);// 
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    
    /**
     * Resume les performances des structures de chaque ministère sur un exercice donné
     * @param ministereId
     * @param exerciceId
     * @return 
     */
    @GetMapping(path = "/perf/{ministereId}/{exerciceId}")
    public ResponseEntity<MinistereGlobalPerfData> resumerPerformance(@PathVariable(name = "ministereId") Long ministereId, @PathVariable(name = "exerciceId") Long exerciceId) { 
        MinistereGlobalPerfData data = service.resumerPerformance(ministereId,exerciceId); 
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    
    @GetMapping(path = "/perf-global/{exerciceId}")
    public ResponseEntity<MinistereGlobalPerfData> resumerGlobalPerformance(@PathVariable(name = "exerciceId") Long exerciceId) { 
        MinistereGlobalPerfData data = service.resumerGlobalPerformance(exerciceId); 
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    
    
    
    @GetMapping(path = "/sectoriel/{ministereId}/{exerciceId}")
    public ResponseEntity<ResumerSectorielData> resumerSectoriel(@PathVariable(name = "ministereId") Long ministereId, @PathVariable(name = "exerciceId") Long exerciceId) { 
        ResumerSectorielData data = service.resumerSectoriel(ministereId,exerciceId);// 
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    
    /**
     * resumerSectorielDepense: resume pour une structure donnée les dépenses
     * @param ministereId
     * @param exerciceId
     * @return 
     */
    @GetMapping(path = "/sectoriel-depense/{ministereId}/{exerciceId}")
    public ResponseEntity<ResumerSectorielDepenseData> resumerSectorielDepense(@PathVariable(name = "ministereId") Long ministereId, @PathVariable(name = "exerciceId") Long exerciceId) { 
        ResumerSectorielDepenseData data = service.resumerSectorielDepense(ministereId,exerciceId);// 
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

     /**
     * resumerEvolution: resume l'ensemble des pour une structure donnée les dépenses
     * @param params
     * @return 
     */
    @PostMapping(path = "/evolution/")
    public ResponseEntity<AllEvolutionData> resumerEvolution(@Valid @RequestBody EvolutionParam params) { 
        AllEvolutionData data = service.resumerEvolution(params);// 
        return new ResponseEntity<>(data, HttpStatus.OK);
    } 
    
     /**
     * resumerEvolution: resume l'ensemble des pour une structure donnée les dépenses
     * @param params
     * @return 
     */
    @PostMapping(path = "/evolution-performance/")
    public ResponseEntity<AllEvolutionData> resumerEvolutionPerformance(@Valid @RequestBody EvolutionParam params) { 
        AllEvolutionData data = service.resumerEvolutionPerformance(params);// 
        return new ResponseEntity<>(data, HttpStatus.OK);
    } 
    
    /**
     *
     * @param id: id of Ministere
     * @return
     */
    @GetMapping(path = "/count/{id}")
    public ResponseEntity<Long> countStructuresByMinistere(@PathVariable(name = "id") Long id) {
        log.debug("Nombre de structures (de niveau 1) du Ministere : {}", id);
        long counter = service.nbStructuresByMinistere(id);
        return new ResponseEntity<>(counter, HttpStatus.OK);
    }

    /**
     *
     * @param id : id of Ministere
     * @return
     */
    @GetMapping(path = "/structure/type/{id}")
    public ResponseEntity<List<CountStructureGroupByType>> countStructuresByMinistereAndByGroupType(@PathVariable(name = "id") Long id) {
        log.debug("Nombre de structures (de niveau 1 et groupées par type) du Ministere : {}", id);
        List<CountStructureGroupByType> response = service.nbStructuresByGroupType(id);
        return ResponseEntity.ok().body(response);
    }

}
