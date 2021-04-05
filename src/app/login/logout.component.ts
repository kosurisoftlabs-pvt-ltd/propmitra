import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'app/auth/auth.service';
import { ToastrService } from 'ngx-toastr';

@Component({
    template: ''
  })

  export class LogoutComponent implements OnInit {

    constructor(private _authService: AuthService, private router: Router, private toastr: ToastrService) {}
  
    ngOnInit() {
      this._authService.logout();
      /*setTimeout(() =>this.toastr.success('Please login again to continue...', 'You\'ve successfully logged out!', {
        timeOut: 4000
      }));
      this.router.navigate(['login']);*/
    }
  
  }