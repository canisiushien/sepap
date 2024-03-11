
export interface Parametre {
    id?:number;
    echeance?:number;
    verrouille?:boolean;
    dateDebutSaisit?:Date;
    dateFinSaisit?:Date;
}

export interface GetAllParametreResponse {
   // totalCount: number;
   parametres: Parametre[];
}
