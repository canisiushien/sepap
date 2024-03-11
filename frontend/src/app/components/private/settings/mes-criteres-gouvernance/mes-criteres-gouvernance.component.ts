import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { LazyLoadEvent, MenuItem, Message } from 'primeng/api';
import { EvaluationGouvernanceDTO } from 'src/app/models/parametrage/evaluation-gouvernance-dto';
import { Exercice } from 'src/app/models/parametrage/exercice';
import { AuthenticationService } from 'src/app/services/parametrage/authentication.service';
import { EvaluationGouvernanceService } from 'src/app/services/parametrage/evaluation-gouvernance.service';
import { ExerciceService } from 'src/app/services/parametrage/exercice.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-mes-criteres-gouvernance',
  templateUrl: './mes-criteres-gouvernance.component.html',
  styleUrls: ['./mes-criteres-gouvernance.component.scss']
})
export class MesCriteresGouvernanceComponent implements OnInit {
  isLoading: boolean=false;
  timeoutHandle: any;
  totalRecords!: number;
  recordsPerPage = environment.recordsPerPage;
  dialogErrorMessage: any;
  isDialogOpInProgress: boolean=false;
  isOpInProgress!: boolean;
  message: any;
  enableBtnInfo = false;
  enableBtnEdit = true;
  enableBtnDelete = false;
  evaluationGouvernances:EvaluationGouvernanceDTO[]=[];
  evaluationGouvernance:EvaluationGouvernanceDTO={};
  exercices:Exercice[]=[];
  exercicesAlls:Exercice[]=[];
  public exerciceId!: number;
  showDialog: boolean=false;
  items!: MenuItem[];
  activeItem!:MenuItem;

  constructor(private evalGouvService:EvaluationGouvernanceService,
              private authenticateService:AuthenticationService,
              private exerciceService:ExerciceService) { }

  ngOnInit(): void {
    this.loadExcercices();
    this.loadAllExcercices();
    this.menuTool();
    //this.load(this.exerciceId);
  }
  getExercice(exercice:any){
   this.exerciceId = exercice.id;
   if(this.exerciceId == null){
     this.loadExcercices();
   } else {
    this.load(this.exerciceId);
   }

  }
  load(id:number, event?: LazyLoadEvent) {
    this.isLoading = true;
    let structureId=this.authenticateService.getStructureId() == 0  ?
     1 : this.authenticateService.getStructureId();
    this.evalGouvService.getAllStruct(structureId,id, event).subscribe(response => {
    this.isLoading = false;
    this.evaluationGouvernances = response.evaluationGouvernances;
   }, error => {
     this.message = { severity: 'error', summary: error.error };
     console.error(JSON.stringify(error));
   });
 }

 menuTool(){
  this.items = [
    {label: 'Indic de gouvernance', icon: 'pi pi-fw pi-plus', routerLink: ['/workspace/performances/evaluation-gouvernance']},
    {label: 'Indicateurs', icon: 'pi pi-fw pi-file',routerLink: ['/workspace/performances/mes-criteres-gouvernance'],},
];

this.activeItem = this.items[0];
}

 loadExcercices(event?: LazyLoadEvent) {
  this.isLoading = true;
  this.exerciceService.getExerciceAttente(event).subscribe(
    (response) => {
      this.isLoading = false;
      this.exercices = response.exercices;

      for(let i=0;i<response.exercices.length;i++){
        this.exerciceId = response.exercices[i].id!;
        this.load(this.exerciceId);
      }
    },

    (error) => {
      this.message = { severity: 'error', summary: error.error };
      console.error(JSON.stringify(error));
    }
  );
}

loadAllExcercices(event?: LazyLoadEvent) {
  this.isLoading = true;
  this.exerciceService.getAll(event).subscribe(
    (response) => {
      this.isLoading = false;
      this.exercicesAlls = response.exercices;
    },

    (error) => {
      this.message = { severity: 'error', summary: error.error };
      console.error(JSON.stringify(error));
    }
  );
}
  // Errors

  handleError(error: HttpErrorResponse) {
    console.error(`Processing Error: ${JSON.stringify(error)}`);
    this.isDialogOpInProgress = false;
    this.dialogErrorMessage = error.error.title;
  }

   // Edit

   onEdit(selection: any) {
    this.evaluationGouvernance = Object.assign({}, selection);
    this.evaluationGouvernance.nonapplicable = !this.evaluationGouvernance.nonapplicable;
    this.clearDialogMessages();
    this.showDialog = true;
  }


  edit() {
    this.clearDialogMessages();
    this.isDialogOpInProgress = true;
    this.evaluationGouvernance.nonapplicable = 
    this.evaluationGouvernance.nonapplicable == false ? true:false; 
    this.evalGouvService.update(this.evaluationGouvernance).subscribe(
      (response) => {
        let index = this.evaluationGouvernances.findIndex(
          (evaluationGouvernance) => evaluationGouvernance.id === response.id
        );
        this.evaluationGouvernances[index] = response;
        this.isDialogOpInProgress = false;
        this.showDialog = false;
        this.showMessage({
          severity: 'success',
          summary: 'Critère modifié avec succès',
        });
        this.load(this.exerciceId);
      },
      (error) => this.handleError(error)
    );
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
