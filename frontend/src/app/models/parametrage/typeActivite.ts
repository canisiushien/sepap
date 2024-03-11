export interface TypeActivite {
  id? :number,
  libelle? : string
}

export interface GetAllTypeActiviteResponse {
  typeActivites : TypeActivite[];
}
