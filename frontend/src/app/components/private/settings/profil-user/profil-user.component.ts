import { CompteService } from 'src/app/services/parametrage/compte.service';
import { Compte } from 'src/app/models/parametrage/compte';
import { Component, OnInit } from '@angular/core';
import { LazyLoadEvent } from 'primeng/api';
import { HttpErrorResponse } from '@angular/common/http';
import { Location } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-profil-user',
  templateUrl: './profil-user.component.html',
  styleUrls: ['./profil-user.component.scss']
})

export class ProfilUserComponent implements OnInit {
  showProfil?:boolean=false;
  compte:Compte={};
  comptes: Compte[]=[];
  constructor(private compteService: CompteService,
    private location: Location,
    private router: Router) { }

  ngOnInit(): void {
   // this.getUserProfil();
  }

  cancel(){
    this.router.navigate(['/workspace'])
      .then(() => {
        window.location.reload();
      });
    //this.location.back();
  }
}
