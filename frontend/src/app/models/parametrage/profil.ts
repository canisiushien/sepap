import { Privilege } from "./privilege";

export interface Profil {
  id?:number;
  name?: string;
  privileges?:Privilege[];
}

export interface GetAllProfilResponse {
  // totalCount: number;
   profils: Profil[];
 }