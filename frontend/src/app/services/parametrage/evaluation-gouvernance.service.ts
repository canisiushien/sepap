import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LazyLoadEvent } from 'primeng/api';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { EvaluationGouvernance, GetAllEvaluationGouvResponse } from 'src/app/models/parametrage/evaluation-gouvernance';
import { EvaluationGouvernanceDTO } from 'src/app/models/parametrage/evaluation-gouvernance-dto';
import { GetAllEvaluationGouverResponse } from 'src/app/models/performance/evaluation-gouvernance';
import { environment } from 'src/environments/environment';
const evaluationGouvernanceUrl = environment.evaluationGouvernanceRessource;

@Injectable({
  providedIn: 'root'
})
export class EvaluationGouvernanceService {
  constructor(private http: HttpClient) { }
  getAll(event?: LazyLoadEvent): Observable<GetAllEvaluationGouvResponse> {
    return this.http.get(evaluationGouvernanceUrl, { observe: 'response' })
    .pipe(map(response => {
        let evaluationGouvernancesResponse: GetAllEvaluationGouvResponse = {
          evaluationGouvernances: response.body as EvaluationGouvernance[]
        };
        return evaluationGouvernancesResponse;
      }));

  }

  getAllStruct(structureId:number,exerciceId:number, event?: LazyLoadEvent): Observable<GetAllEvaluationGouverResponse> {
    return this.http.get(evaluationGouvernanceUrl+'/'+structureId+'/'+exerciceId, { observe: 'response' })
    .pipe(map(response => {
        let evaluationGResponse: GetAllEvaluationGouverResponse = {
          evaluationGouvernances: response.body as EvaluationGouvernance[]
        };
        return evaluationGResponse;
      }));
  }
  create(critere: EvaluationGouvernance): Observable<EvaluationGouvernance> {
    return this.http.post(evaluationGouvernanceUrl,critere);
  }

  update(critere: EvaluationGouvernanceDTO): Observable<EvaluationGouvernanceDTO> {
    return this.http.put(evaluationGouvernanceUrl,critere);
  }
}

