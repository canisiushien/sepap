import { AmendementActiviteService } from './../../../../services/programmation/amendement-activite.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute} from '@angular/router';
import { LazyLoadEvent } from 'primeng/api';
import { ProgrammationForEvaluationDTO } from 'src/app/models/programmation/programmation-for-evaluation-dto';
import { Taches } from 'src/app/models/programmation/taches';
import { Location } from '@angular/common';

@Component({
  selector: 'app-etats-activite-details',
  templateUrl: './etats-activite-details.component.html',
  styleUrls: ['./etats-activite-details.component.scss']
})
export class EtatsActiviteDetailsComponent implements OnInit {

  idp!: number;
  taches: Taches[] = [];
  isLoading!: boolean;
  message: any;
  programmationForEvaluationDTO: ProgrammationForEvaluationDTO = {};
  constructor(private route: ActivatedRoute,private router: Location,
    private amendementActiviteService: AmendementActiviteService) { }

  ngOnInit(): void {
    this.idp = this.route.snapshot.params['idp'];
    this.load();
  }
  load(event?: LazyLoadEvent){
    this.isLoading = true;
    this.amendementActiviteService.getProgrammationForEvaluationDTO(this.idp,event).subscribe(response => {
      this.isLoading = false;
      this.programmationForEvaluationDTO = response;
      this.taches = response.taches as any;
    }, error => {
      this.message = { severity: 'error', summary: error.error };
      console.error(JSON.stringify(error));
    });
  }

 onReturn(){
  this.router.back();
 }

}
