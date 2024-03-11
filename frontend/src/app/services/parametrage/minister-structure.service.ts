import { MinistereStructure } from './../../models/parametrage/ministereStructure';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LazyLoadEvent } from 'primeng/api';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { GetAllMinistereStructureResponse } from 'src/app/models/parametrage/ministereStructure';
import { environment } from 'src/environments/environment';
const ministereStructureUrl = environment.ministerStructureRessource;
@Injectable({
  providedIn: 'root'
})
export class MinisterStructureService {


  constructor(private http:HttpClient) { }

  getAll(event?: LazyLoadEvent): Observable<GetAllMinistereStructureResponse> {
    return this.http.get(ministereStructureUrl, { observe: 'response' })
    .pipe(map(response => {
        let ministereStructsResponse: GetAllMinistereStructureResponse = {
          //totalCount: parseInt(response.headers.get(totalCountHeader)),
          ministereStructures: response.body as MinistereStructure[]
        };
        return ministereStructsResponse;
      }));
  }

  create(ministereStructure: MinistereStructure): Observable<MinistereStructure> {
    return this.http.post(ministereStructureUrl, ministereStructure);
  }



  update(ministereStructure: MinistereStructure): Observable<MinistereStructure> {
    return this.http.put(ministereStructureUrl, ministereStructure);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${ministereStructureUrl}/${id}`);
  }
}
