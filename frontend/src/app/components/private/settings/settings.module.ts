import { SourceFinancementComponent } from './source-financement/source-financement.component';
import { PonderationComponent } from './ponderation/ponderation.component';
import { ExerciceComponent } from './exercice/exercice.component';
import { PeriodeComponent } from './periode/periode.component';
import { ProgrammeComponent } from './programme/programme.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MessageModule } from 'primeng/message';
import { CardModule } from 'primeng/card';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { ContextMenuModule } from 'primeng/contextmenu';
import { DialogModule } from 'primeng/dialog';
import { DividerModule } from 'primeng/divider';
import { BadgeModule } from 'primeng/badge';
import { TagModule } from 'primeng/tag';
import { EditorModule } from 'primeng/editor';
import {CalendarModule} from 'primeng/calendar';
import { InputTextModule } from 'primeng/inputtext';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { TableModule } from 'primeng/table';
import { ExempleComponent } from './exemple/exemple.component';
import { SettingsRoutingModule } from './settings-routing.module';
import { AppCommonModule } from 'src/app/common/app-common.module';
import { ButtonModule } from 'primeng/button';
import { PanelModule } from 'primeng/panel';
import { ToolbarModule } from 'primeng/toolbar';
import { InputNumberModule } from 'primeng/inputnumber';
import { ProgressBarModule } from 'primeng/progressbar';
import { InputSwitchModule } from 'primeng/inputswitch';
import {CheckboxModule} from 'primeng/checkbox';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { MinistereComponent } from './ministere/ministere.component';
import { StructureComponent } from './structure/structure.component';
import { MinistereStructureComponent } from './ministere-structure/ministere-structure.component';
import { AgentComponent } from './agent/agent.component';
import { ActionComponent } from './action/action.component';
import { GrillePerformanceComponent } from './grille-performance/grille-performance.component';
import { TypeActiviteComponent } from './type-activite/type-activite.component';
import { PeriodiciteComponent } from './periodicite/periodicite.component';
import { ProjetComponent } from './projet/projet.component';
import { ProfilComponent } from './profil/profil.component';
import { PrivilegeComponent } from './privilege/privilege.component';

import { CompteComponent } from './compte/compte.component';
import {InputMaskModule} from 'primeng/inputmask';
import { ParametreComponent } from './parametre/parametre.component';
import {DropdownModule} from 'primeng/dropdown';
import { ObjectifComponent } from './objectif/objectif.component';
import { CompteValidationComponent } from './compte-validation/compte-validation.component';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { ActiviteComponent } from './activite/activite.component';
import { ProgrammationComponent } from './programmation/programmation.component';
import { NbCardModule, NbStepperModule, NbButtonModule, NbSelectModule, NbSpinnerModule, NbAutocompleteModule } from '@nebular/theme';
import { AmendementActiviteComponent } from './amendement-activite/amendement-activite.component';
import { ListeProgrammationComponent } from './liste-programmation/liste-programmation.component';

import { TabMenuModule } from 'primeng/tabmenu';
import { ProgrammeActivitesComponent } from './programme-activites/programme-activites.component';
import { EvaluationActivitesComponent } from './evaluation-activites/evaluation-activites.component';
import { IndicateurObjectifComponent } from './indicateur-objectif/indicateur-objectif.component';
import { ToastModule } from 'primeng/toast';
import { ConfirmationService, MessageService } from 'primeng/api';
import { DetailProgrammationComponent } from './detail-programmation/detail-programmation.component';
import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { HttpLoaderFactory } from 'src/app/app.module';
import { NotificationComponent } from './notification/notification.component';
import { ProfilUserComponent } from './profil-user/profil-user.component';
import { CritereGouvernanceComponent } from './critere-gouvernance/critere-gouvernance.component';
import { PerformersComponent } from './performers/performers.component';
import {FileUploadModule} from 'primeng/fileupload';
import { ListeProgrammationsValidesComponent } from './liste-programmations-valides/liste-programmations-valides.component';
import {AvatarModule} from 'primeng/avatar';
import {AvatarGroupModule} from 'primeng/avatargroup';
import { EvaluationGouvernanceComponent } from './evaluation-gouvernance/evaluation-gouvernance.component';
import { MesCriteresGouvernanceComponent } from './mes-criteres-gouvernance/mes-criteres-gouvernance.component';
import { ListeProgrammationsAllComponent } from './liste-programmations-all/liste-programmations-all.component';
import { ResetPasswordComponent } from 'src/app/theme/components/reset-password/reset-password.component';
import {PasswordModule} from 'primeng/password';

@NgModule({
  declarations: [
    ListeProgrammationsValidesComponent,
    ExempleComponent,
    MinistereComponent,
    StructureComponent,
    MinistereStructureComponent,
    AgentComponent,
    ActionComponent,
    GrillePerformanceComponent,
    TypeActiviteComponent,
    PeriodiciteComponent,
    PeriodeComponent,
    ProgrammeComponent,
    ProjetComponent,
    ProfilComponent,
    PrivilegeComponent,
    CompteComponent,
    ParametreComponent,
    ObjectifComponent,
    ActiviteComponent,
    ProgrammationComponent,
    ExerciceComponent,
    PonderationComponent,
    CompteValidationComponent,
    SourceFinancementComponent,
    ProgrammeActivitesComponent,
    EvaluationActivitesComponent,
    AmendementActiviteComponent,
    ListeProgrammationComponent,
    SourceFinancementComponent,
    IndicateurObjectifComponent,
    DetailProgrammationComponent,
    NotificationComponent,
    ProfilUserComponent,
    CritereGouvernanceComponent,
    PerformersComponent,
    EvaluationGouvernanceComponent,
    MesCriteresGouvernanceComponent,
    ListeProgrammationsAllComponent,
    ResetPasswordComponent
  ],
  imports: [
    CheckboxModule,
    CommonModule,
    FormsModule,
    NbButtonModule,
    NbCardModule,
    EditorModule,
    NbStepperModule,
    HttpClientModule,
    ReactiveFormsModule,
    RouterModule,
    NbSelectModule,
    NbAutocompleteModule,
    CardModule,
    PasswordModule,
    TabMenuModule,
    FileUploadModule,
    NbSpinnerModule,
    DropdownModule,
    ProgressBarModule,
    ConfirmDialogModule,
    DropdownModule,
    InputTextareaModule,
    ProgressSpinnerModule,
    ToolbarModule,
    PanelModule,
    InputNumberModule,
    TableModule,
    DialogModule,
    ButtonModule,
    ToastModule,
    InputTextModule,
    ContextMenuModule,
    MessageModule,
    DividerModule,
    AppCommonModule,
    SettingsRoutingModule,
    CalendarModule,
    InputSwitchModule,
    InputMaskModule,
    BadgeModule,
    TagModule,
    InputSwitchModule,
    DialogModule,
    NbButtonModule,
    TabMenuModule,
    TranslateModule.forChild({
      extend: true
    })
  ],
  providers: [ConfirmationService,MessageService],
})
export class SettingsModule { }
