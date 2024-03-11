import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map } from 'rxjs/operators';
import { LazyLoadEvent } from 'primeng/api';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Action, GetAllActionResponse } from 'src/app/models/parametrage/action';


const actionUrl = environment.actionRessource;

@Injectable({
  providedIn: 'root'
})
export class ActionService {

  constructor(private http: HttpClient) { }

  getAll(event?: LazyLoadEvent): Observable<GetAllActionResponse> {
    return this.http.get(actionUrl, { observe: 'response' })
    .pipe(map(response => {
        let actionsResponse: GetAllActionResponse = {
          //totalCount: parseInt(response.headers.get(totalCountHeader)),
          actions: response.body as Action[]
        };
        return actionsResponse;
      }));
  }



  create(request: Action): Observable<Action> {
    return this.http.post(actionUrl, request);
  }



  update(exemple: Action): Observable<Action> {
    return this.http.put(actionUrl, exemple);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${actionUrl}/${id}`);
  }
}
