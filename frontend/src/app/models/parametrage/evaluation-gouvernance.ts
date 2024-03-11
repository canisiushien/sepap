import { EvaluationGouvernanceDTO } from "./evaluation-gouvernance-dto";
import { Exercice } from "./exercice";
import { Structure } from "./structure";

export interface EvaluationGouvernance {
    id?:number;
    valeur?:number;
    exercice?:Exercice;
    structure?:Structure;
    critereGouvernances?:Array<EvaluationGouvernanceDTO>
}

export interface GetAllEvaluationGouvResponse {
    evaluationGouvernances: EvaluationGouvernance[];
  }