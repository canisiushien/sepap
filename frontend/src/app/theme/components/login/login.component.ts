import { Component, OnInit, ViewChild } from '@angular/core';
import { ConfirmationService, LazyLoadEvent, Message } from 'primeng/api';
import { AuthenticationService } from '../../../services/parametrage/authentication.service';
import { Authentication } from 'src/app/models/parametrage/authentication';
import { environment } from 'src/environments/environment';
import { NgForm } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { GuardService } from '../guard/guard.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  username: string = "";
  password: string = "";
  rememberMe: boolean = false;
  spinner: boolean = true;
  erreur: boolean = true;
  messageErreur: string = "";

  timeoutHandle: any;
  totalRecords!: number;
  recordsPerPage = environment.recordsPerPage;
  authenticate: Authentication = {};
  isLoading!: boolean;
  isOpInProgress!: boolean;
  isDialogOpInProgress!: boolean;
  showDialog = false;
  message: any;
  dialogErrorMessage: any;
  permissions : any;
  constructor(private authService: AuthenticationService,
    private router: Router,
    private guardService: GuardService) {
    if (this.guardService.isLoggedIn()) {
      this.router.navigate(['/workspace'])
      .then(() => {
        window.location.reload();
      });
    }
  }

  ngOnInit(): void {

  }

  handleError(error: HttpErrorResponse) {
    this.spinner = true;
    this.erreur = false;
    console.error(`Processing Error: ${JSON.stringify(error)}`);
    //this.messageErreur = error.error.message;
  }

  logIn() {
    this.spinner = false;
    this.erreur = true;
    this.authenticate.username = this.username;
    this.authenticate.password = this.password;
    this.authenticate.rememberMe = this.rememberMe;
    this.authService.login(this.authenticate).subscribe(

      (response) => {
        this.spinner = true;
        this.authService.saveToken(response)
        this.router.navigate(['/workspace'])
        .then(() => {
          window.location.reload();
        });
      },
      (error) => this.handleError(error)
    );
  }
 
}
