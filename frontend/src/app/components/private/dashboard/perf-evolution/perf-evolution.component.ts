import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { LazyLoadEvent, Message } from 'primeng/api';

import { Ministere } from 'src/app/models/parametrage/ministere';

import { MinistereService } from 'src/app/services/parametrage/ministere.service';
import { StatistiqueService } from 'src/app/services/statistique/statistique.service';
import { AuthenticationService } from 'src/app/services/parametrage/authentication.service';
import { Exercice } from 'src/app/models/parametrage/exercice';
import { ExerciceService } from 'src/app/services/parametrage/exercice.service';
import { Structure } from 'src/app/models/parametrage/structure';
import { AllEvolutionData } from 'src/app/models/stats/all-evolution-data';
import { EvolutionParam } from 'src/app/models/stats/evolution-param';
import { MinistereEvolutionBundle } from 'src/app/models/stats/ministere-evolution-bundle';
import { StructureService } from 'src/app/services/parametrage/structure.service';
import { AnneeParam } from 'src/app/models/stats/annee-param';

@Component({
  selector: 'app-perf-evolution',
  templateUrl: './perf-evolution.component.html',
  styleUrls: ['./perf-evolution.component.scss']
})
export class PerfEvolutionComponent implements OnInit {

  @ViewChild('dtf') form!: NgForm;

  ministere: Ministere = {};
  ministeres: Ministere[] = [];
  structure: Structure = {};
  structures: Structure[] = [];

  couvertures: AnneeParam[];

  ministereID?: number;
  structureID?: number;
  annee: AnneeParam = {nbAnnee:5};
  anneeValue?:number;
  param: EvolutionParam = {};

  globalLibelle: string = "";

  currentStructureId?: number;
  currentMinistereId?: number;
  currentExerciceId?: number;

  evolutionData?: AllEvolutionData;
  evolutions: MinistereEvolutionBundle[] = [];
  globalLabel?: string = "-";

  // ready
  pret: boolean = true;

  // construction des figures
  // == courbes linéaires
  globalLinesData: any;
  basicOptions: any;

  // == baton empilé
  stackedData: any;
  stackedOptions: any;

  message: any;
  isDialogOpInProgress!: boolean;
  dialogErrorMessage: any;
  loading: boolean = false;
  showDialog: boolean = false;
  timeoutHandle: any;

  constructor(private ministereService: MinistereService,
    private statService: StatistiqueService,
    private authService: AuthenticationService,
    private exerciceService: ExerciceService,
    private structureService: StructureService) {

      this. couvertures = [
        {nbAnnee: 2},
        {nbAnnee: 3},
        {nbAnnee: 4},
        {nbAnnee: 5},
        {nbAnnee: 6},
        {nbAnnee: 7},
        {nbAnnee: 8},
        {nbAnnee: 9},
        {nbAnnee: 10},
        {nbAnnee: 15},
        {nbAnnee: 20},
      ]; 

      this.annee = {nbAnnee:5};
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

  loadCurrentExercice() {
    // Chargement des données de l'exercice en cours 
    this.exerciceService.getExerciceEncours().subscribe(resp => {
      this.extractExerciceId(resp);
    }, (error) => {
      console.error(JSON.stringify(error));
    });
  }

  private extractExerciceId(exo: Exercice): void {
    this.currentExerciceId = exo.id;
  }

  selectMinistere(event: any) {
    this.ministereID = event.value;
    if (this.ministereID) {
      this.structureService.getStructureByMinistereId(this.ministereID).subscribe(
        (response) => {
          this.structures = response.structures;
        },
        (error) => {
          this.message = { severity: 'error', summary: error.error };
          console.error(JSON.stringify(error));
        }
      );
    }
  } 

  selectAnnee(event: any) { 
    this.anneeValue = event.value; 
  }

  loadStats() {
    // chargement des données depuis le bouton de recherche 
    this.param.couverture = this.anneeValue;//this.annee?.nbAnnee;
    if(this.anneeValue == null){
      this.showMessage({ severity: 'danger', summary: 'Veuillez choisir la periode' });
    } else{
    this.param.ministereId = this.ministereID;
    this.param.structureId = this.structureID; 
    this.statService.getPerformanceEvolutionBundle(this.param).subscribe(
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