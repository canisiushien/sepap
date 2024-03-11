import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { Message } from 'primeng/api';
import { ChangePassword } from 'src/app/models/vo/change-password';
import { AuthenticationService } from 'src/app/services/parametrage/authentication.service';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.scss']
})
export class ResetPasswordComponent implements OnInit {
  @ViewChild('dtf') form!: NgForm;
  isDialogOpInProgress!: boolean;
  timeoutHandle: any;
  message: any;
  dialogErrorMessage: any;
  changePassword: ChangePassword = {};

  constructor(private authService:AuthenticationService,
              private router:Router) { }

  ngOnInit(): void {
  }

  onUpdate(): void{
    this.clearDialogMessages();
    this.isDialogOpInProgress = true;
    this.authService.changePassword(this.changePassword).subscribe(
      (data) => {
          this.authService.signOut();
          this.router.navigate(['/login']);
        //this.showMessage({ severity: 'success', summary: 'Votre mot mot de passe a été modifié avec succès' });
      },
      (error) => this.handleError(error)
    );
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
