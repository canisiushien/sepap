import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { LazyLoadEvent } from 'primeng/api';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Compte, GetAllCompteResponse } from 'src/app/models/parametrage/compte';
const compteUrl= environment.compteRessource;
const agentUrl=environment.agentResource;
const activeCompteUrl= environment.activateResource;
@Injectable({
  providedIn: 'root'
})
export class CompteService {

  constructor(private http: HttpClient) { }
  getAll(event?: LazyLoadEvent): Observable<GetAllCompteResponse> {
    return this.http.get(agentUrl, { observe: 'response' })
    .pipe(map(response => {
        let comptesResponse: GetAllCompteResponse = {
          //totalCount: parseInt(response.headers.get(totalCountHeader)),
          comptes: response.body as Compte[]
        };
        return comptesResponse;
      }));
  }

  getComptebyMatricule(matricule: string): Observable<Compte> {

    return this.http.get<Compte>(`${compteUrl}agents/login/${matricule}`);

  }


  create(request: Compte): Observable<Compte> {
    return this.http.post(compteUrl+'register', request);
  }


  activeCompte(password: String, key:any): Observable<any> {
    return this.http.post(activeCompteUrl,{password,key});
  }


  update(compte: Compte): Observable<Compte> {
    return this.http.put(compteUrl, compte);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${compteUrl}/${id}`);
  }
}
