import { Commentaire } from './../../../../models/programmation/commentaire';
import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AmendementActiviteService } from 'src/app/services/programmation/amendement-activite.service';
import { environment } from 'src/environments/environment';
import { ConfirmationService, Message } from 'primeng/api';
import { HttpErrorResponse } from '@angular/common/http';
import { Programmation } from 'src/app/models/programmation/programmation';
import { AuthenticationService } from 'src/app/services/parametrage/authentication.service';
import { Location } from '@angular/common';


@Component({
  selector: 'app-amendement-activite',
  templateUrl: './amendement-activite.component.html',
  styleUrls: ['./amendement-activite.component.scss']
})
export class AmendementActiviteComponent implements OnInit {
  @ViewChild('dtf') form!: NgForm;
  // taches: any[]=[
  //   {libelle:"tache1",valeur:"valeur1",ponderation:20},
  //   {libelle:"tache2",valeur:"valeur2",ponderation:40}
  // ];
  taches :any[] = [];
  indicateurs: any[]=[];
  programmation: Programmation = {};
  timeoutHandle: any;
  totalRecords!: number;
  recordsPerPage = environment.recordsPerPage;
  showDialog = false;
  message: any;
  isOpInProgress!: boolean;
  isDialogOpInProgress!: boolean;
  dialogErrorMessage: any;
  commentaire: Commentaire = {};
  ids!: number;
  idp!: number;
  btnCommentaire: boolean=false;
  permissions: any;
  // public ps= AppModule.permissions;

  constructor(private route:ActivatedRoute, private router:Router,
    private amendementActiviteService:AmendementActiviteService,
    private confirmationService: ConfirmationService,
    public authService: AuthenticationService,
    private location: Location
   ) {}

  ngOnInit(): void {
    this.ids = this.route.snapshot.params['ids'];
    this.idp = this.route.snapshot.params['idp'];
    this.commentaire.programmationId = this.idp;
    this.permissions=this.authService.getPrivilege();
    this.loadprogrammation(this.ids,this.idp);
    //this.btnCommentaire= this.authService.checkPermission(this.permissions, ['ROLE_ADMIN','ROLE_DIR_DGESS','ROLE_RESP_DGESS']);
  }

  loadprogrammation(ids:number,idp:number){
    this.amendementActiviteService.getProgrammationbyStructProgram(ids,idp).subscribe(data => {
      this.programmation=data;
     this.taches =this.programmation.taches as any;
     this.indicateurs= this.programmation.objectif?.indicateurs as any;
    }, error => this.handleError(error));
  }

  onReject(){
    this.clearDialogMessages();
    this.btnCommentaire=true;
  }

  rejetProgrammation(){
    this.clearDialogMessages();
    this.isDialogOpInProgress = true;
    this.amendementActiviteService.rejetProgrammation(this.commentaire).subscribe(response => {
      this.commentaire=response;
       this.isDialogOpInProgress = false;
      this.btnCommentaire=false;
      this.showMessage({ severity: 'success', summary: 'Programmation rejetée avec succès' });
      this.location.back();
    }, error => this.handleError(error));
  }

  //Retour à la liste
  onReturn() {
    this.location.back();
    //this.router.navigate(['/workspace/listeProgrammation']);
    // this.router.navigate(['/workspace/amendementActivite',this.ids,this.idp]);
  }



  onValider(selection:any){

    this.confirmationService.confirm({
      message: 'Etes-vous sur de vouloir valider cette activité?',
      accept: () => {
        this.valider(this.ids,this.idp);
        //this.router.navigate(['/workspace/programme-activites']);
      }
    });
  }

  valider(ids:number,idp:number){
    this.amendementActiviteService.validerProgrammation(ids,idp).subscribe(data => {
      this.programmation=data;
       this.showMessage({ severity: 'success', summary: 'programmation validée avec succès' });
       this.location.back();
      }, error => this.handleError(error));
  }


  // Errors
  handleError(error: HttpErrorResponse) {
    console.error(`Processing Error: ${JSON.stringify(error)}`);
    this.isDialogOpInProgress = false;
    this.dialogErrorMessage = error.error.title;
  }

  //Messages
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
