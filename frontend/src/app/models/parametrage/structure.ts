import { Ministere } from "./ministere";


export interface Structure {
  id?:number;
  sigle?:string;
  libelle?: string;
  description?: string;
  type?:string;
  statut?:string;
  telephone?:number;
  emailResp?:string;
  emailStruct?:string;
  parent?:Structure;
  ministere?: Ministere;
  niveau?: number;

}

export interface GetAllStructureResponse {
 // totalCount: number;
  structures: Structure[];
}

export enum StructureStatut{
   ACTIF="ACTIF",
   DEACTIVE="DESACTIVE"

}
export interface StructureType{
  id:number;
  libelle:string;
}
export interface TypeStructure{
  id:number;
  type: string;
  libelle:string;
}
export interface GetAllTypeStructureResponse {
  // totalCount: number;
   Typestructures: TypeStructure[];
 }
