export interface CritereGouvernance
{
    id?: number;
    indicateur?:string;
    mode?: boolean;
    actif?:boolean;
    nonapplicable?:boolean;
    valeurReference?:number;

}

export interface GetAllCritereResponse {
  criteres : CritereGouvernance[];
}
