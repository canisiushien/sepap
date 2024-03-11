import { GetAllPrivilegeResponse, Privilege } from './privilege';
import { GetAllProfilResponse, Profil } from 'src/app/models/parametrage/profil';

export interface Compte {
  id?: number;
  matricule?: string;
  nom?: string;
  prenom?: string;
  telephone?: string;
  email?: string;
  actif?:boolean;
  structureId?:number;
  //permissions?:Privilege[];
  profiles?: Profil[];
}

export interface GetAllCompteResponse {
  // totalCount: number;
   comptes: Compte[];
}
