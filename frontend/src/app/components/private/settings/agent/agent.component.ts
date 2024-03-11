import { ProfilService } from './../../../../services/parametrage/profil.service';
import { MinistereService } from './../../../../services/parametrage/ministere.service';
import { StructureService } from './../../../../services/parametrage/structure.service';
import { AuthenticationService } from 'src/app/services/parametrage/authentication.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ConfirmationService, LazyLoadEvent, MenuItem, Message } from 'primeng/api';
import { Agent, AgentStruct } from 'src/app/models/parametrage/agent';
import { MinistereStructure } from 'src/app/models/parametrage/ministereStructure';
import { AgentService } from 'src/app/services/parametrage/agent.service';
import { MinisterStructureService } from 'src/app/services/parametrage/minister-structure.service';

import { environment } from 'src/environments/environment';
import { Ministere } from 'src/app/models/parametrage/ministere';
import { Structure } from 'src/app/models/parametrage/structure';
import { Profil } from 'src/app/models/parametrage/profil';


@Component({
  selector: 'app-agent',
  templateUrl: './agent.component.html',
  styleUrls: ['./agent.component.scss']
})
export class AgentComponent implements OnInit {

  @ViewChild('dtf') form!: NgForm;

  timeoutHandle: any;
  totalRecords!: number;
  recordsPerPage = environment.recordsPerPage;
  agents!: Agent[]
  // selection: any;
  agent: Agent = {};
  agentStruct: AgentStruct = {};
  enableCreate = false;
  enableBtnInfo = true;
  enableBtnEdit = true;
  enableBtnDelete = false;
  enableBtnEmail = true;
  enableBtnChanger = false;
  enableBtnEditProfil = false;
  isLoading!: boolean;
  isOpInProgress!: boolean;
  isDialogOpInProgress!: boolean;
  showDialog = false;
  message: any;
  dialogErrorMessage: any;
  showDialog2: boolean = false;
  showProfilDialog: boolean = false;
  ministereStructure: MinistereStructure = {};
  enableMinistereStructure = false;
  permissions: any;
  ministeres!: Ministere[];
  ministere: Ministere = {};
  ministereID: any;
  structureID: any;
  structs: Structure[] = [];
  profils: Profil[] = [];
  profil: Profil = {};
  agentProfils: Profil[] = [];
  agentProfil: Profil = {};
  selectedProfil: any[] = [];
  cheked: boolean = false;
  showDetailsAgent: boolean =false;
  constructor(private agentService: AgentService,
    private confirmationService: ConfirmationService,
    private ministereStructureService: MinisterStructureService,
    public authService: AuthenticationService,
    private structureService: StructureService,
    private ministereService: MinistereService,
    private profilService: ProfilService) { }

  ngOnInit(): void { 
    this.permissions = this.authService.getPrivilege();
    this.enableBtnChanger = this.authService.checkPermission(this.permissions, ['ROLE_ADMIN']);
    this.enableBtnEditProfil = this.authService.checkPermission(this.permissions, ['ROLE_ADMIN']);
    this.load();
    this.loadMinistere();
    if(this.enableBtnEditProfil){
    this.loadProfil();
    }
  }


