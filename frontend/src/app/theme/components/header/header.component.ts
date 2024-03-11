import { CompteService } from 'src/app/services/parametrage/compte.service';
import { ChangeDetectionStrategy, Component, OnDestroy, OnInit } from '@angular/core';
import { NbDialogService, NbMediaBreakpointsService, NbMenuService, NbSidebarService, NbThemeService } from '@nebular/theme';
import { Subject } from 'rxjs';
import { Router } from '@angular/router';
import { AuthenticationService } from '../../../services/parametrage/authentication.service';
import { NotificationService } from 'src/app/services/notification/notification.service';
import { HttpErrorResponse } from '@angular/common/http';
import { LazyLoadEvent, Message } from 'primeng/api';
import { NotificationAgent } from 'src/app/models/notification/notification-agent';
import { environment } from 'src/environments/environment';
import { Compte } from 'src/app/models/parametrage/compte';
import { ProfilUserComponent } from 'src/app/components/private/settings/profil-user/profil-user.component';


@Component({
  selector: 'app-header',
  changeDetection: ChangeDetectionStrategy.OnPush,
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  private destroy$: Subject<void> = new Subject<void>();
  userPictureOnly: boolean = false;
  nonLu:any;
  showDialog:boolean=false;
  isLoading!: boolean;
  totalRecords!: number;
  recordsPerPage = environment.recordsPerPage;
  notificationAgent: NotificationAgent = {};
  notificationAgents:NotificationAgent[]=[];
  message: any;
  timeoutHandle: any;
  dialogErrorMessage: any;
  isOpInProgress!: boolean;
  isDialogOpInProgress!: boolean;
  user: any;
  username:any;
  showProfil:boolean=false;
  compte:Compte={};
  comptes: Compte[]=[];
  userMenu = [

    {
      title: 'Profil',
      icon: 'person-outline',
      action: 'onInfoProfil()'
    },
    {
      title: 'Change Password',
      icon: 'lock-outline',
      action: 'changePassword()'
    },
    {
      title: 'Déconnexion',
      icon: 'unlock-outline',
      action: 'disconnect()'
    }
  ];

 /* userMenu = [

    {

      title: 'Mon Profil',
      icon: 'person-outline',
      children: [

        {
          title: 'Profile',
          icon: 'person-outline',
          action: 'onInfoProfil()' 
        },
        {
          title: 'Change Password',
          icon: 'lock-outline',
        },

        {
          title: 'Déconnexion',
          icon: 'unlock-outline',
          action: 'disconnect()'
        }]
    },
  ];*/


  constructor(private sidebarService: NbSidebarService,
    private menuService: NbMenuService,
    private notificationService: NotificationService,
    private router: Router,
    private nbDialogService: NbDialogService,
    private authService: AuthenticationService,
    private compteService: CompteService) {
    this.user = this.authService.tokenDecode();
    this.username = this.authService.getUsername(); 
  }

  ngOnInit(): void {
    this.selectClick();
    this.onInfoProfil();
  }

  selectClick() {
    this.menuService.onItemClick().subscribe((event) => {
      if (event.item.title === 'Déconnexion') {
        this.disconnect();
      } 
      if (event.item.title === 'Change Password') {
        this.changePassword();
      } 
      if (event.item.title === 'Profil') {
  
        this.nbDialogService.open(ProfilUserComponent,{
          context: {compte: this.compte}, // here i have got typescript error 
          hasBackdrop: true,
          closeOnBackdropClick: false,
        });
      }
    })
  }
 
  disconnect() {
    this.authService.signOut();
    this.router.navigate(['/login']);
  }

  changePassword() {
    this.router.navigate(['/workspace/changer-mot-de-passe']);
  }

 /* ngOnDestroy() {
    this.destroy$.next();
    this.destroy$.complete();
  }*/

  toggleSidebar(): boolean {
    this.sidebarService.toggle(true, 'menu-sidebar');
    return false;
  }

  navigateHome() {
    this.menuService.navigateHome();
    return false;
  }

  getCount(usermane:any){
    this.notificationService.getCount(usermane).subscribe(response => {
        this.nonLu = response;
      },
      (error) => {
        this.message = { severity: 'error', summary: error.error };
        console.error(JSON.stringify(error));
      }
    );
  }

  onInfo() {
    this.showDialog= true;
    this.loadByID(this.username);
  }

  loadByID(username:any) {
    this.isLoading = true;
    this.notificationService.getAllByID(username).subscribe(
      (response) => {
        this.isLoading = false;
        this.notificationAgents = response.notificationAgents;
      },
      (error) => {
        this.message = { severity: 'error', summary: error.error };
        console.error(JSON.stringify(error));
      }
    );
  }

  onInfoProfil() {
     this.compteService.getComptebyMatricule(this.username).subscribe(response => {
         this.compte = response;
       });
  }

   // Errors

   handleError(error: HttpErrorResponse) {
    console.error(`Processing Error: ${JSON.stringify(error)}`);
    this.isDialogOpInProgress = false;
    this.dialogErrorMessage = error.error.title;
  }

  // Messages

  clearDialogMessages() {
    this.dialogErrorMessage = null;
  }

  showMessage(message: Message) {
    this.message = message;
    this.timeoutHandle = setTimeout(() => {
      this.message = null;
    }, 5000);
  }
}
