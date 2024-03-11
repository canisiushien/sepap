import { AuthenticationService } from 'src/app/services/parametrage/authentication.service';
import { Privilege } from './../../../../models/parametrage/privilege';
import { PrivilegeService } from './../../../../services/parametrage/privilege.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, NgForm, Validators } from '@angular/forms';
import { ConfirmationService, LazyLoadEvent, MenuItem, Message } from 'primeng/api';


import { environment } from 'src/environments/environment';
import { Compte } from 'src/app/models/parametrage/compte';
import { CompteService } from 'src/app/services/parametrage/compte.service';
import { StructureService } from 'src/app/services/parametrage/structure.service';
import { Structure } from 'src/app/models/parametrage/structure';
import { ExerciceService } from 'src/app/services/parametrage/exercice.service';
import { Exercice } from 'src/app/models/parametrage/exercice';
import { CritereGouvernanceService } from 'src/app/services/parametrage/critere-gouvernance.service';
import { CritereGouvernance } from 'src/app/models/performance/critere-gouvernance';
import { EvaluationGouvernanceDTO } from 'src/app/models/parametrage/evaluation-gouvernance-dto';
import { EvaluationGouvernanceService } from 'src/app/services/parametrage/evaluation-gouvernance.service';
import { EvaluationGouvernance } from 'src/app/models/parametrage/evaluation-gouvernance';

@Component({
  selector: 'app-evaluation-gouvernance',
  templateUrl: './evaluation-gouvernance.component.html',
  styleUrls: ['./evaluation-gouvernance.component.scss']
})
export class EvaluationGouvernanceComponent implements OnInit {

  @ViewChild('dtf') form!: NgForm;

  timeoutHandle: any;
  totalRecords!: number;
  recordsPerPage = environment.recordsPerPage;
  comptes!: Compte[];
  compte: Compte = {};
  evaluationGouvernanceDTOs:EvaluationGouvernanceDTO[]=[];
  evaluationGouvernanceDTO:EvaluationGouvernanceDTO={};
  evaluationGouvernances:EvaluationGouvernance[]=[];
  evaluationGouvernance:EvaluationGouvernance={};
  criteres:CritereGouvernance[]=[];
  critere: CritereGouvernance={};
  privileges!:Privilege[];
  privilege:Privilege={};
  structures!:Structure[];
  strucure: Structure={};
  enableCreate = true;
  enableBtnInfo = false;
  enableBtnEdit = true;
  enableBtnDelete = true;

  isLoading!: boolean;
  isOpInProgress!: boolean;
  isDialogOpInProgress!: boolean;
  showDialog = false;
  display=false;
  message: any;
  exercices:Exercice[]=[];
  exercice:Exercice={};
  dialogErrorMessage: any;
  selectedProfil:any[]=[];
  cheked:boolean=false;
  firstForm!: FormGroup;
  secondForm!: FormGroup;
  thirdForm!: FormGroup;
  fourForm!:FormGroup;
  isSuccessful: boolean = false;
  loading:  boolean=false;
  criteresLists: CritereGouvernance[]=[];
  btn: boolean=false;
  items: MenuItem[]=[];
  activeItem!: MenuItem;

  constructor(
    private compteService:CompteService,
    private critereGouvernanceService: CritereGouvernanceService,
    private structureService: StructureService,
    private confirmationService: ConfirmationService,
    private evalGouvService:EvaluationGouvernanceService,
    private fb: FormBuilder,
    private exerciceService: ExerciceService,
    private authenticateService: AuthenticationService) { }

  ngOnInit(): void {
    this.load();
    this.loadCriteres();
    this.loadExcercices();
    this.getAllStructure();
    this.menuTool();
    this.loadCritereApplicable();
    this.firstForm = this.fb.group({
      exerciceId: ['', Validators.required],
      structureId: ['', Validators.required],
    });

    this.secondForm = this.fb.group({
      nonapplicable: [''],
      valeurReference: [''],
    });

    this.thirdForm = this.fb.group({

    });
    this.fourForm = this.fb.group({

    });
  }




onFirstSubmit() {
  this.firstForm.markAsDirty();
}

onSecondSubmit() {
 this.secondForm.markAsDirty();
}

onThirdSubmit() {
  this.thirdForm.markAsDirty();
  this.create();
}
onFourSubmit() {
  this.fourForm.markAsDirty();
 }

load(event?: LazyLoadEvent) {
  this.isLoading = true;
   this.compteService.getAll(event).subscribe(response => {
     this.isLoading = false;
       this.comptes = response.comptes;
   },   error => {
        this.message = { severity: 'error', summary: error.error };
     console.error(JSON.stringify(error));
   });
 }

