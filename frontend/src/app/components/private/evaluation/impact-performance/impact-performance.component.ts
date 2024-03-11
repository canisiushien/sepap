import { Ministere } from 'src/app/models/parametrage/ministere';
import { MinistereService } from 'src/app/services/parametrage/ministere.service';
import { AuthenticationService } from 'src/app/services/parametrage/authentication.service';
import { ImpactService } from './../../../../services/performance/impact.service';
import { Impact } from 'src/app/models/performance/impact';
import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { environment } from 'src/environments/environment';
import { LazyLoadEvent, Message } from 'primeng/api';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-impact-performance',
  templateUrl: './impact-performance.component.html',
  styleUrls: ['./impact-performance.component.scss']
})
export class ImpactPerformanceComponent implements OnInit {



  @ViewChild('dtf') form!: NgForm;

  timeoutHandle: any;
  totalRecords!: number;
  recordsPerPage = environment.recordsPerPage;
  impacts!: Impact[]
  impact: Impact = {};
  // selection: any;
  ministeres: Ministere[]=[];
  ministere: Ministere={};
  enableCreate = true;
  enableBtnInfo = false;
  enableBtnEdit = true;
  enableBtnDelete = false;
  isLoading!: boolean;
  isOpInProgress!: boolean;
  isDialogOpInProgress!: boolean;
  showDialog = false;
  message: any;
  dialogErrorMessage: any;
  impactDetail=false;
  permissions:any
  constructor(private impactService: ImpactService,
    public authService: AuthenticationService,
    private ministereService:MinistereService) { }

  ngOnInit(): void {
    this.permissions=this.authService.getPrivilege();
    this.getMinistere();
  }
  load(id:number) {
     this.isLoading = true;
     this.impactService.getImpactByStructure(id).subscribe(response => {
     this.isLoading = false;
     this.impacts = response.impactStructures;

   }, error => {
     this.message = { severity: 'error', summary: error.error };
     console.error(JSON.stringify(error));
   });
 }

 getMinistere(event?: LazyLoadEvent) {
  this.isLoading = true;
  this.ministereService.getAll(event).subscribe(
    (response) => {
      this.isLoading = false;
      this.ministeres = response.ministeres;
    },
    (error) => {
      this.message = { severity: 'error', summary: error.error };
      console.error(JSON.stringify(error));
    }
  );
}

changeMinistere(selection:any){
  let id =  selection.value.id;
  this.load(id);

}
isEditing() {
  return !!this.impact.id;
}

save() {
  if (this.impact.id) {
    this.edit();
  } else {
    this.create();
  }
}

create() {
  const data=
  this.clearDialogMessages();
  this.isDialogOpInProgress = true;
  this.impactService.create(this.impact).subscribe(response => {

    this.isDialogOpInProgress = false;
    this.showDialog = false;
    this.showMessage({ severity: 'success', summary: 'Informations d\'impact ajoutées avec succès' });
  }, error => this.handleError(error));
}

edit() {
  this.clearDialogMessages();
  this.isDialogOpInProgress = true;
  this.impactService.update(this.impact).subscribe(response => {
    let index = this.impacts.findIndex(impact => impact.id === response.id);
    this.impacts[index] = response;
    this.isDialogOpInProgress = false;
    this.showDialog = false;
    this.showMessage({ severity: 'success', summary: 'Données d\'impact modifiée avec succès' });
  }, error => this.handleError(error));
}

 onCreate() {

  this.impact = {};
  this.clearDialogMessages();
  this.form.resetForm();
  this.showDialog = true;
 }

  onInfo(selection:any){
    this.impact = Object.assign({}, selection);
    this.clearDialogMessages();
    this.impactDetail=true;
  }

  onEdit(selection:any) {
   this.impact = Object.assign({}, selection);
   this.clearDialogMessages();
   this.showDialog = true;
 }
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
