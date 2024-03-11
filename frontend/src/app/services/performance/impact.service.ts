import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { GetAllImpactResponse, Impact } from 'src/app/models/performance/impact';
import { map } from 'rxjs/operators';
import { LazyLoadEvent } from 'primeng/api';
const ressourceUrl= environment.impactRessource
@Injectable({
  providedIn: 'root'
})
export class ImpactService {


  constructor(private http: HttpClient) { }

  getImpactByStructure(id:number): Observable<GetAllImpactResponse> {
    return this.http.get(`${ressourceUrl}/all/${id}`, { observe: 'response' })
    .pipe(map(response => {
        let impactResponse: GetAllImpactResponse = {

          impactStructures: response.body as Impact[]
        };
        return impactResponse;
      }));
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
  create(request: Impact): Observable<Impact> {
    return this.http.post(ressourceUrl, request);
  }
}
