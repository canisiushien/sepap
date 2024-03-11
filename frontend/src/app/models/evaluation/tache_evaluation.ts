import { Programmation } from "../programmation/programmation";

export interface TacheEvaluation
{

    id?: number;
    execute?:boolean;
    valeur?: number;
    programmation?: Programmation

}
