import { AuthenticationService } from 'src/app/services/parametrage/authentication.service';
import { ConfirmationService, LazyLoadEvent, Message, MessageService } from 'primeng/api';
import { ProgrammeService } from './../../../../services/parametrage/programme.service';
import { Component, OnInit, ViewChild } from '@angular/core';
import { Programme, programmeStatut } from 'src/app/models/parametrage/programme';
import { environment } from 'src/environments/environment';
import { NgForm } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-programme',
  templateUrl: './programme.component.html',
  styleUrls: ['./programme.component.scss'],
})
export class ProgrammeComponent implements OnInit {
  @ViewChild('dtf') form!: NgForm;
  timeoutHandle: any;
  totalRecords!: number;
  recordsPerPage = environment.recordsPerPage;
  programmes!: Programme[];
  // selection: any;
  programme: Programme = {};
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
  programmeDetail: boolean=false;
  permissions: any;
  // programmeStatuts: programmeStatut[] = [
  //   { id: 1, libelle: "ENCOURS" },
  //   { id: 2, libelle: "CLOTURE" },
  //   { id: 3, libelle: "ANNULE" }

  // ];

  finProgramme: string = "";
  debutProgramme: string = "";
  codeProgramme?: string;
  constructor(
    private programmeService: ProgrammeService,
    private confirmationservice: ConfirmationService,
    public authService: AuthenticationService,
    private messageService: MessageService
  ) { }

  ngOnInit(): void {
    this.load();
    this.permissions=this.authService.getPrivilege();
    this.enableBtnEdit=this.authService.checkPermission(this.permissions, ['ROLE_ADMIN','ROLE_DIR_DGESS','ROLE_RESP_DGESS']);
    //this.enableBtnDelete=this.authService.checkPermission(this.permissions, ['ROLE_ADMIN','ROLE_DIR_DGESS','ROLE_RESP_DGESS']);

  }

  getValide(){

    if(this.finProgramme < this.debutProgramme )
  {
    alert("veuillez saisir une date de début antérieure à la date de fin");
    this.messageService.add({severity:'warn', life:5000, summary: 'warn', detail: 'La date de fin du projet est antérieure à la date de début'});
  }

  }

  load(event?: LazyLoadEvent) {
    this.isLoading = true;
    this.programmeService.getAll(event).subscribe(
      (response) => {
        this.isLoading = false;
        this.programmes = response.programmes;
        //this.totalRecords = response.totalCount;
      },
      (error) => {
        this.message = { severity: 'error', summary: error.error };
        console.error(JSON.stringify(error));
      }
    );
  }

  onInfo(selection: any) {
    this.programme = Object.assign({}, selection);
    this.clearDialogMessages();
    this.programmeDetail = true;
  }
  onEdit(selection: Programme) {
    this.debutProgramme = ((selection.debut!).toString()).substring(8,10)+"-"+((selection.debut!).toString()).substring(5,7)+"-"+((selection.debut!).toString()).substring(0,4);
    this.finProgramme = ((selection.fin!).toString()).substring(8,10)+"-"+((selection.fin!).toString()).substring(5,7)+"-"+((selection.fin!).toString()).substring(0,4);
    this.programme = Object.assign({}, selection);
    this.clearDialogMessages();
    this.showDialog = true;
  }
  onDelete(selection: any) {
    this.confirmationservice.confirm({
      message: 'Etes-vous sûre de vouloir supprimer ce programme budgétaire?',
      accept: () => {
        this.delete(selection);
      },
    });
  }

  onCreate() {

      this.programme = {};
      this.codeProgramme =this.programme.code;
      this.clearDialogMessages();
      this.form.resetForm();
      this.showDialog = true;
  }
  isEditing() {
    return !!this.programme.id;
  }
  save() {
    if (this.programme.id) {
      this.edit();
    } else {
      this.create();
    }
  }

  create() {
    this.programme.debut = new Date(this.debutProgramme);
    this.programme.fin = new Date(this.finProgramme);
    const data = {
      id: this.programme.id,
      code: this.codeProgramme,
      libelle: this.programme.libelle,
      description:this.programme.description,
      statut: programmeStatut.ENCOURS,
      debut: this.programme.debut,
      fin: this.programme.fin,
      details: this.programme.details,

    }
    this.clearDialogMessages();
    this.isDialogOpInProgress = true;

      this.programmeService.create(data).subscribe(
        (response) => {
          if (this.programmes.length !== this.recordsPerPage   ) {
            this.programmes.push(response);
            this.programmes = this.programmes.slice();
          }
          this.totalRecords++;
          //this.load();
          this.isDialogOpInProgress = false;
          this.showDialog = false;
          this.showMessage({
            severity: 'success',
            summary: 'Programme budgétaire créé avec succès',
          });
        },
        (error) => this.handleError(error)
      );

  }

  edit() {
    this.clearDialogMessages();
    this.isDialogOpInProgress = true;
    if(this.debutProgramme.length == 10){
      let dateParts = this.debutProgramme.split("-");
      this.debutProgramme = new Date(dateParts[2]+"-"+dateParts[1]+"-"+dateParts[0]).toISOString();
    }

    if(this.debutProgramme.length != 10){
      this.debutProgramme = new Date(this.debutProgramme).toISOString();
    }

    if(this.finProgramme.length == 10){
      let dateParts = this.finProgramme.split("-");
      this.finProgramme = new Date(dateParts[2]+"-"+dateParts[1]+"-"+dateParts[0]).toISOString();
    }

    if(this.finProgramme.length != 10){
      this.finProgramme = new Date(this.finProgramme).toISOString();
    }
      this.programme.debut = new Date(this.debutProgramme);
      this.programme.fin = new Date(this.finProgramme);
      this.programmeService.update(this.programme).subscribe(
      (response) => {
        let index = this.programmes.findIndex(
          (programme) => programme.id === response.id
        );
        this.programmes[index] = response;
        this.isDialogOpInProgress = false;
        this.showDialog = false;
        this.showMessage({
          severity: 'success',
          summary: 'Programme budgétaire modifié avec succès',
        });
        this.load();
      },
      (error) => this.handleError(error)
    );
  }


  delete(selection: any) {
    this.isOpInProgress = true;
    this.programmeService.delete(selection.id).subscribe(
      () => {
        this.programmes = this.programmes.filter(
          (programme) => programme.id !== selection.id
        );
        selection = null;
        this.isOpInProgress = false;
        this.totalRecords--;
        this.showMessage({
          severity: 'success',
          summary: 'Programme budgétaire supprimé avec succès',
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
