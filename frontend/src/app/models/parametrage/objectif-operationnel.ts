import { Action } from 'src/app/models/parametrage/action';



export interface ObjectifOperationnel {
    id?:number;
    code?:number;
    libelle?:string;
    description?:string;
    action?:Action;
}

export interface GetAllObjectifOperationnelResponse {
   // totalCount: number;
   objectifOperationnels: ObjectifOperationnel[];
}