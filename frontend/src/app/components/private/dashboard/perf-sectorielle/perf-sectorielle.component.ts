import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { Message } from 'primeng/api';

import { StructureActiviteData } from 'src/app/models/stats/structure-activite-data';

import { MinistereService } from 'src/app/services/parametrage/ministere.service';
import { StatistiqueService } from 'src/app/services/statistique/statistique.service';
import { AuthenticationService } from 'src/app/services/parametrage/authentication.service';
import { Exercice } from 'src/app/models/parametrage/exercice';
import { ExerciceService } from 'src/app/services/parametrage/exercice.service';
import { MinistereGlobalPerfData } from 'src/app/models/stats/ministere-global-perf-data';
import { ResumerPerfData } from 'src/app/models/stats/resumer-perf-data';

@Component({
    selector: 'app-perf-sectorielle',
    templateUrl: './perf-sectorielle.component.html',
    styleUrls: ['./perf-sectorielle.component.scss']
})
export class PerfSectorielleComponent implements OnInit {
    
    @ViewChild('dtf') form!: NgForm;

    exercice: Exercice = {};
    exercices: Exercice[] = [];
    exerciceID?: number;

    performanceData?: MinistereGlobalPerfData;
    globalLibelle: string = "";

    currentStructureId?: number;
    currentMinistereId?: number;
    currentExerciceId?: number;

    // construction des figures
    // == courbes linéaires
    globalLinesData: any;
    basicOptions: any;

    // == baton empilé
    stackedData: any;
    stackedOptions: any;

    // construction du tableau
    dataBound!: StructureActiviteData[];

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
        //this.loadMinistere(); 
        this.loadExercices();
    }

    // Chargement des données de l'utilisateur connecté
    loadCurrentUserData(): void {
        this.currentStructureId = this.authService.getStructureId();
    }

    loadExercices(){
        // Chargement des données de l'exercice en cours 
        this.exerciceService.getExerciceClos().subscribe(resp => {   
            this.exercices = resp.exercices;
        },(error) => { 
            console.error(JSON.stringify(error)); 
        }); 
    } 

    selectExercice(event: any) {
        this.exerciceID = event.value;
        this.statService.getSectorielPerf(this.exerciceID).subscribe(
            (response) => {
                this.loadStatsData(response);
            },
            (error) => {
                this.message = { severity: 'error', summary: error.error };
                console.error(JSON.stringify(error));
            }
        );
    }

    loadStatsData(data?: MinistereGlobalPerfData) {

        this.performanceData = data;

        let libelles: string[] = [];
        let perfs: number[] = []; 

        let values: ResumerPerfData[] =  (this.performanceData && this.performanceData.data) ? this.performanceData.data : [];

        if (values) {
            for (var val of values) {
                libelles.push(val.code ? val.code : '');
                perfs.push(val.taux ? val.taux : -1);
            }
        } 

        this.globalLinesData = {
            labels: libelles,
            datasets: [
                {
                    data: perfs,
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