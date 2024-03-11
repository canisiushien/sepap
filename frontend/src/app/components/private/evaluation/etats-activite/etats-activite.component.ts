import { MinistereService } from 'src/app/services/parametrage/ministere.service';
import { ExerciceService } from 'src/app/services/parametrage/exercice.service';
import { Structure } from 'src/app/models/parametrage/structure';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { LazyLoadEvent, Message, MessageService } from 'primeng/api';
import { Evaluation } from 'src/app/models/evaluation/evaluation';
import { TacheEvaluation } from 'src/app/models/evaluation/tache_evaluation';
import { Programmation } from 'src/app/models/programmation/programmation';
import { ProgrammationForEvaluationDTO } from 'src/app/models/programmation/programmation-for-evaluation-dto';
import { Taches } from 'src/app/models/programmation/taches';
import { EvaluationService } from 'src/app/services/evaluation/evaluation.service';
import { AuthenticationService } from 'src/app/services/parametrage/authentication.service';
import { ActiviteService } from 'src/app/services/programmation/activite.service';
import { AmendementActiviteService } from 'src/app/services/programmation/amendement-activite.service';
import { ProgrammationService } from 'src/app/services/programmation/programmation.service';
import { environment } from 'src/environments/environment';
import { StructureService } from 'src/app/services/parametrage/structure.service';
import { Exercice } from 'src/app/models/parametrage/exercice';
import { Ministere } from 'src/app/models/parametrage/ministere';
import { saveAs } from 'file-saver';
import { AnyObject } from 'chart.js/types/basic';
@Component({
  selector: 'app-etats-activite',
  templateUrl: './etats-activite.component.html',
  styleUrls: ['./etats-activite.component.scss']
})
export class EtatsActiviteComponent implements OnInit {
  ext!: string;

  constructor(private route:ActivatedRoute,private router:Router,
    private structureService: StructureService,
    private programmationService: ProgrammationService,
    private amendementActiviteService:AmendementActiviteService,
    private authenticationService: AuthenticationService,
    private evaluationService: EvaluationService,
    private fb: FormBuilder,private messageService: MessageService,
    private exerciceService: ExerciceService,
    private ministereService:MinistereService
    ) { }
  timeoutHandle: any;
  totalRecords!: number;
  recordsPerPage = environment.recordsPerPage;
  enableCreate = true;
  enableMinistereStructure = true;
  enableBtnInfo = true;
  enableBtnEdit = false;
  enableBtnDelete = false;
  isLoading!: boolean;
  message: any;
  isOpInProgress!: boolean;
  programmationDTO: ProgrammationForEvaluationDTO={};
  programmationDTOs: ProgrammationForEvaluationDTO[]=[];
  tacheEvaluation:TacheEvaluation={};
  tacheEvaluations:TacheEvaluation[]=[];
  evaluations: Evaluation[]=[];
  evaluation: Evaluation={};
  structures: Structure[]=[];
  structs: Structure[]=[];
  structure: Structure={};
  exercice: Exercice={};
  dialogErrorMessage: any;
  ids!: number ;
  idp!: number;
  taches: Taches[] = [];
  idTache?: number;
  checked: boolean = false;
  newTaches:Taches[]=[];
  val:any;
  taux:any;
  permissions:any;
  btnStruct:boolean=false;
  exerciceId:any;
  structureId:any;
  ide:any;
  exercices!: Exercice[];
  exers!:Exercice[];
  btnHiddenMinistere:boolean=false;
  showDialogProgramme:boolean=false;
  showDialog:boolean=false;
  format?:any;
  formats= Array({"name":"PDF"},{"name":"Excel"});
  ministeres!: Ministere[];
  ministere: Ministere={};
  ministereID: any;
  structureID: any;
  exerciceID: any;
  ngOnInit(): void {
    this.permissions=this.authenticationService.getPrivilege();
    this.btnStruct=!this.authenticationService.checkPermission(this.permissions, ['ROLE_RESP_DGESS','ROLE_DIR_DGESS','ROLE_ADMIN']);
    this.loadMinistere();
    this.loadStructure();
    this.loadExercice();
    //this.changeStructure(this.structureId);
    //this.changeExercice(this.exerciceId);
    this.load2();
    this.btnHiddenMinistere=this.authenticationService.checkPermission(this.permissions, ['ROLE_RESP_STRUCT','ROLE_FOCAL_STRUCT']);
    this.selectMinistere(this.ministere);
  }

