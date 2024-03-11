import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ConfirmationService, LazyLoadEvent, Message } from 'primeng/api';
import { Notification } from 'src/app/models/notification/notification';
import { NotificationAgent } from 'src/app/models/notification/notification-agent';
import { NotificationService } from 'src/app/services/notification/notification.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-notification',
  templateUrl: './notification.component.html',
  styleUrls: ['./notification.component.scss']
})
export class NotificationComponent implements OnInit {

  @ViewChild('dtf') form!: NgForm;
  timeoutHandle: any;
  totalRecords!: number;
  recordsPerPage = environment.recordsPerPage;
  notification: Notification = {};
  notifications:Notification[]=[];
  notificationAgent: NotificationAgent = {};
  notificationAgents:NotificationAgent[]=[];
  enableCreate = true;
  enableBtnInfo = true;
  enableBtnEdit = false;
  enableBtnDelete = true;
  selection: any;
  isLoading!: boolean;
  isOpInProgress!: boolean;
  isDialogOpInProgress!: boolean;
  showDialog = false;
  message: any;
  dialogErrorMessage: any;
  notificationShow!: boolean;
  notificationAgentShow!: boolean;
  constructor(private notificationService: NotificationService,
    private confirmationservice: ConfirmationService,) { }

  ngOnInit(): void {
    this.load();
  }


  load(event?: LazyLoadEvent) {
    this.isLoading = true;
    this.notificationService.getAll(event).subscribe(
      (response) => {
        this.isLoading = false;
        this.notifications = response.notifications;
      },
      (error) => {
        this.message = { severity: 'error', summary: error.error };
        console.error(JSON.stringify(error));
      }
    );
  }

/*  getNotificationById(id:number, event?: LazyLoadEvent) {
    this.isLoading = true;
    this.notificationService.getAllByID(id,event).subscribe(
      (response) => {
        this.isLoading = false;
        this.notificationAgents = response.notificationAgents;
      },
      (error) => {
        this.message = { severity: 'error', summary: error.error };
        console.error(JSON.stringify(error));
      }
    );
  }
*/
  onCreate() {
    this.notification = {};
    this.clearDialogMessages();
    this.form.resetForm();
    this.showDialog = true;
  }

  create() {
    this.clearDialogMessages();
    this.isDialogOpInProgress = true;
    this.notificationService.create(this.notification).subscribe(response => {
      if (this.notifications.length !== this.recordsPerPage) {
        this.notifications.push(response);
        this.notifications = this.notifications.slice();
      }
      this.totalRecords++;
      this.isDialogOpInProgress = false;
      this.showDialog = false;
      this.showMessage({ severity: 'success', summary: 'Notification créée avec succes' });
    }, error => this.handleError(error));
  }


  onDelete(selection: any) {
    this.confirmationservice.confirm({
      message: 'Etes-vous sur de vouloir supprimer ce cette notification?',
      accept: () => {
        this.delete(selection);
      },
    });
  }

  delete(selection: any) {
    this.isOpInProgress = true;
    this.notificationService.delete(this.selection.id).subscribe(() => {
      this.notifications = this.notifications.filter(notification => notification.id !== this.selection.id);
      this.selection = null;
      this.isOpInProgress = false;
      this.totalRecords--;
      this.showMessage({ severity: 'success', summary: 'Notification supprimée avec succès' });
    }, error => {
      console.error(JSON.stringify(error));
      this.isOpInProgress = false;
      this.showMessage({ severity: 'error', summary: error.error.message });
    });
  }


  onInfo(selection: any) {
    this.notification = Object.assign({}, selection);
    this.notificationShow= true;
  }

  onInfoI(selection: any) {
    this.notificationAgent = Object.assign({}, selection);
    this.notificationAgentShow= true;
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
