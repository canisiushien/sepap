import { ProgrammationValidation } from 'src/app/models/programmation/programmation-validation';
import { StructureService } from 'src/app/services/parametrage/structure.service';
import { Programmation } from './../../../../models/programmation/programmation';
import { Component, OnInit } from '@angular/core';
import { LazyLoadEvent, MenuItem, Message, MessageService, ConfirmationService } from 'primeng/api';
import { ProgrammationService } from 'src/app/services/programmation/programmation.service';
import { environment } from 'src/environments/environment';
import { Router } from '@angular/router';
import { AnyObject } from 'chart.js/types/basic';
import { AuthenticationService } from 'src/app/services/parametrage/authentication.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Structure } from 'src/app/models/parametrage/structure';

@Component({
  selector: 'app-liste-programmation',
  templateUrl: './liste-programmation.component.html',
  styleUrls: ['./liste-programmation.component.scss'],
  providers: [MessageService],
    styles: [`
        :host ::ng-deep .p-cell-editing {
            padding-top: 0 !important;
            padding-bottom: 0 !important;
        }
    `]
})
export class ListeProgrammationComponent implements OnInit {
  timeoutHandle: any;
  totalRecords!: number;
  recordsPerPage = environment.recordsPerPage;
  programmations!: Programmation[];
  // selection: any;
  programmation: Programmation = {};
  programmationsF!:Programmation[];
  programmatioValidations!: ProgrammationValidation[];
  programmatioValidation: ProgrammationValidation={};
  structures!: Structure[];
  // selection: any;
  structure: Structure = {};
  enableCreate = true;
  enableBtnInfo = true;
  enableBtnEdit = false;
  enableBtnDelete = false;
  permissions: any;
  enableBtnTreat = false;
  isLoading!: boolean;
  isOpInProgress!: boolean;
  isDialogOpInProgress!: boolean;
  showDialog = false;
  message: any;
  dialogErrorMessage: any;
  ids!: number;
  idp!:number;
  btnStruct:boolean=false;
  btnCASEM:boolean=false;
  taches: any[]=[];
  nbTache:any;
  id:any;
  items: MenuItem[]=[];
  activeItem!: MenuItem;
  activiteLibelle: string | undefined;

  constructor(private programmationService: ProgrammationService,
     private router:Router,
     private authenticationService: AuthenticationService,
     private confirmationService: ConfirmationService,
     private structureService: StructureService) {}

  ngOnInit(): void {

    this.permissions=this.authenticationService.getPrivilege();
    this.enableBtnTreat=this.authenticationService.checkPermission(this.permissions, ['ROLE_RESP_DGESS','ROLE_DIR_DGESS','ROLE_ADMIN','ROLE_RESP_STRUCT']);
    this.btnStruct=!this.authenticationService.checkPermission(this.permissions, ['ROLE_RESP_DGESS','ROLE_DIR_DGESS','ROLE_ADMIN']);
    this.btnCASEM=!this.authenticationService.checkPermission(this.permissions, ['ROLE_RESP_DGESS','ROLE_DIR_DGESS','ROLE_ADMIN']);
    this.loadStructure();
    //this.reload();
    this.changeStructure(this.id);
    this.checkBtnEdit(this.authenticationService.getStructureId());
    this.menuTool();
  }

  menuTool(){
    this.items = [
      {label: 'Programmations en cours', icon: 'pi pi-fw pi-home', routerLink: ['/workspace/liste-programmation']},
      {label: 'Programmations en attente', icon: 'pi pi-fw pi-file',routerLink: ['/workspace/toutes-programmations'],},
  ];

  this.activeItem = this.items[0];
  }
changeStructure(selection:any){
  this.id = selection == null ?
  this.authenticationService.getStructureId() : selection.value.id;
  this.reload(this.id);
  this.checkBtnEdit(this.id);
}
   reload(id:any){
    switch (this.authenticationService.checkPermission(this.permissions, ['ROLE_RESP_DGESS','ROLE_DIR_DGESS','ROLE_ADMIN','ROLE_RESP_STRUCT'])) {
      case true:
        this.load(id)
        break;
      case false:
        this.load(id)
        break;
      default:
        this.load(id)
        break;

  }

  }


  checkBtnEdit(id:number){
    if(id != this.authenticationService.getStructureId()){
      this.enableBtnEdit=false;
    }
    else{
      this.enableBtnEdit=true;
    }

  }

