import { Injectable } from '@angular/core';
import { AuthenticationService } from '../../../services/parametrage/authentication.service';

@Injectable({
  providedIn: 'root'
})
export class GuardService {

  constructor(private authService: AuthenticationService) { }

  public isLoggedIn(): boolean {      
    let status = false; 
    let session = this.authService.getToken(); 
    let tokenExpired = this.authService.tokenIsExpired();  
    if (session != null && !tokenExpired) {      
        status = true;      
    }    
    else {      
        status = false;      
        }      
    return status;      
      
  }
}
