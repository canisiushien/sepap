import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map } from 'rxjs/operators';
import { LazyLoadEvent } from 'primeng/api';
import { Observable } from 'rxjs';
import { GetAllPeriodeResponse, Periode} from 'src/app/models/parametrage/periode';


const periodeUrl = environment.periodeRessource;

@Injectable({
  providedIn: 'root'
})
export class PeriodeService {

  constructor(private http: HttpClient) { }

  getAll(event?: LazyLoadEvent): Observable<GetAllPeriodeResponse> {
    return this.http.get(periodeUrl, { observe: 'response' })
    .pipe(map(response => {
        let periodesResponse: GetAllPeriodeResponse = {
          //totalCount: parseInt(response.headers.get(totalCountHeader)),
          periodes: response.body as Periode[]
        };
        return periodesResponse;
      }));
  } 

  create(request: Periode): Observable<Periode> {
    return this.http.post(periodeUrl, request);
  } 

  update(period: Periode): Observable<Periode> {
    return this.http.put(periodeUrl, period);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${periodeUrl}/${id}`);
  }
}
