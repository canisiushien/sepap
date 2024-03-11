import { Programmation } from 'src/app/models/programmation/programmation';
import { ObjectifService } from './../../../../services/parametrage/objectif.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, NgForm, Validators } from '@angular/forms';
import { ConfirmationService, LazyLoadEvent, MenuItem, Message, MessageService } from 'primeng/api';
import { Activite } from 'src/app/models/activites/activite';
import { TypeActiviteEnum } from 'src/app/models/enum/type-activite-enum';
import { Exercice } from 'src/app/models/parametrage/exercice';
import { Periode } from 'src/app/models/parametrage/periode';
import { Programme } from 'src/app/models/parametrage/programme';
import { Projet } from 'src/app/models/parametrage/projet';
import { SourceFinancement } from 'src/app/models/parametrage/source-financement.model';
import { Structure } from 'src/app/models/parametrage/structure';
import { TypeActivite } from 'src/app/models/parametrage/typeActivite';
import { Taches } from 'src/app/models/programmation/taches';
import { ExerciceService } from 'src/app/services/parametrage/exercice.service';
import { PeriodeService } from 'src/app/services/parametrage/periode.service';
import { ProgrammeService } from 'src/app/services/parametrage/programme.service';
import { ProjetService } from 'src/app/services/parametrage/projet.service';
import { SourceFinancementService } from 'src/app/services/parametrage/source-financement.service';
import { StructureService } from 'src/app/services/parametrage/structure.service';
import { TypeActiviteService } from 'src/app/services/parametrage/type-activite.service';
import { ActiviteService } from 'src/app/services/programmation/activite.service';
import { ProgrammationService } from 'src/app/services/programmation/programmation.service';
import { environment } from 'src/environments/environment';
import { Objectif } from 'src/app/models/parametrage/objectif';
import { AuthenticationService } from 'src/app/services/parametrage/authentication.service';

@Component({
  selector: 'app-programmation',
  templateUrl: './programmation.component.html',
  styleUrls: ['./programmation.component.scss']
})
export class ProgrammationComponent implements OnInit {

  @ViewChild('dtf') form!: NgForm;

  enableCreate = true;
  enableBtnInfo = true;
  enableBtnEdit = true;
  enableBtnDelete = true;
  totalRecords!: number;
  recordsPerPage = environment.recordsPerPage;
  tachesBuilder!: FormGroup;
  firstForm!: FormGroup;
  secondForm!: FormGroup;
  thirdForm!: FormGroup;
  fourForm!: FormGroup;
  fiveForm!: FormGroup;
  isLoading!: boolean;
  exercices: Exercice[] = [];
  exercice: Exercice = {};
  projets: Projet[] = [];
  projet: Projet = {};
  activites: Activite[] = [];
  activite: Activite = {};
  structures: Structure[] = [];
  structure: Structure = {};
  sourceFinancements: SourceFinancement[] = [];
  sourceFinancement: SourceFinancement = {};
  message: any;
  isOpInProgress!: boolean;
  isDialogOpInProgress!: boolean;
  showDialog = false;
  loading : boolean = false;
  showDialogSource= false;
  dialogErrorMessage: any;
  timeoutHandle: any;
  tachesInit:any[]=[];
  objectifs: Objectif[]=[];
  objectif: Objectif={};
  sumPoderation:number=0;
  typeActivites: TypeActivite[] = [];
 // taches:Taches[]=[];
  taches:any[]=[];
  tache: Taches={};
  items: MenuItem[]=[];
  activeItem!: MenuItem;
  myCheckbox!:any;
  periodes: Periode[]=[];
  selectedPeriode:any[]=[];
  perms:any;
  lastChoix:any[]=[];
  singleton : boolean = false;
  choix:any[]= [
    {name: 'Oui',checked: false},
    {name: 'Non',checked: false}
  ];
  selected=-1;
  isSuccessful: boolean = false;
  selectedCities: string[] = [];
  checkedPeriodeList: any[]=[];
  boolstep: boolean = true;
  struct!: any;
  required:boolean=true;
  exercicesAtt: any;
  constructor(private fb: FormBuilder,
    private exerciceService: ExerciceService,
    private projetService: ProjetService,
    private activiteService: ActiviteService,
    private programmationService: ProgrammationService,
    private sourceFinancementService: SourceFinancementService,
    private typeActiviteService: TypeActiviteService,
    private structureService: StructureService,
    private confirmationService: ConfirmationService,
    private periodeService:PeriodeService,
    private objectifService: ObjectifService,
    private messageService: MessageService,
    private authService: AuthenticationService) {
      this.perms=this.authService.getPrivilege();
     }

