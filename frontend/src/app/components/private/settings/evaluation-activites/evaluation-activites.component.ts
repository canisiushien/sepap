import { TacheEvaluation } from './../../../../models/evaluation/tache_evaluation';
import { EvaluationService } from 'src/app/services/evaluation/evaluation.service';
import { AuthenticationService } from './../../../../services/parametrage/authentication.service';
import { Programmation } from 'src/app/models/programmation/programmation';
import { Component, OnInit, ViewChild } from '@angular/core';
import { ConfirmationService, LazyLoadEvent, Message } from 'primeng/api';

import { environment } from 'src/environments/environment';
import { ProgrammationService } from 'src/app/services/programmation/programmation.service';
import { ActiviteService } from 'src/app/services/programmation/activite.service';
import { ActivatedRoute, Router } from '@angular/router';
import { AmendementActiviteService } from 'src/app/services/programmation/amendement-activite.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Taches } from 'src/app/models/programmation/taches';
import { FormBuilder } from '@angular/forms';
import { Evaluation } from 'src/app/models/evaluation/evaluation';
import { MessageService } from 'primeng/api';
import { ProgrammationData } from 'src/app/models/programmation/ProgrammationData';
import { Periode } from 'src/app/models/parametrage/periode';
import { DocumentTache } from 'src/app/models/programmation/document';
import { DocumentDTO } from 'src/app/models/programmation/documentDTO';
import { ProgrammationForEvaluationDTO } from 'src/app/models/programmation/programmation-for-evaluation-dto';

@Component({
  selector: 'app-evaluation-activites',
  templateUrl: './evaluation-activites.component.html',
  styleUrls: ['./evaluation-activites.component.scss']
})
export class EvaluationActivitesComponent implements OnInit {

  timeoutHandle: any;
  totalRecords!: number;
  recordsPerPage = environment.recordsPerPage;
  enableCreate = true;
  enableMinistereStructure = true;
  enableBtnInfo = true;
  enableBtnEdit = false;
  enableBtnDelete = false;
  btnHiddenDoc=false;
  isLoading!: boolean;
  message: any;
  isOpInProgress!: boolean;
  programmationForEvaluationDTO: ProgrammationForEvaluationDTO = {};
  programmation: Programmation = {};
  programmations!: Programmation[];
  tacheEvaluation: TacheEvaluation = {};
  tacheEvaluations: TacheEvaluation[] = [];
  dialogErrorMessage: any;
  ids!: number;
  idp!: number;
  taches: Taches[] = [];
  idTache?: number;
  checked: boolean = false;
  newTaches: Taches[] = [];
  val: any;
  evaluation: Evaluation = {};
  evaluations!: Evaluation[];
  programmationDatas: ProgrammationData[]=[];
  per: Periode[] = [];
  file: Blob | string = '';
  documentTache:DocumentTache={};
  documentTaches: DocumentTache[]=[];
  documentDTO: DocumentDTO={};
  documentDialog: boolean=false;
  res: any;
  resultat: any;
  tacheId: any;
  isDialogOpInProgress: boolean=false;
  constructor(private route: ActivatedRoute, private router: Router, private activiteService: ActiviteService, private programmationService: ProgrammationService,
    private amendementActiviteService: AmendementActiviteService, private authenticationService: AuthenticationService,
    private evaluationService: EvaluationService,
    private fb: FormBuilder,
    private messageService: MessageService,
    private confirmationService: ConfirmationService) { }

  ngOnInit(): void {
    this.ids = this.route.snapshot.params['ids'];
    this.idp = this.route.snapshot.params['idp'];

    this.load();
    this.getValeurActuelle();
  }

  load(event?: LazyLoadEvent){
    this.isLoading = true;
    this.amendementActiviteService.getProgrammationForEvaluationDTO(this.idp,event).subscribe(response => {
      this.isLoading = false;
      this.programmationForEvaluationDTO = response;
      this.taches = response.taches as any;
    }, error => {
      this.message = { severity: 'error', summary: error.error };
      console.error(JSON.stringify(error));
    });
  }



onEvaluation() {
  this.confirmationService.confirm({
    message: 'Etes-vous sûre de vouloir évaluer cette activité?',
    accept: () => {
      this.evaluer();
    },
  });
}
  evaluer() {
    for (let i = 0; i < this.taches.length; i++) {
      const data = {
        id: this.taches[i].id,
        valeur: this.taches[i].valeur,
        execute: this.taches[i].execute,
        programmation: {
          id: this.programmationForEvaluationDTO.id
        }
      }
      this.tacheEvaluations.push(data);
    }

    this.evaluationService.create(this.tacheEvaluations).subscribe(response => {
      this.tacheEvaluations = response;
      this.showMessage({ severity: 'success', summary: 'Tâches évaluées avec succès' });
    }, error => this.handleError(error));


  }

  getCheckedExecute(index: any) {
    if (this.taches[index].execute == true) {
      this.taches[index].execute = true;
    }
    else {
      this.taches[index].execute = false;
    }
  }

  getValeurActuelle(): number {
    let val: any;
    let pond: any;
    let res: any = 0;
    let cible: any;
    for (let i = 0; i < this.taches.length; i++) {
      cible = this.programmation.cible;
      val = this.taches[i].valeur;
      pond = this.taches[i].ponderation;
      res = res + (val / cible + pond);
    }
    return res;
  }

  onSelectFile(event:any): void {
    let file:File = event.files[0];
    this.file = file;
  }

 ajouterDocument(id:any){
  this.clearDialogMessages();
  this.documentDialog = true;
  this.tacheId = id;
 }

  ajouterTacheDocument() {
    const documentDTO={
      tacheId:this.tacheId,
      libelle:this.documentDTO.libelle,
      description: this.documentDTO.description,
      estConfidentiel:true
    };
    const data={
      documentDTO:documentDTO,
      documentFile:this.documentTache.documentFile
    }
    this.isDialogOpInProgress = true;

    const formData: FormData = new FormData();
    const fichesAsJson: Blob = new Blob([JSON.stringify(documentDTO)], { type: 'application/json' });
    formData.append('documentDTO', fichesAsJson);
    formData.append('documentFile', this.file);
    this.evaluationService.ajouterTacheDocument(formData).subscribe(response => {
      this.documentTache=response;
      this.load();
      this.documentDialog = false;
    },error => this.handleError(error) );

  }
  handleError(error: HttpErrorResponse) {
    console.error(`Processing Error: ${JSON.stringify(error)}`);
    // this.isDialogOpInProgress = false;
    this.dialogErrorMessage = error.error.title;
  }

  onInfo(selection: any) {
   // console.log(selection);
  }

  onTerminer() {
  
    this.router.navigate(['/workspace/liste-evaluations', this.idp])
  }

  clearDialogMessages() {
    this.dialogErrorMessage = null;
  }

  showMessage(message: Message) {
    this.message = message;
    this.timeoutHandle = setTimeout(() => {
      this.message = null;
    }, 7000);
  }
}
