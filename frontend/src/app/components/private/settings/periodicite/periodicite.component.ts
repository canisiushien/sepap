import { AuthenticationService } from 'src/app/services/parametrage/authentication.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ConfirmationService, LazyLoadEvent, MenuItem, Message } from 'primeng/api';
import { Periodicite } from 'src/app/models/parametrage/periodicite';
import { PeriodiciteService } from 'src/app/services/parametrage/periodicite.service';

import { environment } from 'src/environments/environment';


@Component({
  selector: 'app-periodicite',
  templateUrl: './periodicite.component.html',
  styleUrls: ['./periodicite.component.scss']
})
export class PeriodiciteComponent implements OnInit {

  @ViewChild('dtf') form!: NgForm;

  timeoutHandle: any;
  totalRecords!: number;
  recordsPerPage = environment.recordsPerPage;
  periodicites: Periodicite[] =[];
  // selection: any;
  periodicite: Periodicite = {};
  enableCreate  =  true;
  enableBtnInfo = false;
  enableBtnEdit = false;
  enableBtnDelete = false;
  isLoading!: boolean;
  isOpInProgress!: boolean;
  isDialogOpInProgress!: boolean;
  showDialog = false;
  message: any;
  dialogErrorMessage: any;
  checkSelected = true;
  permissions:any;



  constructor(private periodiciteService:PeriodiciteService,
    private confirmationService: ConfirmationService,
    public authService: AuthenticationService) { }

  ngOnInit(): void {
    this.load();
    this.permissions=this.authService.getPrivilege();
    //this.enableBtnEdit=this.authService.checkPermission(this.permissions, ['ROLE_ADMIN','ROLE_DIR_DGESS','ROLE_RESP_DGESS']);
    //this.enableBtnDelete=this.authService.checkPermission(this.permissions, ['ROLE_ADMIN','ROLE_DIR_DGESS','ROLE_RESP_DGESS']);
  }
  handleChange(e:any) {
    this.periodicite.actif = e.checked;;
}
  load(event?: LazyLoadEvent) {
     this.isLoading = true;
    this.periodiciteService.getAll(event).subscribe(response => {
      this.isLoading = false;
      this.periodicites = response.periodicites;
      //this.totalRecords = response.totalCount;
      // console.log( this.exemples)

    }, error => {
      this.message = { severity: 'error', summary: error.error };
      console.error(JSON.stringify(error));
    });
  }
  //Détail
  onInfo(selection:any){
    this.periodicite = Object.assign({}, selection);
    this.clearDialogMessages();
    this.showDialog = true;
  }

  save() {
    if (this.periodicite.id) {
      this.edit();
    } else {
      this.create();
    }
  }


   //Creation

   onCreate() {
    this.periodicite = {};
    this.clearDialogMessages();
    this.form.resetForm();
    this.showDialog = true;
  }

  create() {
    this.clearDialogMessages();
    this.isDialogOpInProgress = true;
    this.periodicite.actif = true;
    this.periodiciteService.create(this.periodicite).subscribe(response => {
      if (this.periodicites.length !== this.recordsPerPage) {
        this.periodicites.push(response);
        this.periodicites = this.periodicites.slice();
      }
  //    this.totalRecords++;
      this.isDialogOpInProgress = false;
      this.showDialog = false;
      this.showMessage({ severity: 'success', summary: 'Périodicité créée avec succès' });
      this.load();
    }, error => this.handleError(error));
  }


   // Edit

   onEdit(selection:any) {
    this.periodicite = Object.assign({}, selection);
    this.clearDialogMessages();
    this.showDialog = true;
  }

  edit() {
    this.clearDialogMessages();
    this.isDialogOpInProgress = true;
    this.periodiciteService.update(this.periodicite).subscribe(response => {
      let index = this.periodicites.findIndex(periodicite => periodicite.id === response.id);
      this.periodicites[index] = response;
      this.isDialogOpInProgress = false;
      this.showDialog = false;
      this.showMessage({ severity: 'success', summary: 'Périodicité modifiée avec succès' });
    }, error => this.handleError(error));
  }

  isEditing() {
    return !!this.periodicite.id;
  }

  // Deletion

  onDelete(selection:any) {
    this.confirmationService.confirm({
      message: 'Etes-vous sûre de vouloir supprimer cette périodicité?',
      accept: () => {
        this.delete(selection);
      }
    });
  }

  delete(selection:any) {
    this.isOpInProgress = true;
    this.periodiciteService.delete(selection.id).subscribe(() => {
      this.periodicites = this.periodicites.filter(periodicite => periodicite.id !== selection.id);
      selection = null;
      this.isOpInProgress = false;
      this.totalRecords--;
      this.showMessage({ severity: 'success', summary: 'Exemple supprimée avec succès' });
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
