import { ProfilUserComponent } from './../components/private/settings/profil-user/profil-user.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ThemeLandingComponent } from './components/theme-landing/theme-landing.component';
import { LoginComponent } from './components/login/login.component';
import { AuthGuard } from './components/guard/auth.guard';
import { CompteValidationComponent } from '../components/private/settings/compte-validation/compte-validation.component';
import { SendMailComponent } from './components/send-mail/send-mail.component';

const routes: Routes = [
  { path:'profil/user', component: ProfilUserComponent },
  { path:'account/activate', component: CompteValidationComponent},
  { path: 'mot-de-passe-oublier', component: SendMailComponent}
    
]

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ThemeRoutingModule { }
