import { Activite } from "../activites/activite";
import { Exercice } from "../parametrage/exercice";
import { Objectif } from "../parametrage/objectif";
import { Projet } from "../parametrage/projet";
import { SourceFinancement } from "../parametrage/source-financement.model";
import { Structure } from "../parametrage/structure";
import { Taches } from "./taches";

export interface ProgrammationForEvaluationDTO {
    id?:number;
    code?:string;
    coutPrevisionnel?:number;
    coutReel?:number;
    estProgramme?:boolean;
    singleton?:boolean;
    cible?:boolean;
    taux?:boolean;
    periodeActuelle?:string;
    valeurActuelle?:number;
    tauxActuel?:number;
    resultatsAttendus?:string;
    resultatsAtteints?:string;
    observations?:string;
    validationInitial?:boolean;
    validationInterne?:boolean;
    validationFinal?:boolean;
    periodes?:string;
    sourceFinancement?:SourceFinancement;
    taches?:Array<Taches>;
    activite?:Activite;
    projet?:Projet;
    structure?:Structure;
    exercice?:Exercice;
    objectif?:Objectif;
    estPrioritaire?:boolean;
}

export interface GetAllProgrammationForEvResponse {
  // totalCount: number;
  programmations: ProgrammationForEvaluationDTO[];
}
