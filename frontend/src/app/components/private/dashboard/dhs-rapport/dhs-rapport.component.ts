import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { LazyLoadEvent, Message } from 'primeng/api';

import { Ministere } from 'src/app/models/parametrage/ministere';
import { MinistereService } from 'src/app/services/parametrage/ministere.service';
import { Structure } from 'src/app/models/parametrage/structure';
import { StructureService } from 'src/app/services/parametrage/structure.service';
import { Exercice } from 'src/app/models/parametrage/exercice';
import { AuthenticationService } from 'src/app/services/parametrage/authentication.service';
import { ProgrammationService } from './../../../../services/programmation/programmation.service';
import { StructureActiviteData } from 'src/app/models/stats/structure-activite-data';
import { ProgrammationExport } from 'src/app/models/programmation/programmationExport';
import { ExerciceService } from 'src/app/services/parametrage/exercice.service';
import { DomSanitizer } from '@angular/platform-browser';
import { saveAs } from 'file-saver';
import { Periode } from 'src/app/models/parametrage/periode';
import { PeriodeService } from 'src/app/services/parametrage/periode.service';
import { EvolutionParam } from 'src/app/models/stats/evolution-param';
import { AllEvolutionData } from 'src/app/models/stats/all-evolution-data';
import { MinistereEvolutionBundle } from 'src/app/models/stats/ministere-evolution-bundle';
import { StatistiqueService } from 'src/app/services/statistique/statistique.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-dhs-rapport',
  templateUrl: './dhs-rapport.component.html',
  styleUrls: ['./dhs-rapport.component.scss']
})
export class DhsRapportComponent implements OnInit {

  @ViewChild('dtf') form!: NgForm;

  // élements de filtrage
  ministere: Ministere = {};
  ministeres: Ministere[] = [];
  g_ministeres: Ministere[] = [];

  structure: Structure = {};
  structures: Structure[] = [];
  g_structures: Structure[] = [];

  exercice: Exercice={};
  exercices: Exercice[]=[];
  g_exercices: Exercice[]=[];
  rapp_exercices: Exercice[]=[];

  periodes: Periode[] = [];

  formats = Array({"name":"PDF"},{"name":"Excel"},{"name":"Word"});
  extension:string[]=["pdf","excel","docx"];

  // variables de sélection
  g_exercice_id: any;
  g_ministere_id: any;
  g_structure_id: any;

  nbMinistere: any = "32";

  periodicite: any = "Trimestriel";

  nbstructure: number = 17;

  // construction des figures
  // == courbes linéaires
  globalLinesData: any;
  basicOptions: any;

  // == baton empilé
  stackedData: any;
  stackedOptions: any;

  // construction du tableau
  dataBound!: StructureActiviteData [];

  btnExportRapport: boolean=true;
  btnExportProgramme: boolean=true;
  btnMinistere: boolean=false;
  message: any;
  ministereID: any;
  structureID: any;
  periodeID: any;
  exerciceID: any;
  format?:any;

  programmationExports: ProgrammationExport[]=[];
  programmationExport: ProgrammationExport={
    // ministereId:4,
    // structureId: this.authenticateService.getStructureId(),
    // exerciceId: this.exercice.id,
    // currentStructureId: 4
  };

  showDialogProgramme: boolean = false;
  showDialogRapport: boolean = false;
  hiddenStructure: boolean = false;

  param: EvolutionParam = {};
  globalLibelle: string = "";

  evolutionData?: AllEvolutionData;
  evolutions: MinistereEvolutionBundle[] = [];
  globalLabel?: string = "-";

  isDialogOpInProgress!: boolean;
  dialogErrorMessage: any;
  loading: boolean = false;
  timeoutHandle: any;
  ext!: string;
  show!: boolean;
  btnHiddenMinistere:boolean=false;
  permissions:any;
  constructor(private programmationService: ProgrammationService,
              private exerciceService: ExerciceService,
              private authenticateService: AuthenticationService,
              private structureService: StructureService,
              private ministereService: MinistereService,
              private periodeService: PeriodeService,
              private statService: StatistiqueService) {

               }

  ngOnInit(): void {
    this.getExerciceEncours();
   // this.hideStructure(this.hiddenStructure);
    this.loadMinistere();
    this.loadExercice();
    this.loadCloasedExercice();
    this.loadPeriodes();
    this.show = this.authenticateService.checkPermission(this.authenticateService.getPrivilege()!,
        ['ROLE_ADMIN','ROLE_RESP_MIN','ROLE_SG_MIN','ROLE_DIRCAB_MIN']);
    this.permissions=this.authenticateService.getPrivilege();
    this.btnHiddenMinistere=this.authenticateService.checkPermission(this.permissions, ['ROLE_RESP_STRUCT','ROLE_FOCAL_STRUCT']);
    this.selectMinistere(this.ministere);
  }

