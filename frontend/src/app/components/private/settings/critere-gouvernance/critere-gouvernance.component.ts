import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ConfirmationService, LazyLoadEvent, Message } from 'primeng/api';
import { CritereGouvernance } from 'src/app/models/performance/critere-gouvernance';
import { AuthenticationService } from 'src/app/services/parametrage/authentication.service';
import { CritereGouvernanceService } from 'src/app/services/parametrage/critere-gouvernance.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-critere-gouvernance',
  templateUrl: './critere-gouvernance.component.html',
  styleUrls: ['./critere-gouvernance.component.scss']
})
export class CritereGouvernanceComponent implements OnInit {

  @ViewChild('dtf') form!: NgForm;

  timeoutHandle: any;
  totalRecords!: number;
  recordsPerPage = environment.recordsPerPage;
  criteres: CritereGouvernance[]=[];
  critere:CritereGouvernance={}

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
  actionDetail:boolean=false;
  permissions:any;
  critereDetail = false;
  constructor(private critereService:CritereGouvernanceService,

    private confirmationService: ConfirmationService,
    public authService:AuthenticationService) { }

  ngOnInit(): void {

    this.load();

    this.permissions=this.authService.getPrivilege();
    this.enableBtnEdit=this.authService.checkPermission(this.permissions, ['ROLE_ADMIN','ROLE_DIR_DGESS','ROLE_RESP_DGESS']);
    //this.enableBtnDelete=this.authService.checkPermission(this.permissions, ['ROLE_ADMIN','ROLE_DIR_DGESS','ROLE_RESP_DGESS']);
  }

  load(event?: LazyLoadEvent) {
     this.isLoading = true;
    this.critereService.getAll(event).subscribe(response => {
      this.isLoading = false;
      this.criteres = response.criteres;

    }, error => {
      this.message = { severity: 'error', summary: error.error };
      console.error(JSON.stringify(error));
    });
  }
  save() {
    if (this.critere.id) {
      this.edit();
    } else {
      this.create();
    }
  }

  onInfo(selection: any) {
    this.critere = Object.assign({}, selection);
    this.clearDialogMessages();
    this.critereDetail = true;
  }
   //Creation

   onCreate() {

    this.critere = {};
    this.clearDialogMessages();
    this.form.resetForm();
    this.showDialog = true;
   }

  create() {
    this.clearDialogMessages();
    this.isDialogOpInProgress = true;
    this.critereService.create(this.critere).subscribe(response => {
      if (this.criteres.length !== this.recordsPerPage) {
        this.criteres.push(response);
        this.criteres = this.criteres.slice();
      }
      this.totalRecords++;
      this.isDialogOpInProgress = false;
      this.showDialog = false;
      this.showMessage({ severity: 'success', summary: 'Critère ajoutée avec succès' });
    }, error => this.handleError(error));
  }


   // Edit

   onEdit(selection:any) {
    this.critere = Object.assign({}, selection);
    this.clearDialogMessages();
    this.showDialog = true;
  }

  edit() {
    this.clearDialogMessages();
    this.isDialogOpInProgress = true;
    this.critereService.update(this.critere).subscribe(response => {
      let index = this.criteres.findIndex(critere => critere.id === response.id);
      this.criteres[index] = response;
      this.isDialogOpInProgress = false;
      this.showDialog = false;
      this.showMessage({ severity: 'success', summary: 'Critère modifiée avec succès' });
    }, error => this.handleError(error));
  }

  isEditing() {
    return !!this.critere.id;
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
