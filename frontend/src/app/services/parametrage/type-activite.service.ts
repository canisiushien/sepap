import { GetAllTypeActiviteResponse, TypeActivite } from './../../models/parametrage/typeActivite';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LazyLoadEvent } from 'primeng/api';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { environment } from 'src/environments/environment';

 const resourceUrl = environment.typeActiviteResource;
// const resourceUrl = "assets/data/typeActivite.json";
@Injectable({
  providedIn: 'root'
})
export class TypeActiviteService {

  constructor(private http: HttpClient) { }

  getAll(event?: LazyLoadEvent): Observable<GetAllTypeActiviteResponse> {
    return this.http.get(resourceUrl, { observe: 'response' })
    .pipe(map(response => {
        let typeActiviteResponse: GetAllTypeActiviteResponse = {
          //totalCount: parseInt(response.headers.get(totalCountHeader)),
          typeActivites: response.body as TypeActivite[]
        };
        return typeActiviteResponse;
      }));
  }



  create(request: TypeActivite): Observable<TypeActivite> {
    return this.http.post(resourceUrl, request);
  }



  update(typeActivite: TypeActivite): Observable<TypeActivite> {
    return this.http.put(resourceUrl, typeActivite);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${resourceUrl}/${id}`);
  }
}
