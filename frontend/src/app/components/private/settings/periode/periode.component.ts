import { AuthenticationService } from 'src/app/services/parametrage/authentication.service';
import { PeriodiciteService } from 'src/app/services/parametrage/periodicite.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ConfirmationService, LazyLoadEvent, Message } from 'primeng/api';
import { Periode } from 'src/app/models/parametrage/periode';
import { Periodicite } from 'src/app/models/parametrage/periodicite';
import { PeriodeService } from 'src/app/services/parametrage/periode.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-periode',
  templateUrl: './periode.component.html',
  styleUrls: ['./periode.component.scss']
})
export class PeriodeComponent implements OnInit {

  @ViewChild('dtf') form!: NgForm;

  timeoutHandle: any;
  totalRecords!: number;
  recordsPerPage = environment.recordsPerPage;
  periodes!: Periode[]
  periode: Periode = {};
  periodicite: Periode={};
  periodicites!:Periodicite[];
  enableCreate = true;
  enableBtnInfo = false;
  enableBtnEdit = true;
  enableBtnDelete = false;
  isLoading!: boolean;
  isOpInProgress!: boolean;
  isDialogOpInProgress!: boolean;
  showDialog = false;
  checked1!: boolean;
  message: any;
  dialogErrorMessage: any;
  debutPeriode: string = "";
  finPeriode: string = "";
  checkSelected = true;
  permissions:any;


  constructor(private periodeService: PeriodeService,
    private periodiciteService: PeriodiciteService,
    private confirmationService: ConfirmationService,
    public authService:AuthenticationService) { }

  ngOnInit(): void {

    this.load();
    this.getPeriodiciteActif();
    this.permissions=this.authService.getPrivilege();
    this.enableBtnEdit=this.authService.checkPermission(this.permissions, ['ROLE_ADMIN','ROLE_DIR_DGESS','ROLE_RESP_DGESS']);
    //this.enableBtnDelete=this.authService.checkPermission(this.permissions, ['ROLE_ADMIN','ROLE_DIR_DGESS','ROLE_RESP_DGESS']);
  }


  handleChange(e: any) {
    this.periode.valeur = e.checked;;
  }

  load(event?: LazyLoadEvent) {
    this.isLoading = true;
    this.periodeService.getAll(event).subscribe(response => {
    this.isLoading = false;
    this.periodes = response.periodes;

    }, error => {
      this.message = { severity: 'error', summary: error.error };
      console.error(JSON.stringify(error));
    });
  }

  getPeriodiciteActif(event?: LazyLoadEvent){

    this.isLoading = true;
    this.periodiciteService.getPeriodiciteActif(event).subscribe(response => {
    this.periodicites = response.periodicites;
    }, error => {
      this.message = { severity: 'error', summary: error.error };
    });

  }


  onInfo(selection: any) {
    this.periode = Object.assign({}, selection);
    this.clearDialogMessages();
    this.showDialog = true;
  }

  save() {
    if (this.periode.id) {
      this.edit();
    } else {
      this.create();
    }
  }

  //Creation

  onCreate() {
    this.periode = {};
    this.clearDialogMessages();
    this.form.resetForm();
    this.showDialog = true;
  }

  create() {
    this.periode.debut = new Date(this.debutPeriode);
    this.periode.fin = new Date(this.finPeriode);
    this.clearDialogMessages();
    this.isDialogOpInProgress = true;
    this.periodeService.create(this.periode).subscribe(response => {
      if (this.periodes.length !== this.recordsPerPage) {
        this.periodes.push(response);
        this.periodes = this.periodes.slice();
      }
      this.totalRecords++;
      this.isDialogOpInProgress = false;
      this.showDialog = false;
      this.showMessage({
        severity: 'success',
        summary: 'Période créée avec succès'
      });
    }, error => this.handleError(error));
  }


  // Edit

  onEdit(selection: any) {
    this.checkSelected = false;
    this.debutPeriode = ((selection.debut!).toString()).substring(8, 10) + "/" + ((selection.debut!).toString()).substring(5, 7) + "/" + ((selection.debut!).toString()).substring(0, 4);
    this.finPeriode = ((selection.fin!).toString()).substring(8, 10) + "/" + ((selection.fin!).toString()).substring(5, 7) + "/" + ((selection.fin!).toString()).substring(0, 4);
    this.periode = Object.assign({}, selection);
    this.clearDialogMessages();
    this.showDialog = true;
  }

  edit() {
    this.clearDialogMessages();
    this.isDialogOpInProgress = true;

    if (this.debutPeriode.length == 10) {
      let dateParts = this.debutPeriode.split("/");
      this.debutPeriode = new Date(dateParts[2] + "/" + dateParts[1] + "/" + dateParts[0]).toISOString();
    }

    if (this.debutPeriode.length != 10) {
      this.debutPeriode = new Date(this.debutPeriode).toISOString();
    }

    if (this.finPeriode.length == 10) {
      let dateParts = this.finPeriode.split("/");
      this.finPeriode = new Date(dateParts[2] + "/" + dateParts[1] + "/" + dateParts[0]).toISOString();
    }

    if (this.finPeriode.length != 10) {
      this.finPeriode = new Date(this.finPeriode).toISOString();
    }

    this.periode.debut = new Date(this.debutPeriode);
    this.periode.fin = new Date(this.finPeriode);
    console.log("periode",this.periode)
    this.periodeService.update(this.periode).subscribe(response => {
      let index = this.periodes.findIndex(periode => periode.id === response.id);
      this.periodes[index] = response;
      this.isDialogOpInProgress = false;
      this.showDialog = false;
      this.showMessage({
        severity: 'success',
        summary: 'Période modifiée avec succès'
      });
    }, error => this.handleError(error));
  }

  isEditing() {
    return !!this.periode.id;
  }

  // Deletion

  onDelete(selection: any) {
    this.confirmationService.confirm({
      message: 'Etes-vous sûre de vouloir supprimer cette période?',
      accept: () => {
        this.delete(selection);
      }
    });
  }

  delete(selection: any) {
    this.isOpInProgress = true;
    this.periodeService.delete(selection.id).subscribe(() => {
      this.periodes = this.periodes.filter(periode => periode.id !== selection.id);
      selection = null;
      this.isOpInProgress = false;
      this.totalRecords--;
      this.showMessage({ severity: 'success', summary: 'Période supprimée avec succès' });
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
    this.dialogErrorMessage = error.error.title;
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
