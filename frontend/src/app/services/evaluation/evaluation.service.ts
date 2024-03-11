import { DocumentTache } from 'src/app/models/programmation/document';
import { TauxEvaluation } from './../../models/evaluation/taux_evaluation';
import { TacheEvaluation } from './../../models/evaluation/tache_evaluation';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { map } from 'rxjs/operators';
import { Evaluation, GetAllEvaluationResponse } from 'src/app/models/evaluation/evaluation';
import { environment } from 'src/environments/environment';
import { LazyLoadEvent } from 'primeng/api';
import { GetAllProgrammationForEvResponse } from 'src/app/models/programmation/programmation-for-evaluation-dto';
import { ProgrammationForEvaluationDTO } from 'src/app/models/programmation/programmation-for-evaluation-dto';
const tacheDocuUrl=environment.documentRessource;
const resourceUrl = environment.programmationPhysiqueRessource;
const resourceTacheUrl= environment.tacheEvaluationRessource;
const resourceEvalueUrl=environment.programmationsResource;
@Injectable({
  providedIn: 'root'
})
export class EvaluationService {

  constructor(private http: HttpClient) {

   }

   getAllByProgrammation(id:any): Observable<GetAllEvaluationResponse> {
    return this.http.get(`${resourceUrl}/programmation/${id}`, { observe: 'response' })
    .pipe(map(response => {
        let evaluationResponse: GetAllEvaluationResponse = {

          evaluations: response.body as Evaluation[]
        };
        return evaluationResponse;
      }));
  }

  getProgrammationTaux(request: TauxEvaluation): Observable<TauxEvaluation> {
    return this.http.get<TauxEvaluation>( `${resourceUrl}/taux-execution`);
  }

  create(request: TacheEvaluation[]): Observable<any> {
    return this.http.post(resourceTacheUrl, request);
  }

  ajouterTacheDocument(request: any): Observable<DocumentTache> {
    return this.http.post(tacheDocuUrl, request);
  }

  getEvaluationByProgrammation(id:any): Observable<GetAllEvaluationResponse> {
    return this.http.get(`${resourceUrl}/programmation/${id}`, { observe: 'response' })
    .pipe(map(response => {
        let evaluationResponse: GetAllEvaluationResponse = {

          evaluations: response.body as Evaluation[]
        };
        return evaluationResponse;
      }));
  }

  getProgrammationEvaluees(ids:any,ide:any): Observable<GetAllProgrammationForEvResponse> {
    return this.http.get(`${resourceEvalueUrl}/programmations-evaluees/${ids}/${ide}`, { observe: 'response' })
    .pipe(map(response => {
        let evaluationResponse: GetAllProgrammationForEvResponse = {

          programmations: response.body as ProgrammationForEvaluationDTO[]
        };
        return evaluationResponse;
      }));
  }

}
