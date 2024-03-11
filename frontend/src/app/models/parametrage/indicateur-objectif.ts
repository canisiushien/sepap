import { Objectif } from "./objectif";

export interface Indicateur{
  id?:number;
  libelle?:string;
  description?:string;
  typeIndicateur?:String;
  valeur?:number;
  objectif?:Objectif
}

export interface IndicateurType{
  id:number;
  libelle:string;
}

export interface GetAllIndicateurResponse {
  // totalCount: number;
  indicateurs: Indicateur[];
 }
