import { Component, OnInit } from '@angular/core';
import { ConfirmationService, LazyLoadEvent } from 'primeng/api';

import { MinistereBundleData } from 'src/app/models/stats/ministere-bundle-data';
import { MinistereGlobalStatsBundleData } from 'src/app/models/stats/ministere-global-stats-bundle-data';
import { ResumerActiviteData } from 'src/app/models/stats/resumer-activite-data';
import { ResumerDepenseData } from 'src/app/models/stats/resumer-depense-data';
import { ResumerStructureData } from 'src/app/models/stats/resumer-structure-data';
import { StructureActiviteData } from 'src/app/models/stats/structure-activite-data';
import { Exercice } from 'src/app/models/parametrage/exercice';

import { AuthenticationService } from 'src/app/services/parametrage/authentication.service';
import { ExerciceService } from 'src/app/services/parametrage/exercice.service';
import { MinistereService } from 'src/app/services/parametrage/ministere.service';
import { StatistiqueService } from 'src/app/services/statistique/statistique.service';
import { Periode } from 'src/app/models/parametrage/periode';
import { Ministere } from 'src/app/models/parametrage/ministere';
import { Structure } from 'src/app/models/parametrage/structure';
import { ProgrammationService } from 'src/app/services/programmation/programmation.service';
import { StructureService } from 'src/app/services/parametrage/structure.service';
import { PeriodeService } from 'src/app/services/parametrage/periode.service';
import { saveAs } from 'file-saver';

@Component({
    selector: 'app-dhs-structure',
    templateUrl: './dhs-structure.component.html',
    styleUrls: ['./dhs-structure.component.scss']
})
export class DhsStructureComponent implements OnInit {

    ministereBundle?: MinistereBundleData;
    ministereGlobal?: MinistereGlobalStatsBundleData;

    depense?:ResumerDepenseData;
    activite?:ResumerActiviteData;
    resumes?: ResumerStructureData[];

    currentStructureId?:number;
    currentMinistereId:number = -1;
    currentExercieId:number = -1;

    // varibales de gestion des données des dépenses
    depensesData: any;

    // varibales de gestion des données des activités
    activitesData: any;

    // construction des figures
    // == courbes linéaires
    globalLinesData: any;
    basicOptions: any;

    // == baton empilé
    stackedData: any;
    stackedOptions: any;
    showDialogProgramme: boolean = false;
    showDialogRapport: boolean = false;
    hiddenStructure: boolean = false;
    ext!: string;
    // construction du tableau
    dataBound!: StructureActiviteData[];
    show!: boolean;
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
 btnExportRapport: boolean=true;
  btnExportProgramme: boolean=true;
  message: any;
  ministereID: any;
  structureID: any;
  periodeID: any;
  exerciceID: any;
  format?:any;
  btnHiddenMinistere:boolean=false;
  permissions:any;
    constructor(
        private programmationService: ProgrammationService,
        private structureService: StructureService,
        private periodeService: PeriodeService,
        private confirmationservice: ConfirmationService,
        private ministereService: MinistereService,
        private authService: AuthenticationService,
        private statService: StatistiqueService,
        private exerciceService: ExerciceService) {

    }

    ngOnInit(): void {
        this.loadCurrentBundleData();
        this.getExerciceEncours();
        // this.hideStructure(this.hiddenStructure);
         this.loadMinistere();
         this.loadExercice();
         this.loadCloasedExercice();
         this.loadPeriodes();
        this.show = this.authService.checkPermission(this.authService.getPrivilege()!,
        ['ROLE_ADMIN','ROLE_RESP_MIN','ROLE_SG_MIN','ROLE_DIRCAB_MIN']);
        this.permissions=this.authService.getPrivilege();
        this.btnHiddenMinistere=this.authService.checkPermission(this.permissions, ['ROLE_RESP_STRUCT','ROLE_FOCAL_STRUCT']);
        this.selectMinistere(this.ministere);
    }

    // Chargement du ministère de l'utilisateur connecté
    loadCurrentBundleData(): void {
        this.currentStructureId = this.authService.getStructureId();

        // Chargement des données du ministère
        this.ministereService.getBundle(this.currentStructureId).subscribe(response => {
            this.ministereBundle = response;
            //this.currentMinistereId = this.ministereBundle.ministerId;
            this.extractMinisterId(response.ministerId ? response.ministerId : 0);
        },(error) => {
            //this.message = { severity: 'error', summary: error.error };
            console.error(JSON.stringify(error));
        });

        if (!!this.ministereBundle) {
            this.ministereBundle = {};
        }
    }

