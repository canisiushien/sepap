import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PerformanceExport } from 'src/app/models/performance/performanceExport';
import { Performer } from 'src/app/models/performance/performer';
import { ProgrammationExport } from 'src/app/models/programmation/programmationExport';
import { environment } from 'src/environments/environment';

const ressourceUrl= environment.calculPerformanceRessource;
const exportUrl= environment.programmationsResource;

@Injectable({
  providedIn: 'root'
})
export class PerformerService {
  constructor(private http: HttpClient) { }

  calculPerformance(request: Performer): Observable<Performer> {
    return this.http.post(ressourceUrl, request);
  }

  exportPerformance(request: PerformanceExport): Observable<Blob> {
    return this.http.post(exportUrl+'/print/rapport-performance', request, {responseType: 'blob'});
  }
}
