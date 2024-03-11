import { CritereGouvernance } from './../../models/performance/critere-gouvernance';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { LazyLoadEvent } from 'primeng/api';
import { Observable } from 'rxjs';
import { GetAllCritereResponse } from 'src/app/models/performance/critere-gouvernance';
import { environment } from 'src/environments/environment';
import { map } from 'rxjs/operators';
const critereUrl = environment.critereRessource;
@Injectable({
  providedIn: 'root'
})
export class CritereGouvernanceService {

  constructor(private http: HttpClient) { }

  getAll(event?: LazyLoadEvent): Observable<GetAllCritereResponse> {
    return this.http.get(critereUrl, { observe: 'response' })
    .pipe(map(response => {
        let criteresResponse: GetAllCritereResponse = {
          //totalCount: parseInt(response.headers.get(totalCountHeader)),
          criteres: response.body as CritereGouvernance[]
        };
        return criteresResponse;
      }));

  }

  getAllActif(event?: LazyLoadEvent): Observable<GetAllCritereResponse> {
    return this.http.get(critereUrl+'/actif', { observe: 'response' })
    .pipe(map(response => {
        let criteresResponse: GetAllCritereResponse = {
          //totalCount: parseInt(response.headers.get(totalCountHeader)),
          criteres: response.body as CritereGouvernance[]
        };
        return criteresResponse;
      }));

  }
  create(critere: CritereGouvernance): Observable<CritereGouvernance> {
    return this.http.post(critereUrl,critere);
  }



  update(critere: CritereGouvernance): Observable<CritereGouvernance> {
    return this.http.put(critereUrl, critere);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${critereUrl}/${id}`);
  }
}
