import { ActiviteData } from "./activite-data";

export interface StructureActiviteData {
    libelle:String;
    code?:string;
    data?:ActiviteData[];
    statut?: string;
}