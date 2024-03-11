import { MinisterStructureService } from './../../../../services/parametrage/minister-structure.service';
import { MinistereStructure } from './../../../../models/parametrage/ministereStructure';
import { AuthenticationService } from 'src/app/services/parametrage/authentication.service';
import { Privilege } from './../../../../models/parametrage/privilege';
import { PrivilegeService } from './../../../../services/parametrage/privilege.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, NgForm, Validators } from '@angular/forms';
import { ConfirmationService, LazyLoadEvent, Message } from 'primeng/api';

import { ProfilService } from 'src/app/services/parametrage/profil.service';

import { environment } from 'src/environments/environment';
import { Profil, GetAllProfilResponse } from 'src/app/models/parametrage/profil';
import { Compte } from 'src/app/models/parametrage/compte';
import { CompteService } from 'src/app/services/parametrage/compte.service';
import { Router } from '@angular/router';
import { StructureService } from 'src/app/services/parametrage/structure.service';
import { Structure } from 'src/app/models/parametrage/structure';

@Component({
  selector: 'app-compte',
  templateUrl: './compte.component.html',
  styleUrls: ['./compte.component.scss']
})
export class CompteComponent implements OnInit {

  @ViewChild('dtf') form!: NgForm;

  timeoutHandle: any;
  totalRecords!: number;
  recordsPerPage = environment.recordsPerPage;
  comptes!: Compte[];
  compte: Compte = {};
  profils:Profil[]=[];
  profil: Profil={};
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
  dialogErrorMessage: any;
  selectedProfil:any[]=[];
  cheked:boolean=false;
  firstForm!: FormGroup;
  secondForm!: FormGroup;
  thirdForm!: FormGroup;
  fourForm!:FormGroup;
  isSuccessful: boolean = false;
  loading:  boolean=false;
  constructor(
    private compteService:CompteService,
    private profilService: ProfilService,
    private privilegeService: PrivilegeService,
    private structureService: StructureService,
    private confirmationService: ConfirmationService,
    private fb: FormBuilder,
    private router: Router,
    private authenticateService: AuthenticationService
    ) { }

  ngOnInit(): void {
    this.load();
    this.loadPermission();
    this.loadProfil();
    this.getStructByAgent();
    this.firstForm = this.fb.group({
      matricule: ['', Validators.required],
      nom: ['', Validators.required],
      prenom: ['', Validators.required],
      telephone: ['', Validators.required],
      email: ['', Validators.required],
      structureId: ['', Validators.required],

    });

    this.secondForm = this.fb.group({
      profiles: ['',Validators.required],
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
//  this.router.navigate(["/workspace/agent"])
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

 loadProfil(event?: LazyLoadEvent) {
  this.isLoading = true;
   this.profilService.getAll(event).subscribe(response => {
     this.isLoading = false;
     this.profils = response.profils;
   }, error => {
     this.message = { severity: 'error', summary: error.error };
     console.error(JSON.stringify(error));
   });
 }






 loadPermission(event?: LazyLoadEvent) {
  this.isLoading = true;
   this.privilegeService.getAll(event).subscribe(response => {
     this.isLoading = false;
     this.privileges = response.privileges;

   }, error => {
     this.message = { severity: 'error', summary: error.error };
     console.error(JSON.stringify(error));
   });
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


 save() {
   if (this.compte.id) {
     this.edit();
   } else {
     this.create();
   }
 }

  //Creation

  onCreate() {
   this.compte = {};
   this.clearDialogMessages();
   this.form.resetForm();
   this.showDialog = true;
 }

 create() {
   this.loading=true;
   this.clearDialogMessages();
   this.isDialogOpInProgress = true;
   let idStructure=this.authenticateService.getStructureId()!=  this.firstForm.value['structureId'].id ?
   this.firstForm.value['structureId'].id : this.authenticateService.getStructureId();
   const data = {
    matricule: this.firstForm.value['matricule'],
    nom: this.firstForm.value['nom'],
    prenom: this.firstForm.value['prenom'],
    telephone: this.firstForm.value['telephone'],
    email: this.firstForm.value['email'],
    password: this.firstForm.value['password'],
    structureId: idStructure ,
    profiles: this.selectedProfil,
  };
   this.compteService.create(data).subscribe(response => {
    //this.isDialogOpInProgress = false;
    this.isSuccessful=true;
    this.loading = false;
   },
    error => this.handleError(error));
    this.isSuccessful=false;
    this.loading = false;
 }

 onselectProfil(selection:any, event:any) {

 this.profil = Object.assign({}, selection);
 if (this.profil.id){
   this.cheked=true;
   const data= this.profil.name;
   this.selectedProfil.push(data);

 }
}
  // Edit

  onEdit(selection:any) {
   this.compte = Object.assign({}, selection);
   this.clearDialogMessages();
   this.showDialog = true;
 }

 edit() {
   this.clearDialogMessages();
   this.isDialogOpInProgress = true;
   this.compteService.update(this.compte).subscribe(response => {
     let index = this.comptes.findIndex(agent => agent.id === response.id);
     this.comptes[index] = response;
     this.isDialogOpInProgress = false;
     this.showDialog = false;
     this.showMessage({ severity: 'success', summary: 'Compte modifié avec succès' });
   }, error => this.handleError(error));
 }

 isEditing() {
   return !!this.compte.id;
 }

 // Deletion

 onDelete(selection:any) {
   this.confirmationService.confirm({
     message: 'Etes-vous sur de vouloir supprimer cet agent?',
     accept: () => {
       this.delete(selection);
     }
   });
 }

 delete(selection:any) {
   this.isOpInProgress = true;
   this.compteService.delete(selection.id).subscribe(() => {
     this.comptes = this.comptes.filter(agent => agent.id !== selection.id);
     selection = null;
     this.isOpInProgress = false;
     this.totalRecords--;
     this.showMessage({ severity: 'success', summary: 'Compte supprimé avec succès' });
   }, error => this.handleError(error));
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
   if(this.authenticateService.checkPermission(this.authenticateService.getPrivilege()!, ['ROLE_ADMIN']) == true){
     this.loadStructure();
   }
   else{
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
