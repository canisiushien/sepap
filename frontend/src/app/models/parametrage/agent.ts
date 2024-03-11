import { Privilege } from "./privilege";
import { Profil } from "./profil";

export interface Agent {
    id?:number;
    matricule?:string;
    nom?:string;
    prenom?:string;
    telephone?:string;
    email?:string;
    actif?:boolean;
    profiles?:Profil[];
    privileges?:Privilege[]
}

export interface AgentStruct {
    id?:number;
    matricule?:string;
    nom?:string;
    prenom?:string;
    telephone?:string;
    email?:string;
    actif?:boolean;
    structure?:String;
}

export interface GetAllAgentResponse {
   // totalCount: number;
    agents: Agent[];
}
