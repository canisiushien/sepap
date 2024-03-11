import { AuthenticationService } from 'src/app/services/parametrage/authentication.service';
import { IndicateurType } from './../../../../models/parametrage/indicateur-objectif';
import { ObjectifService } from 'src/app/services/parametrage/objectif.service';
import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Indicateur } from 'src/app/models/parametrage/indicateur-objectif';
import { Objectif } from 'src/app/models/parametrage/objectif';
import { environment } from 'src/environments/environment';
import { ConfirmationService, LazyLoadEvent, Message } from 'primeng/api';

import { HttpErrorResponse } from '@angular/common/http';
import { IndicateurImpactService } from 'src/app/services/parametrage/indicateur-impact.service';

@Component({
  selector: 'app-indicateur-objectif',
  templateUrl: './indicateur-objectif.component.html',
  styleUrls: ['./indicateur-objectif.component.scss']
})
export class IndicateurObjectifComponent implements OnInit {


  @ViewChild('dtf') form!: NgForm;

  timeoutHandle: any;
  totalRecords!: number;
  recordsPerPage = environment.recordsPerPage;
  indicateurs!: Indicateur[];
  indicateur: Indicateur = {};

  objectif: Objectif={};
  objectifs!: Objectif[];
  enableCreate = true;
  enableBtnInfo = true;
  enableBtnEdit = false;
  enableBtnDelete = false;
  isLoading!: boolean;
  isOpInProgress!: boolean;
  isDialogOpInProgress!: boolean;
  showDialog = false;
  message: any;
  dialogErrorMessage: any;
  indicateurTypes: IndicateurType[]=[];
  permissions: any;
  indicateurDetail:boolean=false;
  constructor( private indicateurImpactService:IndicateurImpactService,private objectifService: ObjectifService,
    private confirmationService: ConfirmationService, public authService: AuthenticationService) { }

  ngOnInit(): void {
    this.load();
    this.indicateurTypes=[
      {"id":1, "libelle": "IMPACT"},
      {"id":2, "libelle": "EFFET"},
    ]

    this.permissions=this.authService.getPrivilege();
    this.enableBtnEdit=this.authService.checkPermission(this.permissions, ['ROLE_ADMIN','ROLE_DIR_DGESS','ROLE_RESP_DGESS']);
    this.enableBtnInfo =this.authService.checkPermission(this.permissions, ['ROLE_ADMIN','ROLE_DIR_DGESS','ROLE_RESP_DGESS']);
  }

  load(event?: LazyLoadEvent) {
    this.isLoading = true;
     this.indicateurImpactService.getAll(event).subscribe(response => {
      this.isLoading = false;
      this.indicateurs = response.indicateurs;

    }, error => {
      this.message = { severity: 'error', summary: error.error };
      console.error(JSON.stringify(error));
    });
  }
  loadObjectif(event?: LazyLoadEvent) {
    this.isLoading = true;
   this.objectifService.getAll(event).subscribe(response => {
     this.isLoading = false;
     this.objectifs = response.objectifs;

   }, error => {
     this.message = { severity: 'error', summary: error.error };
     console.error(JSON.stringify(error));
   });
 }


 getTypeObjectif(event:any){
    let type = event.value;
   if( type == "EFFET"){
     this.objectifService.getObjectifStrategique().subscribe(response => {
      this.objectifs = response.objectifs;
    })
  }
  if( type == "IMPACT"){
    this.objectifService.getObjectifOperationnel().subscribe(response => {
     this.objectifs = response.objectifs;
   })
 }

}

  //Détail
  onInfo(selection:any){
    this.indicateur = Object.assign({}, selection);
    this.indicateurDetail=true;
  }

  save() {
    if (this.indicateur.id) {
      this.edit();
    } else {
      this.create();
    }
  }

   //Creation

   onCreate() {
    this.indicateur = {};
    this.clearDialogMessages();
    this.form.resetForm();
    this.showDialog = true;
  }

  create() {
    this.clearDialogMessages();
    this.isDialogOpInProgress = true;
    this.indicateurImpactService.create(this.indicateur).subscribe(response => {
      if (this.indicateurs.length !== this.recordsPerPage) {

        this.indicateurs.push(response);
        this.indicateurs = this.indicateurs.slice();

      }
      this.totalRecords++;
      this.isDialogOpInProgress = false;
      this.showDialog = false;
      this.showMessage({ severity: 'success', summary: 'Indicateur créé avec succès' });
    }, error => this.handleError(error));
  }


   // Edit

   onEdit(selection:any) {
    this.indicateur = Object.assign({}, selection);
    this.clearDialogMessages();
    this.showDialog = true;
  }

  edit() {
    this.clearDialogMessages();
    this.isDialogOpInProgress = true;
    this.indicateurImpactService.update(this.indicateur).subscribe(response => {
      let index = this.indicateurs.findIndex(indicateur => indicateur.id === response.id);
      this.indicateurs[index] = response;
      this.isDialogOpInProgress = false;
      this.showDialog = false;
      this.showMessage({ severity: 'success', summary: 'Indicateur modifié avec succès' });
    }, error => this.handleError(error));
  }

  isEditing() {
    return !!this.indicateur.id;
  }

  // Deletion

  onDelete(selection:any) {
    this.confirmationService.confirm({
      message: 'Etes-vous sûre de vouloir supprimer cet indicateur?',
      accept: () => {
        this.delete(selection);
      }
    });
  }

  delete(selection:any) {
    this.isOpInProgress = true;
    this.indicateurImpactService.delete(selection.id).subscribe(() => {
      this.indicateurs = this.indicateurs.filter(indicateur => indicateur.id !== selection.id);
      selection = null;
      this.isOpInProgress = false;
      this.totalRecords--;
      this.showMessage({ severity: 'success', summary: 'Indicateur supprimé avec succès' });
    }, error => {
      console.error(JSON.stringify(error));
      this.isOpInProgress = false;
      this.showMessage({ severity: 'error', summary: error.error.message });
    });
  }

  // Errors

  handleError(error: HttpErrorResponse) {
    console.error(`Processing Error: ${JSON.stringify(error)}`);
    this.isDialogOpInProgress = false;
    this.dialogErrorMessage = error.error.message;
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
