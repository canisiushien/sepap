import { ObjectifStrategique } from "./objectifStrategique";

export interface IndicateurImpact {
  id?:number;
  libelle?:string;
  description?:string;
  valeur?:number;
  effetAttendu?:string,
  objectifStrategique?:ObjectifStrategique,
}

export interface GetAllIndicateurImpactResponse {
 // totalCount: number;
 indicateurImpacts: IndicateurImpact[];
}
