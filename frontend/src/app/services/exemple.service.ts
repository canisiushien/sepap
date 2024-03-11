import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LazyLoadEvent } from 'primeng/api';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { Exemple, GetAllExempleResponse } from '../models/exemple';


const resourceUrl = environment.exempleResource;

@Injectable({
  providedIn: 'root'
})
export class ExempleService {

  constructor(private http: HttpClient){}
  
  getAll(event?: LazyLoadEvent): Observable<GetAllExempleResponse> {
    return this.http.get("assets/data/exemple.json", { observe: 'response' })  
    .pipe(map(response => {
        let exemplesResponse: GetAllExempleResponse = {
          //totalCount: parseInt(response.headers.get(totalCountHeader)),
          exemples: response.body as Exemple[]
        };
        return exemplesResponse;
      }));
  }

 /* getAll(event?: LazyLoadEvent): Observable<GetAllExempleResponse> {
    return this.http.get(resourceUrl, { observe: 'response' })  
    .pipe(map(response => {
        let exemplesResponse: GetAllExempleResponse = {
          //totalCount: parseInt(response.headers.get(totalCountHeader)),
          exemples: response.body as Exemple[]
        };
        return exemplesResponse;
      }));
  }*/

  create(request: Exemple): Observable<Exemple> {
    return this.http.post(resourceUrl, request);
  }

  

  update(exemple: Exemple): Observable<Exemple> {
    return this.http.put(resourceUrl, exemple);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${resourceUrl}/${id}`);
  }
}

function totalCountHeader(totalCountHeader: any): string {
  throw new Error('Function not implemented.');
}

