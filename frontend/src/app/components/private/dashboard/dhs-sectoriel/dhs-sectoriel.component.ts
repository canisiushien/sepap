import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { LazyLoadEvent, Message } from 'primeng/api';

import { StructureActiviteData } from 'src/app/models/stats/structure-activite-data';
import { Ministere } from 'src/app/models/parametrage/ministere';

import { MinistereService } from 'src/app/services/parametrage/ministere.service';
import { ResumerSectorielData } from 'src/app/models/stats/resumer-sectoriel-data';
import { StatistiqueService } from 'src/app/services/statistique/statistique.service';
import { AuthenticationService } from 'src/app/services/parametrage/authentication.service';
import { Exercice } from 'src/app/models/parametrage/exercice';
import { ExerciceService } from 'src/app/services/parametrage/exercice.service';

@Component({
    selector: 'app-dhs-sectoriel',
    templateUrl: './dhs-sectoriel.component.html',
    styleUrls: ['./dhs-sectoriel.component.scss']
})
export class DhsSectorielComponent implements OnInit {

    @ViewChild('dtf') form!: NgForm;

    ministere: Ministere = {};
    ministeres: Ministere[] = [];
    ministereID?: number;
    resumerData?: ResumerSectorielData;
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
        this.statService.getSectorielBundle(this.ministereID, this.currentExerciceId).subscribe(
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

    loadStatsData(data?: ResumerSectorielData) {

        this.resumerData = data;

        let libs: string[] = [];
        let taux: number[] = [];
        let couts: number[] = [];

        if(this.resumerData){
            if(this.resumerData.periodes){
                libs = this.resumerData.periodes;
            }

            if(this.resumerData.finances){
                couts = this.resumerData.finances;
            }

            if(this.resumerData.physiques){
                taux = this.resumerData.physiques;
            }  
        } 

        this.globalLinesData = {
            labels: libs,//['T1', 'T2', 'T3', 'T4'],
            datasets: [
                {
                    label: 'Taux exécution physique',
                    data: taux,//[65, 59, 80, 81],
                    fill: false,
                    borderColor: '#42A5F5',
                    tension: .4
                },
                {
                    label: 'Exécution financière',
                    data: couts,//[28, 48, 40, 19],
                    fill: false,
                    borderColor: '#FFA726',
                    tension: .4
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