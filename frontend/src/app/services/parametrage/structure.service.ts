import { Exercice } from 'src/app/models/parametrage/exercice';

import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LazyLoadEvent } from 'primeng/api';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { GetAllStructureResponse, GetAllTypeStructureResponse, Structure, TypeStructure } from 'src/app/models/parametrage/structure';
import { environment } from 'src/environments/environment';


const structureUrl = environment.structureRessource;
@Injectable({
  providedIn: 'root'
})
export class StructureService {

  constructor(private http: HttpClient) { }

  getAll(event?: LazyLoadEvent): Observable<GetAllStructureResponse> {
    return this.http.get(structureUrl, { observe: 'response' })
    .pipe(map(response => {
        let structuresResponse: GetAllStructureResponse = {

          structures: response.body as Structure[]
        };
        return structuresResponse;
      }));
  }

  getAllType(event?: LazyLoadEvent): Observable<GetAllTypeStructureResponse> {
    return this.http.get("assets/data/structureType.json", { observe: 'response' })
    .pipe(map(response => {
        let TypestructuresResponse: GetAllTypeStructureResponse = {
          //totalCount: parseInt(response.headers.get(totalCountHeader)),
          Typestructures: response.body as TypeStructure[]
        };
        return TypestructuresResponse;
      }));
  }


  getStructureById(id: number): Observable<any> {
    return this.http.get(`${structureUrl}/${id}`, { observe: 'response' })
    .pipe(map(response => {
      let value: Structure={};
      value= response.body as Structure;

      let data :Structure[]=[];
       data.push(value);
        return data;
      }));
    }

    getStructureByMinistereId(id: number): Observable<GetAllStructureResponse> {
      return this.http.get(`${structureUrl}/ministere/${id}`, { observe: 'response' })
      .pipe(map(response => {
          let structuresResponse: GetAllStructureResponse = {

            structures: response.body as Structure[]
          };
          return structuresResponse;
        }));
    }

  create(structure: Structure): Observable<Structure> {
    return this.http.post(structureUrl, structure);
  }

  

  update(structure: Structure): Observable<Structure> {
    return this.http.put(structureUrl, structure);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${structureUrl}/${id}`);
  }

}
