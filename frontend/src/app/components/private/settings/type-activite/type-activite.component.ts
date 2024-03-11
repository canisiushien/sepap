import { AuthenticationService } from 'src/app/services/parametrage/authentication.service';
import { TypeActiviteService } from './../../../../services/parametrage/type-activite.service';
import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { environment } from 'src/environments/environment';
import { ConfirmationService, LazyLoadEvent, Message } from 'primeng/api';
import { TypeActivite } from 'src/app/models/parametrage/typeActivite';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-type-activite',
  templateUrl: './type-activite.component.html',
  styleUrls: ['./type-activite.component.scss']
})
export class TypeActiviteComponent implements OnInit {
  @ViewChild('dtf') form!: NgForm;

  timeoutHandle: any;
  totalRecords!: number;
  recordsPerPage = environment.recordsPerPage;
  typeActivites!: TypeActivite[]
  // selection: any;
  typeActivite: TypeActivite = {};
  enableCreate = true;
  enableBtnInfo = false;
  enableBtnEdit = false;
  enableBtnDelete = false;
  isLoading!: boolean;
  isOpInProgress!: boolean;
  isDialogOpInProgress!: boolean;
  showDialog = false;
  message: any;
  dialogErrorMessage: any;
  permissions:any;

  constructor(private typeActiviteService:TypeActiviteService,
    private confirmationService: ConfirmationService,
    public authService: AuthenticationService) { }

  ngOnInit(): void {

    this.load();
    this.permissions=this.authService.getPrivilege();
    this.enableBtnEdit=this.authService.checkPermission(this.permissions, ['ROLE_ADMIN']);
    //this.enableBtnDelete=this.authService.checkPermission(this.permissions, ['ROLE_ADMIN']);
  }

  load(event?: LazyLoadEvent) {
     this.isLoading = true;
    this.typeActiviteService.getAll(event).subscribe(response => {
      this.isLoading = false;
      this.typeActivites = response.typeActivites;
  }, error => {
      this.message = { severity: 'error', summary: error.error };
      console.error(JSON.stringify(error));
    });
  }

  save() {
    if (this.typeActivite.id) {
      this.edit();
    } else {
      this.create();
    }
  }

   //Creation

   onCreate() {
    this.typeActivite = {};
    this.clearDialogMessages();
    this.form.resetForm();
    this.showDialog = true;
  }

  create() {
    this.clearDialogMessages();
    this.isDialogOpInProgress = true;
    this.typeActiviteService.create(this.typeActivite).subscribe(response => {
      if (this.typeActivites.length !== this.recordsPerPage) {
        this.typeActivites.push(response);
        this.typeActivites = this.typeActivites.slice();
      }
      this.totalRecords++;
      this.isDialogOpInProgress = false;
      this.showDialog = false;
      this.showMessage({ severity: 'success', summary: 'Type activité créé avec succès' });
    }, error => this.handleError(error));
  }


   // Edit

   onEdit(selection:any) {
     console.log(selection);
    this.typeActivite = Object.assign({}, selection);
    this.clearDialogMessages();
    this.showDialog = true;
  }

  edit() {
    this.clearDialogMessages();
    this.isDialogOpInProgress = true;
    this.typeActiviteService.update(this.typeActivite).subscribe(response => {
      let index = this.typeActivites.findIndex(typeActivite => typeActivite.id === response.id);
      this.typeActivites[index] = response;
      this.isDialogOpInProgress = false;
      this.showDialog = false;
      this.showMessage({ severity: 'success', summary: 'Type activité modifié avec succès' });
    }, error => this.handleError(error));
  }

  isEditing() {
    return !!this.typeActivite.id;
  }

  // Deletion

  onDelete(selection:any) {
    this.confirmationService.confirm({
      message: 'Etes-vous sur de vouloir supprimer cet type d\'activité?',
      accept: () => {
        this.delete(selection);
      }
    });
  }

  delete(selection:any) {
    console.log(selection.id);
    this.isOpInProgress = true;
    this.typeActiviteService.delete(selection.id).subscribe(() => {
      this.typeActivites = this.typeActivites.filter(typeActivite => typeActivite.id !== selection.id);
      selection = null;
      this.isOpInProgress = false;
      this.totalRecords--;
      this.showMessage({ severity: 'success', summary: 'type d\'activité supprimé avec succès' });
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
