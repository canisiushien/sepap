import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LazyLoadEvent } from 'primeng/api';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Activite, GetAllActiviteResponse } from 'src/app/models/activites/activite';
import { environment } from 'src/environments/environment';

const resourceUrl = environment.activitesResource;

@Injectable({
  providedIn: 'root'
})
export class ActiviteService {

  constructor(private http: HttpClient) { }

  getAll(event?: LazyLoadEvent): Observable<GetAllActiviteResponse> {
    return this.http.get(resourceUrl, { observe: 'response' })
    .pipe(map(response => {
        let activiteResponse: GetAllActiviteResponse = {
          //totalCount: parseInt(response.headers.get(totalCountHeader)),
          activtes: response.body as Activite[]
        };
        return activiteResponse;
      }));
  }



  create(request: Activite): Observable<Activite> {
    return this.http.post(resourceUrl, request);
  }



  update(activites: Activite): Observable<Activite> {
    return this.http.put(resourceUrl, activites);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${resourceUrl}/${id}`);
  }
}
