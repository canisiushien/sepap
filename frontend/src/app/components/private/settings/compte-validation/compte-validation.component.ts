import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { CompteService } from 'src/app/services/parametrage/compte.service';
import { ConfirmedValidator } from 'src/app/theme/confirmed.validator';

@Component({
  selector: 'app-compte-validation',
  templateUrl: './compte-validation.component.html',
  styleUrls: ['./compte-validation.component.scss']
})
export class CompteValidationComponent implements OnInit {
  form: FormGroup = new FormGroup({});
  key:any;
  password!: string;
  confirm_password?:string;
  messageErreur : string ="";
  dialogErrorMessage: any;
  constructor( private fb: FormBuilder,private compteService: CompteService,private route: ActivatedRoute, private router: Router) {
    route.queryParams.subscribe((params) => {
        this.key = params['key'];
      });

      this.form = fb.group({
        password: ['', [Validators.required]],
        confirm_password: ['', [Validators.required]]
      }, {
        validator: ConfirmedValidator('password', 'confirm_password')
      })
    }

  ngOnInit(): void {
  }

  get f(){
    return this.form.controls;
  }


  onSubmit(): void {
    this.password = this.form.value['password'];
     this.compteService.activeCompte(this.password , this.key).subscribe(
       data => {
        this.router.navigate(["/login"]);
       },
       err => this.handleError(err));
   }
   handleError(err: HttpErrorResponse) {
    console.error(`Processing Error: ${JSON.stringify(err)}`);
    this.dialogErrorMessage = err.error.message;
  }
}



