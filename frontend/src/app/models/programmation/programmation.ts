import { Objectif } from 'src/app/models/parametrage/objectif';
import { Activite } from "../activites/activite";
import { Periode } from '../parametrage/periode';

import { Projet } from "../parametrage/projet";
import { SourceFinancement } from "../parametrage/source-financement.model";
import { Structure } from "../parametrage/structure";
import { Taches } from "./taches";

export interface Programmation {
    id?: number;
    code?:String;
    coutPrevisionnel?: number;
    coutReel?: number;
    cible?: number;
    resultatsAtteints?:string;
    resultatsAttendus?:string;
    observations?:String;
    indicateur?:string;
    taux?:number;
    singleton?:boolean;
    structure?: Structure;
    projet?: Projet;
    objectif?:Objectif;
    sourceFinancement?: SourceFinancement;
    activite?: Activite;
    taches?: Array<Taches>;
    periodes?:Array<Periode>;
    estPrioritaire?:boolean;
    validationFinal?:boolean;
    validationInitial?:boolean;
    validationInterne?:boolean;

}


export interface GetAllProgrammationResponse {
    // totalCount: number;
    programmations: Programmation[];
}
