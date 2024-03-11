import { Structure } from 'src/app/models/parametrage/structure';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LazyLoadEvent } from 'primeng/api';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { GetAllProgrammationResponse, Programmation } from 'src/app/models/programmation/programmation';
import { ProgrammationExport } from 'src/app/models/programmation/programmationExport';
import { environment } from 'src/environments/environment';
import { ProgrammationValidation } from 'src/app/models/programmation/programmation-validation';

const resourceUrl = environment.programmationsResource;

@Injectable({
  providedIn: 'root'
})
export class ProgrammationService{
  constructor(private http: HttpClient) { }

  getAllProgrammation(event?: LazyLoadEvent):Observable<GetAllProgrammationResponse>{
    return this.http.get(`${resourceUrl}/${'all'}`, { observe: 'response' })
    .pipe(map(response => {

        let programmationResponse: GetAllProgrammationResponse = {
          //totalCount: parseInt(response.headers.get(totalCountHeader)),
          programmations: response.body as Programmation[]
        };
        return programmationResponse;
      }));
  }
  // Toutes les programmations d'une structure(validés et non validés)
  getAllEncoursbyStruct(ids: number,event?: LazyLoadEvent): Observable<GetAllProgrammationResponse> {
    return this.http.get(`${resourceUrl}/${'all/exercice-encours'}/${ids}`, { observe: 'response' })
    .pipe(map(response => {

        let programmationResponse: GetAllProgrammationResponse = {
          //totalCount: parseInt(response.headers.get(totalCountHeader)),
          programmations: response.body as Programmation[]
        };
        return programmationResponse;
      }));
  }

  getAllbyStruct(ids: number,event?: LazyLoadEvent): Observable<GetAllProgrammationResponse> {
    return this.http.get(`${resourceUrl}/${'all'}/${'exercice-enattente'}/${ids}`, { observe: 'response' })
    .pipe(map(response => {

        let programmationResponse: GetAllProgrammationResponse = {
          //totalCount: parseInt(response.headers.get(totalCountHeader)),
          programmations: response.body as Programmation[]
        };
        return programmationResponse;
      }));
  }

    getAllValidebyStruct(ids: number,event?: LazyLoadEvent): Observable<GetAllProgrammationResponse> {
    return this.http.get(`${resourceUrl}/${'all/valide'}/${ids}`, { observe: 'response' })
    .pipe(map(response => {

        let programmationResponse: GetAllProgrammationResponse = {
          //totalCount: parseInt(response.headers.get(totalCountHeader)),
          programmations: response.body as Programmation[]
        };
        return programmationResponse;
      }));
  }

  create(request: Programmation): Observable<Programmation> {
    return this.http.post(resourceUrl, request);
  }


  validationGlobale(structure: ProgrammationValidation): Observable<ProgrammationValidation> {
    return this.http.put(resourceUrl+'/validation-all', structure);
  }

  update(programmations: Programmation): Observable<Programmation> {
    return this.http.put(resourceUrl, programmations);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${resourceUrl}/${id}`);
  }

  download(name:string):Observable<Blob>
  {
    return this.http.get(resourceUrl+'download/'+name,{responseType: 'blob'});
  }

  exportProgramme(request: ProgrammationExport): Observable<Blob> {
    return this.http.post(resourceUrl+'/print/programme-activites', request, {responseType: 'blob'});
  }

  exportRapport(request: ProgrammationExport): Observable<Blob> {
    return this.http.post(resourceUrl+'/print/rapport-activites', request, {responseType: 'blob'});
  }

  exportRapportPerformance(request: ProgrammationExport): Observable<Blob> {
    return this.http.post(resourceUrl+'/print/rapport-performance', request, {responseType: 'blob'});
  }

  modifierProgrammation(request: Programmation): Observable<Programmation>{
   // let request: Programmation={};
     return this.http.put(resourceUrl, request);
   }
}
