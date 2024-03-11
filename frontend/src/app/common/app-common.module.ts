import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { ButtonModule } from "primeng/button";
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { ConfirmationComponent } from "./confirmation/confirmation.component";
import { CrudToolbarComponent } from "./crud-toolbar/crud-toolbar.component";
import { PanelHeaderComponent } from "./panel-header/panel-header.component";
import { ActionsToolbalIudComponent } from './actions-toolbal-iud/actions-toolbal-iud.component';
import { CrudToolbarMinistereComponent } from './crud-toolbar-ministere/crud-toolbar-ministere.component';
import { CrudProBarComponent } from './crud-pro-bar/crud-pro-bar.component';
import { TooltipModule } from 'primeng/tooltip';


@NgModule({
    declarations: [
        CrudToolbarComponent,
        PanelHeaderComponent,
        ConfirmationComponent,
        ActionsToolbalIudComponent,
        CrudToolbarMinistereComponent,
        CrudProBarComponent,
        CrudProBarComponent

    ],
    imports: [
        ButtonModule,
        ConfirmDialogModule,
        CommonModule,
        TooltipModule
    ],
    exports: [
        CrudToolbarComponent,
        PanelHeaderComponent,
        ConfirmationComponent,
        ActionsToolbalIudComponent,
        CrudToolbarMinistereComponent,
        CrudProBarComponent
    ]
})
export class AppCommonModule {}
