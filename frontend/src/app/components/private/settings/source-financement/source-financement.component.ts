import { AuthenticationService } from 'src/app/services/parametrage/authentication.service';
import { HttpErrorResponse } from '@angular/common/http';
import { NgForm } from '@angular/forms';
import { Component, OnInit, ViewChild } from '@angular/core';
import { LazyLoadEvent, Message, ConfirmationService } from 'primeng/api';
import { SourceFinancement } from 'src/app/models/parametrage/source-financement.model';
import { SourceFinancementService } from 'src/app/services/parametrage/source-financement.service';
import { environment } from 'src/environments/environment';
@Component({
  selector: 'appsource-financ-ement',
  templateUrl: './source-financement.component.html',
  styleUrls: ['./source-financement.component.scss']
})
export class SourceFinancementComponent implements OnInit {


  @ViewChild('dtf') form!: NgForm;

  timeoutHandle: any;
  totalRecords!: number;
  recordsPerPage = environment.recordsPerPage;
  sources!: SourceFinancement[];
  // selection: any;
  source: SourceFinancement = {};
  enableCreate = true;
  enableBtnInfo = true;
  enableBtnEdit = true;
  enableBtnDelete = false;
  isLoading!: boolean;
  isOpInProgress!: boolean;
  isDialogOpInProgress!: boolean;
  showDialog = false;
  message: any;
  sourceDetail: boolean=false;
  dialogErrorMessage: any;
  debutS : string = "";
  finS : string = "";
  permissions: any;
  constructor(
    private sourceFinancementServices: SourceFinancementService,
    private confirmationservice: ConfirmationService,
    public authService: AuthenticationService

    ) { }

  ngOnInit(): void {

    this.load();
    this.permissions=this.authService.getPrivilege();
    this.enableBtnEdit=this.authService.checkPermission(this.permissions, ['ROLE_ADMIN','ROLE_DAF','RESP_DGESS']);
    // this.enableBtnDelete=this.authService.checkPermission(this.permissions, ['ROLE_ADMIN','ROLE_DAF','RESP_DGESS']);
  }


  load(event?: LazyLoadEvent) {
     this.isLoading = true;
     this.sourceFinancementServices.getAll(event).subscribe(response => {
     this.sources = response.sources;
     this.isLoading=false;
   }, error => {
     this.message = { severity: 'error', summary: error.error };
     console.error(JSON.stringify(error));
   });
 }



 onInfo(selection: any) {
  this.source = Object.assign({}, selection);
  this.clearDialogMessages();
  this.sourceDetail = true;
}
onEdit(selection: SourceFinancement) {
  this.debutS = ((selection.debut!).toString()).substring(8,10)+"-"+((selection.debut!).toString()).substring(5,7)+"-"+((selection.debut!).toString()).substring(0,4);
  this.finS = ((selection.fin!).toString()).substring(8,10)+"-"+((selection.fin!).toString()).substring(5,7)+"-"+((selection.fin!).toString()).substring(0,4);
  this.source = Object.assign({}, selection);
  this.clearDialogMessages();
  this.showDialog = true;
}
onDelete(selection: any) {
  this.confirmationservice.confirm({
    message: 'Etes-vous sûre de vouloir supprimer cette source de financement?',
    accept: () => {
      this.delete(selection);
    },
  });
}

onCreate() {
  this.source = {};
  this.clearDialogMessages();
  this.form.resetForm();
  this.showDialog = true;
}
isEditing() {
  return !!this.source.id;
}
save() {
  if (this.source.id) {
    this.edit();
  } else {
    this.create();
  }
}

create() {
    this.source.debut = new Date(this.debutS);
    this.source.fin = new Date(this.finS);
    const data={
    id:this.source.id,
    libelle:this.source.libelle,
    montant:this.source.montant,
    debut:this.source.debut,
    fin:this.source.fin,

  }
  this.clearDialogMessages();
  this.isDialogOpInProgress = true;
  this.sourceFinancementServices.create(data).subscribe(
    (response) => {
      if (this.sources.length !== this.recordsPerPage) {
        this.sources.push(response);
        this.sources = this.sources.slice();
      }
      this.totalRecords++;
      this.isDialogOpInProgress = false;
      this.showDialog = false;
      this.showMessage({
        severity: 'success',
        summary: 'Source de financement créée avec succès',
      });
    },
    (error) => this.handleError(error)
  );
}

edit() {
  this.clearDialogMessages();
  this.isDialogOpInProgress = true;
  if(this.debutS.length == 10){
    let dateParts = this.debutS.split("-");
    this.debutS = new Date(dateParts[2]+"-"+dateParts[1]+"-"+dateParts[0]).toISOString();
  }

  if(this.debutS.length != 10){
    this.debutS = new Date(this.debutS).toISOString();
  }

  if(this.finS.length == 10){
    let dateParts = this.finS.split("-");
    this.finS = new Date(dateParts[2]+"-"+dateParts[1]+"-"+dateParts[0]).toISOString();
  }

  if(this.finS.length != 10){
    this.finS = new Date(this.finS).toISOString();
  }
    this.source.debut = new Date(this.debutS);
    this.source.fin = new Date(this.finS);
    this.sourceFinancementServices.update(this.source).subscribe(
    (response) => {
      let index = this.sources.findIndex(
        (source) => source.id === response.id
      );
      this.sources[index] = response;
      this.isDialogOpInProgress = false;
      this.showDialog = false;
      this.showMessage({
        severity: 'success',
        summary: 'Source de financement modifiée avec succès',
      });
    },
    (error) => this.handleError(error)
  );
}


delete(selection: any) {
  this.isOpInProgress = true;
  this.sourceFinancementServices.delete(selection.id).subscribe(
    () => {
      this.sources = this.sources.filter(
        (source) => source.id !== selection.id
      );
      selection = null;
      this.isOpInProgress = false;
      this.totalRecords--;
      this.showMessage({
        severity: 'success',
        summary: 'source de financement supprimée avec succès',
      });
    },
    (error) => {
      console.error(JSON.stringify(error));
      this.isOpInProgress = false;
      this.showMessage({ severity: 'error', summary: error.error.message });
    }
  );
}
clearDialogMessages() {
  this.dialogErrorMessage = null;
}

showMessage(message: Message) {
  this.message = message;
  this.timeoutHandle = setTimeout(() => {
    this.message = null;
  }, 5000);
}

// Errors

handleError(error: HttpErrorResponse) {
  console.error(`Processing Error: ${JSON.stringify(error)}`);
  this.isDialogOpInProgress = false;
  this.dialogErrorMessage = error.error.message;
}
}
