import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AnyObject } from 'chart.js/types/basic';
import { LazyLoadEvent, MenuItem, Message, MessageService } from 'primeng/api';
import { Structure } from 'src/app/models/parametrage/structure';
import { Programmation } from 'src/app/models/programmation/programmation';
import { ProgrammationValidation } from 'src/app/models/programmation/programmation-validation';
import { AuthenticationService } from 'src/app/services/parametrage/authentication.service';
import { StructureService } from 'src/app/services/parametrage/structure.service';
import { ProgrammationService } from 'src/app/services/programmation/programmation.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-liste-programmations-all',
  templateUrl: './liste-programmations-all.component.html',
  styleUrls: ['./liste-programmations-all.component.scss']
})
export class ListeProgrammationsAllComponent implements OnInit {

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
  constructor(private programmationService: ProgrammationService,
     private router:Router,
     private authenticationService: AuthenticationService,
     private messageService: MessageService,
     private structureService: StructureService) {}

  ngOnInit(): void {

    this.permissions=this.authenticationService.getPrivilege();
    //this.enableBtnTreat=this.authenticationService.checkPermission(this.permissions, ['ROLE_RESP_DGESS','ROLE_DIR_DGESS','ROLE_ADMIN','ROLE_RESP_STRUCT']);
    this.btnStruct=!this.authenticationService.checkPermission(this.permissions, ['ROLE_RESP_DGESS','ROLE_DIR_DGESS']);
    this.btnCASEM=!this.authenticationService.checkPermission(this.permissions, ['ROLE_RESP_DGESS','ROLE_DIR_DGESS']);
    this.loadStructure();
    //this.reload();
    this.changeStructure(this.id);
    //this.checkBtnEdit(this.authenticationService.getStructureId());
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
    this.programmationService.getAllbyStruct(id,event).subscribe(
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

  validationCASEM(){
    const validationData={
      id:this.id,
      validatedByDGESS: true,
    }
    this.programmationService.validationGlobale(validationData).subscribe(
      (response) => {
      this.programmatioValidation=response;
        this.showMessage({
          severity: 'success',
          summary: 'Validation CASEM éffectuée avec succès',
        });
      },(error) => this.handleErrorModif(error)
    )

  }

  validationAvantCASEM(){
  let validationData={};
    switch (this.authenticationService.checkPermission(this.permissions,
       ['ROLE_RESP_DGESS','ROLE_DIR_DGESS','ROLE_ADMIN'])) {
      case true:
         validationData={
          id:this.id,
          validatedByDGESS: true,
        }
        break;
      case false:
         validationData={
          id:this.authenticationService.getStructureId(),
          validatedBySTRUCT: true,
        }
        break;
      default:
        validationData={
          id:this.authenticationService.getStructureId(),
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
    this.router.navigate(['/workspace/amendementActivite',this.id,selection.id]);
  }

  // Edit

  onEdit(selection: any) {
    this.programmation = Object.assign({}, selection);
    this.clearDialogMessages();
    this.showDialog = true;
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
