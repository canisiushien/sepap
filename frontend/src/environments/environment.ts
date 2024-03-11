// This file can be replaced during build by using the `fileReplacements` array.
// `ng build` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.
const commonAppURI: string = 'http://localhost:8090/dgess/api/';

export const environment = {
  production: false,
  exempleResource: commonAppURI +'exemples',
  agentResource: commonAppURI +'agents',
  resentEmailRessource:commonAppURI + 'account/send-activated-notification/',
  indicateurImpactResource: commonAppURI + 'indicateur/objectif',
  objectifStrategiqueResource:commonAppURI + 'objectifStrategiques',
  typeActiviteResource:commonAppURI+ 'activites/type',
  grillePerformanceResource:commonAppURI+ 'grille-performances',
  parametreRessource: commonAppURI + 'parametres',
  activitesResource: commonAppURI + 'activites',
  programmationPhysiqueRessource:commonAppURI + 'programmation-physique',
  documentRessource:commonAppURI + 'documents',
  tacheEvaluationRessource:commonAppURI + 'taches',
  programmationsResource: commonAppURI + 'programmations',
  ponderationRessouce: commonAppURI + 'pnderations',
  objectifRessource: commonAppURI + 'objectifs',
  authenticationRessource: commonAppURI + 'authenticate',
  actionRessource: commonAppURI + 'actions',
  ministereRessource: commonAppURI+'ministeres',
  ministereBundleRessource: commonAppURI+'ministeres/bundle',
  projetRessource: commonAppURI+'projets',
  ministerStructureRessource:commonAppURI+'structures/change-ministere',
  periodiciteRessource:commonAppURI + 'periodicites',
  periodeRessource: commonAppURI + 'periodes',
  privilegeRessource: commonAppURI+'privileges',
  profilRessource: commonAppURI + 'profiles',
  compteRessource: commonAppURI  ,
  activateResource:commonAppURI+'activate',
  structureRessource: commonAppURI + 'structures',
  programmeRessource:commonAppURI +'programmes',
  exerciceRessource:commonAppURI + 'exercices',
  ponderationResource:commonAppURI + 'ponderations',
  sourcefinancementResource:commonAppURI + 'source-financements',
  notificationResource:commonAppURI + 'notifications',
  impactRessource: commonAppURI+'impacts',
  parametreImpactRessource: commonAppURI+'parametrer',
  critereRessource: commonAppURI+'criteres',
  evaluationGRessource: commonAppURI+'evaluat',
  //calculPerformanceRessource: commonAppURI+'evaluat',
  statsAllDepensesRessource: commonAppURI+'stats/depense/',
  statsAllActivitesRessource: commonAppURI+'stats/activite/',
  statsStuctureResumeRessource: commonAppURI+'stats/structure/',
  statsAllResumeRessource: commonAppURI+'stats/resumer/', 
  statsAllPerformanceRessource: commonAppURI+'stats/performance/',
  statsAllSectorielResumeRessource: commonAppURI+'stats/sectoriel/',
  statsAllSectorielDepenseResumeRessource: commonAppURI+'stats/sectoriel-depense/',
  evolutionRessourceUrl: commonAppURI+'stats/evolution/', 
  evolutionPerformanceRessourceUrl: commonAppURI+'stats/evolution-performance/',

  calculPerformanceRessource: commonAppURI+'performers/calculer',

  evaluationGouvernanceRessource: commonAppURI+'evaluation-gouvernance',

  performanceRessource: commonAppURI+'performances/ministere-structure',
  perfMinistereRessource: commonAppURI+'stats/perf/',
  perfGlobalRessource: commonAppURI+'stats/perf-global/',

  contribuerRessource : commonAppURI +'contribuer',

  passwordForgetRessource : commonAppURI +'account/reset-password-init',

  changePasswordRessource : commonAppURI +'account/change-password',
  recordsPerPage: 10,
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/plugins/zone-error';  // Included with Angular CLI.
