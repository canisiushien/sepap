import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LazyLoadEvent } from 'primeng/api';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { EvaluationGouvernance, GetAllEvaluationGouverResponse } from 'src/app/models/performance/evaluation-gouvernance';
import { environment } from 'src/environments/environment';
const ressourceUrl= environment.evaluationGRessource
@Injectable({
  providedIn: 'root'
})
export class EvaluationGouvernanceService {


  constructor(private http: HttpClient) { }

  getAll(event?: LazyLoadEvent): Observable<GetAllEvaluationGouverResponse> {
    return this.http.get(ressourceUrl, { observe: 'response' })
    .pipe(map(response => {
        let evaluationGResponse: GetAllEvaluationGouverResponse = {
          //totalCount: parseInt(response.headers.get(totalCountHeader)),
          evaluationGouvernances: response.body as EvaluationGouvernance[]
        };
        return evaluationGResponse;
      }));
  }

 
  update(evaluationGouvernance: EvaluationGouvernance): Observable<EvaluationGouvernance> {
    return this.http.put(ressourceUrl, evaluationGouvernance);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${ressourceUrl}/${id}`);
  }
  create(request: EvaluationGouvernance): Observable<any> {
    return this.http.post(ressourceUrl, request);
  }
}
