
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LazyLoadEvent } from 'primeng/api';
import { Observable } from 'rxjs';
import { GetAllMinistereResponse, Ministere } from 'src/app/models/parametrage/ministere';
import { map } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { MinistereBundleData } from 'src/app/models/stats/ministere-bundle-data';
const ministereUrl = environment.ministereRessource;

@Injectable({
  providedIn: 'root'
})
export class MinistereService {

  constructor(private http:HttpClient) { }

  getAll(event?: LazyLoadEvent): Observable<GetAllMinistereResponse> {
    return this.http.get(ministereUrl, { observe: 'response' })
    .pipe(map(response => {
        let ministeresResponse: GetAllMinistereResponse = { 
          ministeres: response.body as Ministere[]
        };
        return ministeresResponse;
      }));
  }

  create(ministere: Ministere): Observable<Ministere> {
    return this.http.post(ministereUrl, ministere);
  }

  getBundle(ministerId: number): Observable<MinistereBundleData> {
    return this.http.get(environment.ministereBundleRessource+'/'+ministerId, { observe: 'response' })
    .pipe(map(response => {
      let bundle: MinistereBundleData =  response.body as MinistereBundleData; 
      return bundle;
    }));
  }

  update(ministere: Ministere): Observable<Ministere> {
    return this.http.put(ministereUrl, ministere);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${ministereUrl}/${id}`);
  }
}
