import { ParametrerImpactService } from './../../../../services/performance/parametrer-impact.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { LazyLoadEvent, Message } from 'primeng/api';
import { Ministere } from 'src/app/models/parametrage/ministere';
import { ParametreImpact } from 'src/app/models/performance/parametrer-impact';
import { AuthenticationService } from 'src/app/services/parametrage/authentication.service';
import { MinistereService } from 'src/app/services/parametrage/ministere.service';
import { ImpactService } from 'src/app/services/performance/impact.service';
import { environment } from 'src/environments/environment';
import { Impact } from 'src/app/models/performance/impact';
import { ExerciceService } from 'src/app/services/parametrage/exercice.service';
import { Exercice } from 'src/app/models/parametrage/exercice';

@Component({
  selector: 'app-parametre-performance',
  templateUrl: './parametre-performance.component.html',
  styleUrls: ['./parametre-performance.component.scss']
})
export class ParametrePerformanceComponent implements OnInit {

  @ViewChild('dtf') form!: NgForm;

  timeoutHandle: any;
  totalRecords!: number;
  recordsPerPage = environment.recordsPerPage;
  parametreImpacts!: ParametreImpact[]
  parametreImpact: ParametreImpact = {};
  impacts!: Impact[]
  impact: Impact = {};
  // selection: any;
  ministeres: Ministere[]=[];
  ministere: Ministere={};
  exercice: Exercice={};
  exercices: Exercice[]=[];
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
  permissions:any;
  exerciceID:any;
  ministereID:any;
  id:any;
  constructor(
    public authService: AuthenticationService,
    private ministereService:MinistereService,
    private impactService: ImpactService,
    private exerciceService: ExerciceService,
    private parametreImpactService: ParametrerImpactService) { }

  ngOnInit(): void {
    this.permissions=this.authService.getPrivilege();
    this.getMinistere();
    this.loadExercice();
    this.reload();
  }
  reload(){
   if(this.ministereID == null ||this.exerciceID == null){

   }
   else {
    this.load();
   }
}

  load(event?: LazyLoadEvent) {
    this.isLoading = true;
    this.parametreImpactService.getParametreImpactById(this.ministereID, this.exerciceID).subscribe(response => {
    this.isLoading = false;
    this.parametreImpacts = response.parametreImpacts;
  }, error => {
    this.message = { severity: 'error', summary: error.error };
    console.error(JSON.stringify(error));
  });
}

getImpact(id:number) {
  this.isLoading = true;
  this.impactService.getImpactByStructure(id).subscribe(response => {
  this.isLoading = false;
  this.impacts = response.impactStructures;

}, error => {
  this.message = { severity: 'error', summary: error.error };
  console.error(JSON.stringify(error));
});
}

getMinistere(event?: LazyLoadEvent) {
 this.isLoading = true;
 this.ministereService.getAll(event).subscribe(
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

getExcerciceEncours(event?: LazyLoadEvent) {
  this.isLoading = true;
  this.exerciceService.getExerciceEncours(event).subscribe(
    (response) => {
      this.isLoading = false;
      this.exercice = response;
     this.exercices.push(this.exercice);
    },
    (error) => {
      this.message = { severity: 'error', summary: error.error };
      console.error(JSON.stringify(error));
    }
  );

}

changeMinistereGlobal(selection:any){
  let id = selection.value.id;
 // this.load(id);
 }

 selectExercice(event: any){
  this.exerciceID= event == null ? null : event.value;
 }

 selectMinistere( event: any){
 
  this.ministereID= event == null ? null : event.value;
 }
changeMinistere(selection:any){
 let id = selection.value.id;
 this.getImpact(id);
}

isEditing() {
 return !!this.parametreImpact.id;
}

save() {
 if (this.parametreImpact.id) {
   this.edit();
 } else {
   this.create();
 }
}

create() {
 this.clearDialogMessages();
 this.isDialogOpInProgress = true;
 this.parametreImpactService.create(this.parametreImpact).subscribe(response => {
   this.isDialogOpInProgress = false;
   this.showDialog = false;
   this.showMessage({ severity: 'success', summary: 'Informations d\'parametreImpact ajoutées avec succès' });
 }, error => this.handleError(error));
}

edit() {
 this.clearDialogMessages();
 this.isDialogOpInProgress = true;
 this.parametreImpactService.update(this.parametreImpact).subscribe(response => {
   let index = this.parametreImpacts.findIndex(parametreImpacts => parametreImpacts.id === response.id);
   this.parametreImpacts[index] = response;
   this.isDialogOpInProgress = false;
   this.showDialog = false;
   this.showMessage({ severity: 'success', summary: 'Données d\'parametreImpacts modifiée avec succès' });
 }, error => this.handleError(error));
}

onCreate() {

 this.parametreImpact = {};
 this.clearDialogMessages();
 this.form.resetForm();
 this.showDialog = true;
}

 onInfo(selection:any){
   this.parametreImpact = Object.assign({}, selection);
   this.clearDialogMessages();
   this.impactDetail=true;
 }

 onEdit(selection:any) {
  this.parametreImpact = Object.assign({}, selection);
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
