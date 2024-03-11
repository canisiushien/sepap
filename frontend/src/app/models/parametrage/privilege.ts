export interface Privilege {
  id?:number;
  name?: string;
}

export interface GetAllPrivilegeResponse {
  // totalCount: number;
   privileges: Privilege[];
 }

