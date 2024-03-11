export interface ObjectifStrategique {
  id?:number;
  code?:string;
  libelle?:string;
  description?:string;
}

export interface GetAllObjectifStrategiqueResponse {
 // totalCount: number;
 objectifStrategiques: ObjectifStrategique[];
}
