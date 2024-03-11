import { Periodicite } from "./periodicite";

export interface Periode {
    id?:number;
    libelle?:string;
    debut?:Date;
    fin?: Date;
    valeur?: boolean;
    periodicite?:Periodicite;
}

export interface GetAllPeriodeResponse {
   // totalCount: number;
    periodes: Periode[];
}
