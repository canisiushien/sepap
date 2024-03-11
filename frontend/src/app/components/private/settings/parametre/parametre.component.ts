import { Component, OnInit, ViewChild } from '@angular/core';
import { ConfirmationService, LazyLoadEvent, Message } from 'primeng/api';
import { ParametreService } from './../../../../services/parametrage/parametre.service';
import { Parametre } from 'src/app/models/parametrage/parametre';
import { environment } from 'src/environments/environment';
import { NgForm } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-parametre',
  templateUrl: './parametre.component.html',
  styleUrls: ['./parametre.component.scss']
})
export class ParametreComponent implements OnInit {

  @ViewChild('dtf') form!: NgForm;

  timeoutHandle: any;
  totalRecords!: number;
  recordsPerPage = environment.recordsPerPage;
  parametres!: Parametre[];
  parametre: Parametre = {};
  enableCreate = true;
  enableBtnInfo = false;
  enableBtnEdit = true;
  enableBtnDelete = true;
  isLoading!: boolean;
  isOpInProgress!: boolean;
  isDialogOpInProgress!: boolean;
  showDialog = false;
  message: any;
  dialogErrorMessage: any;
  checked1!:boolean;
  checkSelected = true;
  debut : string = "";
  fin : string = "";


  constructor(
    private parametreService: ParametreService,
    private confirmationservice: ConfirmationService
  ) {

  }

  ngOnInit(): void {
    this.load();
  }

  handleChange(e:any) {
    this.parametre.verrouille = e.checked;;
}

  load(event?: LazyLoadEvent) {
    this.isLoading = true;
    this.parametreService.getAll(event).subscribe(
      (response) => {
        this.isLoading = false;
        this.parametres = response.parametres;
      },
      (error) => {
        this.message = { severity: 'error', summary: error.error };
        console.error(JSON.stringify(error));
      }
    );
  }

  onInfo(selection: any) {
    this.parametre = Object.assign({}, selection);
    this.clearDialogMessages();
    this.showDialog = true;
  }
  onEdit(selection: Parametre) {
    this.checkSelected = false;
    this.debut = ((selection.dateDebutSaisit!).toString()).substring(8,10)+"-"+((selection.dateDebutSaisit!).toString()).substring(5,7)+"-"+((selection.dateDebutSaisit!).toString()).substring(0,4);
    this.fin = ((selection.dateFinSaisit!).toString()).substring(8,10)+"-"+((selection.dateFinSaisit!).toString()).substring(5,7)+"-"+((selection.dateFinSaisit!).toString()).substring(0,4);
    this.parametre = Object.assign({}, selection);
    this.clearDialogMessages();
    this.showDialog = true;
  }
  onDelete(selection: any) {
    this.confirmationservice.confirm({
      message: 'Etes-vous sur de vouloir supprimer ?',
      accept: () => {
        this.delete(selection);
      },
    });
  }

  onCreate() {
    this.checkSelected = true;
    this.parametre = {};
    this.clearDialogMessages();
    this.form.resetForm();
    this.showDialog = true;
  }
  isEditing() {
    return !this.parametre.id;
  }
  save() {
    if (this.parametre.id) {
      this.edit();
    } else {
      this.create();
    }
  }

  create() {
    this.parametre.dateDebutSaisit = new Date(this.debut);
    this.parametre.dateFinSaisit = new Date(this.fin);
    this.clearDialogMessages();
    this.isDialogOpInProgress = true;
    this.parametreService.create(this.parametre).subscribe(
      (response) => {
        if (this.parametres.length !== this.recordsPerPage) {
          this.parametres.push(response);
          this.parametres = this.parametres.slice();
        }
        this.totalRecords++;
        this.isDialogOpInProgress = false;
        this.showDialog = false;
        this.showMessage({
          severity: 'success',
          summary: 'Paramètre créé avec succes',
        });
      },
      (error) => this.handleError(error)
    );
  }

  edit() {
    this.clearDialogMessages();
    this.isDialogOpInProgress = true;
    if(this.debut.length == 10){
      let dateParts = this.debut.split("-");
      this.debut = new Date(dateParts[2]+"-"+dateParts[1]+"-"+dateParts[0]).toISOString();
    }

    if(this.debut.length != 10){
      this.debut = new Date(this.debut).toISOString();
    }

    if(this.fin.length == 10){
      let dateParts = this.fin.split("-");
      this.fin = new Date(dateParts[2]+"-"+dateParts[1]+"-"+dateParts[0]).toISOString();
    }

    if(this.fin.length != 10){
      this.fin = new Date(this.fin).toISOString();
    }

    this.parametre.dateDebutSaisit = new Date(this.debut);
    this.parametre.dateFinSaisit = new Date(this.fin);
    this.parametreService.update(this.parametre).subscribe(
      (response) => {
        let index = this.parametres.findIndex(
          (parametre) => parametre.id === response.id
        );
        this.parametres[index] = response;
        this.isDialogOpInProgress = false;
        this.showDialog = false;
        this.showMessage({
          severity: 'success',
          summary: 'Paramètre modifié avec succès',
        });
      },
      (error) => this.handleError(error)
    );
  }


  delete(selection: any) {
    this.isOpInProgress = true;
    this.parametreService.delete(selection.id).subscribe(
      () => {
        this.parametres = this.parametres.filter(
          (parametre) => parametre.id !== selection.id
        );
        selection = null;
        this.isOpInProgress = false;
        this.totalRecords--;
        this.showMessage({
          severity: 'success',
          summary: 'Paramètre supprimé avec succès',
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

