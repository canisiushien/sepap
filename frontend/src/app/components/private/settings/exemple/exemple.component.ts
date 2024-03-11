import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ConfirmationService, LazyLoadEvent, MenuItem, Message } from 'primeng/api';
import { Exemple } from 'src/app/models/exemple';
import { ExempleService } from 'src/app/services/exemple.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-exemple',
  templateUrl: './exemple.component.html',
  styleUrls: ['./exemple.component.scss']
})
export class ExempleComponent implements OnInit, OnDestroy {
  
  @ViewChild('dtf') form!: NgForm;

  timeoutHandle: any;
  totalRecords!: number;
  recordsPerPage = environment.recordsPerPage;
  exemples!: Exemple[]
  selection: any;
  exemple: Exemple = {};
  enableCreate = true;
  isLoading!: boolean;
  isOpInProgress!: boolean;
  isDialogOpInProgress!: boolean;
  showDialog = false;
  message: any;
  dialogErrorMessage: any;
  contextMenuItems!: MenuItem[];

  constructor(
    private confirmationService: ConfirmationService,
    private exempleService: ExempleService,
  ) { }

  ngOnInit(): void {
    this.contextMenuItems = [
      { label: 'Editer', icon: 'pi pi-fw pi-pencil', command: () => this.onEdit() },
      { label: 'Supprimer', icon: 'pi pi-fw pi-trash', command: () => this.onDelete() }
    ];
    this.load();
  }

  ngOnDestroy() {
    clearTimeout(this.timeoutHandle);
  }

  load(event?: LazyLoadEvent) {
    this.isLoading = true;
    this.exempleService.getAll(event).subscribe(response => {
      this.isLoading = false;
      this.exemples = response.exemples;
      //this.totalRecords = response.totalCount;
      // console.log( this.exemples)

    }, error => {
      this.message = { severity: 'error', summary: error.error };
      console.error(JSON.stringify(error));
    });
  }

  save() {
    if (this.exemple.id) {
      this.edit();
    } else {
      this.create();
    }
  }

  //Creation

  onCreate() {
    this.exemple = {};
    this.clearDialogMessages();
    this.form.resetForm();
    this.showDialog = true;
  }

  create() {
    this.clearDialogMessages();
    this.isDialogOpInProgress = true;
    this.exempleService.create(this.exemple).subscribe(response => {
      if (this.exemples.length !== this.recordsPerPage) {
        this.exemples.push(response);
        this.exemples = this.exemples.slice();
      }
      this.totalRecords++;
      this.isDialogOpInProgress = false;
      this.showDialog = false;
      this.showMessage({ severity: 'success', summary: 'Exemple créée avec succes' });
    }, error => this.handleError(error));
  }

  // Edit

  onEdit() {
    this.exemple = Object.assign({}, this.selection);
    this.clearDialogMessages();
    this.showDialog = true;
  }

  edit() {
    this.clearDialogMessages();
    this.isDialogOpInProgress = true;
    this.exempleService.update(this.exemple).subscribe(response => {
      let index = this.exemples.findIndex(exemple => exemple.id === response.id);
      this.exemples[index] = response;
      this.isDialogOpInProgress = false;
      this.showDialog = false;
      this.showMessage({ severity: 'success', summary: 'Exemple modifiée avec succès' });
    }, error => this.handleError(error));
  }

  isEditing() {
    return !!this.exemple.id;
  }

  // Deletion

  onDelete() {
    this.confirmationService.confirm({
      message: 'Etes-vous sur de vouloir supprimer cette exemple ?',
      accept: () => {
        this.delete();
      }
    });
  }

  delete() {
    this.isOpInProgress = true;
    this.exempleService.delete(this.selection.id).subscribe(() => {
      this.exemples = this.exemples.filter(exemple => exemple.id !== this.selection.id);
      this.selection = null;
      this.isOpInProgress = false;
      this.totalRecords--;
      this.showMessage({ severity: 'success', summary: 'Exemple supprimée avec succès' });
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
