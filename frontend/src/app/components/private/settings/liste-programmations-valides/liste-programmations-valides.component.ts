import { MinistereService } from 'src/app/services/parametrage/ministere.service';
import { ExerciceService } from 'src/app/services/parametrage/exercice.service';
import { AmendementActiviteService } from 'src/app/services/programmation/amendement-activite.service';
import { EvaluationService } from 'src/app/services/evaluation/evaluation.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LazyLoadEvent, MenuItem, MessageService } from 'primeng/api';
import { Structure } from 'src/app/models/parametrage/structure';
import { Programmation } from 'src/app/models/programmation/programmation';
import { ProgrammationValidation } from 'src/app/models/programmation/programmation-validation';
import { AuthenticationService } from 'src/app/services/parametrage/authentication.service';
import { StructureService } from 'src/app/services/parametrage/structure.service';
import { ProgrammationService } from 'src/app/services/programmation/programmation.service';
import { environment } from 'src/environments/environment';
import { ProgrammationForEvaluationDTO } from 'src/app/models/programmation/programmation-for-evaluation-dto';
import { AnyObject } from 'chart.js/types/basic';
import { HttpErrorResponse } from '@angular/common/http';
import { ProgrammationData } from 'src/app/models/programmation/ProgrammationData';
import { Evaluation } from 'src/app/models/evaluation/evaluation';
import { Exercice } from 'src/app/models/parametrage/exercice';
import { Ministere } from 'src/app/models/parametrage/ministere';
import { saveAs } from 'file-saver';
@Component({
  selector: 'app-liste-programmations-valides',
  templateUrl: './liste-programmations-valides.component.html',
  styleUrls: ['./liste-programmations-valides.component.scss']
})
export class ListeProgrammationsValidesComponent implements OnInit {

  timeoutHandle: any;
  totalRecords!: number;
  recordsPerPage = environment.recordsPerPage;
  programmations!: Programmation[];
  // selection: any;
  programmation: Programmation = {};
  programmationsF!:Programmation[];
  programmatioValidations!: ProgrammationValidation[];
  programmatioValidation: ProgrammationValidation={};
  programmationDTO: ProgrammationForEvaluationDTO={};
  programmationDTOs: ProgrammationForEvaluationDTO[]=[];
  structures!: Structure[];
  structs!: Structure[];
  structure: Structure = {};
  enableCreate = true;
  enableBtnInfo = true;
  enableBtnEdit = false;
  enableBtnDelete = false;
  permissions: any;
  enableBtnTreat = false;
  isLoading!: boolean;
  isOpInProgress!: boolean;
  isDialogOpInProgress!: boolean;
  showDialog = false;
  message: any;
  dialogErrorMessage: any;
  ids!: number;
  idp!:number;
  btnStruct:boolean=false;
  btnCASEM:boolean=false;
  items: MenuItem[]=[];
  activeItem!: MenuItem;
  exerciceId:any;
  structureId:any;
  ide:any;
  detailDialog:boolean=false;
  taches :any[] = [];
  programmationData: ProgrammationData = {};
  programmationDatas!: ProgrammationData[];


  evaluation: Evaluation={};
  evaluations!: Evaluation[];
  exercice: Exercice={};
  exercices!: Exercice[];
  exers!:Exercice[];
  ministeres!: Ministere[];
  ministere: Ministere={};
  ministereID: any;
  structureID: any;
  exerciceID: any;
  btnHiddenMinistere:boolean=false;
  showDialogProgramme:boolean=false;
  format?:any;
  formats= Array({"name":"pdf"},{"name":"excel"});
  constructor(private programmationService: ProgrammationService,
     private router:Router,
     private authenticationService: AuthenticationService,
     private messageService: MessageService,
     private structureService: StructureService,
     private evaluationService: EvaluationService,
     private exerciceService: ExerciceService,
     private ministereService:MinistereService,
     private amendementActiviteService: AmendementActiviteService) {}

  ngOnInit(): void {
    this.menuTool();
    this.permissions=this.authenticationService.getPrivilege();
    this.enableBtnTreat=this.authenticationService.checkPermission(this.permissions, ['ROLE_RESP_DGESS','ROLE_DIR_DGESS','ROLE_ADMIN','ROLE_RESP_STRUCT']);
    this.btnStruct=!this.authenticationService.checkPermission(this.permissions, ['ROLE_RESP_DGESS','ROLE_DIR_DGESS','ROLE_ADMIN']);
    this.loadStructure();
    this.loadExercice();
    this.loadMinistere();
    //this.changeStructure(this.structureId);
    //this.changeExercice(this.ide);
    this.load2();
    this.btnHiddenMinistere=this.authenticationService.checkPermission(this.permissions, ['ROLE_RESP_STRUCT','ROLE_FOCAL_STRUCT']);

  }

