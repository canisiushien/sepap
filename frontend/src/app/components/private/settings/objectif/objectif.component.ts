import { AuthenticationService } from 'src/app/services/parametrage/authentication.service';
import { ProgrammeService } from 'src/app/services/parametrage/programme.service';
import { Component, OnInit, ViewChild } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { NgForm } from '@angular/forms';
import {ConfirmationService,LazyLoadEvent,MenuItem,Message} from 'primeng/api';
import { Action } from 'src/app/models/parametrage/action';
import { Objectif } from 'src/app/models/parametrage/objectif';
import { ActionService } from 'src/app/services/parametrage/action.service';
import { ObjectifService } from 'src/app/services/parametrage/objectif.service';
import { environment } from 'src/environments/environment';
import { Programme } from 'src/app/models/parametrage/programme';

@Component({
  selector: 'app-objectif',
  templateUrl: './objectif.component.html',
  styleUrls: ['./objectif.component.scss'],
})
export class ObjectifComponent implements OnInit {
  @ViewChild('dtf') form!: NgForm;

  timeoutHandle: any;
  totalRecords!: number;
  recordsPerPage = environment.recordsPerPage;
  objectifs!: Objectif[];
  // selection: any;
  objectif: Objectif = {};
  enableCreate = true;
  enableBtnInfo = false;
  enableBtnEdit = true;
  enableBtnDelete = false;
  isLoading!: boolean;
  isOpInProgress!: boolean;
  isDialogOpInProgress!: boolean;
  showDialog = false;
  message: any;
  dialogErrorMessage: any;
  actions!: Action[];
  action: Action = {};
  showDialogAction: boolean = false;
  selectedAction!: Action;
  selectedParent!: Objectif;
  types!: String[];
  selectedType!: String;
  actionInput: boolean = false;
  choixObjectif: Objectif[] = [];
  actifAction: boolean = false;
  programmeInput: boolean = false;
  actifProgramme: boolean = false;
  programme: Programme = {};
  programmes: Programme[] = [];
  obgectifStrategique!: Objectif[];
  permissions: any;
  objectifDetail:boolean=false;
  constructor(
    private actionService: ActionService,
    private objectifService: ObjectifService,
    private confirmationService: ConfirmationService,
    private programmeService: ProgrammeService,
    public authService: AuthenticationService
  ) {}

  ngOnInit(): void {
    this.types = ['OPERATIONNEL', 'STRATEGIQUE'];

    this.load();
    this.loadActions();
    this.loadProgrammes();
    this.permissions = this.authService.getPrivilege();
    this.enableBtnEdit = this.authService.checkPermission(this.permissions, [
      'ROLE_ADMIN',
      'ROLE_DIR_DGESS',
      'ROLE_RESP_DGESS',
    ]);
    this.enableBtnInfo=this.authService.checkPermission(this.permissions,
      ['ROLE_ADMIN',
      'ROLE_DIR_DGESS',
      'ROLE_RESP_DGESS']);
  }

  actif() {}
  load(event?: LazyLoadEvent) {
    this.isLoading = true;
    this.objectifService.getAll(event).subscribe(
      (response) => {
        this.isLoading = false;
        this.objectifs = response.objectifs;
      },
      (error) => {
        this.message = { severity: 'error', summary: error.error };
        console.error(JSON.stringify(error));
      }
    );
  }

  actionHidden(event: any) {
    let type = event.value;

    if (type == 'OPERATIONNEL') {
      this.actionInput = false;

      this.programmeInput = true;

      this.actifAction = true;
      this.objectifService.getObjectifOperationnel().subscribe((response) => {
        this.choixObjectif = response.objectifs;
      });
    }
    if (type == 'STRATEGIQUE') {
      this.actionInput = true;

      this.programmeInput = false;

      this.actifProgramme = true;
      this.objectifService.getObjectifStrategique().subscribe((response) => {
        this.choixObjectif = response.objectifs;
      });
    }
  }

