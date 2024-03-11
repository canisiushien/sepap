import { ExerciceService } from 'src/app/services/parametrage/exercice.service';
import { StructureService } from 'src/app/services/parametrage/structure.service';
import { ParametrerImpactService } from './../../../../services/performance/parametrer-impact.service';
import { Ministere } from 'src/app/models/parametrage/ministere';
import { ImpactService } from 'src/app/services/performance/impact.service';
import { MinistereService } from 'src/app/services/parametrage/ministere.service';
import { ParametreImpact } from 'src/app/models/performance/parametrer-impact';
import { ContribuerService } from './../../../../services/performance/contribuer.service';
import { Component, OnInit, ViewChild } from '@angular/core';
import { LazyLoadEvent, Message } from 'primeng/api';
import { Contribuer } from 'src/app/models/performance/contribuer';
import { AuthenticationService } from 'src/app/services/parametrage/authentication.service';
import { environment } from 'src/environments/environment';
import { NgForm } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { Impact } from 'src/app/models/performance/impact';
import { Structure } from 'src/app/models/parametrage/structure';
import { Exercice } from 'src/app/models/parametrage/exercice';

@Component({
  selector: 'app-contribuer',
  templateUrl: './contribuer.component.html',
  styleUrls: ['./contribuer.component.scss']
})
export class ContribuerComponent implements OnInit {

  @ViewChild('dtf') form!: NgForm;
  timeoutHandle: any;
  totalRecords!: number;
  recordsPerPage = environment.recordsPerPage;
  contribution: Contribuer={};
  contributions: Contribuer[]=[];
  impact: Impact={};
  impacts:Impact[]=[];
  parametreImpact: ParametreImpact={};
  parametreImpacts: ParametreImpact[]=[];
  ministere: Ministere = {};
  ministeres: Ministere[]=[];
  structure: Structure = {};
  structures: Structure[]=[];
  exercice: Exercice = {};
  exercices: Exercice[]=[];
  message: any;
  permissions: any;
  isOpInProgress!: boolean;
  enableCreate = true;
  enableBtnInfo = true;
  enableBtnEdit = true;
  enableBtnDelete = false;
  isLoading!: boolean;
  isDialogOpInProgress!: boolean;
  showDialog = false;
  dialogErrorMessage: any;
  impactID: any;
  ministereID: any;
  structureID: any;
  exerciceID: any;
  detailDialog:boolean= false;
  constructor(
    private contribuerService: ContribuerService,
    public authService: AuthenticationService,
    private ministereService: MinistereService,
    private impactService: ImpactService,
    private parametreImpactService: ParametrerImpactService,
    private structureService: StructureService,
    private exerciceService: ExerciceService) { }

  ngOnInit(): void {
    this.permissions=this.authService.getPrivilege();
    this.getMinistere();
    this.loadExercice();
  }

  load(event?: LazyLoadEvent) {
    this.isLoading = true;
    this.contribuerService.getAll().subscribe(response => {
    this.isLoading = false;
    this.contributions = response.contributions;

  }, error => {
    this.message = { severity: 'error', summary: error.error };
    console.error(JSON.stringify(error));
  });
}



isEditing() {
  return !!this.contribution.id;
}

save() {
  if (this.contribution.id) {
    this.edit();
  } else {
    this.create();
  }
}

onCreate() {

  this.contribution = {};
  this.clearDialogMessages();
  this.form.resetForm();
  this.showDialog = true;
 }

create() {
  this.clearDialogMessages();
  this.isDialogOpInProgress = true;
  console.error("-------imp-------", this.contribution);
  this.contribuerService.create(this.contribution).subscribe(response => {

    this.isDialogOpInProgress = false;
    this.showDialog = false;
    this.showMessage({ severity: 'success', summary: 'Informations d\'impact ajoutées avec succès' });
  }, error => this.handleError(error));
}

// Détail

  onInfo(selection:any){
  this.contribution = Object.assign({}, selection);
  this.clearDialogMessages();
  this.detailDialog = true;
  }

//  editer
onEdit(selection:any) {
  this.contribution = Object.assign({}, selection);
  this.clearDialogMessages();
  this.showDialog = true;
}

edit() {
  this.clearDialogMessages();
  this.isDialogOpInProgress = true;
  this.contribuerService.update(this.contribution).subscribe(response => {
    let index = this.contributions.findIndex(contribution => contribution.id === response.id);
    this.contributions[index] = response;
    this.isDialogOpInProgress = false;
    this.showDialog = false;
    this.showMessage({ severity: 'success', summary: 'Données de contribution modifiée avec succès' });
  }, error => this.handleError(error));
}

// volet impact

getStructureByMinistere(id:number){
  this.structureService.getStructureByMinistereId(id).subscribe(response => {

    this.structures = response.structures;

  }, error => {
    this.message = { severity: 'error', summary: error.error };
    console.error(JSON.stringify(error));
  });
  }

loadParametreImpact(event: any) {
  this.isLoading = true;
  this.parametreImpactService.getParametreImpactByIdIM(this.ministereID, event.value.id).subscribe(response => {
  this.isLoading = false;
  this.parametreImpacts = response.parametreImpacts;
}, error => {
  this.message = { severity: 'error', summary: error.error };
  console.error(JSON.stringify(error));
});
}


loadImpact(event:any) {
  console.error("--------------", event);
  this.isLoading = true;
  this.ministereID=event.value.id;
  this.impactService.getImpactByStructure(event.value.id).subscribe(response => {
  this.isLoading = false;
  this.impacts = response.impactStructures;

}, error => {
  this.message = { severity: 'error', summary: error.error };
  console.error(JSON.stringify(error));
});

  this.getStructureByMinistere(event.value.id);
}


getMinistere() {
this.isLoading = true;
this.ministereService.getAll().subscribe(
 (response) => {
   this.isLoading = false;
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
selectMinistere( event: any){
  this.ministereID= event.value;
  this.getStructureByMinistere(this.ministereID);

 }


 selectStructure(event:any){
  this.structureID= event == null ? null : event.value;
 }

 selectExercice(event: any){
  this.exerciceID= event == null ? null : event.value;
 }

 getContributionByStructExe() {
  this.isLoading = true;
  this.contribuerService.getByStructureAndExercice(this.exerciceID,this.structureID).subscribe(response => {
  this.isLoading = false;
  this.contributions = response.contributions;

}, error => {
  this.message = { severity: 'error', summary: error.error };
  console.error(JSON.stringify(error));
});
}

getContributionByMinExe() {
  this.isLoading = true;
  this.contribuerService.getByMinistereAndExercice(this.ministereID,this.exerciceID).subscribe(response => {
  this.isLoading = false;
  this.contributions = response.contributions;

}, error => {
  this.message = { severity: 'error', summary: error.error };
  console.error(JSON.stringify(error));
});
}

getContrbutions(){

  if(this.structureID== null){
    this.getContributionByMinExe();
 } else{
   this.getContributionByStructExe();
 }
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
