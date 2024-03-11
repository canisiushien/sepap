import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ConfirmationService, LazyLoadEvent, Message } from 'primeng/api';
import { GrillePerformance } from 'src/app/models/parametrage/grillePerformance';
import { GrillePerformanceService } from 'src/app/services/parametrage/grille-performance.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-grille-performance',
  templateUrl: './grille-performance.component.html',
  styleUrls: ['./grille-performance.component.scss']
})
export class GrillePerformanceComponent implements OnInit {
  @ViewChild('dtf') form!: NgForm;

  timeoutHandle: any;
  totalRecords!: number;
  recordsPerPage = environment.recordsPerPage;
  grillePerformances!: GrillePerformance[]
  // selection: any;
  grillePerformance: GrillePerformance = {};
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
  // contextMenuItems!: MenuItem[];

  constructor(private grillePerformanceService:GrillePerformanceService,
    private confirmationService: ConfirmationService) { }

  ngOnInit(): void {
    // this.contextMenuItems = [
    //   { label: 'Editer', icon: 'pi pi-fw pi-pencil', command: () => this.onEdit() },
    //   { label: 'Supprimer', icon: 'pi pi-fw pi-trash', command: () => this.onDelete() }
    // ];
    this.load();
  }

  load(event?: LazyLoadEvent) {
     this.isLoading = true;
    this.grillePerformanceService.getAll(event).subscribe(response => {
      this.isLoading = false;
      this.grillePerformances = response.grillePerformances;

    }, error => {
      this.message = { severity: 'error', summary: error.error };
      console.error(JSON.stringify(error));
    });
  }

  save() {
    if (this.grillePerformance.id) {
      this.edit();
    } else {
      this.create();
    }
  }

   //Creation

   onCreate() {
    this.grillePerformance = {};
    this.clearDialogMessages();
    this.form.resetForm();
    this.showDialog = true;
  }

  create() {
    this.clearDialogMessages();
    this.isDialogOpInProgress = true;
    this.grillePerformanceService.create(this.grillePerformance).subscribe(response => {
      if (this.grillePerformances.length !== this.recordsPerPage) {
        this.grillePerformances.push(response);
        this.grillePerformances = this.grillePerformances.slice();
      }
      this.totalRecords++;
      this.isDialogOpInProgress = false;
      this.showDialog = false;
      this.showMessage({ severity: 'success', summary: 'Grille performanceService créée avec succes' });
    }, error => this.handleError(error));
  }


   // Edit

   onEdit(selection:any) {
    this.grillePerformance = Object.assign({}, selection);
    this.clearDialogMessages();
    this.showDialog = true;
  }

  edit() {
    this.clearDialogMessages();
    this.isDialogOpInProgress = true;
    this.grillePerformanceService.update(this.grillePerformance).subscribe(response => {
      let index = this.grillePerformances.findIndex(grillePerformance => grillePerformance.id === response.id);
      this.grillePerformances[index] = response;
      this.isDialogOpInProgress = false;
      this.showDialog = false;
      this.showMessage({ severity: 'success', summary: 'Grille performance modifiée avec succès' });
    }, error => this.handleError(error));
  }

  isEditing() {
    return !!this.grillePerformance.id;
  }

  // Deletion

  onDelete(selection:any) {
    this.confirmationService.confirm({
      message: 'Etes-vous sur de vouloir supprimer cet grille performance?',
      accept: () => {
        this.delete(selection);
      }
    });
  }

  delete(selection:any) {
    this.isOpInProgress = true;
    this.grillePerformanceService.delete(selection.id).subscribe(() => {
      this.grillePerformances = this.grillePerformances.filter(grillePerformance => grillePerformance.id !== selection.id);
      selection = null;
      this.isOpInProgress = false;
      this.totalRecords--;
      this.showMessage({ severity: 'success', summary: 'Grille performance supprimée avec succès' });
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
