import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map } from 'rxjs/operators';
import { LazyLoadEvent } from 'primeng/api';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { SourceFinancement, GetAllSourceFinancementResponse } from 'src/app/models/parametrage/source-financement.model';
// import { Agent, GetAllAgentResponse } from '../models/agent';


const resourceUrl = environment.sourcefinancementResource;

@Injectable({
  providedIn: 'root'
})
export class SourceFinancementService {

  constructor(private http: HttpClient) { }

  getAll(event?: LazyLoadEvent): Observable<GetAllSourceFinancementResponse> {
    return this.http.get(resourceUrl, { observe: 'response' })
    .pipe(map(response => {
        let agentsResponse: GetAllSourceFinancementResponse = {
          //totalCount: parseInt(response.headers.get(totalCountHeader)),
          sources: response.body as SourceFinancement[]
        };
        return agentsResponse;
      }));
  }



  create(request: SourceFinancement): Observable<SourceFinancement> {
    return this.http.post(resourceUrl, request);
  }



  update(exemple: SourceFinancement): Observable<SourceFinancement> {
    return this.http.put(resourceUrl, exemple);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${resourceUrl}/${id}`);
  }
}
