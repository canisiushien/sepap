import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LazyLoadEvent } from 'primeng/api';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { GetAllPrivilegeResponse, Privilege } from 'src/app/models/parametrage/privilege';
import { environment } from 'src/environments/environment';
const privilegeUrl = environment.privilegeRessource;
@Injectable({
  providedIn: 'root'
})
export class PrivilegeService {

  constructor(private http: HttpClient) { }
  getAll(event?: LazyLoadEvent): Observable<GetAllPrivilegeResponse> {
    return this.http.get(privilegeUrl, { observe: 'response' })
    .pipe(map(response => {
        let privilegesResponse: GetAllPrivilegeResponse = {
          //totalCount: parseInt(response.headers.get(totalCountHeader)),
          privileges: response.body as Privilege[]
        };
        return privilegesResponse;
      }));
  }

  create(privilege: Privilege): Observable<Privilege> {
    return this.http.post(privilegeUrl, privilege);
  }



  update(privilege: Privilege): Observable<Privilege> {
    return this.http.put(privilegeUrl, privilege);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${privilegeUrl}/${id}`);
  }
}
