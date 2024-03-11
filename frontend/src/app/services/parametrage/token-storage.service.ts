import { Injectable } from '@angular/core';

const TOKEN_NAME = 's2epap_token';
const TOKEN_EXPIRES_DATE = 's2epap_expires_date'; 
const CONNECTED_USER_DATA = "s2epap_user_data";
//const AGENT_VALUE = "s2epap_agent_at";

@Injectable({
  providedIn: 'root'
})
export class TokenStorageService {
  
  constructor() { }

  signOut(): void {
    window.sessionStorage.clear();
  }

  public saveToken(token: string): void {
    window.sessionStorage.removeItem(TOKEN_NAME);
    window.sessionStorage.setItem(TOKEN_NAME, token);
  }

  public getToken(): string | null {
    return window.sessionStorage.getItem(TOKEN_NAME);
  }

  public saveUserData(user: any): void {
    window.sessionStorage.removeItem(CONNECTED_USER_DATA);
    window.sessionStorage.setItem(CONNECTED_USER_DATA, JSON.stringify(user));
  }

  public saveCompte(user: any, agent:any, token:string, expiration: any): void {

    window.sessionStorage.removeItem(CONNECTED_USER_DATA);
    window.sessionStorage.setItem(CONNECTED_USER_DATA, JSON.stringify(user));

    //window.sessionStorage.removeItem(AGENT_VALUE);
    // window.sessionStorage.setItem(AGENT_VALUE, JSON.stringify(agent));

    window.sessionStorage.removeItem(TOKEN_NAME);
    window.sessionStorage.setItem(TOKEN_NAME, token); 

    window.sessionStorage.removeItem(TOKEN_EXPIRES_DATE);
    window.sessionStorage.setItem(TOKEN_EXPIRES_DATE, expiration);
  }

  public getUserData(): any {
    const user = window.sessionStorage.getItem(CONNECTED_USER_DATA);
    if (user) {
      return JSON.parse(user);
    }

    return {};
  } 

  // public getAgent(): any {
  //   const agent = window.sessionStorage.getItem(AGENT_VALUE); 
  //   if (agent) {
  //     return JSON.parse(agent);
  //   }

  //   return {};
  // } 

  isTokenExpired(): boolean {
    let expiration = this.getExpiration();  
    if (expiration) {
      return !(Date.now() < expiration);
    } 
    return false;
  }

  private getExpiration(): number {
    let expiresAt: number; 
    const expiration = localStorage.getItem(TOKEN_EXPIRES_DATE); 
    if (expiration) {
        expiresAt = JSON.parse(expiration);
        return expiresAt;
    }
      return -1;
  }
}