  menuTool(){
    this.items = [
      {label: 'Activités', icon: 'pi pi-fw pi-home', routerLink: ['/workspace/activite']},
      {label: 'Programmer', icon: 'pi pi-fw pi-file',routerLink: ['/workspace/programmation']},
      {label: 'Programme d\'activités(validées au CASEM)', icon: 'pi pi-fw pi-file',routerLink: ['/workspace/listeProgramme-valides'],}
  ];

  this.activeItem = this.items[0];
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
    if (this.exerciceID == null && structureId == null){}
    if (structureId == null){}
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


  loadExercice(event?: LazyLoadEvent) {

    this.exerciceService.getAll(event).subscribe(response => {

      this.exercices = response.exercices;

    }, error => {
      this.message = { severity: 'error', summary: error.error };
      console.error(JSON.stringify(error));
    });
  }

  getExerciceEncours(ide:number) {
    this.structureId= (this.authenticationService.checkPermission(this.authenticationService.getPrivilege()!,['ROLE_ADMIN','ROLE_RESP_DGESS','ROLE_DIR_DGESS'])
    == true && this.structureId == null) ? this.structureId : this.authenticationService.getStructureId();
   if(ide== null){
    this.exerciceService.getExerciceEncours().subscribe(
      (response) => {
        this.ide = response.id;
        this.load(this.structureId,this.ide);

      }
    );
   }
        else {
          this.load(this.structureId,ide);
        }

  }


  getExerciceEncours2(): number{
    this.exerciceService.getExerciceEncours().subscribe(
      (response) => {
        this.ide = response.id;

      }
    );

    return this.ide;

  }

    loadprogrammation(ids:number,idp:number){
      this.amendementActiviteService.getProgrammationbyStructProgram(ids,idp).subscribe(data => {
        this.programmation=data;
       this.taches =this.programmation.taches as any;

      }, error => this.handleError(error));
    }

  // -------------------------------------------


    onInfo(selection:any) {
      this.programmationDTO = Object.assign({}, selection);
      if(!this.structureID){
        this.structureID = this.authenticationService.getStructureId();
      }
      this.loadprogrammation(this.structureID,selection.id)
      this.detailDialog = true;
    }

  /*  changeStructure(selection:any){
      this.structureId= selection == null ? this.authenticationService.getStructureId(): selection.value.id;
    }*/

    changeExercice(selection:any){
      let id= selection == null ? null: selection.value.id;
      this.getExerciceEncours(id);

    }

    // Errors
  handleError(error: HttpErrorResponse) {
    console.error(`Processing Error: ${JSON.stringify(error)}`);
    this.isDialogOpInProgress = false;
    this.dialogErrorMessage = error.error.message;
  }

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
   this.getStructureByMinistere(this.ministereID);

 }

 selectStructure(event:any){
   this.structureID= event == null ? this.authenticationService.getStructureId(): event.value;
 }

 selectExercice(event: any){
   this.exerciceID= event.value;
 }

 selectFormat(event:any){
  this.format=event.value;
}
exportProgrammeActivites(){
  const data ={
    ministereId:this.ministereID,
    structureId: this.structureID,
    format: this.format,
    exerciceId: this.exerciceID,
    currentStructureId:this.authenticationService.getStructureId()
  };

  this.programmationService.exportProgramme(data).subscribe(blob => saveAs(blob, "Programme_activite_"+data.ministereId+"."+data.format),

  )
  this.showDialogProgramme=false;

}

onExport(){
  this.showDialogProgramme=true;
}
onExportStructure(){
  this.showDialogProgramme=true;
  this.getStructureById(this.authenticationService.getStructureId());

}

// choiceSelect(){
//   if(this.authenticationService.checkPermission(this.authenticationService.getPrivilege()!,['ROLE_RESP_DGESS','ROLE_DIR_DGESS'])== true){
//     //this.btnMin = true;
//     //this.loadStructure();
//     //this.loadPerformance();
//     this.load2();
//   }

//   if(this.authenticationService.checkPermission(this.authenticationService.getPrivilege()!,['ROLE_RESP_STRUCT','ROLE_FOCAL_STRUCT'])== true){
//     this.btnStruct = true;
//     //this.btnMin = true;
//     this.structureID = this.authenticationService.getStructureId();
//     //this.loadPerformance();
//     this.load2();
//   }
// }

}
