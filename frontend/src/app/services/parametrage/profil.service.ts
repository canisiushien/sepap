import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LazyLoadEvent } from 'primeng/api';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { GetAllProfilResponse, Profil } from 'src/app/models/parametrage/profil';
import { environment } from 'src/environments/environment';


const profilUrl = environment.profilRessource;

@Injectable({
  providedIn: 'root'
})
export class ProfilService {

  constructor(private http:HttpClient) { }

   getAll(event?: LazyLoadEvent): Observable<GetAllProfilResponse> {
    return this.http.get(profilUrl, { observe: 'response' })
    .pipe(map(response => {
        let profilsResponse: GetAllProfilResponse = {
          //totalCount: parseInt(response.headers.get(totalCountHeader)),
          profils: response.body as Profil[]
        };
        return profilsResponse;
      }));
  }

  create(profil: Profil): Observable<Profil> {
    return this.http.post(profilUrl, profil);
  }

  show(name: string): Observable<Profil> {
    return this.http.get<Profil>(`${profilUrl}/${name}`);
  }

  update(profil: Profil): Observable<Profil> {
    return this.http.put(profilUrl, profil);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${profilUrl}/${id}`);
  }
}
