import { AuthenticationService } from 'src/app/services/parametrage/authentication.service';
import { ConfirmationService, LazyLoadEvent, Message } from 'primeng/api';
import { MinistereService } from './../../../../services/parametrage/ministere.service';
import { Component, OnInit, ViewChild } from '@angular/core';
import { Ministere } from 'src/app/models/parametrage/ministere';
import { environment } from 'src/environments/environment';
import { NgForm } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-ministere',
  templateUrl: './ministere.component.html',
  styleUrls: ['./ministere.component.scss'],
})
export class MinistereComponent implements OnInit {

  @ViewChild('dtf') form!: NgForm;

  timeoutHandle: any;
  totalRecords!: number;
  recordsPerPage = environment.recordsPerPage;
  ministeres!: Ministere[];
  ministere: Ministere = {};

  enableCreate = true;
  enableBtnInfo = true;
  enableBtnEdit = false;
  enableBtnDelete = false;
  isLoading!: boolean;
  isOpInProgress!: boolean;
  isDialogOpInProgress!: boolean;
  showDialog = false;
  ministereDetail:boolean=false;

  message: any;
  dialogErrorMessage: any;
  permissions: any;

  constructor(
    private ministereService: MinistereService,
    private confirmationservice: ConfirmationService,
    public authService: AuthenticationService
  ) {}

  ngOnInit(): void {
    this.load();
    this.permissions=this.authService.getPrivilege();
    this.enableBtnEdit=this.authService.checkPermission(this.permissions, ['ROLE_ADMIN', 'ROLE_RESP_DGESS']);
  //  this.enableBtnDelete= this.authService.checkPermission(this.permissions, ['ROLE_ADMIN', 'ROLE_RESP_DGESS']);
  }

  load(event?: LazyLoadEvent) {
    this.isLoading = true;
    this.ministereService.getAll(event).subscribe(
      (response) => {
        this.isLoading = false;
        this.ministeres = response.ministeres;
      },
      (error) => {
        this.message = { severity: 'error', summary: error.error };
      }
    );
  }

  onInfo(selection: any) {
    this.ministere = Object.assign({}, selection);
    this.clearDialogMessages();
    this.ministereDetail = true;
  }

  onEdit(selection: any) {
    this.ministere = Object.assign({}, selection);
    this.clearDialogMessages();
    this.showDialog = true;
  }

  onDelete(selection: any) {
    this.confirmationservice.confirm({
      message: 'Etes-vous sur de vouloir supprimer ce ministere?',
      accept: () => {
        this.delete(selection);
      },
    });
  }

  onCreate() {
    this.ministere = {};
    this.clearDialogMessages();
    this.form.resetForm();
    this.showDialog = true;
  }

  isEditing() {
    return !!this.ministere.id;
  }

  save() {
    if (this.ministere.id) {
      this.edit();
    } else {
      this.create();
    }
  }

  create() {
    this.clearDialogMessages();
    this.isDialogOpInProgress = true;
    this.ministereService.create(this.ministere).subscribe(
      (response) => {
        if (this.ministeres.length !== this.recordsPerPage) {
          this.ministeres.push(response);
          this.ministeres = this.ministeres.slice();
        }
        this.totalRecords++;
        this.isDialogOpInProgress = false;
        this.showDialog = false;
        this.showMessage({
          severity: 'success',
          summary: 'Ministere créé avec succes',
        });
      },
      (error) => this.handleError(error)
    );
  }

  edit() {
    this.clearDialogMessages();
    this.isDialogOpInProgress = true;
    this.ministereService.update(this.ministere).subscribe(
      (response) => {
        let index = this.ministeres.findIndex(
          (ministere) => ministere.id === response.id
        );
        this.ministeres[index] = response;
        this.isDialogOpInProgress = false;
        this.showDialog = false;
        this.showMessage({
          severity: 'success',
          summary: 'Ministère modifié avec succès',
        });
      },
      (error) => this.handleError(error)
    );
  }

  delete(selection: any) {
    this.isOpInProgress = true;
    this.ministereService.delete(selection.id).subscribe(
      () => {
        this.ministeres = this.ministeres.filter(
          (ministere) => ministere.id !== selection.id
        );
        selection = null;
        this.isOpInProgress = false;
        this.totalRecords--;
        this.showMessage({
          severity: 'success',
          summary: 'ministere supprimée avec succès',
        });
      },
      (error) => {
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
    this.isDialogOpInProgress = false;
    this.dialogErrorMessage = error.error.message;
  }
}
