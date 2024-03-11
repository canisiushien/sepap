import { Privilege } from 'src/app/models/parametrage/privilege';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ConfirmationService, LazyLoadEvent, MenuItem, Message } from 'primeng/api';

import { environment } from 'src/environments/environment';
import { PrivilegeService } from 'src/app/services/parametrage/privilege.service';


@Component({
  selector: 'app-privilege',
  templateUrl: './privilege.component.html',
  styleUrls: ['./privilege.component.scss']
})
export class PrivilegeComponent implements OnInit {

  @ViewChild('dtf') form!: NgForm;

  timeoutHandle: any;
  totalRecords!: number;
  recordsPerPage = environment.recordsPerPage;
  privileges!: Privilege[]
  // selection: any;
  privilege: Privilege = {};
  enableCreate = true;
  enableBtnInfo = true;
  enableBtnEdit = true;
  enableBtnDelete = true;
  isLoading!: boolean;
  isOpInProgress!: boolean;
  isDialogOpInProgress!: boolean;
  showDialog = false;
  message: any;
  dialogErrorMessage: any;
  // contextMenuItems!: MenuItem[];

  constructor(private privilegeService: PrivilegeService,
    private confirmationService: ConfirmationService) { }

  ngOnInit(): void {
    // this.contextMenuItems = [
    //   { label: 'Editer', icon: 'pi pi-fw pi-pencil', command: () => this.onEdit() },
    //   { label: 'Supprimer', icon: 'pi pi-fw pi-trash', command: () => this.onDelete() }
    // ];
    this.load();
  }

  load(event?: LazyLoadEvent) {
    this.isLoading = true;
    this.privilegeService.getAll(event).subscribe(response => {
      this.isLoading = false;
      this.privileges = response.privileges;

    }, error => {
      this.message = { severity: 'error', summary: error.error };
      console.error(JSON.stringify(error));
    });
  }
  //Détail
  onInfo(selection: any) {
    console.log(selection);
  }

  save() {
    if (this.privilege.id) {
      this.edit();
    } else {
      this.create();
    }
  }

  //Creation

  onCreate() {
    this.privilege = {};
    this.clearDialogMessages();
    this.form.resetForm();
    this.showDialog = true;
  }

  create() {
    this.clearDialogMessages();
    this.isDialogOpInProgress = true;
    this.privilegeService.create(this.privilege).subscribe(response => {
      if (this.privileges.length !== this.recordsPerPage) {
        this.privileges.push(response);
        this.privileges = this.privileges.slice();
      }
      this.totalRecords++;
      this.isDialogOpInProgress = false;
      this.showDialog = false;
      this.showMessage({ severity: 'success', summary: 'Privilège créée avec succès' });
    }, error => this.handleError(error));
  }


  // Edit

  onEdit(selection: any) {

    this.privilege = Object.assign({}, selection);
    this.clearDialogMessages();
    this.showDialog = true;
  }

  edit() {
    this.clearDialogMessages();
    this.isDialogOpInProgress = true;
    this.privilegeService.update(this.privilege).subscribe(response => {
      let index = this.privileges.findIndex(privilege => privilege.id === response.id);
      this.privileges[index] = response;
      this.isDialogOpInProgress = false;
      this.showDialog = false;
      this.showMessage({ severity: 'success', summary: 'Privilège modifiée avec succès' });
    }, error => this.handleError(error));
  }

  isEditing() {
    return !!this.privilege.id;
  }

  // Deletion

  onDelete(selection: any) {
    this.confirmationService.confirm({
      message: 'Etes-vous sur de vouloir supprimer cet privilege?',
      accept: () => {
        this.delete(selection);
      }
    });
  }

  delete(selection: any) {
    this.isOpInProgress = true;
    this.privilegeService.delete(selection.id).subscribe(() => {
      this.privileges = this.privileges.filter(privilege => privilege.id !== selection.id);
      selection = null;
      this.isOpInProgress = false;
      this.totalRecords--;
      this.showMessage({ severity: 'success', summary: 'Privilège supprimée avec succès' });
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
