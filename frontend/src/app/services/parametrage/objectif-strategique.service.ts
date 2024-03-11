import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LazyLoadEvent } from 'primeng/api';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { GetAllObjectifStrategiqueResponse, ObjectifStrategique } from 'src/app/models/parametrage/objectifStrategique';
import { environment } from 'src/environments/environment';

const resourceUrl = environment.objectifStrategiqueResource;

@Injectable({
  providedIn: 'root'
})
export class ObjectifStrategiqueService {

  constructor(private http: HttpClient) { }

  getAll(event?: LazyLoadEvent): Observable<GetAllObjectifStrategiqueResponse> {
    return this.http.get("assets/data/objectifStrategique.json", { observe: 'response' })
    .pipe(map(response => {
        let objectifStrategiqueResponse: GetAllObjectifStrategiqueResponse = {
          //totalCount: parseInt(response.headers.get(totalCountHeader)),
          objectifStrategiques: response.body as ObjectifStrategique[]
        };
        return objectifStrategiqueResponse;
      }));
  }

  // getAllObjStra():Observable<ObjectifStrategique []>{
  //   return this.http.get<ObjectifStrategique []>("assets/data/objectifStrategique.json");
  // }



  create(request: ObjectifStrategique): Observable<ObjectifStrategique> {
    return this.http.post(resourceUrl, request);
  }



  update(agent: ObjectifStrategique): Observable<ObjectifStrategique> {
    return this.http.put(resourceUrl, agent);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${resourceUrl}/${id}`);
  }
}
