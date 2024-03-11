import { Exercice } from "../parametrage/exercice";
import { Ponderation } from "../parametrage/ponderation";
import { Structure } from "../parametrage/structure";

export interface Performance
{
  id?: number;
  efficacite?: number;
  efficience?: number;
  gouvernance?: number;
  impact?: number;
  pgs?: number;
  appreciation?:string;
  exercice?: Exercice;
  structure?: Structure;
  ponderation?:Ponderation

}

export interface GetAllPerformanceResponse{
  performances:Performance[];
}
