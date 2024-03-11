export interface Exemple {
    id?:number;
    libelle?:string;
    description?:string;
}

export interface GetAllExempleResponse {
   // totalCount: number;
    exemples: Exemple[];
}