import { Ministere } from 'src/app/models/parametrage/ministere';
import { Structure } from './structure';
export interface MinistereStructure {


  ministereId?:number;
  structureId?:number;
  structureParentId?:number;

}

export interface GetAllMinistereStructureResponse {
  // totalCount: number;
  ministereStructures: MinistereStructure[];
 }