  choixExtension(event:any){
   //   this.format1=event.value;
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

  dowloadDocument(){
    if (this.extension) {
      let fileUrl;
      this.programmationService.download("extension").subscribe(blob => {
        fileUrl = window.URL.createObjectURL(blob);
        window.open(fileUrl);
      });
    }
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

  onExportRapport(){
    this.showDialogRapport=true;
  }

  exportRapportActivites(){
    const data = {
      ministereId:this.ministereID,
      structureId: this.structureID,
      format: this.format,
      exerciceId: this.exerciceID,
      periodeId: this.periodeID,
      currentStructureId:this.authenticateService.getStructureId()
    };
    this.programmationService.exportRapport(data).subscribe(blob => saveAs(blob, "Rapport_activite_"+data.structureId+"."+this.ext));
    this.showDialogProgramme = false;
  }

  onExportProgramme(){
    this.showDialogProgramme = true;
  }

  exportProgrammeActivites(){
    const data = {
      ministereId:this.ministereID,
      structureId: this.structureID,
      format: this.format,
      exerciceId: this.exerciceID,
      currentStructureId:this.authenticateService.getStructureId()
    };
    this.programmationService.exportProgramme(data).subscribe(blob => saveAs(blob, "Programme_activite_"+data.structureId+"."+this.ext));
    this.showDialogProgramme = false;
  }

  loadMinistere(event?: LazyLoadEvent) {
    this.ministereService.getAll(event).subscribe(
      (response) => {
        this.ministeres = response.ministeres;
        this.g_ministeres = response.ministeres;
      },
      (error) => {
        this.message = { severity: 'error', summary: error.error };
        console.error(JSON.stringify(error));
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

  loadCloasedExercice(event?: LazyLoadEvent) {
    this.exerciceService.getExerciceClos(event).subscribe(response => {
      this.rapp_exercices = response.exercices;
    }, error => {
      this.message = { severity: 'error', summary: error.error };
      console.error(JSON.stringify(error));
    });
  }

  loadExercice(event?: LazyLoadEvent) {
   this.exerciceService.getAll(event).subscribe(response => {
     this.exercices = response.exercices;
     this.g_exercices = response.exercices;
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
  this.structures=[];
 this.structureService.getStructureById(id).subscribe(response => {

   this.structures = response;
 }, error => {
   this.message = { severity: 'error', summary: error.error };
   console.error(JSON.stringify(error));
 });
}

 loadStructureByMinistere(id:number){
  this.structureService.getStructureByMinistereId(id).subscribe(response => {
    this.g_structures = response.structures;
  }, error => {
    this.message = { severity: 'error', summary: error.error };
    console.error(JSON.stringify(error));
  });
 }

 hideStructure(hiddenStructure:boolean){
  return hiddenStructure;
 }

 selectMinistere(event: any){
   this.ministereID= event.value;
   if(this.ministereID==null){
    this.getStructureById(this.authenticateService.getStructureId());
   } else{
   this.getStructureByMinistere(this.ministereID);
   }
 }

 g_selectMinistere(event: any){
  this.g_ministere_id = event.value;
  this.loadStructureByMinistere(this.g_ministere_id);
}

 selectStructure(event:any){
   this.structureID = event.value;
 }

 selectPeriode(event:any){
   this.periodeID = event.value;
 }

 selectExercice(event: any){
   this.exerciceID= event.value;
 }

 loadStats() {
   // chargement des données depuis le bouton de recherche
   if((!this.g_ministere_id) || (!this.exerciceID)){
    this.message = { severity: 'error', summary: 'Veuillez sélectionner le ministère et ou l\' exercice SVP' };
     this.showMessage(this.message);
     return;
   }else{
 
   this.param.couverture = 1;//this.annee?.nbAnnee;
   this.param.ministereId = this.g_ministere_id;
   this.param.structureId = this.structureID;
   this.param.exerciceId = this.exerciceID;

   this.statService.getEvolutionBundle(this.param).subscribe(
     (response) => {
       this.loadStatsGraph(response);
     },
     (error) => {
       this.message = { severity: 'error', summary: error.error };
       console.error(JSON.stringify(error));
     }
   );
   //
 }
}

 loadStatsGraph(data: AllEvolutionData) {

   this.evolutionData = data;
   this.evolutions = this.evolutionData.data ? this.evolutionData.data : [];

   this.globalLabel = this.evolutionData ? this.evolutionData.libelle : "-";

   let libelles: string[] = [];

   let my_dataset: any[] = [];//{ datasets: any[], labels: any[] } = { datasets: [], labels: [] };

   if (this.evolutions) {
     //Extraction des données du premier élément de la liste
     let premier = this.evolutions[0];
     libelles = premier.exercices ? premier.exercices : [];
     for (var evol of this.evolutions) {
       let dataValue = {
         label: evol.structure,
         data: evol.taux,
         fill: false,
         tension: .4
       }
       my_dataset.push(dataValue);
       //my_dataset.datasets.push(evol.taux);
       //my_dataset.labels.push(evol.structure);
     }
   }

   this.globalLinesData = {
     labels: libelles,
     datasets: my_dataset
   };
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
