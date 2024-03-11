import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LazyLoadEvent } from 'primeng/api';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Contribuer, GetAllContribuerResponse } from 'src/app/models/performance/contribuer';
import { environment } from 'src/environments/environment';
const ressourceUrl= environment.contribuerRessource
@Injectable({
  providedIn: 'root'
})
export class ContribuerService {

  constructor(private http: HttpClient) { }
  update(contribuer: Contribuer): Observable< Contribuer> {
    return this.http.put(ressourceUrl, contribuer);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${ressourceUrl}/${id}`);
  }

  create(request: Contribuer): Observable<Contribuer> {
    return this.http.post(ressourceUrl, request);
  }

  getAll(event?: LazyLoadEvent): Observable<GetAllContribuerResponse> {
    return this.http.get(ressourceUrl, { observe: 'response' })
    .pipe(map(response => {
        let contribuerResponse: GetAllContribuerResponse = {
          //totalCount: parseInt(response.headers.get(totalCountHeader)),
          contributions: response.body as Contribuer[]
        };
        return contribuerResponse;
      }));
  }

  getByStructureAndExercice(ids:number, ide:number): Observable<GetAllContribuerResponse> {
    return this.http.get(`${ressourceUrl}/structure/${ids}/${ide}`, { observe: 'response' })
    .pipe(map(response => {
      let contribuerResponse: GetAllContribuerResponse = {
        //totalCount: parseInt(response.headers.get(totalCountHeader)),
        contributions: response.body as Contribuer[]
      };
      return contribuerResponse;
    }));
  }

  getByMinistereAndExercice(idm:number, ide:number): Observable<GetAllContribuerResponse> {
    return this.http.get(`${ressourceUrl}/minister/${idm}/${ide}`, { observe: 'response' })
    .pipe(map(response => {
      let contribuerResponse: GetAllContribuerResponse = {
        //totalCount: parseInt(response.headers.get(totalCountHeader)),
        contributions: response.body as Contribuer[]
      };
      return contribuerResponse;
    }));
  }
}


