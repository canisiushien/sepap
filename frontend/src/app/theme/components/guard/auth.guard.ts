import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot,Router } from '@angular/router';
import { GuardService } from 'src/app/theme/components/guard/guard.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  constructor(
    private router : Router,
    private guardService : GuardService
  ) { 

  }
  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {      
    if (this.guardService.isLoggedIn()) { 
        return true;      
    }      
    // navigate to login page as user is not authenticated      
    this.router.navigate(['/login']);      
    return false;      
  } 
  
}
