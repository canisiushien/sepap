import { environment } from './../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map } from 'rxjs/operators';
import { LazyLoadEvent } from 'primeng/api';
import { Observable } from 'rxjs';
import { Parametre, GetAllParametreResponse } from 'src/app/models/parametrage/parametre';

const parametreUrl = environment.parametreRessource;

@Injectable({
  providedIn: 'root'
})
export class ParametreService {

  constructor(private http: HttpClient) { }

  getAll(event?: LazyLoadEvent): Observable<GetAllParametreResponse> {
    return this.http.get(parametreUrl, { observe: 'response' })
    .pipe(map(response => {
        let parametresResponse: GetAllParametreResponse = {
          //totalCount: parseInt(response.headers.get(totalCountHeader)),
          parametres: response.body as Parametre[]
        };
        return parametresResponse;
      }));
  }



  create(request: Parametre): Observable<Parametre> {
    return this.http.post(parametreUrl, request);
  }



  update(parametre: Parametre): Observable<Parametre> {
    return this.http.put(parametreUrl, parametre);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${parametreUrl}/${id}`);
  }
}
