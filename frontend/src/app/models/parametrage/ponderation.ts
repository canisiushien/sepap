export interface Ponderation {
    id?:number;
    efficacite?:number;
    efficience?:number;
    gouvernance?:number;
    actif?:enumStatut;
    impact?:number;
}


export interface GetAllPonderationResponse {
   // totalCount: number;
    ponderations: Ponderation[];
}


export enum enumStatut{
  true="true",
  false="false"
}
