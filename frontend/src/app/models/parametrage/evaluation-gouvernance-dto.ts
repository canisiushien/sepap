import { CritereGouvernance } from "../performance/critere-gouvernance";

export interface EvaluationGouvernanceDTO {
    id?:number;
    nonapplicable?:boolean;
    valeurReference?:number;
    valeur?:number;
    critereGouvernance?:CritereGouvernance
}
