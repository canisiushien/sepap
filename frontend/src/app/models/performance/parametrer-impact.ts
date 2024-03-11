import { Exercice } from "../parametrage/exercice";
import { Ponderation } from "../parametrage/ponderation";
import { Impact } from "./impact";

export interface ParametreImpact
{
    id?: number;
    cible?: number;
    valeurReference?:number;
    valeurAtteinte?:number;
    exercice?: Exercice;
    impact?: Impact

}

export interface GetAllParametreImpactResponse {
  parametreImpacts : ParametreImpact[];
}

// export interface Exercice
// {

//     debut?:number;
//     description?:number;
//     statut?: string;
//     ponderation ?: Ponderation;
//     observations?:Observations;

// }

// export interface Observations{
//   id?:number;
//   libelle?: string;
//   type?:string;


// }
