import { Programme } from "./programme";

export interface Projet {
  id?:number;
  libelle?: string;
  description?: string;
  statut?: string;
  debut?: Date;
  fin?: Date;
  details?:string;
  programme?:Programme;

}

export interface GetAllProjetResponse {
  // totalCount: number;
   projets: Projet[];
 }

 export enum projetStatut{
  ENCOURS="EN_COURS",
  ANNULE="ANNULER",
  CLOTURE="CLOTURER"
}
