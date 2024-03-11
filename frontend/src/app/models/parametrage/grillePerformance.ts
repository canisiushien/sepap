
export interface GrillePerformance {
  id?:number;
  borneInferieur?:number;
  borneSuperieur?:number;
  appreciation?:string
}

export interface GetAllGrillePerformanceResponse {
  grillePerformances : GrillePerformance[];
}
