import { PeriodeService } from 'src/app/services/parametrage/periode.service';
import { Ministere } from 'src/app/models/parametrage/ministere';
import { MinistereService } from 'src/app/services/parametrage/ministere.service';
import { Structure } from 'src/app/models/parametrage/structure';
import { StructureService } from 'src/app/services/parametrage/structure.service';
import { Exercice } from 'src/app/models/parametrage/exercice';
import { AuthenticationService } from 'src/app/services/parametrage/authentication.service';
import { ProgrammationService } from './../../../../services/programmation/programmation.service';
import { Component, OnInit, ViewChild } from '@angular/core';
import { StructureActiviteData } from 'src/app/models/stats/structure-activite-data';
import { ProgrammationExport } from 'src/app/models/programmation/programmationExport';
import { ExerciceService } from 'src/app/services/parametrage/exercice.service';
import { ConfirmationService, LazyLoadEvent, MenuItem, Message } from 'primeng/api';
import { DomSanitizer } from '@angular/platform-browser';
import { saveAs } from 'file-saver';
import { NgForm } from '@angular/forms';
import { Performer } from 'src/app/models/performance/performer';
import { Performance } from 'src/app/models/performance/performance';
import { HttpErrorResponse } from '@angular/common/http';
import { PerformerService } from 'src/app/services/performance/performer.service';
import { PerformanceService } from 'src/app/services/performance/performance.service';
import { environment } from 'src/environments/environment';
import { Periode } from 'src/app/models/parametrage/periode';



@Component({
  selector: 'app-performers',
  templateUrl: './performers.component.html',
  styleUrls: ['./performers.component.scss']
})
export class PerformersComponent implements OnInit {
  @ViewChild('dtf') form!: NgForm;
  btnExportRapport: boolean=true;
  btnExportProgramme: boolean=true;
  btnHiddenMinistere:boolean=false;
  exercice: Exercice={};
  exercices: Exercice[]=[];
  structure: Structure={};
  structures: Structure[]=[];
  ministere: Ministere={};
  ministeres:Ministere[]=[];
  timeoutHandle: any;
  message: any;
  ministereID: any;
  structureID: any;
  periodeID: any;
  totalRecords!: number;
  recordsPerPage = environment.recordsPerPage;
  isDialogOpInProgress!: boolean;
  dialogErrorMessage: any;
  exerciceID: any;
  performers: Performer[]=[];
  performer: Performer = {};
  format?:any;
  isOpInProgress!: boolean;
  loading:boolean=false;
  showDialog: boolean=false;
  programmationExports: ProgrammationExport[]=[];
  programmationExport: ProgrammationExport={};
  permissions: any;
  showDialogRapport: boolean =false;
  hiddenStructure: boolean =false;
  isLoading: boolean=false;
  performances: Performance[]=[];
  btnMin: boolean=false;
  btnStruct: boolean=false;
  showDialogPerformance:boolean=false;
  formats = Array({"name":"PDF"},{"name":"Word"});
  periodes: Periode[] = [];
  ext!: string;
  structs: Structure[]=[];
  constructor(private programmationService: ProgrammationService,
              private exerciceService: ExerciceService,
              private authenticateService: AuthenticationService,
              private structureService: StructureService,
              private ministereService: MinistereService,
              private performerService: PerformerService,
              private performanceService: PerformanceService,
              private authenticationService: AuthenticationService,
              private periodeService: PeriodeService
             ) { }

  ngOnInit(): void {
    //this.btnStruct=!this.authenticationService.checkPermission(this.permissions, ['ROLE_RESP_DGESS','ROLE_DIR_DGESS','RESP_STRUCT','ROLE_ADMIN']);
   // this.getExerciceEncours();
      this.loadMinistere();
      this.loadExercice();
      this.choiceSelect();
      //this.loadStructure();
      this.loadPeriodes();
   // this.loadPerformance();
  // this.btnHiddenMinistere=this.authenticationService.checkPermission(this.permissions, ['ROLE_RESP_STRUCT']);
   this.selectMinistere(this.ministere);
  }

  choixExtension(event:any){
   //   this.format1=event.value;
  }

  getExerciceEncours() {

    this.exerciceService.getExerciceEncours().subscribe(
      (response) => {

        this.exercice = response;
      },
      (error) => {
        this.message = { severity: 'error', summary: error.error };
        console.error(JSON.stringify(error));
      }
    );

  }
  onCalculPerformance(){
    this.clearDialogMessages();
    this.form.resetForm();
    this.showDialog =true;

  }
  calculPerformance(){
    this.isDialogOpInProgress = true;
    this.loading = true;
    this.performer.userId = this.authenticateService.getStructureId();
    this.performerService.calculPerformance(this.performer).subscribe(response => {
        this.isDialogOpInProgress = false;
        this.showDialog = false;
        this.loading = false;
        this.showMessage({ severity: 'success', summary: 'Calcul des performances éffectue avec succès' });
      },
      (error) => this.handleError(error),

    );
    this.loading = false;
  }

