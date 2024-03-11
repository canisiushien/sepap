import { Ministere } from './../parametrage/ministere';
export interface Impact
{

    id?: number;
    libelle?:string;
    description?: string;
    inactif ?:boolean;
    statistique?:boolean;
    ministere?: Ministere

}


export interface GetAllImpactResponse {
  impactStructures : Impact[];
}
