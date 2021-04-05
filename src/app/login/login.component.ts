import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {
    FormGroup,
    FormControl,
    Validators,
    FormBuilder
} from '@angular/forms';
import { AuthService } from 'app/auth/auth.service';
import { ToastrService } from 'ngx-toastr';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
    loginform: FormGroup;
    email: FormControl;
    password: FormControl;

    constructor(private fb: FormBuilder,
        private authService: AuthService, private spinner: NgxSpinnerService,
        private router: Router, private toastr: ToastrService) {

        /*this.form = this.fb.group({
            email: ['',Validators.required],
            password: ['',Validators.required]
        });*/
    }

    ngOnInit() {
        this.createFormControls();
        this.createForm();
    }

    createFormControls() {
        this.email = new FormControl('', Validators.required);
        this.password = new FormControl('', Validators.required);
    }

    createForm() {
        this.loginform = new FormGroup({
            email: this.email,
            password: this.password
        });
    }

    login() {
        const val = this.loginform.value;

        if (this.loginform.valid) {
            this.spinner.show();
            this.authService.login(val.email, val.password)
                .subscribe(
                    res => {
                        //console.log("User is logged in");
                        this.spinner.hide();
                        window.location.href = '/';
                        //this.router.navigateByUrl('/dashboard');
                    },
                    err => {
                        //console.log("Error occured", err);                        
                        this.spinner.hide();
                        let msg1 = "";
                        let msg2 = "";
                        //console.log('err.error.message', err.error.message);
                        if(err.status == 401 && err.error.message != undefined) {
                            msg2 = err.error.message;
                            msg1 = "Please try again!";
                        } else if(err.status == 401) {
                            msg2 = "Invalid user name or password";
                            msg1 = "Please try again!";
                        } else {
                            msg2 = "Oops something went wrong...";
                            msg1 = "Please try again after some time!";
                        }
                        this.toastr.error(msg1, msg2, {
                            timeOut: 4000
                          });
                          this.loginform.reset();
                    }
                );
        } else{
            Object.keys(this.loginform.controls).forEach(field => { // {1}
                const control = this.loginform.get(field);            // {2}
                control.markAsTouched({ onlySelf: true });       // {3}
              });              
        }
    }

}
