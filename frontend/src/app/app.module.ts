import { HttpClient, HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NbEvaIconsModule } from '@nebular/eva-icons';
import { NbDialogModule, NbIconModule, NbLayoutModule, NbMenuModule, NbSidebarModule, NbThemeModule } from '@nebular/theme';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ThemeRoutingModule } from './theme/theme-routing.module';
import { ThemeModule } from './theme/theme.module';
import { APP_INITIALIZER } from '@angular/core';
import { ConfirmationService, MessageService } from 'primeng/api';
import { ImageModule } from 'primeng/image';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { ToastModule } from 'primeng/toast';

import { SettingsModule } from './components/private/settings/settings.module';
import { DashboardModule } from './components/private/dashboard/dashboard.module';
import { EvaluationModule } from './components/private/evaluation/evaluation.module';
import { AuthInterceptorProviders } from './_helpers/auth.interceptor';
import { AuthenticationService } from './services/parametrage/authentication.service';
import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
export function HttpLoaderFactory(httpClient: HttpClient) {
  return new TranslateHttpLoader(httpClient);
}


@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule,
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    ThemeModule,
    SettingsModule,
    DashboardModule,
    EvaluationModule,
    ToastModule,
    BrowserAnimationsModule,
    NbLayoutModule,
    ThemeRoutingModule,
    ImageModule,
    NbEvaIconsModule,
    TranslateModule.forRoot({
      defaultLanguage: 'fr',
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      }
    }),
    NbIconModule,               // <---------
    NbSidebarModule.forRoot(),  // <---------
    NbMenuModule.forRoot(),
    NbDialogModule.forRoot(),
    NbThemeModule.forRoot({ name: 'corporate' })
  ],
  providers: [ConfirmationService, MessageService,AuthInterceptorProviders,
   ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
