import { ParametreImpact } from 'src/app/models/performance/parametrer-impact';
import { Structure } from '../parametrage/structure';
export interface Contribuer{

  id?: number;
  cible?: number;
  valeur?: number;
  monapplicable?: boolean;
  parametrerImpact?:ParametreImpact;
  structure?: Structure
}

export interface GetAllContribuerResponse {
  contributions : Contribuer[];
}
