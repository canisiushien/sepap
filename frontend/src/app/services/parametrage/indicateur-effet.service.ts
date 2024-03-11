import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map } from 'rxjs/operators';
import { LazyLoadEvent } from 'primeng/api';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { IndicateurEffet, GetAllIndicateurEffetResponse } from 'src/app/models/parametrage/indicateur-effet';

const resourceUrl = environment.exempleResource;

@Injectable({
  providedIn: 'root'
})
export class IndicateurEffetService {

  constructor(private http: HttpClient) { }

  getAll(event?: LazyLoadEvent): Observable<GetAllIndicateurEffetResponse> {
    return this.http.get("assets/data/indicateur-effet.json", { observe: 'response' })
    .pipe(map(response => {
        let indicateursResponse: GetAllIndicateurEffetResponse = {
          //totalCount: parseInt(response.headers.get(totalCountHeader)),
          indicateurEffet: response.body as IndicateurEffet[]
        };
        return indicateursResponse;
      }));
  }



  create(request: IndicateurEffet): Observable<IndicateurEffet> {
    return this.http.post(resourceUrl, request);
  }



  update(exemple: IndicateurEffet): Observable<IndicateurEffet> {
    return this.http.put(resourceUrl, exemple);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${resourceUrl}/${id}`);
  }
}