  ngOnInit(): void {

   // this.loadExcercices();
    this.loadExcerciceAttente();
    this.loadProjet();
    this.loadActivites();
    this.loadTypeActivites();
    this.loadFinancement();
    this.loadStructure();
    this.loadPeriodes();
    this.updateSingleton(this.singleton);
    this.getValide();
    this.menuTool();
    this.loadChoix(this.choix);
    this.loadTaches(this.tachesInit);
    this.getObjectifOperationne();
    this.firstForm = this.fb.group({
      exercice: [""],
      activite: ["", Validators.required],
      projet: ["", Validators.required],
      estPrioritaire: [""],
      objectif: ["", Validators.required],

    });

    this.secondForm = this.fb.group({
      sourceFinancement: ['', Validators.required],
      coutPrevisionnel: ['', Validators.required],
      resultatsAttendus: ['', Validators.required],
      indicateur:['',Validators.required],
      cible: ['', Validators.required],
      checked: ['', Validators.requiredTrue],
      listePeriode: [''],
    });

   /* this.thirdForm = this.fb.group({
      taches: this.fb.array([this.createTaches()],Validators.required)
    });*/
    this.thirdForm = this.fb.group({
      libelle: ['', Validators.required],
      //description: ['', Validators.required],
      ponderation: ['', Validators.required],
      valeur: [''],
    });

    this.fourForm = this.fb.group({
     // data: ['', Validators.required],
    });

    this.fiveForm = this.fb.group({
    });
  }

  getObjectifOperationne(){
    this.objectifService.getObjectifOperationnel().subscribe(response => {
      this.objectifs = response.objectifs;
    })
  }

  createTaches():FormGroup{
    return this.fb.group({
      libelle: ['', Validators.required],
     // description: ['', Validators.required],
      ponderation: ['', Validators.required],
      valeur: [''],
    })
  }



  addTaches() {
    if(this.thirdForm.value['libelle']=="" ||
    this.thirdForm.value['ponderation'] == ""){
      this.thirdForm.reset();
    } else{
    const data =
      {
        id: this.taches.length+1,
        libelle: this.thirdForm.value['libelle'],
        //description: this.thirdForm.value['description'],
        ponderation: this.thirdForm.value['ponderation'],
        valeur: this.thirdForm.value['valeur'],
      };
    this.sumPoderation = this.sumPoderation + data.ponderation;
    this.taches.push(data);
    this.loadTaches(this.taches);
    this.thirdForm.reset();
    }
  }

