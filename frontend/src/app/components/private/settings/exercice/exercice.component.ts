import { AuthenticationService } from 'src/app/services/parametrage/authentication.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ConfirmationService, LazyLoadEvent, MenuItem, Message } from 'primeng/api';
import { Exercice, statuts } from 'src/app/models/parametrage/exercice';
import { ExerciceService } from 'src/app/services/parametrage/exercice.service';

import { environment } from 'src/environments/environment';


@Component({
  selector: 'app-exercice',
  templateUrl: './exercice.component.html',
  styleUrls: ['./exercice.component.scss']
})
export class ExerciceComponent implements OnInit {

  @ViewChild('dtf') form!: NgForm;

  timeoutHandle: any;
  totalRecords!: number;
  recordsPerPage = environment.recordsPerPage;
  exercices!: Exercice[]
  // selection: any;
  exercice: Exercice = {};
  enableCreate = true;
  enableBtnInfo = false;
  enableBtnEdit = true;
  enableBtnDelete = false;
  enableBtnClose = true;
  isLoading!: boolean;
  isOpInProgress!: boolean;
  isDialogOpInProgress!: boolean;
  showDialog = false;
  message: any;
  dialogErrorMessage: any;
  permissions:any;
  // contextMenuItems!: MenuItem[];

  constructor(private exerciceService:ExerciceService,
    private confirmationService: ConfirmationService,
    public authService:AuthenticationService) { }

  ngOnInit(): void {
    this.load();
    this.permissions=this.authService.getPrivilege();
    this.enableBtnEdit=this.authService.checkPermission(this.permissions, ['ROLE_ADMIN']);
    this.enableBtnClose  =this.authService.checkPermission(this.permissions, ['ROLE_ADMIN']);
  }

  load(event?: LazyLoadEvent) {
     this.isLoading = true;
    this.exerciceService.getAll(event).subscribe(response => {
      this.isLoading = false;
      this.exercices = response.exercices;

    }, error => {
      this.message = { severity: 'error', summary: error.error };
      console.error(JSON.stringify(error));
    });
  }

  onClose(event?: LazyLoadEvent){
    this.confirmationService.confirm({
      message: ' Cette action est irreversible, êtes-vous sûre de vouloir clôturer cet exercice?',
      accept: () => {
        this.exerciceService.cloturer(event).subscribe(response => {
        this.exercices = response.exercices;
        this.load();
        });
  }
    })
  }

  save() {
    if (this.exercice.id) {
      this.edit();
    } else {
      this.create();
    }
  }

   //Creation

   onCreate() {
    this.exercice = {};
    this.clearDialogMessages();
    this.form.resetForm();
    this.showDialog = true;
  }

  create() {
    const data={
      id:this.exercice.id,
      description:this.exercice.description,
      statut:statuts.EN_ATTENTE,
      debut:this.exercice.debut,
      fin:this.exercice.fin,

    }
    this.clearDialogMessages();
    this.isDialogOpInProgress = true;
    this.exerciceService.create(this.exercice).subscribe(response => {
      if (this.exercices.length !== this.recordsPerPage) {
        this.exercices.push(response);
        this.exercices = this.exercices.slice();
      }
      this.totalRecords++;
      this.isDialogOpInProgress = false;
      this.showDialog = false;
      this.showMessage({ severity: 'success', summary: 'Exercice créé avec succès' });
    }, error => this.handleError(error));
  }


   // Edit

   onEdit(selection:any) {
    this.exercice = Object.assign({}, selection);
    this.clearDialogMessages();
    this.showDialog = true;
  }

  edit() {
    this.clearDialogMessages();
    this.isDialogOpInProgress = true;
    this.exerciceService.update(this.exercice).subscribe(response => {
      let index = this.exercices.findIndex(exercice => exercice.id === response.id);
      this.exercices[index] = response;
      this.isDialogOpInProgress = false;
      this.showDialog = false;
      this.showMessage({ severity: 'success', summary: 'Exercice modifié avec succès' });
    }, error => this.handleError(error));
  }

  isEditing() {
    return !!this.exercice.id;
  }

  // Deletion

  onDelete(selection:any) {
    this.confirmationService.confirm({
      message: 'Etes-vous sûre de vouloir supprimer cet exercice?',
      accept: () => {
        this.delete(selection);
      }
    });
  }

  delete(selection:any) {
    this.isOpInProgress = true;
    this.exerciceService.delete(selection.id).subscribe(() => {
      this.exercices = this.exercices.filter(exercice => exercice.id !== selection.id);
      selection = null;
      this.isOpInProgress = false;
      this.totalRecords--;
      this.showMessage({ severity: 'success', summary: 'Exercice supprimé avec succès' });
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