  loadActions(event?: LazyLoadEvent) {
    this.isLoading = true;
    this.actionService.getAll(event).subscribe(
      (response) => {
        this.isLoading = false;
        this.actions = response.actions;
      },
      (error) => {
        this.message = { severity: 'error', summary: error.error };
        console.error(JSON.stringify(error));
      }
    );
  }

  loadProgrammes(event?: LazyLoadEvent) {
    this.isLoading = true;
    this.programmeService.getProgrammeENCOURS(event).subscribe(
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

  loadObjectifStrategique(event?: LazyLoadEvent) {
    this.isLoading = true;
    this.objectifService.getObjectifStrategique().subscribe(
      (response) => {
        this.isLoading = false;
        this.obgectifStrategique = response.objectifs;
      },
      (error) => {
        this.message = { severity: 'error', summary: error.error };
        console.error(JSON.stringify(error));
      }
    );
  }
  //Détail
  onInfo(selection: any) {
    this.objectif = Object.assign({}, selection);
    this.objectifDetail=true;
  }

  save() {
    if (this.objectif.id) {
      this.edit();
    } else {
      this.create();
    }
  }

  //Creation

  onCreate() {
    this.objectif = {};
    this.clearDialogMessages();
    this.form.resetForm();
    this.showDialog = true;
  }
  onCreateAction() {
    this.action = {};
    this.clearDialogMessages();
    this.form.resetForm();
    this.showDialogAction = true;
    this.loadObjectifStrategique();
  }

  createAction() {
    this.clearDialogMessages();
    this.isDialogOpInProgress = true;
    this.actionService.create(this.action).subscribe(
      (response) => {
        this.loadActions();
        this.isDialogOpInProgress = false;
        this.showDialogAction = false;
      },
      (error) => this.handleError(error)
    );
  }

  create() {
    //console.log(this.objectif);
    this.clearDialogMessages();
    this.isDialogOpInProgress = true;
    this.objectifService.create(this.objectif).subscribe(
      (response) => {
        if (this.objectifs.length !== this.recordsPerPage) {
          this.objectifs.push(response);
          this.objectifs = this.objectifs.slice();
        }
        this.totalRecords++;
        this.isDialogOpInProgress = false;
        this.showDialog = false;
        this.showMessage({
          severity: 'success',
          summary: 'Objectif créée avec succès',
        });
      },
      (error) => this.handleError(error)
    );
  }

  // Edit

  onEdit(selection: any) {
    console.log(selection);
    this.objectif = Object.assign({}, selection);
    this.clearDialogMessages();
    this.showDialog = true;
  }

  edit() {
    this.clearDialogMessages();
    this.isDialogOpInProgress = true;
    this.objectifService.update(this.objectif).subscribe(
      (response) => {
        let index = this.objectifs.findIndex(
          (objectif) => objectif.id === response.id
        );
        this.objectifs[index] = response;
        this.isDialogOpInProgress = false;
        this.showDialog = false;
        this.showMessage({
          severity: 'success',
          summary: 'Objectif modifiée avec succès',
        });
        this.load();
      },
      (error) => this.handleError(error)
    );
  }

  isEditing() {
    return !!this.objectif.id;
  }

  // Deletion

  onDelete(selection: any) {
    this.confirmationService.confirm({
      message: 'Etes-vous sûr de vouloir supprimer ?',
      accept: () => {
        this.delete(selection);
      },
    });
  }

  delete(selection: any) {
    console.log(selection.id);
    this.isOpInProgress = true;
    this.objectifService.delete(selection.id).subscribe(
      () => {
        this.objectifs = this.objectifs.filter(
          (objectif) => objectif.id !== selection.id
        );
        selection = null;
        this.isOpInProgress = false;
        this.totalRecords--;
        this.showMessage({
          severity: 'success',
          summary: 'Objectif supprimé avec succès',
        });
      },
      (error) => {
        console.error(JSON.stringify(error));
        this.isOpInProgress = false;
        this.showMessage({ severity: 'error', summary: error.error.message });
      }
    );
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
