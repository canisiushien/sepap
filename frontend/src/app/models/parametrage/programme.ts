export interface Programme {
  id?:number;
  code?:string;
  libelle?: string;
  description?: string;
  statut?: programmeStatut;
  debut?: Date;
  fin?: Date;
  details?:string

}

export interface GetAllProgrammeResponse {
  // totalCount: number;
   programmes: Programme[];
 }

 export enum programmeStatut{
    ENCOURS="EN_COURS",
    ANNULE="ANNULER",
    CLOTURE="CLOTURER"
 }

//  }
//  export interface programmeStatuts {
//    id?: number;
//    libelle?: string;
//  }

