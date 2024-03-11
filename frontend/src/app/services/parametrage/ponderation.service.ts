import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map } from 'rxjs/operators';
import { LazyLoadEvent } from 'primeng/api';
import { Observable } from 'rxjs';
import { GetAllPonderationResponse, Ponderation } from 'src/app/models/parametrage/ponderation';


const resourceUrl = environment.ponderationResource;
// const resourceUrl = "assets/data/ponderation.json";

@Injectable({
  providedIn: 'root'
})
export class PonderationService {

  constructor(private http: HttpClient) { }

  getAll(event?: LazyLoadEvent): Observable<GetAllPonderationResponse> {
    return this.http.get(resourceUrl, { observe: 'response' })
    .pipe(map(response => {
        let ponderationsResponse: GetAllPonderationResponse = {
          //totalCount: parseInt(response.headers.get(totalCountHeader)),
          ponderations: response.body as Ponderation[]
        };
        return ponderationsResponse;
      }));
  }



  create(request: Ponderation): Observable<Ponderation> {
    return this.http.post(resourceUrl, request);
  }



  update(ponderation: Ponderation): Observable<Ponderation> {
    return this.http.put(resourceUrl, ponderation);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${resourceUrl}/${id}`);
  }
}