 loadChoix(choix:any){
   this.choix = choix;
 }
  loadTaches(taches:any){
    this.tachesInit = taches.value;
  }
  loadStructure(event?: LazyLoadEvent){
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

  loadTypeActivites(event?: LazyLoadEvent){
    this.isLoading = true;
    this.typeActiviteService.getAll(event).subscribe(
      (response) => {
        this.isLoading = false;
        this.typeActivites = response.typeActivites;
      },
      (error) => {
        this.message = { severity: 'error', summary: error.error };
        console.error(JSON.stringify(error));
      }
    );

  }

  loadPeriodes(event?: LazyLoadEvent){
    this.isLoading = true;
    this.periodeService.getAll(event).subscribe(
      (response) => {
        this.isLoading = false;
        this.periodes = response.periodes;
        this.loadDataPeriode(this.periodes);
      },
      (error) => {
        this.message = { severity: 'error', summary: error.error };
        console.error(JSON.stringify(error));
      }
    );

  }

  loadDataPeriode(data:any) {
    for (var i = 0; i < data.length; i++) {
      const d =
      {
        libelle: data[i].libelle,
        valeur: data[i].valeur,
      };
      this.selectedPeriode.push(d);

    }
  }
  menuTool(){
    this.items = [
      {label: 'Activités', icon: 'pi pi-fw pi-home', routerLink: ['/workspace/activite']},
      {label: 'Programmer', icon: 'pi pi-fw pi-file',routerLink: ['/workspace/programmation']},
      {label: 'Programme d\'activités (validées au CASEM)', icon: 'pi pi-fw pi-file',routerLink: ['/workspace/liste-programme-valides'],}
  ];

  this.activeItem = this.items[0];
  }
  onFirstSubmit() {
   this.firstForm.markAsDirty();
  }

  onSecondSubmit() {

    this.secondForm.markAsDirty();


   //
  }

  onThirdSubmit() {
    this.getValide();
      this.thirdForm.markAsDirty();
    //
  }

  onFourSubmit() {

    this.create();
    this.fourForm.markAsDirty();
  }

  onFiveSubmit() {
    this.fiveForm.markAsDirty();

  }
    onCreateActivite() {

    this.activite = {};
    this.clearDialogMessages();
    this.form.resetForm();
    this.showDialog = true;
  }
  createActivite() {
    this.clearDialogMessages();
    this.isDialogOpInProgress = true;
    this.activite.status = TypeActiviteEnum.PAS_COMMENCEE;
    this.activiteService.create(this.activite).subscribe(response => {
     this.loadActivites()
      this.isDialogOpInProgress = false;
      this.showDialog = false;
      this.showMessage({ severity: 'success', summary: 'Activité créée avec succes' });
    }, error => this.handleError(error));
  }

  onCreateSource() {
    this.sourceFinancement = {};
    this.clearDialogMessages();
    this.form.resetForm();
    this.showDialogSource = true;
  }

  getValide(){
    if(this.sumPoderation < 100 ){
      this.boolstep = false;
    this.messageService.add({severity:'warn', life:5000, summary: 'warn', detail: 'La somme des ponderation n\'atteignent pas 100%'});
    } else if (this.sumPoderation > 100){
      this.boolstep = false;
      this.messageService.add({severity:'warn', life:5000, summary: 'warn', detail: 'La somme des ponderation depassent 100%'});
    } else {
      this.boolstep = true;
      this.thirdForm.removeControl('libelle');
      //this.thirdForm.removeControl('description');
      this.thirdForm.removeControl('ponderation');
      this.thirdForm.removeControl('valeur');
    }
  }
  createSourceFinancement() {
    this.clearDialogMessages();
    this.isDialogOpInProgress = true;
    this.sourceFinancementService.create(this.sourceFinancement).subscribe(response => {
     this.loadFinancement()
      this.isDialogOpInProgress = false;
      this.showDialogSource = false;
      this.showMessage({ severity: 'success', summary: 'activité créée avec succès' });
    }, error => this.handleError(error));
  }
  // loadExcercices(event?: LazyLoadEvent) {
  //   this.isLoading = true;
  //   this.exerciceService.getExerciceEncoursList(event).subscribe(
  //     (response) => {
  //       this.isLoading = false;
  //       this.exercices = response.exercices;
  //     },
  //     (error) => {
  //       this.message = { severity: 'error', summary: error.error };
  //       console.error(JSON.stringify(error));
  //     }
  //   );

  // }

  loadExcerciceAttente(event?: LazyLoadEvent) {
    this.isLoading = true;
    this.exerciceService.getExerciceAttente(event).subscribe(
      (response) => {
        this.isLoading = false;
        this.exercicesAtt = response.exercices;
        this.exercices.push(this.exercicesAtt);
      },
      (error) => {
        this.message = { severity: 'error', summary: error.error };
        console.error(JSON.stringify(error));
      }
    );

  }

  loadActivites(event?: LazyLoadEvent) {
    this.isLoading = true;
    this.activiteService.getAll(event).subscribe(
      (response) => {
        this.isLoading = false;
        this.activites = response.activtes;
      },
      (error) => {
        this.message = { severity: 'error', summary: error.error };
        console.error(JSON.stringify(error));
      }
    );

  }

  loadFinancement(event?: LazyLoadEvent) {
    this.isLoading = true;
    this.sourceFinancementService.getAll(event).subscribe(
      (response) => {
        this.isLoading = false;
        this.sourceFinancements = response.sources;
      },
      (error) => {
        this.message = { severity: 'error', summary: error.error };
        console.error(JSON.stringify(error));
      }
    );

  }
  loadProjet(event?: LazyLoadEvent) {
    this.isLoading = true;
    this.projetService.getAll(event).subscribe(
      (response) => {
        this.isLoading = false;
        this.projets = response.projets;
      },
      (error) => {
        this.message = { severity: 'error', summary: error.error };
        console.error(JSON.stringify(error));
      }
    );

  }

  create() {
    this.clearDialogMessages();
    this.isDialogOpInProgress = true;
    this.loading = true;
    // let idStructure = this.authService.checkPermission(this.perms,['ROLE_ADMIN','ROLE_RESP_DGESS','ROLE_DIR_DGESS']) == true ?
    // this.firstForm.value['structure'].id : this.authService.getStructureId();
     let idStructure= this.authService.getStructureId();
     const data = {
      coutPrevisionnel: this.secondForm.value['coutPrevisionnel'],
      coutReel: this.secondForm.value['coutReel'],
      cible: this.secondForm.value['cible'],
      structure: {
        id: idStructure
      },
      projet: this.firstForm.value['projet'],
      objectif: this.firstForm.value['objectif'],
      taches: this.taches,
      activite: this.firstForm.value['activite'],
      resultatsAttendus:this.secondForm.value['resultatsAttendus'],
      indicateur:this.secondForm.value['indicateur'],
      sourceFinancement:this.secondForm.value['sourceFinancement'],
      singleton: this.singleton,
      periodes: this.selectedPeriode,
      estPrioritaire:this.firstForm.value['estPrioritaire']
    };
    this.programmationService.create(data).subscribe(response => {
      this.isDialogOpInProgress = false;
      this.isSuccessful = true;
      this.loading = false;
      this.showDialog = false;
      this.showMessage({ severity: 'success', summary: 'Programmation créée avec succès' });
    }, error => {this.handleError(error);
    this.isSuccessful = false;
    this.loading = false;
  });
  }


  onDeleteTache(selection:any) {
    this.confirmationService.confirm({
      message: 'Etes-vous sûre de vouloir supprimer cette programmation?',
      accept: () => {
        this.deleteTache(selection);
      }
    });
  }

  deleteTache(tache:any) {
    this.isOpInProgress = true;
    if(tache){
      const index: number = this.taches.indexOf(tache);
      this.taches.splice(index, 1);
      this.isOpInProgress = false;
      this.totalRecords--;
      this.loadTaches(this.taches);
      this.showMessage({ severity: 'success', summary: 'Tâche supprimée avec succès' });
      this.sumPoderation = this.sumPoderation - tache.ponderation;
    //console.error('____________data', this.sumPoderation);
    if(this.sumPoderation < 100 ){
      this.boolstep = false;
    this.messageService.add({severity:'warn', life:5000, summary: 'warn', detail: 'La somme des ponderations n\'atteigne pas 100%'});
    } else if (this.sumPoderation > 100){
      this.boolstep = false;
      this.messageService.add({severity:'warn', life:5000, summary: 'warn', detail: 'La somme des ponderations depasse 100%'});
    } else {
      this.boolstep = true;
    }
    }

  }
  showMessage(message: Message) {
    this.message = message;
    this.timeoutHandle = setTimeout(() => {
      this.message = null;
    }, 5000);
  }


  onCheckboxChange(index:any) {
    for  (var i = 0; i < this.choix.length; i++) {
      this.choix[i].checked = false;
      // this.sumPoderation = 100;
    }
    this.choix[index].checked = true;
    if(this.choix[index].name == "Non"){
      let single = true;
      this.updateSingleton(single);
    } else if(this.choix[index].name == "Oui"){
      let single = false;
      this.updateSingleton(single);
    }

  }

  updateSingleton(singleton:boolean){
    this.singleton = singleton;
  }
  getCheckedPeriodeList(index:any){

   if(this.selectedPeriode[index].valeur == true){
   this.selectedPeriode[index].valeur= true;
   }
   else {
    this.selectedPeriode[index].valeur = false;
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

  getStructure(event:any){
    let structure = event.value;
    this.structureService.getStructureById(structure).subscribe(response => {
      this.structures = response.structures;
     this.struct= this.structure.libelle;

   })

  }

  OnInput(event: any) {
    this.struct = event.target.value;
    }

}
