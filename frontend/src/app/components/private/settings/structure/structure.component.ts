import { AppModule } from './../../../../app.module';
import { AuthenticationService } from 'src/app/services/parametrage/authentication.service';
import { StructureStatut, TypeStructure } from './../../../../models/parametrage/structure';
import { MinistereService } from './../../../../services/parametrage/ministere.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ConfirmationService, LazyLoadEvent, Message } from 'primeng/api';
import { Structure } from 'src/app/models/parametrage/structure';
import { StructureService } from 'src/app/services/parametrage/structure.service';
import { environment } from 'src/environments/environment';
import { Ministere } from 'src/app/models/parametrage/ministere';
import { MinistereStructure } from 'src/app/models/parametrage/ministereStructure';
import { MinisterStructureService } from 'src/app/services/parametrage/minister-structure.service';

@Component({
  selector: 'app-structure',
  templateUrl: './structure.component.html',
  styleUrls: ['./structure.component.scss'],
})
export class StructureComponent implements OnInit {

  @ViewChild('dtf') form!: NgForm;

  timeoutHandle: any;
  totalRecords!: number;
  recordsPerPage = environment.recordsPerPage;

  enableCreate = true;
  enableMinistereStructure = false;
  enableBtnInfo = true;
  enableBtnEdit = false;
  enableBtnDelete = false;
  isLoading!: boolean;
  isOpInProgress!: boolean;
  isDialogOpInProgress!: boolean;
  showDialog = false;
  showDialog2 = false;
  structureDetail: boolean = false;

  message: any;
  dialogErrorMessage: any;

  structures!: Structure[];
  // selection: any;
  structure: Structure = {};
  ministeres!: Ministere[];
  ministereStructures!: MinistereStructure[];
  ministereStructure: MinistereStructure = {};
  Typestructures!: TypeStructure[];
  ministereControl: boolean = true;
  modifStructure: boolean = false;
  structureSelected: any[] = [];
  cleanStructures: any[] = [];
  //currentParent: Structure = {};
  permissions: any;

  constructor(
    private structureService: StructureService, private ministereStructureService: MinisterStructureService,
    private confirmationservice: ConfirmationService, private ministereService: MinistereService,
    public authService: AuthenticationService) {

    }

  ngOnInit(): void {
    this.loadAllStructures();
    this.loadMinisteres();
    this.loadType();
    this.permissions = this.authService.getPrivilege();
    this.enableBtnEdit = this.authService.checkPermission(this.permissions, ['ROLE_ADMIN', 'ROLE_DIR_DGESS', 'ROLE_RESP_DGESS']);
  }

  loadAllStructures(event?: LazyLoadEvent) {
    this.isLoading = true;
    this.structureService.getAll(event).subscribe(
      (response) => {
        this.isLoading = false;
        this.structures = response.structures;
      },
      (error) => {
        this.message = { severity: 'error', summary: error.error };
      }
    );
  }

  filterStructure(data?: any) {
    this.cleanStructures = [];
    if (data && data.id) {
      this.structure = Object.assign(this.structure, data);
      if (this.structure.parent) {
        this.cleanStructures.push(this.structure.parent);
      }
    }

    this.structures.forEach(strc => {
      if (strc !== data) {
          this.cleanStructures.push(strc);
      }
    })
  }

  loadMinisteres(event?: LazyLoadEvent) {
    this.isLoading = true;
    this.ministereService.getAll(event).subscribe(
      (response) => {
        this.isLoading = false;
        this.ministeres = response.ministeres;
      },
      (error) => {
        this.message = { severity: 'error', summary: error.error };
      }
    );
  }

  getMinistereStructures(event: any) {
    let ministereId = event.value;
    this.isLoading = true;
    this.structureService.getStructureByMinistereId(ministereId).subscribe(
      (response) => {
        this.isLoading = false;
        this.structures = response.structures;
      },
      (error) => {
        this.message = { severity: 'error', summary: error.error };
      }
    );
  }