  loadPerformance(event?: LazyLoadEvent) {
    this.isLoading = true;
    this.performer.userId = this.authenticateService.getStructureId();
    let id = (this.authenticateService.checkPermission(this.authenticateService.getPrivilege()!,['ROLE_ADMIN']) == true && this.structureID == null) ? this.authenticateService.getStructureId() : this.structureID;
    this.performer.structureId = (this.authenticateService.checkPermission(this.authenticateService.getPrivilege()!,['ROLE_ADMIN']) == false && this.structureID == null) ? this.authenticateService.getStructureId(): id;
    this.performer.ministerId = this.ministereID;
    this.performer.exerciceId = this.exerciceID;
    this.performanceService.getAll(this.performer,event).subscribe(
      (response) => {
        this.isLoading = false;
        this.performances = response.performances;
      },
      (error) => {
        this.message = { severity: 'error', summary: error.error.title };
      }
    );
  }

  loadPeriodes(event?: LazyLoadEvent){
    this.periodeService.getAll(event).subscribe(response => {
      this.periodes = response.periodes;
    }, error => {
      this.message = { severity: 'error', summary: error.error };
      console.error(JSON.stringify(error));
    });
  }
  onExportRapport(){
    this.showDialogRapport=true;
  }
  exportRapportActivites(){
    const data ={
      ministereId:this.ministereID,
      structureId: this.structureID,
      format: this.format,
      exerciceId: this.exerciceID,
      currentStructureId:this.authenticateService.getStructureId()
    };

    this.programmationService.exportRapport(data).subscribe(blob => saveAs(blob, "Rapport_activite_"+data.ministereId+"."+data.format),

    )
    this.showDialog=false;
  }

  onExport(){
    this.showDialog=true;
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


 loadStructure(event?: LazyLoadEvent) {
  this.isLoading = true;
  this.structureService.getAll(event).subscribe(
    (response) => {
      this.isLoading = false;
      this.structures = response.structures;
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

 getStructureByMinistere(id:number){
  this.structureService.getStructureByMinistereId(id).subscribe(response => {

    this.structures = response.structures;
  }, error => {
    this.message = { severity: 'error', summary: error.error };
    console.error(JSON.stringify(error));
  });
 }
 getStructureById(id:number){
 this.structureService.getStructureById(id).subscribe(response => {
   this.structures = response;
 }, error => {
   this.message = { severity: 'error', summary: error.error };
   console.error(JSON.stringify(error));
 });
}
 hideStructure(hiddenStructure:boolean){
 return hiddenStructure;
 }
 selectMinistere( event: any){
   this.ministereID= event == null ? null: event.value;
   if(this.ministereID==null){
    this.getStructureById(this.authenticationService.getStructureId());
   }else{
   this.getStructureByMinistere(this.ministereID);
   }
 }

 selectStructure(event:any){
   this.structureID=event.value;
 }

 selectExercice(event: any){
   this.exerciceID= event.value;
 }

 selectPeriode(event:any){
  this.periodeID = event.value;
}

choiceSelect(){
  if(this.authenticateService.checkPermission(this.authenticateService.getPrivilege()!,['ROLE_RESP_DGESS','ROLE_DIR_DGESS'])== true){
    this.btnMin = true;
    this.loadStructure();
    this.loadPerformance();
  }

  if(this.authenticateService.checkPermission(this.authenticateService.getPrivilege()!,['ROLE_RESP_STRUCT','ROLE_FOCAL_STRUCT'])== true){
    this.btnStruct = true;
    this.btnMin = true;
    this.structureID = this.authenticateService.getStructureId();
    this.loadPerformance();
  }
}

selectFormat(event:any){
  this.format=event.value;
  switch (this.format) {
    case "PDF":
      this.ext = "pdf";
      break;
    case "Word":
      this.ext = "docx";
      break;
    case "Excel":
      this.ext = "xlsx";
      break;
    default:
      break;
  }
}

onExportPerformance(){
  this.showDialogPerformance=true;
}

exportPerformance(){
  const data ={
    ministereId:this.ministereID,
    structureId: this.structureID,
    format: this.format,
    exerciceId: this.exerciceID,
    periodeId: this.periodeID,
    currentStructureId:this.authenticationService.getStructureId()
  };

  this.performerService.exportPerformance(data).subscribe(blob => saveAs(blob, "Rapport_performance_"+data.ministereId+"."+this.ext),

  )
  this.showDialogPerformance=false;

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
