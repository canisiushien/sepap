import { Programmation } from './../programmation/programmation';
import { Periode } from "../parametrage/periode";

export interface Evaluation
{

    id?: number;
    evaluer?:boolean;
    valeur?: string;
    observations ?:string;
    periode?:Periode;
    programmation?: Programmation

}

export interface GetAllEvaluationResponse
{
   evaluations: Evaluation[]
}
