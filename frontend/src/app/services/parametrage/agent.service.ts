import { AgentStructure } from './../../models/parametrage/agentStructure';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map } from 'rxjs/operators';
import { LazyLoadEvent } from 'primeng/api';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Agent, GetAllAgentResponse } from 'src/app/models/parametrage/agent';


const resourceUrl = environment.agentResource;
const sendMailUrl= environment.resentEmailRessource;

@Injectable({
  providedIn: 'root'
})
export class AgentService {

  constructor(private http: HttpClient) { }

  getAll(idStructure:number, event?: LazyLoadEvent): Observable<GetAllAgentResponse> {
    return this.http.get(resourceUrl+'/'+idStructure, { observe: 'response' })
    .pipe(map(response => {
        let agentsResponse: GetAllAgentResponse = {
          //totalCount: parseInt(response.headers.get(totalCountHeader)),
          agents: response.body as Agent[]
        };
        return agentsResponse;
      }));
  }

 resentMail(id:number):Observable<any>{
    return this.http.get(sendMailUrl+id);
 }

 changerStructure(request: AgentStructure): Observable<AgentStructure> {
  return this.http.post(resourceUrl+'/affectation', request);
}

  create(request: Agent): Observable<Agent> {
    return this.http.post(resourceUrl, request);
  }

  updateProfil(agent: Agent): Observable<Agent> {
    return this.http.put(resourceUrl+'/profiles', agent);
  }

  update(agent: Agent): Observable<Agent> {
    return this.http.put(resourceUrl, agent);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${resourceUrl}/${id}`);
  }

  getAgentWithStructure(id:number):Observable<any>{
    return this.http.get(resourceUrl+'/struct/'+id);
 }
}
