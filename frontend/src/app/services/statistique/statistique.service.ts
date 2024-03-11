import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { GetAllImpactResponse, Impact } from 'src/app/models/performance/impact';
import { map } from 'rxjs/operators';
import { LazyLoadEvent } from 'primeng/api';
import { MinistereGlobalStatsBundleData } from 'src/app/models/stats/ministere-global-stats-bundle-data';
import { ResumerSectorielData } from 'src/app/models/stats/resumer-sectoriel-data';
import { ResumerSectorielDepenseData } from 'src/app/models/stats/resumer-sectoriel-depense-data';
import { EvolutionParam } from 'src/app/models/stats/evolution-param';
import { MinistereEvolutionBundle } from 'src/app/models/stats/ministere-evolution-bundle';
import { AllEvolutionData } from 'src/app/models/stats/all-evolution-data';
import { MinistereGlobalPerfData } from 'src/app/models/stats/ministere-global-perf-data';

const ressourceUrl = environment.statsAllResumeRessource; 
const ressourcePerformanceUrl = environment.statsAllPerformanceRessource;
const sectorielRessourceUrl = environment.statsAllSectorielResumeRessource;
const sectorielDepenseRessourceUrl = environment.statsAllSectorielDepenseResumeRessource;
const evolutionRessourceUrl = environment.evolutionRessourceUrl; 
const evolutionPerformanceRessourceUrl = environment.evolutionPerformanceRessourceUrl; 

const ressourcePerfUrl = environment.perfMinistereRessource;
const perfGlobalRessource = environment.perfGlobalRessource; 

@Injectable({
  providedIn: 'root'
})
export class StatistiqueService {


  constructor(private http: HttpClient) { }

  /**
   * chargement des statistiques globales d'un ministère sur un exercice donné
   * @param ministereId 
   * @param exerciceId 
   * @returns 
   */
  getMinistereBundle(ministereId:number, exerciceId:number): Observable<MinistereGlobalStatsBundleData> {
    return this.http.get(ressourceUrl + ministereId + '/' + exerciceId, { observe: 'response' })
    .pipe(map(response => {
        let globalResponse: MinistereGlobalStatsBundleData = response.body as MinistereGlobalStatsBundleData ; 
        return globalResponse;
      }));
  }

/**
   * chargement des performances globales des structures d'un ministère sur un exercice donné
   * @param ministereId 
   * @param exerciceId 
   * @returns 
   */
 getMinisterePerformance(ministereId:number, exerciceId:number): Observable<MinistereGlobalPerfData> {
  return this.http.get(ressourcePerfUrl + ministereId + '/' + exerciceId, { observe: 'response' })
  .pipe(map(response => {
      let globalResponse: MinistereGlobalPerfData = response.body as MinistereGlobalPerfData ; 
      return globalResponse;
    }));
}

  /**
   * chargement des statistiques globales d'un ministère sur un exercice donné
   * @param ministereId 
   * @param exerciceId 
   * @returns 
   */
   getMinisterePerformanceBundle(ministereId:number, exerciceId:number): Observable<MinistereGlobalStatsBundleData> {
    return this.http.get(ressourcePerformanceUrl + ministereId + '/' + exerciceId, { observe: 'response' })
    .pipe(map(response => {
        let globalResponse: MinistereGlobalStatsBundleData = response.body as MinistereGlobalStatsBundleData ; 
        return globalResponse;
      }));
  }

  // ResumerSectorielData
/**
 * chargement des statistiques globales sectorielle d'un ministère sur un exercice donné
 * @param ministereId 
 * @param exerciceId 
 * @returns 
 */
  getSectorielBundle(ministereId?:number, exerciceId?:number): Observable<ResumerSectorielData> {
    return this.http.get(sectorielRessourceUrl + ministereId + '/' + exerciceId, { observe: 'response' })
    .pipe(map(response => {
        let globalResponse: ResumerSectorielData = response.body as ResumerSectorielData ; 
        return globalResponse;
      }));
  }

  getSectorielPerf(exerciceId?:number): Observable<MinistereGlobalPerfData> {
    return this.http.get(perfGlobalRessource + exerciceId, { observe: 'response' })
    .pipe(map(response => {
        let globalResponse: MinistereGlobalPerfData = response.body as MinistereGlobalPerfData ; 
        return globalResponse;
      }));
  }

  /**
   * 
   * @param ministereId Chargement des dépenses sectorielles par ministère et par période
   * @param exerciceId 
   * @returns 
   */
  getSectorielDepenseBundle(ministereId?:number, exerciceId?:number): Observable<ResumerSectorielDepenseData> {
    return this.http.get(sectorielDepenseRessourceUrl + ministereId + '/' + exerciceId, { observe: 'response' })
    .pipe(map(response => {
        let globalResponse: ResumerSectorielDepenseData = response.body as ResumerSectorielData ; 
        return globalResponse;
      }));
  }

/**
 * 
 * @param evolution 
 * @returns 
 */
  getEvolutionBundle(evolution: EvolutionParam): Observable<AllEvolutionData> { 
    return this.http.post<AllEvolutionData>(evolutionRessourceUrl, evolution);
  } 

  /**
 * 
 * @param evolution 
 * @returns 
 */
   getPerformanceEvolutionBundle(evolution: EvolutionParam): Observable<AllEvolutionData> { 
    return this.http.post<AllEvolutionData>(evolutionPerformanceRessourceUrl, evolution);
  } 


  /**
 * 
 * @param evolution 
 * @returns 
 */
   getEvolutionPerformanceBundle(evolution: EvolutionParam): Observable<AllEvolutionData> { 
    return this.http.post<AllEvolutionData>(evolutionPerformanceRessourceUrl, evolution);
  } 

  getAll(event?: LazyLoadEvent): Observable<GetAllImpactResponse> {
    return this.http.get(ressourceUrl, { observe: 'response' })
    .pipe(map(response => {
        let impactResponse: GetAllImpactResponse = {
          //totalCount: parseInt(response.headers.get(totalCountHeader)),
          impactStructures: response.body as Impact[]
        };
        return impactResponse;
      }));
  }

  update(impact: Impact): Observable<Impact> {
    return this.http.put(ressourceUrl, impact);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${ressourceUrl}/${id}`);
  }
  create(request: Impact): Observable<any> {
    return this.http.post(ressourceUrl, request);
  }
}
