import { Component, OnDestroy } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { PrimeNGConfig } from 'primeng/api';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnDestroy {
  title = 'ui-dgessddi';
  lang: string = 'fr';
  
    subscription: Subscription;
  
    constructor(
      public translate: TranslateService,
      public primeNGConfig: PrimeNGConfig,
    ) {
      translate.addLangs(['fr']);
      translate.setDefaultLang('fr');
  
      const browserLang = translate.getBrowserLang();
      let lang = browserLang.match(/en|fr/) ? browserLang : 'en';
      this.changeLang(lang);
  
      this.subscription = this.translate.stream('primeng').subscribe(data => {
        this.primeNGConfig.setTranslation(data);
      });
    }
  
    changeLang(lang: string) {
      this.translate.use(lang);
    }
  
    ngOnDestroy() {
      if (this.subscription) {
        this.subscription.unsubscribe();
      }
    }
}