 loadCriteres(event?: LazyLoadEvent) {
  this.isLoading = true;
   this.critereGouvernanceService.getAllActif(event).subscribe(response => {
     this.isLoading = false;
     if(response.criteres.length==0){
       this.btn = true;
     }
     for(let i=0;i<response.criteres.length;i++){
      let critereList:CritereGouvernance={};
       critereList.nonapplicable = false;
       critereList.actif = response.criteres[i].actif;
       critereList.indicateur = response.criteres[i].indicateur;
       critereList.id = response.criteres[i].id;
       critereList.mode = response.criteres[i].mode;
       this.criteres.push(critereList);
     }
     this.totalRecords = this.criteres.length;

   }, error => {
     this.message = { severity: 'error', summary: error.error };
     console.error(JSON.stringify(error));
   });
 }

loadCritereApplicable(){

  for (let i = 0; i < this.criteres.length; i++) {
    let data = {
      nonapplicable:this.criteres[i].nonapplicable,
      valeurReference: this.criteres[i].valeurReference,
      critereGouvernance:{
      id: this.criteres[i].id,
      indicateur: this.criteres[i].indicateur,
      mode: this.criteres[i].mode,
      actif: this.criteres[i].actif,
      nonapplicable: this.criteres[i].nonapplicable,
      valeurReference: this.criteres[i].valeurReference
      }
    }
  this.criteresLists.push(data.critereGouvernance);
  }
}

critereApplicable(){
  this.criteresLists = [];
  this.evaluationGouvernanceDTOs = [];
  for (let i = 0; i < this.criteres.length; i++) {
    let data = {
      nonapplicable:!this.criteres[i].nonapplicable,
      valeurReference: this.criteres[i].valeurReference,
      critereGouvernance:{
      id: this.criteres[i].id,
      indicateur: this.criteres[i].indicateur,
      mode: this.criteres[i].mode,
      actif: this.criteres[i].actif,
      nonapplicable: !this.criteres[i].nonapplicable,
      valeurReference: this.criteres[i].valeurReference
      }
    }
  this.evaluationGouvernanceDTOs.push(data);
  this.criteresLists.push(data.critereGouvernance);
  }

  this.evaluationGouvernance = {
    exercice: this.firstForm.value['exerciceId'],
    structure:this.firstForm.value['structureId'],
    critereGouvernances: this.evaluationGouvernanceDTOs
  };

}

getStructureById(id:number){
  this.structureService.getStructureById(id).subscribe(response => {

    this.structures = response;
  }, error => {
    this.message = { severity: 'error', summary: error.error };
    console.error(JSON.stringify(error));
  });
  }

  getStructByAgent(){
    if(this.authenticateService.checkPermission(this.authenticateService.getPrivilege()!, ['ROLE_ADMIN','ROLE_RESP_DDII']) == true){
      this.loadStructure();
    }
    else{
      this.getStructureById(this.authenticateService.getStructureId());
    }
  }

menuTool(){
  this.items = [
    {label: 'Indic de gouvernance', icon: 'pi pi-fw pi-plus', routerLink: ['/workspace/performances/evaluation-gouvernance']},
    {label: 'Indicateurs', icon: 'pi pi-fw pi-file',routerLink: ['/workspace/performances/mes-criteres-gouvernance'],},
];

this.activeItem = this.items[0];
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

loadExcercices(event?: LazyLoadEvent) {
  this.isLoading = true;
  this.exerciceService.getAll(event).subscribe(
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


 create() {
   this.evalGouvService.create(this.evaluationGouvernance).subscribe(response => {
    this.isSuccessful=true;
    this.loading = false;
   },
    error => this.handleError(error));
    this.showMessage({ severity: 'success', summary: 'Les critères validés avec succès' });
    this.isSuccessful=false;
    this.loading = false;
 }


 getCheckedNonapplicable(index: any) {
  if (this.criteres[index].nonapplicable == true) {
    this.criteres[index].nonapplicable = true;
  }
  else {
    this.criteres[index].nonapplicable = false;
  }
}

getAllStructure(){
  if(this.authenticateService.checkPermission(this.authenticateService.getPrivilege()!,['ROLE_ADMIN'])==true){
    this.loadStructure();
  }else{
      this.getStructureById(this.authenticateService.getStructureId());
  }
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
