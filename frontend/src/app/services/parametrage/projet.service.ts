import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LazyLoadEvent } from 'primeng/api';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { GetAllProjetResponse, Projet } from 'src/app/models/parametrage/projet';
import { environment } from 'src/environments/environment';
const projetUrl = environment.projetRessource;
@Injectable({
  providedIn: 'root'
})
export class ProjetService {

  constructor(private http: HttpClient) { }
  getAll(event?: LazyLoadEvent): Observable<GetAllProjetResponse> {
    return this.http.get(projetUrl, { observe: 'response' })
    .pipe(map(response => {
        let programmesResponse: GetAllProjetResponse = {
          //totalCount: parseInt(response.headers.get(totalCountHeader)),
          projets: response.body as Projet[]
        };
        return programmesResponse;
      }));
  }

  create(projet: Projet): Observable<Projet> {
    return this.http.post(projetUrl, projet);
  }



  update(projet: Projet): Observable<Projet> {
    return this.http.put(projetUrl, projet);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${projetUrl}/${id}`);
  }
}
