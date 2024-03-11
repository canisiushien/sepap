import { AuthenticationService } from 'src/app/services/parametrage/authentication.service';
import { Component, OnInit, ViewChild } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { NgForm } from '@angular/forms';
import { ConfirmationService, LazyLoadEvent, MenuItem, Message } from 'primeng/api';
import { Action } from 'src/app/models/parametrage/action';
import { ObjectifStrategique } from 'src/app/models/parametrage/objectif-strategique';
import { ActionService } from 'src/app/services/parametrage/action.service';

import { environment } from 'src/environments/environment';
import { Activite } from 'src/app/models/activites/activite';
import { ActiviteService } from 'src/app/services/programmation/activite.service';
import { TypeActiviteService } from 'src/app/services/parametrage/type-activite.service';
import { TypeActivite } from 'src/app/models/parametrage/typeActivite';
import { TypeActiviteEnum } from 'src/app/models/enum/type-activite-enum';
import { Router } from '@angular/router';


@Component({
  selector: 'app-activite',
  templateUrl: './activite.component.html',
  styleUrls: ['./activite.component.scss']
})

export class ActiviteComponent implements OnInit {

  @ViewChild('dtf') form!: NgForm;
  btnAdmin: boolean = false;
  btnUser: boolean = false;
  btnOther: boolean = false;
  timeoutHandle: any;
  totalRecords!: number;
  recordsPerPage = environment.recordsPerPage;
  activites: Activite[] = [];
  typeActivites: TypeActivite[] = [];
  selection!: Activite;
  activite: Activite = {};
  enablePro = true;
  enableCreate = true;
  enableBtnInfo = true;
  enableBtnEdit = true;
  enableBtnDelete = false;
  isLoading!: boolean;
  isOpInProgress!: boolean;
  isDialogOpInProgress!: boolean;
  showDialog = false;
  detailDialog=false;
  message: any;
  dialogErrorMessage: any;
  objectifStrategiques!: ObjectifStrategique[];
  selectedObjStra!: ObjectifStrategique;
  permissions: any;
  items: MenuItem[]=[];
  activeItem!: MenuItem;
  resultat:boolean=false;
  constructor(
    private confirmationService: ConfirmationService,
    private activiteService: ActiviteService,
    private typeActiviteService: TypeActiviteService,
    private router: Router,
    public authService: AuthenticationService) { }

  ngOnInit(): void {
    this.load();
    this.loadTypeActivites();
    this.menuTool();
    this.permissions=this.authService.getPrivilege();
    this.enableCreate=   this.enableBtnEdit=this.authService.checkPermission(this.permissions, ['ROLE_ADMIN','ROLE_DIR_DGESS','ROLE_RESP_DGESS','ROLE_FOCAL_STRUCT']);
    this.enableBtnEdit=   this.enableBtnEdit=this.authService.checkPermission(this.permissions, ['ROLE_ADMIN','ROLE_DIR_DGESS','ROLE_RESP_DGESS']);
   // this.enableBtnDelete=   this.enableBtnEdit=this.authService.checkPermission(this.permissions, ['ROLE_ADMIN','ROLE_DIR_DGESS','ROLE_RESP_DGESS']);
  }

  load(event?: LazyLoadEvent) {
    this.isLoading = true;
    this.activiteService.getAll(event).subscribe(response => {
      this.isLoading = false;
      this.activites = response.activtes;

    }, error => {
      this.message = { severity: 'error', summary: error.error };
      console.error(JSON.stringify(error));
    });
  }

menuTool(){
  this.items = [
    {label: 'Activités', icon: 'pi pi-fw pi-home', routerLink: ['/workspace/activite']},
    {label: 'Programmer', icon: 'pi pi-fw pi-file',routerLink: ['/workspace/programmation']},
    {label: 'Programme d\'activités (validées au CASEM)', icon: 'pi pi-fw pi-file',routerLink: ['/workspace/liste-programme-valides'],}
];

this.activeItem = this.items[0];
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


  save() {

    if (this.activite.id) {
      this.edit();
    } else {
      this.create();
    }
  }

  onPro(){

      this.router.navigate(["/workspace/programmation"]);

  }
   //Creation

   onCreate() {
    this.activite = {};
    this.clearDialogMessages();
    this.form.resetForm();
    this.showDialog = true;
  }

    //Creation

    onInfo(selection:any) {
      this.activite = Object.assign({}, selection);
      this.clearDialogMessages();
      this.detailDialog = true;
    }

  create() {
    this.clearDialogMessages();
    this.isDialogOpInProgress = true;
    this.activite.status = TypeActiviteEnum.PAS_COMMENCEE;
    this.activiteService.create(this.activite).subscribe(response => {
      if (this.activites.length !== this.recordsPerPage) {
        this.activites.push(response);
        this.activites = this.activites.slice();
      }
      this.totalRecords++;
      this.isDialogOpInProgress = false;
      this.showDialog = false;
      this.showMessage({ severity: 'success', summary: 'Activité créée avec succès' });
    }, error => this.handleError(error));
  }


   // Edit

   onEdit(selection:any) {
    this.activite = Object.assign({}, selection);
    this.clearDialogMessages();
    this.showDialog = true;
  }

  edit() {
    this.clearDialogMessages();
    this.isDialogOpInProgress = true;
    this.activiteService.update(this.activite).subscribe(response => {
      let index = this.activites.findIndex(activite => activite.id === response.id);
      this.activites[index] = response;
      this.isDialogOpInProgress = false;
      this.showDialog = false;
      this.load();
      this.showMessage({ severity: 'success', summary: 'Activité modifiée avec succès' });
    }, error => this.handleError(error));

  }

  isEditing() {

    return !!this.activite.id;
  }


  // Deletion

  onDelete(selection:any) {
    this.confirmationService.confirm({
      message: 'Etes-vous sûre de vouloir supprimer cette activité ?',
      accept: () => {
        //this.delete(selection);
      }
    });
  }

  delete(selection:any) {
    this.isOpInProgress = true;
    this.activiteService.delete(selection.id).subscribe(() => {
      this.activites = this.activites.filter(activite => activite.id !== selection.id);
      selection = null;
      this.isOpInProgress = false;
      this.totalRecords--;
      this.showMessage({ severity: 'success', summary: 'Activité supprimée avec succès' });
    }, error => {
      console.error(JSON.stringify(error));
      this.isOpInProgress = false;
      this.showMessage({ severity: 'error', summary: error.error.message });
    });
  }

  // Errors

  handleError(error: HttpErrorResponse) {
    console.error(`Processing Error: ${JSON.stringify(error)}`);
    this.isDialogOpInProgress = false;
    this.dialogErrorMessage = error.error.message;
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

