import { HTTP_INTERCEPTORS, HttpEvent } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpHandler, HttpRequest } from '@angular/common/http';
import {JwtHelperService} from '@auth0/angular-jwt';
import {NgxPermissionsService, NgxRolesService} from 'ngx-permissions';
import { Observable } from 'rxjs';


@Injectable()
export class RoleInterceptor implements HttpInterceptor {
  constructor(private roleService: NgxRolesService,
    private permissionService: NgxPermissionsService) { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let authReq = req;
   /* this.roleService.addRole(dec decodedToken.roles[0], decodedToken.permissions);
    this.permissionService.addPermission(decodedToken.permissions);
    if (roles != null) {

      authReq = req.clone({ headers: req.headers.set(TOKEN_HEADER_KEY, 'Bearer ' + token) });


    }*/
    return next.handle(authReq);
  }
}

export const roleInterceptorProviders = [
  { provide: HTTP_INTERCEPTORS, useClass: RoleInterceptor, multi: true }
];
