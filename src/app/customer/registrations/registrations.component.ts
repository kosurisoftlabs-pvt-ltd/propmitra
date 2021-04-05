import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormArray, ValidatorFn, FormControl } from '@angular/forms';
import { MatTableDataSource } from '@angular/material';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { CustomerService } from '../customer.service';

@Component({
  selector: 'app-registrations',
  templateUrl: './registrations.component.html',
  styleUrls: ['./registrations.component.scss']
})
export class RegistrationsComponent implements OnInit {

  isUserSelected: boolean = true;
  userForm: FormGroup;
  userList: any[];
  dataSource: MatTableDataSource<any>;
  displayedColumns: string[] = ['userIdChk', 'name', 'contact', 'email', 'username', 'city', 'isActive'];
  constructor(fb: FormBuilder, private spinner: NgxSpinnerService, private service: CustomerService,
    private toastr: ToastrService) {
    this.userForm = fb.group({
      userChks: new FormArray([], this.minSelectedCheckboxes(1)),
    });
  }

  ngOnInit() {
    this.getRegistrations();
  }

  getRegistrations() {
    this.spinner.show();
    let msg1 = "";
    let msg2 = "";
    this.service.getRegistrations()
      .subscribe(
        res => {
          //console.log('response', res);
          if (res.body != null && res.body != undefined) {
            this.userList = res.body;
              //console.log('this.userList', this.userList);
              this.addCheckboxes();
              this.dataSource = new MatTableDataSource<any>(this.userList);
          } else if (res.status == 204) {
            this.userList = [];
            this.dataSource = new MatTableDataSource<any>(this.userList);
          }
          this.spinner.hide();
        },
        err => {
          //console.log("Error occured", err);
          this.spinner.hide();
          msg2 = "Oops... something went wrong!";
          msg1 = "Please try again after some time!";
          this.toastr.error(msg1, msg2, {
            timeOut: 4000
          });
        }
      );
  }

  public approve(): void {
    let formData: FormData = new FormData();
    formData.append("value", "1");
    this.processApproveOrReject(formData, "approved");
  }

  public reject(): void {
    let formData: FormData = new FormData();
    formData.append("value", "0");
    this.processApproveOrReject(formData, "rejected");
  }

  private processApproveOrReject(formData: FormData, msg: string) {
    const selectedIds = this.userForm.value.userChks
      .map((v, i) => v ? this.userList[i].id : null)
      .filter(v => v !== null);
      //console.log('selectedIds', selectedIds);
      if(selectedIds.length > 0) {
        this.isUserSelected = true;
      } else {
        this.isUserSelected = false;
      }
      if (this.isUserSelected && this.userForm.valid) {
        formData.append("uids", selectedIds);
        this.spinner.show();
        this.service.activeUpdate(formData)
          .subscribe(
            res => {
              //console.log("res", res);
              this.spinner.hide();
              if (res.status == 200) {
                this.getRegistrations();
                setTimeout(() => this.toastr.success('Selected user registration(s) were successfully ' + msg + '.', 'Success!', {
                  timeOut: 4000
                }));
              }
            },
            err => this.toastError(err)
          );
      } else {
        Object.keys(this.userForm.controls).forEach(field => { // {1}
          const control = this.userForm.get(field);            // {2}
          control.markAsTouched({ onlySelf: true });       // {3}
        });
      }
  }



  toastError(err: any) {
    //console.log("Error occured", err);
    this.spinner.hide();
    let msg1 = "";
    let msg2 = "";
    if (err.error.message != undefined) {
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


  private minSelectedCheckboxes(min = 1) {
    const validator: ValidatorFn = (formArray: FormArray) => {
      const totalSelected = formArray.controls
        .map(control => control.value)
        .reduce((prev, next) => next ? prev + next : prev, 0);
      return totalSelected >= min ? null : { required: true };
    };
    return validator;
  }

  private addCheckboxes() {
    this.userList.map((o, i) => {
      const control = new FormControl();
      (this.userForm.controls.userChks as FormArray).push(control);
    });
  }

}
