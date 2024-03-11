import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ThemeRoutingModule } from './theme-routing.module';
import { FooterComponent } from './components/footer/footer.component';
import { HeaderComponent } from './components/header/header.component';
import { NbActionsModule, NbButtonModule, NbContextMenuModule, NbDialogModule, NbIconModule, NbLayoutModule, NbMenuModule, NbSearchModule, NbSelectModule, NbSidebarModule, NbUserModule } from '@nebular/theme';
import { NbEvaIconsModule } from '@nebular/eva-icons';
import { MenuComponent } from './components/menu/menu.component';
import { ThemeLandingComponent } from './components/theme-landing/theme-landing.component';
import { NbSecurityModule } from '@nebular/security';
import { OneColumnLayoutComponent } from './components/layouts/one-column-layout/one-column-layout.component';
import { TwoColumnLayoutComponent } from './components/layouts/two-column-layout/two-column-layout.component';
import { ThreeColumnLayoutComponent } from './components/layouts/three-column-layout/three-column-layout.component';
import { HttpClientModule } from '@angular/common/http';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MenuParametreComponent } from './components/menu-parametre/menu-parametre.component';
import {MenubarModule} from 'primeng/menubar';
import { LoginComponent } from './components/login/login.component';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import {BadgeModule} from 'primeng/badge';
import { DialogModule } from 'primeng/dialog';
import { TableModule } from 'primeng/table';
import { CardModule } from 'primeng/card';
import { ButtonModule } from 'primeng/button';
import { DividerModule } from 'primeng/divider';
import { SendMailComponent } from './components/send-mail/send-mail.component';
import {PasswordModule} from 'primeng/password';

@NgModule({
  declarations: [
    FooterComponent,
    HeaderComponent,
    MenuComponent,
    ThemeLandingComponent,
    OneColumnLayoutComponent,
    TwoColumnLayoutComponent,
    ThreeColumnLayoutComponent,
    MenuParametreComponent,
    LoginComponent,
    PageNotFoundComponent,
    SendMailComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    NbUserModule,
    DialogModule,
    ButtonModule,
    DividerModule,
    TableModule,
    PasswordModule,
    CardModule,
    BadgeModule,
    ReactiveFormsModule,
    CommonModule,
    NbActionsModule,
    NbIconModule,
    NbEvaIconsModule,
    NbSearchModule,
    NbSidebarModule,
    NbContextMenuModule,
    NbSecurityModule,
    NbButtonModule,
    NbSelectModule,
    NbLayoutModule,
    NbMenuModule,
    NbIconModule,
    NbEvaIconsModule,
    NbDialogModule.forChild(),
    ThemeRoutingModule,
    MenubarModule,

  ],
  providers: [],
})

export class ThemeModule { }
