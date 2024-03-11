import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LazyLoadEvent } from 'primeng/api';
import { Observable } from 'rxjs';
import { Commentaire } from 'src/app/models/programmation/commentaire';
import { Programmation } from 'src/app/models/programmation/programmation';
import { ProgrammationForEvaluationDTO } from 'src/app/models/programmation/programmation-for-evaluation-dto';
import { environment } from 'src/environments/environment';


const resourceUrlprogrammation = environment.programmationsResource;

@Injectable({
  providedIn: 'root'
})
export class AmendementActiviteService {

  constructor(private http: HttpClient) { }

  getProgrammationbyStructProgram(ids: number,idp: number,event?: LazyLoadEvent): Observable<Programmation> {
    // return this.http.get<Programmation>(`${resourceUrlprogrammation}/${ids}/${idp}`);
    return this.http.get<Programmation>(`${resourceUrlprogrammation}/${ids}/${idp}`);

  }

  getProgrammationForEvaluationDTO(idp:number, event?: LazyLoadEvent): Observable<ProgrammationForEvaluationDTO> {
    return this.http.get<ProgrammationForEvaluationDTO>(`${resourceUrlprogrammation}/detail/${idp}`);

  }


 validerProgrammation(ids: number,idp: number): Observable<Programmation>{
  let request: Programmation={};
   return this.http.put(`${resourceUrlprogrammation}/validation/${ids}/${idp}`, request);
 }

  rejetProgrammation(request: Commentaire): Observable<Commentaire> {
    return this.http.put(resourceUrlprogrammation+'/rejet', request);
  }



}
