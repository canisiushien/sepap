import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LazyLoadEvent } from 'primeng/api';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { GetAllIndicateurResponse, Indicateur } from 'src/app/models/parametrage/indicateur-objectif';
import { GetAllIndicateurImpactResponse, IndicateurImpact } from 'src/app/models/parametrage/indicateurImpact';
import { environment } from 'src/environments/environment';

const resourceUrl = environment.indicateurImpactResource;

@Injectable({
  providedIn: 'root'
})
export class IndicateurImpactService {

  constructor(private http: HttpClient) { }

  getAll(event?: LazyLoadEvent): Observable<GetAllIndicateurResponse> {
    return this.http.get(resourceUrl, { observe: 'response' })
    .pipe(map(response => {
        let indicateurResponse: GetAllIndicateurResponse = {
          //totalCount: parseInt(response.headers.get(totalCountHeader)),
          indicateurs: response.body as Indicateur[]
        };
        return indicateurResponse;
      }));
  }



  create(request: Indicateur): Observable<Indicateur> {
    return this.http.post(resourceUrl, request);
  }



  update(indicateur: Indicateur): Observable<IndicateurImpact> {
    return this.http.put(resourceUrl, indicateur);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${resourceUrl}/${id}`);
  }
}
