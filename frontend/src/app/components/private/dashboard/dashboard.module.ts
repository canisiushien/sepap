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
import { DashboardRoutingModule } from './dashboard-routing.module';
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
import {FieldsetModule} from 'primeng/fieldset';
import { NbCardModule, NbStepperModule, NbButtonModule, NbSelectModule, NbSpinnerModule } from '@nebular/theme'; 

import { TabMenuModule } from 'primeng/tabmenu'; 
import { ToastModule } from 'primeng/toast';
import { ConfirmationService, MessageService } from 'primeng/api';

import { PerfRappportComponent } from './perf-rappport/perf-rappport.component';
import { PerfEvolutionComponent } from './perf-evolution/perf-evolution.component';
import { PerfSectorielleComponent } from './perf-sectorielle/perf-sectorielle.component';
import { SettingsRoutingModule } from '../settings/settings-routing.module';
import { DhsEvolutionComponent } from './dhs-evolution/dhs-evolution.component';
import { DhsFinanceComponent } from './dhs-finance/dhs-finance.component';
import { DhsRapportComponent } from './dhs-rapport/dhs-rapport.component';
import { DhsSectorielComponent } from './dhs-sectoriel/dhs-sectoriel.component';
import { DhsStructureComponent } from './dhs-structure/dhs-structure.component';
import { PerfStructureComponent } from './perf-structure/perf-structure.component';

@NgModule({
  declarations: [
    PerfRappportComponent,
    PerfEvolutionComponent,
    PerfSectorielleComponent,
    PerfStructureComponent,
    DhsRapportComponent,
    DhsEvolutionComponent,
    DhsFinanceComponent,
    DhsSectorielComponent,
    DhsStructureComponent,

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
    DashboardRoutingModule,
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
    FieldsetModule,
  ],
  providers: [ConfirmationService,MessageService],
})
export class DashboardModule { }
