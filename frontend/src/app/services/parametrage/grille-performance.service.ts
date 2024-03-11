import { GetAllGrillePerformanceResponse, GrillePerformance } from './../../models/parametrage/grillePerformance';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LazyLoadEvent } from 'primeng/api';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { map } from 'rxjs/operators';
const resourceUrl = environment.grillePerformanceResource;
// const resourceUrl = "assets/data/grillePerformance.json";

@Injectable({
  providedIn: 'root'
})
export class GrillePerformanceService {

  constructor(private http: HttpClient) { }

  getAll(event?: LazyLoadEvent): Observable<GetAllGrillePerformanceResponse> {
    return this.http.get(resourceUrl, { observe: 'response' })
    .pipe(map(response => {
        let grillePerformanceResponse: GetAllGrillePerformanceResponse = {
          //totalCount: parseInt(response.headers.get(totalCountHeader)),
          grillePerformances: response.body as GrillePerformance[]
        };
        return grillePerformanceResponse;
      }));
  }



  create(request: GrillePerformance): Observable<GrillePerformance> {
    return this.http.post(resourceUrl, request);
  }



  update(grillePerformance: GrillePerformance): Observable<GrillePerformance> {
    return this.http.put(resourceUrl, grillePerformance);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${resourceUrl}/${id}`);
  }
}
