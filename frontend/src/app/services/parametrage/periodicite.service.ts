import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map } from 'rxjs/operators';
import { LazyLoadEvent } from 'primeng/api';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { GetAllPeriodiciteResponse, Periodicite } from 'src/app/models/parametrage/periodicite';
// import { Periodicite, GetAllPeriodiciteResponse } from '../models/periodicite';


const periodiciteUrl = environment.periodiciteRessource;

@Injectable({
  providedIn: 'root'
})
export class PeriodiciteService {

  constructor(private http: HttpClient) { }

  getAll(event?: LazyLoadEvent): Observable<GetAllPeriodiciteResponse> {
    return this.http.get(periodiciteUrl, { observe: 'response' })
    .pipe(map(response => {
        let periodicitesResponse: GetAllPeriodiciteResponse = {
          //totalCount: parseInt(response.headers.get(totalCountHeader)),
          periodicites: response.body as Periodicite[]
        };
        return periodicitesResponse;
      }));
  }

  getPeriodiciteActif(event?: LazyLoadEvent): Observable<GetAllPeriodiciteResponse> {
    return this.http.get(periodiciteUrl+'/actif', { observe: 'response' })
    .pipe(map(response => {
        let data:Periodicite[] = [];
        let value:Periodicite= response.body as Periodicite;
        data.push(value);
        let periodicitesResponse: GetAllPeriodiciteResponse = {
          periodicites: data
        };
        return periodicitesResponse;
      }));
  }

  create(request: Periodicite): Observable<Periodicite> {
    return this.http.post(periodiciteUrl, request);
  } 

  update(exemple: Periodicite): Observable<Periodicite> {
    return this.http.put(periodiciteUrl, exemple);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${periodiciteUrl}/${id}`);
  }
}
