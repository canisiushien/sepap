import { environment } from './../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Authentication } from 'src/app/models/parametrage/authentication';
import { JwtHelperService } from '@auth0/angular-jwt';
import { ChangePassword } from 'src/app/models/vo/change-password';
import { PasswordForgot } from 'src/app/models/vo/password-forgot';

const authenticationUrl = environment.authenticationRessource;
const passwordForgetUrl = environment.passwordForgetRessource;
const changePasswordUrl = environment.changePasswordRessource;
const TOKEN_KEY = 'auth-token';

@Injectable({
  providedIn: 'root'
})
export  class AuthenticationService {

  constructor(private http: HttpClient) { }

  login(request: Authentication): Observable<Authentication> {
    return this.http.post(authenticationUrl, request);
  }

  passwordForget(request: PasswordForgot): Observable<PasswordForgot>{
    return this.http.post(passwordForgetUrl, request);
  }

  changePassword(changePassword: ChangePassword): Observable<ChangePassword> {
    return this.http.post(changePasswordUrl, changePassword);
  }

  tokenIsExpired(): boolean {
    const jwtHelper = new JwtHelperService();
    const token = this.getToken();
    if(token != null){
      const tokenExpired = jwtHelper.isTokenExpired(token);
      return tokenExpired;
    }
    return true;
  }

  public saveToken(token: any): void {
    localStorage.removeItem(TOKEN_KEY);
    localStorage.setItem(TOKEN_KEY, JSON.stringify(token));
  }

  public getToken(): string | null {
    let token = JSON.parse(localStorage.getItem(TOKEN_KEY)!)
    if(token != null){
      token = token.access_token;
    }
    return token;
  }

  public  getPrivilege(): Array<any> | null {
    let privilege= JSON.parse(localStorage.getItem(TOKEN_KEY)!)
    if(privilege != null){
      privilege = privilege.additionalInfo.privileges;
    }
    return privilege;
  }

  public  getUsername(): String | null {
    let user= JSON.parse(localStorage.getItem(TOKEN_KEY)!)
    if(user != null){
      user = user.additionalInfo.username;
    }
    return user;
  }

  public tokenDecode() : any {
    const jwtHelper = new JwtHelperService();
    return jwtHelper.decodeToken(this.getToken()!);
  }

  signOut(): void {
    window.sessionStorage.clear();
    window.localStorage.clear();
    localStorage.removeItem(TOKEN_KEY);
  }

  getStructureId(): number {
    const user = JSON.parse(localStorage.getItem(TOKEN_KEY)!);
    return  user.structureId;
  }

  public  checkPermission(permissions: string[], perm:string[]): boolean{
    let resultat=false;
    for( let i=0; i< permissions.length; i++){
      for(let index=0; index<perm.length; index++)
          if(permissions[i]== perm[index]){
            return true;
          }
    }
    return resultat;
  }


  public static checkPermissionTest(permissions: string[], perm:string[]): boolean{
    let resultat=false;
    for( let i=0; i< permissions.length; i++){
      for(let index=0; index<perm.length; index++)
          if(permissions[i]== perm[index]){
            return true;
          }
    }
    return resultat;
  }

  public  static getPrivilegeTest(): Array<any> | null {
    let privilege= JSON.parse(localStorage.getItem(TOKEN_KEY)!)
    if(privilege != null){
      privilege = privilege.additionalInfo.privileges;
    }
    return privilege;
  }
}

