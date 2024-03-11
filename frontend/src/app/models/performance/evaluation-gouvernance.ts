import { Structure } from 'src/app/models/parametrage/structure';
import { CritereGouvernance } from './critere-gouvernance';
import { Exercice } from 'src/app/models/parametrage/exercice';
export interface EvaluationGouvernance
{

    id?: number;
    valeur?:number;
    valeurReference?: number;
    applicable ?:boolean;
    exercice?:Exercice;
    critere?: CritereGouvernance,
    structure?: Structure

}

export interface GetAllEvaluationGouverResponse {
  evaluationGouvernances: EvaluationGouvernance[];
}