  loadMinistere(event?: LazyLoadEvent) {
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


  load(event?: LazyLoadEvent) {
    this.isLoading = true;
    let id = this.authService.getStructureId();
    this.agentService.getAll(id, event).subscribe(response => {
      this.isLoading = false;
      this.agents = response.agents;

    }, error => {
      this.message = { severity: 'error', summary: error.error };
      console.error(JSON.stringify(error));
    });
  }


  onSentMail(selection: any) {
    this.confirmationService.confirm({
      message: 'Etes-vous sur de renvoyer le mail?',
      accept: () => {
        this.resentMail(selection);
      }
    });
  }
  resentMail(selection: any) {
    this.agentService.resentMail(selection.id).subscribe(response => {
      this.showMessage({ severity: 'success', summary: 'Mail envoyé avec succès' });
    }, error => this.handleError(error));
  }
  //Détail
  onInfo(selection: any) {
    this.agentService.getAgentWithStructure(selection.id).subscribe(response => {
      this.agentStruct = response;
    },);
    this.clearDialogMessages();
    this.form.reset();
    this.showDetailsAgent = true;
  
  }
  onChangeMinistere(selection: any) {
    this.agent = Object.assign({}, selection);
    this.clearDialogMessages();
    this.showDialog2 = true;
  }

  changeAgentStructure() {
    const data = {
      username: this.agent.matricule,
      structureId: this.structureID
    }
    this.agentService.changerStructure(data).subscribe(response => {
      this.showDialog2 = false;
      this.showMessage({ severity: 'success', summary: 'Agent affecté avec succès' });
    }, error => this.handleError(error));
  }

  save() {
    if (this.agent.id) {
      this.edit();
    } else {
      this.create();
    }
  }

  //Creation

  onCreate() {
    this.agent = {};
    this.clearDialogMessages();
    this.form.resetForm();
    this.showDialog = true;
  }

  create() {
    this.clearDialogMessages();
    this.isDialogOpInProgress = true;
    this.agentService.create(this.agent).subscribe(response => {
      if (this.agents.length !== this.recordsPerPage) {
        this.agents.push(response);
        this.agents = this.agents.slice();
      }
      this.totalRecords++;
      this.isDialogOpInProgress = false;
      this.showDialog = false;
      this.showMessage({ severity: 'success', summary: 'Agent créé avec succès' });
    }, error => this.handleError(error));
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


  onselectProfil(selection: any, event: any) {
    this.profil = Object.assign({}, selection);
    this.selectedProfil = this.agent.profiles as string[];
    let index = this.selectedProfil.indexOf(this.profil.name);
    if (index !== -1) {
      this.selectedProfil.splice(index, 1);
    } else{
      this.selectedProfil.push(this.profil.name);
    }
  }
  // Edit

  onEdit(selection: any) {
    this.agent = Object.assign({}, selection);
    this.clearDialogMessages();
    this.showDialog = true;
  }

  edit() {
    this.clearDialogMessages();
    this.isDialogOpInProgress = true;
    this.agentService.update(this.agent).subscribe(response => {
      let index = this.agents.findIndex(agent => agent.id === response.id);
      this.agents[index] = response;
      this.isDialogOpInProgress = false;
      this.showDialog = false;
      this.showMessage({ severity: 'success', summary: 'Agent modifié avec succès' });
    }, error => this.handleError(error));
  }

  onEditProfil(selection: any) {
    this.agent = Object.assign({}, selection);
    this.agentProfils = [];
    for (let i = 0; i < this.agent.profiles!.length; i++) {
      for (let k = 0; k < this.profils.length; k++) {
        if (this.profils[k].name == this.agent.profiles![i] as string) {
          this.agentProfils.push(this.profils[k]);
        }
      }
    }
    /* this.agentProfils = [];
     if(this.agent.profiles){
       for (var prof of this.agent.profiles) { 
         let name = prof as string;
         let profData : AgentProfil = {};
         profData.name = name;
         this.agentProfils.push(profData);
       }
     }*/
    this.clearDialogMessages();
    this.showProfilDialog = true;
  }

  modifierProfil() {
    const data = {
      matricule: this.agent.matricule,
      profiles: this.selectedProfil,
    };
    this.clearDialogMessages();
    this.isDialogOpInProgress = true;
    this.agentService.updateProfil(data).subscribe(response => {
      let index = this.agents.findIndex(agent => agent.id === response.id);
      this.agents[index] = response;
      this.isDialogOpInProgress = false;
      this.showProfilDialog = false;
      this.showMessage({ severity: 'success', summary: 'Agent modifié avec succès' });
    }, error => this.handleError(error));
  }

  isEditing() {
    return !!this.agent.id;
  }

  // Deletion

  onDelete(selection: any) {
    this.confirmationService.confirm({
      message: 'Etes-vous sûre de vouloir supprimer cet agent?',
      accept: () => {
        this.delete(selection);
      }
    });
  }

  delete(selection: any) {
    this.isOpInProgress = true;
    this.agentService.delete(selection.matricule).subscribe(() => {
      this.agents = this.agents.filter(agent => agent.id !== selection.id);
      selection = null;
      this.isOpInProgress = false;
      this.totalRecords--;
      this.showMessage({ severity: 'success', summary: 'Agent supprimé avec succès' });
    }, error => {
      console.error(JSON.stringify(error));
      this.isOpInProgress = false;
      this.showMessage({ severity: 'error', summary: error.error.message });
    });
  }

  // Modifier agent

  getStructureByMinistere(id: number) {
    this.structs = [];
    this.structureService.getStructureByMinistereId(id).subscribe(response => {

      this.structs = response.structures;

    }, error => {
      this.message = { severity: 'error', summary: error.error };
      console.error(JSON.stringify(error));
    });
  }

  selectMinistere(event: any) {

    this.ministereID = event.value;
    this.getStructureByMinistere(this.ministereID);

  }

  selectStructure(event: any) {
    this.structureID = event.value;
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
