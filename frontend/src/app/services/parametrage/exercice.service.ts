import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LazyLoadEvent } from 'primeng/api';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { GetAllExerciceResponse, Exercice } from 'src/app/models/parametrage/exercice';
import { environment } from 'src/environments/environment';


const exerciceUrl = environment.exerciceRessource;
@Injectable({
  providedIn: 'root'
})
export class ExerciceService {

  constructor(private http: HttpClient) { }

  getAll(event?: LazyLoadEvent): Observable<GetAllExerciceResponse> {
    return this.http.get(exerciceUrl, { observe: 'response' })
    .pipe(map(response => {
        let exercicesResponse: GetAllExerciceResponse = {
          exercices: response.body as Exercice[]
        };
        return exercicesResponse;
      }));
  }

  getExerciceAttente(event?: LazyLoadEvent): Observable<GetAllExerciceResponse> {
    return this.http.get(exerciceUrl+'/statut/attente', { observe: 'response' })
    .pipe(map(response => {
        let exercicesResponse: GetAllExerciceResponse = {
          exercices: response.body as Exercice[]
        };
        return exercicesResponse;
      }));
  }

  getExerciceClos(event?: LazyLoadEvent): Observable<GetAllExerciceResponse> {
    return this.http.get(exerciceUrl+'/statut/CLOS', { observe: 'response' })
    .pipe(map(response => {
        let exercicesResponse: GetAllExerciceResponse = {
          exercices: response.body as Exercice[]
        };
        return exercicesResponse;
      }));
  }

  getExerciceEncours(event?: LazyLoadEvent): Observable<Exercice> {
    return this.http.get(exerciceUrl+'/statut/EN_COURS', { observe: 'response' })
    .pipe(map(response => {
         let values = response.body as Exercice[];
         return values[0];
      }));
  }

  getExerciceEncoursList(event?: LazyLoadEvent): Observable<GetAllExerciceResponse> {
    return this.http.get(exerciceUrl+'/statut/EN_COURS', { observe: 'response' })
    .pipe(map(response => {
      let exercicesResponse: GetAllExerciceResponse = {
        exercices: response.body as Exercice[]
      };
      return exercicesResponse;
    }));
  }

  cloturer(event?: LazyLoadEvent): Observable<GetAllExerciceResponse> {
    return this.http.get(exerciceUrl +'/cloture', { observe: 'response' })
    .pipe(map(response => {
        let exercicesResponse: GetAllExerciceResponse = {
          exercices: response.body as Exercice[]
        };
        return exercicesResponse;
      }));
  }

  create(exercice: Exercice): Observable<Exercice> {
    return this.http.post(exerciceUrl,exercice);
  }

  update(exercice: Exercice): Observable<Exercice> {
    return this.http.put(exerciceUrl, exercice);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${exerciceUrl}/${id}`);
  }
}
