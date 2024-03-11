import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LazyLoadEvent } from 'primeng/api';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { GetAllPerformanceResponse, Performance } from 'src/app/models/performance/performance';
import { Performer } from 'src/app/models/performance/performer';
import { environment } from 'src/environments/environment';

const ressourceUrl = environment.performanceRessource;

@Injectable({
  providedIn: 'root'
})
export class PerformanceService {

  constructor(private http: HttpClient) { }

  getAll(performer:Performer ,event?: LazyLoadEvent): Observable<GetAllPerformanceResponse> {
    return this.http.post(ressourceUrl,performer, { observe: 'response' })
    .pipe(map(response => {
        let performancesResponse: GetAllPerformanceResponse = {
          performances: response.body as Performance[]
        };
        return performancesResponse;
      }));
  }
}