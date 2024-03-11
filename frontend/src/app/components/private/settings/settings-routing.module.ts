import { ContribuerComponent } from './../evaluation/contribuer/contribuer.component';
import { Contribuer } from 'src/app/models/performance/contribuer';
import { ListeProgrammationsAllComponent } from './liste-programmations-all/liste-programmations-all.component';
import { EtatsActiviteDetailsComponent } from './../evaluation/etats-activite-details/etats-activite-details.component';
import { ListeProgrammationsValidesComponent } from './liste-programmations-valides/liste-programmations-valides.component';

import { EtatsPerformanceComponent } from './../evaluation/etats-performance/etats-performance.component';
import { EtatsActiviteComponent } from './../evaluation/etats-activite/etats-activite.component';
import { EvaluerPerformanceComponent } from './../evaluation/evaluer-performance/evaluer-performance.component';
import { CritereGouvernanceComponent } from './critere-gouvernance/critere-gouvernance.component';
import { CritereGouvernance } from './../../../models/performance/critere-gouvernance';
import { ImpactPerformanceComponent } from './../evaluation/impact-performance/impact-performance.component';
import { ParametrePerformanceComponent } from './../evaluation/parametre-performance/parametre-performance.component';
import { DetailProgrammationComponent } from './detail-programmation/detail-programmation.component';
import { IndicateurObjectifComponent } from './indicateur-objectif/indicateur-objectif.component';
import { EvaluationActivitesComponent } from './evaluation-activites/evaluation-activites.component';
import { SourceFinancementComponent } from './source-financement/source-financement.component';
import { PonderationComponent } from './ponderation/ponderation.component';
import { CompteValidationComponent } from './compte-validation/compte-validation.component';
import { CompteComponent } from './compte/compte.component';
import { ExerciceComponent } from './exercice/exercice.component';
import { PrivilegeComponent } from './privilege/privilege.component';
import { NgModule, Component } from '@angular/core';
import { MinistereStructureComponent } from './ministere-structure/ministere-structure.component';
import { ProjetComponent } from './projet/projet.component';
import { ProgrammeComponent } from './programme/programme.component';
import { StructureComponent } from './structure/structure.component';
import { MinistereComponent } from './ministere/ministere.component';
import { RouterModule, Routes } from '@angular/router';
import { ThemeLandingComponent } from 'src/app/theme/components/theme-landing/theme-landing.component';
import { AgentComponent } from './agent/agent.component';
import { TypeActiviteComponent } from './type-activite/type-activite.component';
import { PeriodiciteComponent } from './periodicite/periodicite.component';
import { PeriodeComponent } from './periode/periode.component';
import { ProfilComponent } from './profil/profil.component';
import { ActionComponent } from './action/action.component';
import { ParametreComponent } from './parametre/parametre.component';
import { GrillePerformanceComponent } from './grille-performance/grille-performance.component';
import { ObjectifComponent } from './objectif/objectif.component';
import { LoginComponent } from '../../../theme/components/login/login.component';
import { AuthGuard } from '../../../theme/components/guard/auth.guard';
import { ExempleComponent } from './exemple/exemple.component';
import { ActiviteComponent } from './activite/activite.component';
import { ProgrammationComponent } from './programmation/programmation.component';
import { ProgrammeActivitesComponent } from './programme-activites/programme-activites.component';
import { AmendementActiviteComponent } from './amendement-activite/amendement-activite.component';
import { ListeProgrammationComponent } from './liste-programmation/liste-programmation.component';
import { DhsEvolutionComponent } from '../dashboard/dhs-evolution/dhs-evolution.component';
import { DhsFinanceComponent } from '../dashboard/dhs-finance/dhs-finance.component';
import { DhsRapportComponent } from '../dashboard/dhs-rapport/dhs-rapport.component';
import { DhsSectorielComponent } from '../dashboard/dhs-sectoriel/dhs-sectoriel.component';
import { DhsStructureComponent } from '../dashboard/dhs-structure/dhs-structure.component';
import { PerfEvolutionComponent } from '../dashboard/perf-evolution/perf-evolution.component';
import { PerfRappportComponent } from '../dashboard/perf-rappport/perf-rappport.component';
import { PerfSectorielleComponent } from '../dashboard/perf-sectorielle/perf-sectorielle.component';
import { PerfStructureComponent } from '../dashboard/perf-structure/perf-structure.component';
import { NotificationComponent } from './notification/notification.component';
import { PerformersComponent } from './performers/performers.component';
import { EvaluationGouvernanceComponent } from './evaluation-gouvernance/evaluation-gouvernance.component';
import { MesCriteresGouvernanceComponent } from './mes-criteres-gouvernance/mes-criteres-gouvernance.component';
import { ResetPasswordComponent } from 'src/app/theme/components/reset-password/reset-password.component';