    loadCurrentExercice(){
        // Chargement des données de l'exercice en cours
        this.exerciceService.getExerciceEncours().subscribe(resp => {
            this.extractExerciceId(resp.id);
            this.loadStatsData(this.currentMinistereId, this.currentExercieId);
        },(error) => {
            console.error(JSON.stringify(error));
        });
    }

    private extractExerciceId(exoId:any): void {
        this.currentExercieId = exoId ;
    }

    private extractMinisterId(minID:number): void {
        this.currentMinistereId = minID ;
        this.loadCurrentExercice();
    }

    // Chargement données statistiques
    loadStatsData(minID:number, exID:number): void {
        // Chargement des données globales
        this.statService.getMinistereBundle(minID, exID).subscribe(resp => {
            this.ministereGlobal = resp;
            this.depense = this.ministereGlobal.depense;
            this.activite = this.ministereGlobal.activite;
            this.resumes = this.ministereGlobal.resumes;
        },(error) => {
            console.error(JSON.stringify(error));
        });

          // Chargement données dépenses
        // Depense Data //depense activite resumes

        this.depensesData = {
            labels: ['Coûts prévisionnels','Coûts réels'],
            datasets: [
                {
                    data: [this.depense?.previsionnel, this.depense?.reel],
                    backgroundColor: [
                        "#42A5F5",
                        "#66BB6A",
                        "#FFA726"
                    ],
                    hoverBackgroundColor: [
                        "#64B5F6",
                        "#81C784",
                        "#FFB74D"
                    ]
                }
            ]
        };

        // Chargement des données des activités
        this.activitesData = {
            labels: ['EN ATTENTE','EN COURS','TERMINES'],
            datasets: [
                {
                    data: [this.activite?.enattente, this.activite?.encours, this.activite?.termine],
                    backgroundColor: [
                        "#42A5F5",
                        "#66BB6A",
                        "#FFA726"
                    ],
                    hoverBackgroundColor: [
                        "#64B5F6",
                        "#81C784",
                        "#FFB74D"
                    ]
                }
            ]
        };

        // Chargement du diagramme en bâton
        this.loadStackedData();
    }

    loadStackedData() {

        let libelles: string[] = []; // libellés des structures
        let executes: number[] = []; // table de décompte des activités exécutées à 100%
        let encours: number[] = []; // table de décompte des activités exécutées à  un taux strictement compris entre 0 et 100%
        let reste: number[] = []; // table de décompte des activités exécutées à 0%

        // chargement des tableaux
        if(this.resumes){
            for (var val of this.resumes) {
                libelles.push(val.structureCode? val.structureCode : '');
                executes.push(val.termine? val.termine : 0);
                encours.push(val.encours? val.encours : 0);
                reste.push(val.enattente? val.enattente : 0);
            }
        }

        this.stackedData = {
            labels: libelles,
            datasets: [{
                type: 'bar',
                label: 'EXECUTEES',
                backgroundColor: '#42A5F5',
                data: executes
            }, {
                type: 'bar',
                label: 'EN COURS',
                backgroundColor: '#66BB6A',
                data: encours
            }, {
                type: 'bar',
                label: 'NON COMMENCES',
                backgroundColor: '#FFA726',
                data: reste
            }]
        };

        this.stackedOptions = {
            tooltips: {
                mode: 'index',
                intersect: false
            },
            responsive: true,
            scales: {
                xAxes: [{
                    stacked: true,
                }],
                yAxes: [{
                    stacked: true
                }]
            }
        };
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
          currentStructureId:this.authService.getStructureId()
        };
        this.programmationService.exportRapport(data).subscribe(blob => saveAs(blob, "Rapport_activite_"+data.ministereId+"."+this.ext));
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
          currentStructureId:this.authService.getStructureId()
        };
        this.programmationService.exportProgramme(data).subscribe(blob => saveAs(blob, "Programme_activite_"+data.ministereId+"."+this.ext));
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
       this.ministereID= event ==null ? null : event.value;
       if(this.ministereID==null){
        this.getStructureById(this.authService.getStructureId());
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
       console.error("exercie", event.value)
       this.exerciceID= event.value;
     }


}
