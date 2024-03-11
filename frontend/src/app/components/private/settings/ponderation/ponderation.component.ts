import { ConfirmationService, LazyLoadEvent, Message } from 'primeng/api';
import { PonderationService } from './../../../../services/parametrage/ponderation.service';
import { Component, OnInit, ViewChild } from '@angular/core';
import { environment } from 'src/environments/environment';
import { NgForm } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { enumStatut, Ponderation } from 'src/app/models/parametrage/ponderation';

@Component({
  selector: 'app-ponderation',
  templateUrl: './ponderation.component.html',
  styleUrls: ['./ponderation.component.scss'],
})
export class PonderationComponent implements OnInit {
  @ViewChild('dtf') form!: NgForm;

  timeoutHandle: any;
  totalRecords!: number;
  recordsPerPage = environment.recordsPerPage;
  ponderations!: Ponderation[];
  // selection: any;
  ponderation: Ponderation = {};
  enableCreate = true;
  enableBtnInfo = true;
  enableBtnEdit = true;
  enableBtnDelete = false;
  isLoading!: boolean;
  isOpInProgress!: boolean;
  isDialogOpInProgress!: boolean;
  showDialog = false;
  message: any;
  dialogErrorMessage: any;
  isRequired:boolean=true
  ponderationDetail: boolean=false;
  // ponderationStatuts: ponderationStatut[] = [
  //   { id: 1, libelle: "ENCOURS" },
  //   { id: 2, libelle: "CLOTURE" },
  //   { id: 3, libelle: "ANNULE" }

  // ];


  constructor(
    private ponderationService: PonderationService,
    private confirmationservice: ConfirmationService
  ) { }

  ngOnInit(): void {
    this.load();


  }

  load(event?: LazyLoadEvent) {
    this.isLoading = true;
    this.ponderationService.getAll(event).subscribe(
      (response) => {
        this.isLoading = false;
        this.ponderations = response.ponderations;
        //this.totalRecords = response.totalCount;
        // console.log( this.exemples)
      },
      (error) => {
        this.message = { severity: 'error', summary: error.error };
        console.error(JSON.stringify(error));
      }
    );
  }

  onInfo(selection: any) {
    this.ponderation = Object.assign({}, selection);
    this.clearDialogMessages();
    this.ponderationDetail = true;
  }
  onEdit(selection: any) {
    console.log(selection);
    this.ponderation = Object.assign({}, selection);
    this.clearDialogMessages();
    this.showDialog = true;
  }
  onDelete(selection: any) {
    this.confirmationservice.confirm({
      message: 'Etes-vous sur de vouloir supprimer ce ponderation?',
      accept: () => {
        this.delete(selection);
      },
    });
  }

  onCreate() {
    this.ponderation = {};
    this.clearDialogMessages();
    this.form.resetForm();
    this.showDialog = true;
  }
  isEditing() {
    return !!this.ponderation.id;
  }
  save() {
    if (this.ponderation.id) {
      this.edit();
    } else {
      this.create();
    }
  }

  create() {
    const data = {
      id: this.ponderation.id,
      efficacite: this.ponderation.efficacite,
      efficience: this.ponderation.efficience,
      actifs:enumStatut.false,
      impact: this.ponderation.impact,

    }
    this.clearDialogMessages();
    this.isDialogOpInProgress = true;
    this.ponderationService.create(data).subscribe(
      (response) => {
        if (this.ponderations.length !== this.recordsPerPage) {
          this.ponderations.push(response);
          this.ponderations = this.ponderations.slice();
        }
        this.totalRecords++;
        this.isDialogOpInProgress = false;
        this.showDialog = false;
        this.showMessage({
          severity: 'success',
          summary: 'Ponderation créé avec succes',
        });
      },
      (error) => this.handleError(error)
    );
  }

  edit() {
    this.clearDialogMessages();
    this.isDialogOpInProgress = true;
    this.ponderationService.update(this.ponderation).subscribe(
      (response) => {
        let index = this.ponderations.findIndex(
          (ponderation) => ponderation.id === response.id
        );
        this.ponderations[index] = response;
        this.isDialogOpInProgress = false;
        this.showDialog = false;
        this.showMessage({
          severity: 'success',
          summary: 'Ponderation modifié avec succès',
        });
      },
      (error) => this.handleError(error)
    );
  }


  delete(selection: any) {
    console.log(selection.id);
    this.isOpInProgress = true;
    this.ponderationService.delete(selection.id).subscribe(
      () => {
        this.ponderations = this.ponderations.filter(
          (ponderation) => ponderation.id !== selection.id
        );
        selection = null;
        this.isOpInProgress = false;
        this.totalRecords--;
        this.showMessage({
          severity: 'success',
          summary: 'ponderation supprimée avec succès',
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
