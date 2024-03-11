import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map } from 'rxjs/operators';
import { LazyLoadEvent } from 'primeng/api';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { ObjectifOperationnel, GetAllObjectifOperationnelResponse } from 'src/app/models/parametrage/objectif-operationnel';

const resourceUrl = environment.exempleResource;

@Injectable({
  providedIn: 'root'
})
export class ObjectifOperationnelService {

  constructor(private http: HttpClient) { }

  getAll(event?: LazyLoadEvent): Observable<GetAllObjectifOperationnelResponse> {
    return this.http.get("assets/data/objectif-operationnel.json", { observe: 'response' })
    .pipe(map(response => {
        let objectifOperationnelResponse: GetAllObjectifOperationnelResponse = {
          //totalCount: parseInt(response.headers.get(totalCountHeader)),
          objectifOperationnels: response.body as ObjectifOperationnel[]
        };
        return objectifOperationnelResponse;
      }));
  }



  create(request: ObjectifOperationnel): Observable<ObjectifOperationnel> {
    return this.http.post(resourceUrl, request);
  }



  update(exemple: ObjectifOperationnel): Observable<ObjectifOperationnel> {
    return this.http.put(resourceUrl, exemple);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${resourceUrl}/${id}`);
  }
} 