  loadStructure() {
    this.isLoading = true;
    this.structureService.getAll().subscribe(
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

  load(id:number,event?:LazyLoadEvent) {
    this.isLoading = true;
    this.programmationService.getAllEncoursbyStruct(id,event).subscribe(
      (response) => {
        this.isLoading = false;
           this.programmations = response.programmations;
            this.programmations.forEach(prog =>{
             let t: any=prog.taches;
              this.nbTache= t.length;
            })

      },
      (error) => {
        this.message = { severity: 'error', summary: error.error };
        console.error(JSON.stringify(error));
      }
    );
  }

  filterStructure(selection:any){

    this.programmation = Object.assign({}, selection);

    this.programmationService.getAllValidebyStruct(selection.value.id).subscribe(
      (response) => {
        this.isLoading = false;
        this.programmations = response.programmations;
        this.programmations=this.programmations.slice();

      },
      (error)=> {
        this.message = { severity: 'error', summary: error.error };
        console.error(JSON.stringify(error));
      }
    );
  }

  onValidationCASEM(){
    this.confirmationService.confirm({
      message: 'Etes-vous sûre de vouloir valider toutes les programmations?',
      accept: () => {
        this.validationCASEM();
      }
    });
  }

  validationCASEM(){
    const validationData={
      structureId:this.id,
      validatedByCASEM: true,
    }
    this.programmationService.validationGlobale(validationData).subscribe(
      (response) => {
      this.programmatioValidation=response;
        this.showMessage({
          severity: 'success',
          summary: 'Validation CASEM éffectuée avec succès',
        });
        this.reload(this.id);
      },(error) => this.handleErrorModif(error)
    )

  }

  onValidationAvantCASEM(){
    this.confirmationService.confirm({
      message: 'Etes-vous sûre de vouloir valider toutes les programmations?',
      accept: () => {
        this.validationAvantCASEM();
      }
    });
  }

  validationAvantCASEM(){
  let validationData={};
    switch (this.authenticationService.checkPermission(this.permissions,
       ['ROLE_RESP_DGESS','ROLE_DIR_DGESS','ROLE_ADMIN'])) {
      case true:
         validationData={
          structureId:this.id,
          validatedByDGESS: true,
        }
        break;
      case false:
         validationData={
          structureId:this.authenticationService.getStructureId(),
          validatedBySTRUCT: true,
        }
        break;
      default:
        validationData={
          structureId:this.authenticationService.getStructureId(),
          validatedBySTRUCT: true,
        }
        break;
  }
    this.programmationService.validationGlobale(validationData).subscribe(
      (response) => {
        this.programmatioValidation=response;
        this.showMessage({
          severity: 'success',
          summary: 'Validation éffectuée avec succès',
        });
        this.reload(this.id);
      },(error) => this.handleErrorModif(error)
    )
  }
  save() {
    this.clearDialogMessages();
    this.isDialogOpInProgress = true;
    this.programmationService.modifierProgrammation(this.programmation).subscribe(
      (response) => {
        let index = this.programmations.findIndex(
          (programmation) => this.idp === response.id
        );
        this.programmations[index] = response;
        this.isDialogOpInProgress = false;
        this.showDialog = false;
        this.showMessage({
          severity: 'success',
          summary: 'Programmation modifiée avec succès',
        });
        this.load(this.authenticationService.getStructureId())
      },
      (error) => this.handleErrorModif(error)
    );
  }

  //Détail de la programmation
  onInfo(selection: AnyObject) {
    this.router.navigate(['/workspace/detail-programmation',this.id,selection.id]);
  }

  //Pour aller à la page amendementActivite
  onTreat(selection: AnyObject) {
    this.router.navigate(['/workspace/amendement-activite',this.id,selection.id]);
  }

  // Edit

  onEdit(selection: any) {
    this.programmation = Object.assign({}, selection);
    this.activiteLibelle = this.programmation.activite?.libelle;
    this.clearDialogMessages();
    this.showDialog = true;
  }

  onRecherche(){
    this.router.navigate(['/workspace/toutes-programmations']);
  }

  clearDialogMessages() {
    this.dialogErrorMessage = null;
  }

  showMessage(message: Message) {
    this.message = message;
    this.timeoutHandle = setTimeout(() => {
      this.message = null;
    }, 5000);
  }
  handleErrorModif(error: HttpErrorResponse) {
    console.error(`Processing Error: ${JSON.stringify(error)}`);
    this.isDialogOpInProgress = false;
    this.dialogErrorMessage = error.error.title;
  }

  handleError(error: HttpErrorResponse) {
    console.error(`Processing Error: ${JSON.stringify(error)}`);
    this.isDialogOpInProgress = false;
    this.dialogErrorMessage = error.error.message;
  }
}
