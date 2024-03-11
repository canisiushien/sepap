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
import { CalendarModule } from 'primeng/calendar';
import { InputTextModule } from 'primeng/inputtext';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { TableModule } from 'primeng/table';
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

import { InputMaskModule } from 'primeng/inputmask';
import { DropdownModule } from 'primeng/dropdown';
import { HttpClientModule } from '@angular/common/http';
import { ChartModule } from 'primeng/chart';
import { NbCardModule, NbStepperModule, NbButtonModule, NbSelectModule, NbSpinnerModule } from '@nebular/theme';

import { TabMenuModule } from 'primeng/tabmenu';
import { ToastModule } from 'primeng/toast';
import { ConfirmationService, MessageService } from 'primeng/api';
import { SettingsRoutingModule } from '../settings/settings-routing.module';
import { ParametrePerformanceComponent } from './parametre-performance/parametre-performance.component';
import { ImpactPerformanceComponent } from './impact-performance/impact-performance.component';
import { EvaluerPerformanceComponent } from './evaluer-performance/evaluer-performance.component';
import { EtatsActiviteComponent } from './etats-activite/etats-activite.component';
import { EtatsPerformanceComponent } from './etats-performance/etats-performance.component';
import {AvatarModule} from 'primeng/avatar';
import {AvatarGroupModule} from 'primeng/avatargroup';
import { EtatsActiviteDetailsComponent } from './etats-activite-details/etats-activite-details.component';
import { ContribuerComponent } from './contribuer/contribuer.component';
@NgModule({
  declarations: [
 ParametrePerformanceComponent, ImpactPerformanceComponent, EvaluerPerformanceComponent, EtatsActiviteComponent, EtatsPerformanceComponent, EtatsActiviteDetailsComponent, ContribuerComponent,
  ],
  imports: [
    CheckboxModule,
    CommonModule,
    FormsModule,
    NbButtonModule,
    NbCardModule,
    NbStepperModule,
    HttpClientModule,
    ReactiveFormsModule,
    RouterModule,
    NbSelectModule,
    CardModule,
    TabMenuModule,
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
    ChartModule,
    AvatarModule,
    AvatarGroupModule
  ],
  providers: [ConfirmationService,MessageService],
})
export class EvaluationModule { }
