import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map } from 'rxjs/operators';
import { LazyLoadEvent } from 'primeng/api';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Objectif, GetAllObjectifResponse } from 'src/app/models/parametrage/objectif';


const objectifUrl = environment.objectifRessource;

@Injectable({
  providedIn: 'root'
})
export class ObjectifService {
  constructor(private http: HttpClient) { }

  getAll(event?: LazyLoadEvent): Observable<GetAllObjectifResponse> {
    return this.http.get(objectifUrl, { observe: 'response' })
    .pipe(map(response => {
        let objectifsResponse: GetAllObjectifResponse = {
          objectifs: response.body as Objectif[]
        };
        return objectifsResponse;
      }));
  }

  getObjectifStrategique(): Observable<GetAllObjectifResponse> {
    return this.http.get(`${objectifUrl}/search/type/STRATEGIQUE`, { observe: 'response' })
    .pipe(map(response => {
        let objectifsResponse: GetAllObjectifResponse = {
          objectifs: response.body as Objectif[]
        };
        return objectifsResponse;
      }));


  }

  getObjectifOperationnel(): Observable<GetAllObjectifResponse> {
    return this.http.get(`${objectifUrl}/search/type/OPERATIONNEL`, { observe: 'response' })
    .pipe(map(response => {
        let objectifsResponse: GetAllObjectifResponse = {
          objectifs: response.body as Objectif[]
        };
        return objectifsResponse;
      }));

  }

  create(request: Objectif): Observable<Objectif> {
    return this.http.post(objectifUrl, request);
  }



  update(exemple: Objectif): Observable<Objectif> {
    return this.http.put(objectifUrl, exemple);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${objectifUrl}/${id}`);
  }
}
