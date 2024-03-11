import { Indicateur } from 'src/app/models/parametrage/indicateur-objectif';
import { Action } from 'src/app/models/parametrage/action';
import { Programme } from './programme';
export interface Objectif {
    id?:number;
    code?:string;
    libelle?:string;
    description?:string;
    type?:string;
    parent?:Objectif;
    action?:Action;
    programme?:Programme;
    indicateurs?: Array<Indicateur>;
}

export interface GetAllObjectifResponse {
   // totalCount: number;
   objectifs: Objectif[];

}

export interface GetAllObjectifStrategiqueResponse {
  // totalCount: number;

  obgectifStrategique: Objectif[];
}
