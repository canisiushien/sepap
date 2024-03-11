import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LazyLoadEvent } from 'primeng/api';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { GetAllProgrammeResponse, Programme } from 'src/app/models/parametrage/programme';
import { environment } from 'src/environments/environment';


const programmeUrl = environment.programmeRessource;
@Injectable({
  providedIn: 'root'
})
export class ProgrammeService {

  constructor(private http: HttpClient) { }
  getAll(event?: LazyLoadEvent): Observable<GetAllProgrammeResponse> {
    return this.http.get(programmeUrl, { observe: 'response' })
    .pipe(map(response => {
        let programmesResponse: GetAllProgrammeResponse = {
          //totalCount: parseInt(response.headers.get(totalCountHeader)),
          programmes: response.body as Programme[]
        };
        return programmesResponse;
      }));

  }


  getProgrammeENCOURS(event?: LazyLoadEvent): Observable<GetAllProgrammeResponse> {
    return this.http.get(programmeUrl+'/encours', { observe: 'response' })
    .pipe(map(response => {
        let programmesResponse: GetAllProgrammeResponse = {
          //totalCount: parseInt(response.headers.get(totalCountHeader)),
          programmes: response.body as Programme[]
        };
        return programmesResponse;
      }));

  }
  create(programme: Programme): Observable<Programme> {
    return this.http.post(programmeUrl,programme);
  }



  update(programme: Programme): Observable<Programme> {
    return this.http.put(programmeUrl, programme);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${programmeUrl}/${id}`);
  }
}
