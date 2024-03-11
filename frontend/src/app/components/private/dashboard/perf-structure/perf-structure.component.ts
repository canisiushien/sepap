import { Component, OnInit } from '@angular/core';
import { ConfirmationService } from 'primeng/api';

import { MinistereBundleData } from 'src/app/models/stats/ministere-bundle-data';
import { StructureActiviteData } from 'src/app/models/stats/structure-activite-data';

import { AuthenticationService } from 'src/app/services/parametrage/authentication.service';
import { ExerciceService } from 'src/app/services/parametrage/exercice.service';
import { MinistereService } from 'src/app/services/parametrage/ministere.service';
import { StatistiqueService } from 'src/app/services/statistique/statistique.service';
import { ResumerPerfData } from 'src/app/models/stats/resumer-perf-data';
import { MinistereGlobalPerfData } from 'src/app/models/stats/ministere-global-perf-data';

@Component({
  selector: 'app-perf-structure',
  templateUrl: './perf-structure.component.html',
  styleUrls: ['./perf-structure.component.scss']
})
export class PerfStructureComponent implements OnInit {

  ministereBundle?: MinistereBundleData;
  ministereGlobal?: MinistereGlobalPerfData;
   
  data?: ResumerPerfData[];

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

  // construction du tableau
  dataBound!: StructureActiviteData[];

  constructor(
      private confirmationservice: ConfirmationService,
      private ministereService: MinistereService,
      private authService: AuthenticationService,
      private statService: StatistiqueService,
      private exerciceService: ExerciceService) {

  }

  ngOnInit(): void {  
      this.loadCurrentBundleData();  
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
      this.statService.getMinisterePerformance(minID, exID).subscribe(resp => {
          this.ministereGlobal = resp;   
          this.data = this.ministereGlobal.data;
      },(error) => { 
          console.error(JSON.stringify(error)); 
      });  

      // Chargement du diagramme en bâton 
      this.loadStackedData();
  }   

    loadStackedData() {

        let libelles: string[] = []; // table des libellés des structures
        let taux: number[] = []; // tables performances des structures 

        // chargement des tableaux
        if (this.data) {
            for (var val of this.data) {
                libelles.push(val.code ? val.code : '');
                taux.push(val.taux ? val.taux : -1);
            }
        }

        this.stackedData = {
            labels: libelles,
            datasets: [{
                type: 'bar',
                label: 'PERFORMANCE',
                backgroundColor: '#42A5F5',
                data: taux
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

}
