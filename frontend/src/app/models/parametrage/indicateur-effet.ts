import { ObjectifStrategique } from 'src/app/models/parametrage/objectif-strategique';

export interface IndicateurEffet {
    id?:number;
    libelle?:string;
    description?:string;
    type?:string;
    valeur?:number;
    impact?:string;
    objectifOperationnel?:ObjectifStrategique
}

export interface GetAllIndicateurEffetResponse {
   // totalCount: number;
    indicateurEffet: IndicateurEffet[];
}