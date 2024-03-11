import { AuthenticationService } from 'src/app/services/parametrage/authentication.service';
import { projetStatut } from './../../../../models/parametrage/projet';
import { HttpErrorResponse } from '@angular/common/http';
import { ThrowStmt } from '@angular/compiler';
import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ConfirmationService, LazyLoadEvent, Message, MessageService,ConfirmEventType } from 'primeng/api';
import { Programme } from 'src/app/models/parametrage/programme';
import { Projet } from 'src/app/models/parametrage/projet';
import { ProgrammeService } from 'src/app/services/parametrage/programme.service';
import { ProjetService } from 'src/app/services/parametrage/projet.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-projet',
  templateUrl: './projet.component.html',
  styleUrls: ['./projet.component.scss'],
  providers: [ConfirmationService,MessageService]
})
export class ProjetComponent implements OnInit {

  @ViewChild('dtf') form!: NgForm;
  timeoutHandle: any;
  totalRecords!: number;
  recordsPerPage = environment.recordsPerPage;
  projets!: Projet[];
  programmes!: Programme[];
  // selection: any;
  projet: Projet = {};
  projetDetail: boolean=false;
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
  debut : string = "";
  fin : string = "";
  permissions: any;
  constructor(private projetService: ProjetService, private programmeService: ProgrammeService,
    private confirmationservice: ConfirmationService, private messageService: MessageService,
    public authService: AuthenticationService) { }

  ngOnInit(): void {
  this.load();
  this.loadProgramme();
  this.permissions=this.authService.getPrivilege();
    this.enableBtnEdit=this.authService.checkPermission(this.permissions, ['ROLE_ADMIN','ROLE_DIR_DGESS','ROLE_RESP_DGESS']);
    //this.enableBtnDelete=this.authService.checkPermission(this.permissions, ['ROLE_ADMIN','ROLE_DIR_DGESS','ROLE_RESP_DGESS']);
  }

  isEditing() {
    return !!this.projet.id;
  }
  onInfo(selection: any) {
    this.projet = Object.assign({}, selection);
    this.clearDialogMessages();
    this.projetDetail = true;
  }


  onEdit(selection: Projet) {

    this.debut = ((selection.debut!).toString()).substring(8,10)+"-"+((selection.debut!).toString()).substring(5,7)+"-"+((selection.debut!).toString()).substring(0,4);
     this.fin = ((selection.fin!).toString()).substring(8,10)+"-"+((selection.fin!).toString()).substring(5,7)+"-"+((selection.fin!).toString()).substring(0,4);
    this.projet = Object.assign({}, selection);
    this.clearDialogMessages();
    this.showDialog = true;
  }

  onDelete(selection: any) {
    this.confirmationservice.confirm({
      message: 'Etes-vous sûre de vouloir supprimer ce projet?',
      accept: () => {
        this.delete(selection);
      },
    });
  }

  onCreate() {
    this.projet = {};
    this.clearDialogMessages();
    this.form.resetForm();
    this.showDialog = true;
  }

  load(event?: LazyLoadEvent) {
    this.isLoading = true;
    this.projetService.getAll(event).subscribe(
      (response) => {
        this.isLoading = false;
        this.projets = response.projets;
      },
      (error) => {
        this.message = { severity: 'error', summary: error.error };
        console.error(JSON.stringify(error));
      }
    );
  }


  loadProgramme(event?: LazyLoadEvent) {
    this.isLoading = true;
    this.programmeService.getAll(event).subscribe(
      (response) => {
        this.isLoading = false;
        this.programmes = response.programmes;
      },
      (error) => {
        this.message = { severity: 'error', summary: error.error };
        console.error(JSON.stringify(error));
      }
    );
  }

  save() {
    if (this.projet.id) {
      this.edit();
    } else {
      this.create();
    }
  }

  create() {
    this.projet.debut = new Date(this.debut);
    this.projet.fin = new Date(this.fin);
    const data={
      id:this.projet.id,
      libelle: this.projet.libelle,
      description: this.projet.description,
      statut: projetStatut.ENCOURS,
      debut: this.projet.debut,
      fin: this.projet.fin,
      details: this.projet.details,
      programme:this.projet.programme
    }

    this.clearDialogMessages();

    this.isDialogOpInProgress = true;
      this.projetService.create(data).subscribe(

        (response) => {
          if (this.projets.length !== this.recordsPerPage) {
            this.projets.push(response);
            this.projets = this.projets.slice();
            //console.log(data);
          }
          this.totalRecords++;
          this.isDialogOpInProgress = false;
          this.showDialog = false;
          this.showMessage({
            severity: 'success',
            summary: 'Projet créé avec succès',
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
      this.projet.debut = new Date(this.debut);
      this.projet.fin = new Date(this.fin);
      this.projetService.update(this.projet).subscribe(
      (response) => {
        let index = this.projets.findIndex(
          (projet) => projet.id === response.id
        );
        this.projets[index] = response;
        this.isDialogOpInProgress = false;
        this.showDialog = false;
        this.showMessage({
          severity: 'success',
          summary: 'Projet modifié avec succès',
        });
      },
      (error) => this.handleError(error)
    );
  }

  delete(selection: any) {
    console.log(selection.id);
    this.isOpInProgress = true;
    this.projetService.delete(selection.id).subscribe(
      () => {
        this.projets = this.projets.filter(
          (projet) => projet.id !== selection.id
        );
        selection = null;
        this.isOpInProgress = false;
        this.totalRecords--;
        this.showMessage({
          severity: 'success',
          summary: 'Projet supprimé avec succès',
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
