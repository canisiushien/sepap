export interface Periodicite{

  id?:number,
  libelle?:string;
  actif?: boolean;
}


export interface GetAllPeriodiciteResponse { 
   periodicites: Periodicite[];
}