const routes: Routes = [
  { path:'', redirectTo:'/login',pathMatch:'full'},
  { path:'login', component: LoginComponent},
  {
    path: 'validationcompte', component: CompteValidationComponent,
  },
  { path: 'workspace', component: ThemeLandingComponent, canActivate: [AuthGuard],


    children: [
      { path:'', component: DhsStructureComponent },
      { path: 'exemple', component: ExempleComponent },
      { path:'compte', component: CompteComponent},
      { path:'programmation', component: ProgrammationComponent},
      { path:'changer-mot-de-passe', component: ResetPasswordComponent},
      {
        path: 'agent',
        component: AgentComponent,
      },
      {
        path: 'objectif',
        component: ObjectifComponent,
      },
      {
        path: 'sources-financement',
        component: SourceFinancementComponent,
      },
      // {
      //   path: 'indicateurImpact',
      //   component: IndicateurImpactComponent,
      // },
      {
        path: 'type-activite',
        component: TypeActiviteComponent,
      },

      {
        path: 'activite',
        component: ActiviteComponent,
      },
      {
        path: 'grille-performance',
        component: GrillePerformanceComponent,
      },
      {
        path: 'amendement-activite/:ids/:idp',
        component: AmendementActiviteComponent,
      },
      {
        path: 'detail-programmation/:ids/:idp',
        component: DetailProgrammationComponent,
      },
      {
        path: 'liste-programmation',
        component: ListeProgrammationComponent,
      },
      {
        path: 'toutes-programmations',
        component: ListeProgrammationsAllComponent,
      },

      {
        path: 'action',
        component: ActionComponent,
      },


      {
        path: 'ministere',
        component: MinistereComponent,
      },
      {
        path: 'structure',
        component: StructureComponent,
      },
      {
        path: 'action',
        component: ActionComponent,
      },

      {
        path: 'indicateur',
        component: IndicateurObjectifComponent,
      },

      {
        path: 'type-activite', component: TypeActiviteComponent,
      },
      {
        path: 'programme', component: ProgrammeComponent,
      },
      {
        path: 'periodicite', component: PeriodiciteComponent,
      },
      {
        path: 'periode', component: PeriodeComponent,
      },
      {
        path: 'privilege', component: PrivilegeComponent,
      },
      {
        path: 'ministere-structure', component: MinistereStructureComponent,
      },

      {
        path: 'projet', component: ProjetComponent,
      },



    {
      path: 'projet', component: ProjetComponent,
    },

    {
      path: 'parametre', component: ParametreComponent,
    },
    {
      path: 'periodicite',
      component: PeriodiciteComponent,
    },
    {
      path: 'periode', component: PeriodeComponent,
    },

      {
        path: 'exercice', component: ExerciceComponent,
      },
      {
        path: 'parametre', component: ParametreComponent,
       },
       {
        path: 'privilege', component: PrivilegeComponent,
       },
       {
        path: 'ministere-structure', component: MinistereStructureComponent,
       },
       {
        path: 'profil', component: ProfilComponent,
      },
      {
        path: 'compte', component: CompteComponent,
      },

       {
        path: 'ponderation', component: PonderationComponent,
       },
       {
        path: 'programme-activites', component: ProgrammeActivitesComponent,
       },
       {
        path: 'evaluation-activites/:ids/:idp',
        component: EvaluationActivitesComponent,
       },
       {
        path: 'liste-evaluations/:idp',
        component: EtatsActiviteComponent,
       },
       {
        path: 'liste-evaluations',
        component: EtatsActiviteComponent,
       },
       {
         path: 'evaluation-activite-detail/:idp',
         component: EtatsActiviteDetailsComponent,
       },
       {path: 'notifications',component: NotificationComponent },
       {path: 'liste-programme-valides', component: ListeProgrammationsValidesComponent },
       {path: 'criteres',component: CritereGouvernanceComponent },
  ]},
  {
    path: 'workspace/dashboard', component: ThemeLandingComponent, canActivate: [AuthGuard],

    children: [

      { path: 'dhsstructure', component: DhsStructureComponent },

      { path: 'dhssectoriel', component: DhsSectorielComponent },

      { path: 'dhsfinance', component: DhsFinanceComponent },

      { path: 'dhsevolution', component: DhsEvolutionComponent },

      { path: 'dhsrapport', component: DhsRapportComponent },

      { path: 'perfstructure', component: PerfStructureComponent },

      { path: 'perfsectorielle', component: PerfSectorielleComponent },

      { path: 'perfevolution', component: PerfEvolutionComponent },

      { path: 'perfrapport', component: PerfRappportComponent },
    ]
  },

  { path: 'workspace/performances', component: ThemeLandingComponent, canActivate: [AuthGuard],


  children: [
    { path: 'performers', component: PerformersComponent },
    {path:  'evaluation-gouvernance',component: EvaluationGouvernanceComponent},
    {path:  'mes-criteres-gouvernance',component: MesCriteresGouvernanceComponent},
    { path: 'parametre-impact', component: ParametrePerformanceComponent },
    { path: 'impact', component: ImpactPerformanceComponent },
    { path: 'evaluation-gouvernance', component: EvaluerPerformanceComponent },
    { path: 'liste-performance', component: EtatsPerformanceComponent },
    { path: 'contribuer', component: ContribuerComponent},
  ]},

  { path: "**", redirectTo: '/login' }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class SettingsRoutingModule { }
