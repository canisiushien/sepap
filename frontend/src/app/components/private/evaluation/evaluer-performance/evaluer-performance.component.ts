import { CritereGouvernanceService } from 'src/app/services/parametrage/critere-gouvernance.service';
import { StructureService } from 'src/app/services/parametrage/structure.service';
import { ExerciceService } from './../../../../services/parametrage/exercice.service';
import { Structure } from 'src/app/models/parametrage/structure';
import { EvaluationGouvernance } from 'src/app/models/performance/evaluation-gouvernance';
import { EvaluationGouvernanceService } from './../../../../services/performance/evaluation-gouvernance.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { LazyLoadEvent, Message } from 'primeng/api';
import { AuthenticationService } from 'src/app/services/parametrage/authentication.service';
import { MinistereService } from 'src/app/services/parametrage/ministere.service';
import { environment } from 'src/environments/environment';
import { NgForm } from '@angular/forms';
import { Ministere } from 'src/app/models/parametrage/ministere';
import { Exercice } from 'src/app/models/parametrage/exercice';
import { CritereGouvernance } from 'src/app/models/performance/critere-gouvernance';

@Component({
  selector: 'app-evaluer-performance',
  templateUrl: './evaluer-performance.component.html',
  styleUrls: ['./evaluer-performance.component.scss']
})
export class EvaluerPerformanceComponent implements OnInit {



  @ViewChild('dtf') form!: NgForm;

  timeoutHandle: any;
  totalRecords!: number;
  recordsPerPage = environment.recordsPerPage;
  evaluationGouvernances!: EvaluationGouvernance[]
  evaluationGouvernance: EvaluationGouvernance = {};
  ministeres: Ministere[]=[];
  ministere: Ministere={};
  structures: Structure[]=[];
  structure: Structure={};
  exercice: Exercice={};
  exercices: Exercice[]=[];
  critere: CritereGouvernance={};
  criteres: CritereGouvernance[]=[];
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
  impactDetail=false;
  permissions:any
  constructor(private evaluationGService: EvaluationGouvernanceService,
    public authService: AuthenticationService,
    private ministereService:MinistereService,
    private exerciceService: ExerciceService,
    private structureService: StructureService,
    private critereService: CritereGouvernanceService) { }

  ngOnInit(): void {
    this.permissions=this.authService.getPrivilege();
    this.loadMinistere();
    this.loadExercice();
    this.loadCritere();

  }
  load(event: LazyLoadEvent) {
     this.isLoading = true;
     this.evaluationGService.getAll().subscribe(response => {
     this.isLoading = false;
     this.evaluationGouvernances = response.evaluationGouvernances;

   }, error => {
     this.message = { severity: 'error', summary: error.error };
     console.error(JSON.stringify(error));
   });
 }

 loadMinistere(event?: LazyLoadEvent) {

  this.ministereService.getAll(event).subscribe(
    (response) => {

      this.ministeres = response.ministeres;

    },
    (error) => {
      this.message = { severity: 'error', summary: error.error };
      console.error(JSON.stringify(error));
    }
  );
}

loadExercice(event?: LazyLoadEvent) {

 this.exerciceService.getAll(event).subscribe(response => {

   this.exercices = response.exercices;

 }, error => {
   this.message = { severity: 'error', summary: error.error };
   console.error(JSON.stringify(error));
 });
}

loadCritere(event?: LazyLoadEvent) {
  this.isLoading = true;
 this.critereService.getAll(event).subscribe(response => {
   this.isLoading = false;
   this.criteres = response.criteres;

 }, error => {
   this.message = { severity: 'error', summary: error.error };
   console.error(JSON.stringify(error));
 });
}
changeMinistere(selection:any){
  let id =  selection.value.id;
  this.getStructureByMinistere(id);

 }

 getStructureByMinistere(id:number){
  this.structureService.getStructureByMinistereId(id).subscribe(response => {

    this.structures = response.structures;

  }, error => {
    this.message = { severity: 'error', summary: error.error };
    console.error(JSON.stringify(error));
  });
 }
isEditing() {
  return !!this.evaluationGouvernance.id;
}

save() {
  if (this.evaluationGouvernance.id) {
    this.edit();
  } else {
    this.create();
  }
}

create() {
  this.clearDialogMessages();
  this.isDialogOpInProgress = true;
  this.evaluationGService.create(this.evaluationGouvernance).subscribe(response => {
    if (this.evaluationGouvernances.length !== this.recordsPerPage) {
      this.evaluationGouvernances.push(response);
      this.evaluationGouvernances = this.evaluationGouvernances.slice();
    }
    this.totalRecords++;
    this.isDialogOpInProgress = false;
    this.showDialog = false;
    this.showMessage({ severity: 'success', summary: 'Informations d\'impact ajoutées avec succès' });
  }, error => this.handleError(error));
}

edit() {
  this.clearDialogMessages();
  this.isDialogOpInProgress = true;
  this.evaluationGService.update(this.evaluationGouvernance).subscribe(response => {
    let index = this.evaluationGouvernances.findIndex(evaluationGouvernance => evaluationGouvernance.id === response.id);
    this.evaluationGouvernances[index] = response;
    this.isDialogOpInProgress = false;
    this.showDialog = false;
    this.showMessage({ severity: 'success', summary: 'Données d\'evaluationGouvernance modifiée avec succès' });
  }, error => this.handleError(error));
}

 onCreate() {

  this.evaluationGouvernance = {};
  this.clearDialogMessages();
  this.form.resetForm();
  this.showDialog = true;
 }
  onInfo(selection:any){
    this.evaluationGouvernance = Object.assign({}, selection);
    this.clearDialogMessages();
    this.impactDetail=true;
  }

  onEdit(selection:any) {
   this.evaluationGouvernance = Object.assign({}, selection);
   this.clearDialogMessages();
   this.showDialog = true;
 }
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
