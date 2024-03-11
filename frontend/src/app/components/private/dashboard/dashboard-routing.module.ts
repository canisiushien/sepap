import { NgModule } from '@angular/core';

import { RouterModule, Routes } from '@angular/router';
import { ThemeLandingComponent } from 'src/app/theme/components/theme-landing/theme-landing.component';

import { LoginComponent } from '../../../theme/components/login/login.component';
import { AuthGuard } from '../../../theme/components/guard/auth.guard';

import { DhsStructureComponent } from './dhs-structure/dhs-structure.component';
import { DhsSectorielComponent } from './dhs-sectoriel/dhs-sectoriel.component';
import { DhsFinanceComponent } from './dhs-finance/dhs-finance.component';
import { DhsEvolutionComponent } from './dhs-evolution/dhs-evolution.component';
import { DhsRapportComponent } from './dhs-rapport/dhs-rapport.component';
import { PerfStructureComponent } from './perf-structure/perf-structure.component';
import { PerfSectorielleComponent } from './perf-sectorielle/perf-sectorielle.component';
import { PerfEvolutionComponent } from './perf-evolution/perf-evolution.component';
import { PerfRappportComponent } from './perf-rappport/perf-rappport.component';

const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
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
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class DashboardRoutingModule { }
