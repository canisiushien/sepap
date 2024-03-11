import { ResumerActiviteData } from "./resumer-activite-data";
import { ResumerDepenseData } from "./resumer-depense-data";
import { ResumerStructureData } from "./resumer-structure-data";

export interface MinistereGlobalStatsBundleData {
    depense?:ResumerDepenseData; 
    activite?:ResumerActiviteData; 
    resumes?: ResumerStructureData[];
}