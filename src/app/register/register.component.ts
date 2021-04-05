import { Component, OnInit } from '@angular/core';
import {
    FormGroup,
    FormControl,
    Validators,
    FormBuilder
} from '@angular/forms';
import { RegisterService } from './register.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
@Component({
    selector: 'app-register',
    templateUrl: './register.component.html',
    styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

    registerform: FormGroup;
    name: FormControl;
    username: FormControl;
    email: FormControl;
    password: FormControl;
    contact: FormControl;
    city: FormControl;
    address: FormControl;

    constructor(private service: RegisterService, private spinner: NgxSpinnerService, private toastr: ToastrService) {
    }

    ngOnInit() {
        this.createFormControls();
        this.createForm();
    }

    createFormControls() {
        this.name = new FormControl('', [
            Validators.required,
            Validators.pattern("^[A-Za-z]{4,40}$")
        ]);
        this.username = new FormControl('', [
            Validators.required,
            Validators.pattern("^[a-z0-9_-]{4,15}$"),
        ]);
        this.email = new FormControl('', [
            Validators.required,
            Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$"),
        ]);
        this.password = new FormControl('', [
            Validators.required,
            Validators.pattern("^[ A-Za-z0-9_@./#&+-]{6,15}$")
        ]);
        this.contact = new FormControl('', [
            Validators.required,
            Validators.pattern("^[0-9]{10}$")
        ]);
        this.city = new FormControl('', [
            Validators.required,
            Validators.pattern("^[a-zA-Z]{4,25}$")
        ]);
        this.address = new FormControl('', [
            Validators.required,
            Validators.pattern("^[ A-Za-z0-9_@./#&+-]{4,250}$")
        ]);
    }

    createForm() {
        this.registerform = new FormGroup({
            name: this.name,
            username: this.username,
            email: this.email,
            password: this.password,
            contact: this.contact,
            city: this.city,
            address: this.address
        });
    }

    register() {
        const formData = this.registerform.value;
        if (this.registerform.valid) {
            this.spinner.show();
            this.service.register(formData)
                .subscribe(
                    res => {
                        //console.log("res", res);
                        this.spinner.hide();
                        if(res.body.message != undefined) {
                            this.registerform.reset();
                            setTimeout(() =>this.toastr.success(res.body.message, 'Registration Successful', {
                                timeOut: 4000
                            }));
                        }                        
                    },
                    err => {
                        //console.log("Error occured", err);                        
                        this.spinner.hide();
                        let msg1 = "";
                        let msg2 = "";
                        if(err.error.message != undefined) {
                            msg2 = err.error.message;
                            msg1 = "Please try again!";
                        } else {
                            msg2 = "Oops something went wrong...";
                            msg1 = "Please try again after some time!";
                        }
                        this.toastr.error(msg1, msg2, {
                            timeOut: 4000
                        });                          
                    }
                );
        } else{
            Object.keys(this.registerform.controls).forEach(field => { // {1}
                const control = this.registerform.get(field);            // {2}
                control.markAsTouched({ onlySelf: true });       // {3}
              });              
        }
    }

}
