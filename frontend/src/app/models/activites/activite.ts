import { TypeActivite } from "../parametrage/typeActivite";

export interface Activite {
    id?: number;
    code?: string;
    libelle?: string;
    description?: string;
    status?: string;
    typeActivites?: TypeActivite;
}

export interface GetAllActiviteResponse {
    // totalCount: number;
    activtes: Activite[];
}