  loadType(event?: LazyLoadEvent) {
    this.isLoading = true;
    this.structureService.getAllType(event).subscribe(
      (response) => {
        this.isLoading = false;
        this.Typestructures = response.Typestructures;
      },
      (error) => {
        this.message = { severity: 'error', summary: error.error };
      }
    );
  }

  isEditing() {
    return !!this.structure.id;
  }

  onInfo(selection: any) {
    this.structure = Object.assign({}, selection);
    this.structureDetail = true;
  }

  onEdit(selection: Structure) {
    //this.structure = Object.assign({}, selection);
    this.filterStructure(selection);
    //this.clearDialogMessages();
    this.showDialog = true;
    this.modifStructure = true;
  }

  onDelete(selection: any) {
    this.confirmationservice.confirm({
      message: 'Etes-vous sûr de vouloir supprimer cette structure?',
      accept: () => {
        this.delete(selection);
      },
    });
  }

  onCreate() {
    this.structure = {};
    this.clearDialogMessages();
    this.form.resetForm();
    this.showDialog = true;
    this.modifStructure = false;
    this.filterStructure(null);
  }

  onCreateMS() {
    this.ministereStructure = {};
    this.clearDialogMessages();
    this.form.resetForm();
    this.showDialog2 = true;
  }

  create() {

    const data = {
      sigle: this.structure.sigle,
      libelle: this.structure.libelle,
      description: this.structure.description,
      type: this.structure.type,
      statut: StructureStatut.ACTIF,
      telephone: this.structure.telephone,
      emailResp: this.structure.emailResp,
      emailStruct: this.structure.emailStruct,
      parent: this.structure.parent,
      ministere: this.structure.ministere,
      niveau: this.structure.niveau
    }

    this.clearDialogMessages();
    this.isDialogOpInProgress = true;
    this.structureService.create(data).subscribe(
      (response) => {
        this.loadAllStructures()
        this.isDialogOpInProgress = false;
        this.showDialog = false;
        this.showMessage({
          severity: 'success',
          summary: 'Structure créée avec succès',
        });
      },
      (error) => this.handleError(error)
    );
  }

  createMS() {
    this.clearDialogMessages();
    this.isDialogOpInProgress = true;
    this.ministereStructureService.create(this.ministereStructure).subscribe(
      (response) => {
        this.isDialogOpInProgress = false;
        this.showDialog2 = false;
        this.showMessage({
          severity: 'success',
          summary: 'Structure modifiée avec succès',
        });
      },
      (error) => this.handleError(error)
    );
  }

  edit() {
    this.clearDialogMessages();
    this.isDialogOpInProgress = true;
    this.structureService.update(this.structure).subscribe(
      (response) => {
        this.loadAllStructures();
        this.isDialogOpInProgress = false;
        this.showDialog = false;
        this.showMessage({
          severity: 'success',
          summary: 'Structure modifiée avec succès',
        });
      },
      (error) => this.handleError(error)
    );
  }

  delete(selection: any) {
    this.isOpInProgress = true;
    this.structureService.delete(selection.id).subscribe(
      () => {
        this.structures = this.structures.filter(
          (structure) => structure.id !== selection.id
        );
        selection = null;
        this.isOpInProgress = false;
        this.totalRecords--;
        this.showMessage({
          severity: 'success',
          summary: 'structure supprimée avec succès',
        });
      },
      (error) => {
        console.error(JSON.stringify(error));
        this.isOpInProgress = false;
        this.showMessage({ severity: 'error', summary: error.error.message });
      }
    );
  }

  save() {
    if (this.structure.id) {
      this.edit();
    } else {
      this.create();
    }
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

  // Errors
  handleError(error: HttpErrorResponse) {
    this.isDialogOpInProgress = false;
    this.dialogErrorMessage = error.error.title;
  }
}
