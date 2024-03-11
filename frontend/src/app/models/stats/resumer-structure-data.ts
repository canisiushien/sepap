
export interface ResumerStructureData { 
    libelle?: string; 
    structureCode?: string; 
    termine?:number ;// nombre d'activités avec un taux d'évaluation à 100% 
    encours?:number;// nombre d'activités dont le taux est strcitement compris entre 0 et 100 % 
    enattente?:number;// nombre des activités à 0% 
    total?:number;// nombre total des activités
}