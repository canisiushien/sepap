export interface Exercice {
  id?: number;
  code?: string;
  libelle?: string;
  description?: string;
  // statut?: exerciceStatut;
  statut?: string;
  debut?: Date;
  fin?: Date;

}

export interface GetAllExerciceResponse {
  // totalCount: number;
  exercices: Exercice[];
}


 export enum statuts{
    ENCOURS="ENCOURS",
    EN_ATTENTE="EN_ATTENTE",
    CLOS="CLOS"
 }

//  }
//  export interface exerciceStatuts {
//    id?: number;
//    libelle?: string;
//  }

