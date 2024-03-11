import { Objectif } from 'src/app/models/parametrage/objectif';


export interface Action {
    id?:number;
    code?:string;
    libelle?:string;
    description?:string;
    objectif?:Objectif;
}

export interface GetAllActionResponse {
   // totalCount: number;
    actions: Action[];
}