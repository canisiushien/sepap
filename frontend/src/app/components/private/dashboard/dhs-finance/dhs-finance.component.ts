import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { LazyLoadEvent, Message } from 'primeng/api'; 

import { Ministere } from 'src/app/models/parametrage/ministere';

import { MinistereService } from 'src/app/services/parametrage/ministere.service';
import { ResumerSectorielDepenseData } from 'src/app/models/stats/resumer-sectoriel-depense-data';
import { StatistiqueService } from 'src/app/services/statistique/statistique.service';
import { AuthenticationService } from 'src/app/services/parametrage/authentication.service';
import { Exercice } from 'src/app/models/parametrage/exercice';
import { ExerciceService } from 'src/app/services/parametrage/exercice.service';

@Component({
  selector: 'app-dhs-finance',
  templateUrl: './dhs-finance.component.html',
  styleUrls: ['./dhs-finance.component.scss']
})
export class DhsFinanceComponent implements OnInit {

    @ViewChild('dtf') form!: NgForm;

    ministere: Ministere = {};
    ministeres: Ministere[] = [];
    ministereID?: number;
    resumerData?: ResumerSectorielDepenseData;
    globalLibelle: string = "";

    currentStructureId?: number;
    currentMinistereId?: number;
    currentExerciceId?: number;

    // construction des figures
    // == courbes linéaires
    globalLinesData: any;
    depensesData: any;
    basicOptions: any;

    message: any;
    isDialogOpInProgress!: boolean;
    dialogErrorMessage: any;
    loading: boolean = false;
    showDialog: boolean = false;
    timeoutHandle: any;

    constructor(private ministereService: MinistereService,
        private statService: StatistiqueService,
        private authService: AuthenticationService,
        private exerciceService: ExerciceService) {

    }

    ngOnInit(): void {
        this.loadCurrentUserData();
        this.loadMinistere(); 
        this.loadCurrentExercice();
    }

    // Chargement des données de l'utilisateur connecté
    loadCurrentUserData(): void {
        this.currentStructureId = this.authService.getStructureId(); 
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

    loadCurrentExercice(){
        // Chargement des données de l'exercice en cours 
        this.exerciceService.getExerciceEncours().subscribe(resp => {    
            this.extractExerciceId(resp); 
        }, (error) => {
            console.error(JSON.stringify(error)); 
        });
    }

    private extractExerciceId(exo:Exercice): void {  
        this.currentExerciceId = exo.id ;   
    }

    selectMinistere(event: any) { 
        this.ministereID = event.value;
        this.statService.getSectorielDepenseBundle(this.ministereID, this.currentExerciceId).subscribe(
            (response) => {
                //this.resumerData = response;
                this.loadStatsData(response);
            },
            (error) => {
                this.message = { severity: 'error', summary: error.error };
                console.error(JSON.stringify(error));
            }
        );

    }

    loadStatsData(data?: ResumerSectorielDepenseData) {

        this.resumerData = data;

        let totalReel: number = 0;
        let totalPrevision: number = 0;

        let libs: string[] = [];
        let reels: number[] = [];
        let previsionnels: number[] = [];

        if(this.resumerData){
            if(this.resumerData.periodes){
                libs = this.resumerData.periodes;
            }

            if(this.resumerData.reels){
                reels = this.resumerData.reels;
            }

            if(this.resumerData.previsionnels){
                previsionnels = this.resumerData.previsionnels;
            } 
            
            if(this.resumerData.avgreel){
                totalReel = this.resumerData?.avgreel;
            }
            
            if(this.resumerData.avgprevisionnel){
                totalPrevision = this.resumerData?.avgprevisionnel;
            }
        }

        this.globalLinesData = {
            labels: libs,//['T1', 'T2', 'T3', 'T4'],
            datasets: [
                {
                    label: 'Courbe exécution',
                    data: reels,//[65, 59, 80, 81],
                    fill: false,
                    borderColor: '#42A5F5',
                    tension: .4
                },
                {
                    label: 'Courbe prévision',
                    data: previsionnels,//[28, 48, 40, 19],
                    fill: false,
                    borderColor: '#FFA726',
                    tension: .4
                }
            ]
        };

        this.depensesData = {
            labels: ['Coûts prévisionnels','Coûts réels'],
            datasets: [
                {
                    data: [totalPrevision, totalReel],
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