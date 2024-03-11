import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ConfirmationService, LazyLoadEvent, Message } from 'primeng/api';
import { Ministere } from 'src/app/models/parametrage/ministere';
import { MinistereStructure } from 'src/app/models/parametrage/ministereStructure';
import { Structure } from 'src/app/models/parametrage/structure';
import { MinisterStructureService } from 'src/app/services/parametrage/minister-structure.service';
import { MinistereService } from 'src/app/services/parametrage/ministere.service';
import { StructureService } from 'src/app/services/parametrage/structure.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-ministere-structure',
  templateUrl: './ministere-structure.component.html',
  styleUrls: ['./ministere-structure.component.scss']
})
export class MinistereStructureComponent implements OnInit {
  @ViewChild('dtf') form!: NgForm;

  timeoutHandle: any;
  totalRecords!: number;
  recordsPerPage = environment.recordsPerPage;
  ministereStructures!: MinistereStructure[];
  structures!: Structure[];
  ministeres!: Ministere[];
  // selection: any;
  ministereStructure: MinistereStructure = {};
  enableCreate = true;
  enableBtnInfo = true;
  enableBtnEdit = true;
  enableBtnDelete = false;
  isLoading!: boolean;
  isOpInProgress!: boolean;
  isDialogOpInProgress!: boolean;
  showDialog = false;
  message: any;
  dialogErrorMessage: any;
  constructor( private ministereStructureService: MinisterStructureService,private ministereService: MinistereService,
    private confirmationservice: ConfirmationService,private structureService: StructureService,) { }

  ngOnInit(): void {

  }


}
