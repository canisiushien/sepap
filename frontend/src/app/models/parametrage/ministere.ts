

export interface Ministere {
  id?:number;
  code?:string;
  libelle?:string;
  sigle?:string


}

export interface GetAllMinistereResponse {
 // totalCount: number;
  ministeres: Ministere[];
}
