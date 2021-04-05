import { Injectable } from '@angular/core';
import { Router, CanActivate } from '@angular/router';
import { AuthService } from './auth.service';
import { ToastrService } from 'ngx-toastr';

@Injectable()
export class AuthGuardService implements CanActivate {

    constructor(public auth: AuthService, public router: Router, private toastr: ToastrService) { }

    canActivate(): boolean {
        //console.log('authguard', this.auth.isLoggedIn());
        if (!this.auth.isLoggedIn()) {
            this.toastr.error('Please login again...', 'Your Session has been Expired!', {
                timeOut: 4000
              });
            //this.router.navigate(['login']);
            window.location.href = '/signin';
        }
        return true;
    }

}