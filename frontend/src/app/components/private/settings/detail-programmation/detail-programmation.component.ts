import { Location } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Message, MessageService } from 'primeng/api';
import { Commentaire } from 'src/app/models/programmation/commentaire';
import { Programmation } from 'src/app/models/programmation/programmation';
import { AmendementActiviteService } from 'src/app/services/programmation/amendement-activite.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-detail-programmation',
  templateUrl: './detail-programmation.component.html',
  styleUrls: ['./detail-programmation.component.scss']
})
export class DetailProgrammationComponent implements OnInit {

  @ViewChild('dtf') form!: NgForm;

  taches :any[] = [];
  programmation: Programmation = {};
  timeoutHandle: any;
  totalRecords!: number;
  recordsPerPage = environment.recordsPerPage;
  showDialog = false;
  message: any;
  isOpInProgress!: boolean;
  isDialogOpInProgress!: boolean;
  dialogErrorMessage: any;
  commentaire: Commentaire = {};
  ids!: number;
  idp!: number;

  constructor(private route:ActivatedRoute, private location:Location,
    private amendementActiviteService:AmendementActiviteService) { }

  ngOnInit(): void {
    this.ids = this.route.snapshot.params['ids'];
    this.idp = this.route.snapshot.params['idp'];
    this.commentaire.programmationId = this.idp;
    this.loadprogrammation(this.ids,this.idp);
  }

  loadprogrammation(ids:number,idp:number){
    this.amendementActiviteService.getProgrammationbyStructProgram(ids,idp).subscribe(data => {
      this.programmation=data;
     this.taches =this.programmation.taches as any;

    }, error => this.handleError(error));
  }

  //Retour à la liste
  onReturn() {
    this.location.back();
  }

  // Errors
  handleError(error: HttpErrorResponse) {
    console.error(`Processing Error: ${JSON.stringify(error)}`);
    this.isDialogOpInProgress = false;
    this.dialogErrorMessage = error.error.message;
  }

  //Messages
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
