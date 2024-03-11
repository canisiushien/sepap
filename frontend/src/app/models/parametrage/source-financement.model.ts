export interface SourceFinancement {
    id?:number;
    libelle?:string;
    montant?:number;
    debut?:Date;
    fin?:Date;

  }

  export interface GetAllSourceFinancementResponse {
   // totalCount: number;
    sources: SourceFinancement[];
  }
