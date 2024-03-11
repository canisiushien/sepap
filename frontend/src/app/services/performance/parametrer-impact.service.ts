import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { GetAllParametreImpactResponse, ParametreImpact } from 'src/app/models/performance/parametrer-impact';
import { environment } from 'src/environments/environment';
const ressourceUrl= environment.parametreImpactRessource
@Injectable({
  providedIn: 'root'
})
export class ParametrerImpactService {

  constructor(private http: HttpClient) {

   }
   getParametreImpactById(ids:number, ide:number): Observable<GetAllParametreImpactResponse> {
    return this.http.get(`${ressourceUrl}/exercice/${ids}/${ide}`, { observe: 'response' })
    .pipe(map(response => {
        let parametreImpactResponse: GetAllParametreImpactResponse = {

          parametreImpacts: response.body as ParametreImpact[]
        };
        return parametreImpactResponse;
      }));
  }

  getParametreImpactByIdIM(idm:number, idi:number): Observable<GetAllParametreImpactResponse> {
    return this.http.get(`${ressourceUrl}/impact/${idm}/${idi}`, { observe: 'response' })
    .pipe(map(response => {
        let parametreImpactResponse: GetAllParametreImpactResponse = {

          parametreImpacts: response.body as ParametreImpact[]
        };
        return parametreImpactResponse;
      }));
  }

  update(parametreImpact: ParametreImpact): Observable<ParametreImpact> {
    return this.http.put(ressourceUrl, parametreImpact);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${ressourceUrl}/${id}`);
  }
  create(request: ParametreImpact): Observable<any> {
    return this.http.post(ressourceUrl, request);
  }

}
