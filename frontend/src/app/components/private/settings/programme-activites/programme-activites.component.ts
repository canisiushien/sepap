
import { Evaluation } from 'src/app/models/evaluation/evaluation';
import { EvaluationService } from './../../../../services/evaluation/evaluation.service';
import { ExerciceService } from 'src/app/services/parametrage/exercice.service';
import { Programmation } from 'src/app/models/programmation/programmation';
import { ProgrammationService } from 'src/app/services/programmation/programmation.service';
import { ActiviteService } from './../../../../services/programmation/activite.service';
import { ActiviteComponent } from './../activite/activite.component';
import { Activite } from 'src/app/models/activites/activite';
import { Component, OnInit, ViewChild } from '@angular/core';
import { ConfirmationService, LazyLoadEvent, MenuItem, Message } from 'primeng/api';
import { THIS_EXPR } from '@angular/compiler/src/output/output_ast';
import { environment } from 'src/environments/environment';
import { NgForm } from '@angular/forms';
import { Structure, StructureStatut, StructureType } from 'src/app/models/parametrage/structure';
import { Ministere } from 'src/app/models/parametrage/ministere';
import { MinistereStructure } from 'src/app/models/parametrage/ministereStructure';
import { StructureService } from 'src/app/services/parametrage/structure.service';
import { MinisterStructureService } from 'src/app/services/parametrage/minister-structure.service';
import { MinistereService } from 'src/app/services/parametrage/ministere.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/services/parametrage/authentication.service';
import { Exercice } from 'src/app/models/parametrage/exercice';
import { AnyObject } from 'chart.js/types/basic';
import { ProgrammationData } from 'src/app/models/programmation/ProgrammationData';
import { ProgrammationForEvaluationDTO } from 'src/app/models/programmation/programmation-for-evaluation-dto';


@Component({
  selector: 'app-programme-activites',
  templateUrl: './programme-activites.component.html',
  styleUrls: ['./programme-activites.component.scss']
})
export class ProgrammeActivitesComponent implements OnInit {
  @ViewChild('dtf') form!: NgForm;
  timeoutHandle: any;
  totalRecords!: number;
  recordsPerPage = environment.recordsPerPage;
  structures!: Structure[];
  // selection: any;
  structure: Structure = {};
  exercice: Exercice={};
  exercices!:Exercice[];
  ministeres!:Ministere[];
  programmation: Programmation = {};
  programmations!: Programmation[];

  programmationData: ProgrammationData = {};
  programmationDatas!: ProgrammationData[];


  evaluation: Evaluation={};
  evaluations!: Evaluation[];
  enableCreate = false;
  enableMinistereStructure = true;
  enableBtnInfo = false;
  enableBtnEdit = false;
  enableBtnDelete = false;
  enableBtnEvaluer=true;
  isLoading!: boolean;
  isOpInProgress!: boolean;
  isDialogOpInProgress!: boolean;
  showDialog = false;
  showDialog2=false;
  structureDetail:boolean=false;
  message: any;
  dialogErrorMessage: any;
  ministereStructures!: MinistereStructure[];
  ministereStructure: MinistereStructure = {};
  idp!: Number;
  periodelist :any[]=[];
  permissions:any=[""];
  programmationDTO: ProgrammationForEvaluationDTO={};
  programmationDTOs: ProgrammationForEvaluationDTO[]=[];

  constructor(private exerciceService: ExerciceService, private router: Router, private programmationService: ProgrammationService,
    private structureService: StructureService,private ministereStructureService: MinisterStructureService,
    private confirmationservice: ConfirmationService, private ministereService :MinistereService,
    private authenticationService: AuthenticationService,
    private authenticateService:AuthenticationService,
    private evaluationService: EvaluationService) {}

    ngOnInit(): void {
    this.loadExcercices();
    this.loadStructure();
    this.loadMinistere();
    //this.loadbyStructure();
    this.programmationValidebyStructure();

  }

  loadExcercices(event?: LazyLoadEvent) {
    this.isLoading = true;
    this.exerciceService.getExerciceAttente(event).subscribe(
      (response) => {
        this.isLoading = false;
        this.exercices = response.exercices;
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

  loadMinistere(event?: LazyLoadEvent){
    this.isLoading = true;
    this.ministereService.getAll(event).subscribe(
      (response) => {
        this.isLoading = false;
        this.ministeres = response.ministeres;
      },
      (error) => {
        this.message = { severity: 'error', summary: error.error };
        console.error(JSON.stringify(error));
      }
    );

  }
  programmationValidebyStructure(){
    this.exerciceService.getExerciceEncours().subscribe(
      (response) => {
        let exerciceId = response.id;
        this.evaluationService.getProgrammationEvaluees(this.authenticationService.getStructureId(),exerciceId).subscribe(
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

  loadbyStructure(event?: LazyLoadEvent) {
  this.isLoading = true;
  this.programmationDatas = [];
  this.programmationService.getAllValidebyStruct(this.authenticationService.getStructureId()).subscribe(
      (response) => {
        this.isLoading = false;
           this.programmations = response.programmations;
            this.programmations.forEach(programmation => {
              let prgData: ProgrammationData = {};
              prgData.programmation = programmation;
              this.evaluationService.getAllByProgrammation(programmation.id).subscribe(
                (response) => {
                  this.isLoading = false;
                    this.evaluations = response.evaluations;
                    let value = ""; let resultat="";
                    this.evaluations.forEach(ev => {
                      value = value + (ev.periode? ev.periode.libelle: '-')+'-';
                    });

                    var str = value.substring(0, value.length - 1);
                    prgData.periode = str;
                    this.programmationDatas.push(prgData);

                },
                (error) => {
                  this.message = { severity: 'error', summary: error.error };
                  console.error(JSON.stringify(error));
                }
              );
            });
      },
      (error)=> {
        this.message = { severity: 'error', summary: error.error };
        console.error(JSON.stringify(error));
      }
    );
  }

  getProgrammationPeriode(idp: any): string{
    let value="";
    this.evaluationService.getAllByProgrammation(idp).subscribe(
      (response) => {
        this.isLoading = false;
           this.evaluations = response.evaluations;
           this.evaluations.forEach(ev => {
           value=value + (ev.periode? ev.periode.libelle: '-');
  });

      },
      (error) => {
        this.message = { severity: 'error', summary: error.error };
        console.error(JSON.stringify(error));
      }
    );

    return value;
  }

  isEditing() {
    return !!this.structure.id;
  }



  onInfo(selection: AnyObject) {

     //this.programmation = Object.assign({}, selection);

    this.router.navigate(['/workspace/evaluation-activites', this.authenticateService.getStructureId(),selection.id]);
  }

  onEdit(selection: any) {

  }

  onDelete(selection: any) {

  }

  onEvaluate() {
   this.router.navigate(['evaluation-activites'])
  }



  // Errors

  handleError(error: HttpErrorResponse) {
    console.error(`Processing Error: ${JSON.stringify(error)}`);
    this.isDialogOpInProgress = false;
    this.dialogErrorMessage = error.error.message;
  }
}
