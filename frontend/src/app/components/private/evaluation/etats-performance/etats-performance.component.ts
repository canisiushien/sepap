import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { LazyLoadEvent, Message } from 'primeng/api';
import { Exercice } from 'src/app/models/parametrage/exercice';
import { Ministere } from 'src/app/models/parametrage/ministere';
import { Structure } from 'src/app/models/parametrage/structure';
import { Performer } from 'src/app/models/performance/performer';
import { ProgrammationExport } from 'src/app/models/programmation/programmationExport';
import { AuthenticationService } from 'src/app/services/parametrage/authentication.service';
import { ExerciceService } from 'src/app/services/parametrage/exercice.service';
import { MinistereService } from 'src/app/services/parametrage/ministere.service';
import { StructureService } from 'src/app/services/parametrage/structure.service';
import { PerformanceService } from 'src/app/services/performance/performance.service';
import { PerformerService } from 'src/app/services/performance/performer.service';
import { ProgrammationService } from 'src/app/services/programmation/programmation.service';
import { environment } from 'src/environments/environment';
import { saveAs } from 'file-saver';
import { Performance } from 'src/app/models/performance/performance';
@Component({
  selector: 'app-etats-performance',
  templateUrl: './etats-performance.component.html',
  styleUrls: ['./etats-performance.component.scss']
})
export class EtatsPerformanceComponent implements OnInit {
  @ViewChild('dtf') form!: NgForm;
  btnExportRapport: boolean=true;
  btnExportProgramme: boolean=true;
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
  performances: Performance[]=[];
  showDialogRapport: boolean =false;
  hiddenStructure: boolean =false;
  isLoading: boolean=false;
  //performances: Performance[]=[];
  btnMin: boolean=false;
  btnStruct: boolean=false;
  constructor(private programmationService: ProgrammationService,
              private exerciceService: ExerciceService,
              private authenticateService: AuthenticationService,
              private structureService: StructureService,
              private ministereService: MinistereService,
              private performerService: PerformerService,
              private performanceService: PerformanceService
             ) { }

  ngOnInit(): void {
   // this.getExerciceEncours();
      this.loadMinistere();
      this.loadExercice();
      this.choiceSelect();
      this.loadStructure();
   // this.loadPerformance();
  }

  choixExtension(event:any){
   //   this.format1=event.value;
  }

  selectFormat(event:any){
    this.format=event.value;
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
    let id = (this.authenticateService.checkPermission(this.authenticateService.getPrivilege()!,['ROLE_ADMIN']) == true && this.structureID == null) ? null : this.structureID;
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
 hideStructure(hiddenStructure:boolean){
 return hiddenStructure;
 }
 selectMinistere( event: any){
   this.ministereID= event.value;
   this.getStructureByMinistere(this.ministereID)
 }

 selectStructure(event:any){
   this.structureID=event.value;
 }

 selectExercice(event: any){
   this.exerciceID= event.value;
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
