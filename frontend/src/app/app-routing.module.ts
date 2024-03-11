import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [


 // {path: '', redirectTo: 'home', pathMatch: 'full', component: ThemeLandingComponent},
//  { path: '', loadChildren: () => import('./theme/theme.module').then(m => m.ThemeModule) },
 // {path: '**', redirectTo: 'home'},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
