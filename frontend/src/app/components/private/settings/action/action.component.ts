import { AuthenticationService } from 'src/app/services/parametrage/authentication.service';
import { Component, OnInit, ViewChild } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { NgForm } from '@angular/forms';
import { ConfirmationService, LazyLoadEvent, MenuItem, Message } from 'primeng/api';
import { Action } from 'src/app/models/parametrage/action';
import { Objectif } from 'src/app/models/parametrage/objectif';
import { ActionService } from 'src/app/services/parametrage/action.service';
import { ObjectifService } from 'src/app/services/parametrage/objectif.service';
import { environment } from 'src/environments/environment';


@Component({
  selector: 'app-action',
  templateUrl: './action.component.html',
  styleUrls: ['./action.component.scss']
})

export class ActionComponent implements OnInit {

  @ViewChild('dtf') form!: NgForm;

  timeoutHandle: any;
  totalRecords!: number;
  recordsPerPage = environment.recordsPerPage;
  actions!: Action[]
  // selection: any;
  action: Action = {};
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
  objectifs!: Objectif[];
  selectedObjectif!: Objectif;
  actionDetail:boolean=false;
  permissions:any;
  constructor(private actionService:ActionService,
    private objectifService:ObjectifService,
    private confirmationService: ConfirmationService,
    public authService:AuthenticationService) { }

  ngOnInit(): void {

    this.load();
    this.loadObjectifs();
    this.permissions=this.authService.getPrivilege();
    this.enableBtnEdit=this.authService.checkPermission(this.permissions, ['ROLE_ADMIN','ROLE_DIR_DGESS','ROLE_RESP_DGESS']);
    //this.enableBtnDelete=this.authService.checkPermission(this.permissions, ['ROLE_ADMIN','ROLE_DIR_DGESS','ROLE_RESP_DGESS']);
  }

  load(event?: LazyLoadEvent) {
     this.isLoading = true;
    this.actionService.getAll(event).subscribe(response => {
      this.isLoading = false;
      this.actions = response.actions;
    }, error => {
      this.message = { severity: 'error', summary: error.error };
      console.error(JSON.stringify(error));
    });
  }


  loadObjectifs(event?: LazyLoadEvent) {
    this.isLoading = true;
   this.objectifService.getObjectifStrategique().subscribe(response => {
     this.isLoading = false;
     this.objectifs = response.objectifs;
   }, error => {
     this.message = { severity: 'error', summary: error.error };
     console.error(JSON.stringify(error));
   });
 }


  //Détail
  onInfo(selection:any){
    this.action = Object.assign({}, selection);
    this.clearDialogMessages();
    this.actionDetail=true;
  }

  save() {
    if (this.action.id) {
      this.edit();
    } else {
      this.create();
    }
  }

   //Creation

   onCreate() {

    this.action = {};
    this.clearDialogMessages();
    this.form.resetForm();
    this.showDialog = true;
   }

  create() {
    this.clearDialogMessages();
    this.isDialogOpInProgress = true;
    this.actionService.create(this.action).subscribe(response => {
      if (this.actions.length !== this.recordsPerPage) {
        this.actions.push(response);
        this.actions = this.actions.slice();
      }
      this.totalRecords++;
      this.isDialogOpInProgress = false;
      this.showDialog = false;
      this.showMessage({ severity: 'success', summary: 'Action créée avec succès' });
    }, error => this.handleError(error));
  }


   // Edit

   onEdit(selection:any) {
    this.action = Object.assign({}, selection);
    this.clearDialogMessages();
    this.showDialog = true;
  }

  edit() {
    this.clearDialogMessages();
    this.isDialogOpInProgress = true;
    this.actionService.update(this.action).subscribe(response => {
      let index = this.actions.findIndex(action => action.id === response.id);
      this.actions[index] = response;
      this.isDialogOpInProgress = false;
      this.showDialog = false;
      this.showMessage({ severity: 'success', summary: 'Action modifiée avec succès' });
    }, error => this.handleError(error));
  }

  isEditing() {
    return !!this.action.id;
  }

  // Deletion

  onDelete(selection:any) {
    this.confirmationService.confirm({
      message: 'Etes-vous sûre de vouloir supprimer cette action ?',
      accept: () => {
        this.delete(selection);
      }
    });
  }

  delete(selection:any) {
    this.isOpInProgress = true;
    this.actionService.delete(selection.id).subscribe(() => {
      this.actions = this.actions.filter(action => action.id !== selection.id);
      selection = null;
      this.isOpInProgress = false;
      this.totalRecords--;
      this.showMessage({ severity: 'success', summary: 'Action supprimée avec succès' });
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