  load(structureId:number,exerciceId:number) {
    this.isLoading = true;
    this.evaluationService.getProgrammationEvaluees(structureId,exerciceId).subscribe(
      (response) => {
        this.isLoading = false;
           this.programmationDTOs= response.programmations;
      },
      (error) => {
        this.message = { severity: 'error', summary: error.error };
        console.error(JSON.stringify(error));
      }
    );
  }


  load2(event?: LazyLoadEvent) {
    let id = (this.authenticationService.checkPermission(this.authenticationService.getPrivilege()!,['ROLE_ADMIN']) == true && this.structureID == null) ? this.authenticationService.getStructureId() : this.structureID;
    let structureId = (this.authenticationService.checkPermission(this.authenticationService.getPrivilege()!,['ROLE_ADMIN']) == false && this.structureID == null) ? this.authenticationService.getStructureId(): id;
    // let exerciceId= this.exerciceID == null ? this.getExerciceEncours2():this.exerciceID;
    // this.isLoading = true;
     if (this.exerciceID == null){

      this.exerciceService.getExerciceEncours().subscribe(
        (response) => {
          let exerciceId = response.id;

          this.evaluationService.getProgrammationEvaluees(structureId,exerciceId).subscribe(
            (response) => {
              this.isLoading = false;
                 this.programmationDTOs= response.programmations;
            },
            (error) => {
              this.message = { severity: 'error', summary: error.error };
              console.error(JSON.stringify(error));
            }
          );

        }
      );
     }
      else {
        this.evaluationService.getProgrammationEvaluees(structureId,this.exerciceID).subscribe(
          (response) => {
            this.isLoading = false;
               this.programmationDTOs= response.programmations;
          },
          (error) => {
            this.message = { severity: 'error', summary: error.error };
            console.error(JSON.stringify(error));
          }
        );
      }
  }

  loadStructure() {
    this.isLoading = true;
    this.structureService.getAll().subscribe(
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

  // choix de exercice et structure

  loadExercice(event?: LazyLoadEvent) {

    this.exerciceService.getAll(event).subscribe(response => {

      this.exercices = response.exercices;

    }, error => {
      this.message = { severity: 'error', summary: error.error };
      console.error(JSON.stringify(error));
    });
  }

  getExerciceEncours() {

    this.exerciceService.getExerciceEncours().subscribe(
      (response) => {},
      (error) => {
        this.message = { severity: 'error', summary: error.error };
        console.error(JSON.stringify(error));
      }
    );

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
  onInfo(selection: AnyObject) {

    //this.programmation = Object.assign({}, selection);

   this.router.navigate(['/workspace/evaluation-activite-detail',selection.id]);
 }

  handleError(error: HttpErrorResponse) {
    console.error(`Processing Error: ${JSON.stringify(error)}`);
    // this.isDialogOpInProgress = false;
    this.dialogErrorMessage = error.error.message;
  }


clearDialogMessages() {
  this.dialogErrorMessage = null;
}

showMessage(message: Message) {
  this.message = message;
  this.timeoutHandle = setTimeout(() => {
    this.message = null;
  }, 5000);
}
//  --------------PARTIE EXPORT------------------------

// -----------PARTIE EXPORT---------------------------------------

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

getStructureByMinistere(id:number){
this.structs=[];
this.structureService.getStructureByMinistereId(id).subscribe(response => {

  this.structs = response.structures;

}, error => {
  this.message = { severity: 'error', summary: error.error };
  console.error(JSON.stringify(error));
});
}

getStructureById(id:number){
 this.structs=[];
this.structureService.getStructureById(id).subscribe(response => {

  this.structs = response;
}, error => {
  this.message = { severity: 'error', summary: error.error };
  console.error(JSON.stringify(error));
});
}

selectMinistere( event: any){

 this.ministereID= event.value;
 if(this.ministereID==null){
  this.getStructureById(this.authenticationService.getStructureId());
 }
 this.getStructureByMinistere(this.ministereID);

}


selectStructure(event:any){
 this.structureID= event == null ? null : event.value;
}

selectExercice(event: any){
 this.exerciceID= event == null ? null : event.value;
}



exportRapportActivites(){
  const data ={
    ministereId:this.ministereID,
    structureId: this.structureID,
    format: this.format,
    exerciceId: this.exerciceID,
    currentStructureId:this.authenticationService.getStructureId()
  };

  this.programmationService.exportRapport(data).subscribe(blob => saveAs(blob, "Rapport_activite_"+data.structureId+"."+this.ext),

  )
  this.showDialogProgramme=false;
}

onExport(){
this.showDialogProgramme=true;
}
onExportStructure(){
this.btnHiddenMinistere=true;
this.getStructureById(this.authenticationService.getStructureId());
this.showDialogProgramme=true;

}
}
