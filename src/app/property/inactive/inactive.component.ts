import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormArray, ValidatorFn, FormControl, Validators } from '@angular/forms';
import { RangesFooter } from 'app/components/ranges-footer/ranges-footer.component';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { MatTableDataSource, MatDialog } from '@angular/material';
import { SearchService } from 'app/service/search.service';
import { PropertyService } from '../property.service';
import { StatusService } from 'app/service/status.service';
import { ConfirmPopupComponent } from 'app/popup/confirm-popup/confirm-popup.component';

@Component({
  selector: 'app-inactive',
  templateUrl: './inactive.component.html',
  styleUrls: ['./inactive.component.scss']
})
export class InactiveComponent implements OnInit {

  serachType: string = "propId";
  resultsFound: boolean = false;
  isPropSelected: boolean = true;
  inactiveForm: FormGroup;
  inactiveForm2: FormGroup;
  rangesFooter = RangesFooter;
  propList = [];
  dataSource: MatTableDataSource<any>;
  displayedColumns: string[] = ['propId', 'propertyBasic.rentSale',
    'propertyBasic.propStatus', 'isActive', 'isVerified', 'progress', 'createdAt', 'actions'];
  maxDate: Date;
  
  constructor(fb: FormBuilder, private spinner: NgxSpinnerService, private service: SearchService,
    private pService: PropertyService, private toastr: ToastrService, private statusService: StatusService, public dialog: MatDialog) {
    this.inactiveForm = fb.group({
      date: [{ begin: new Date(), end: new Date(), max: new Date() }],
      propId: [''],
    });
    this.inactiveForm2 = fb.group({
      propChks: new FormArray([], this.minSelectedCheckboxes(1)),
      comments: ['', Validators.required],
    });
    this.maxDate = new Date();
  }

  ngOnInit() { }

  public activate(): void {
    let formData: FormData = new FormData();
    formData.append("value", "1");
    this.processAvtivateDeactivate(formData, "activated");
  }

  public deactivate(): void {
    let formData: FormData = new FormData();
    formData.append("value", "0");
    this.processAvtivateDeactivate(formData, "inactivated");
  }

  private processAvtivateDeactivate(formData: FormData, msg: string) {
    const selectedProps = this.inactiveForm2.value.propChks
      .map((v, i) => v ? this.propList[i].id : null)
      .filter(v => v !== null);
    //console.log('selectedProps', selectedProps);
    if (selectedProps.length > 0) {
      this.isPropSelected = true;
    } else {
      this.isPropSelected = false;
    }
    if (this.isPropSelected && this.inactiveForm2.valid) {
      formData.append("propIds", selectedProps);
      formData.append("comments", this.inactiveForm2.value.comments);
      this.spinner.show();
      this.pService.inactiveProprties(formData)
        .subscribe(
          res => {
            //console.log("res", res);
            this.spinner.hide();
            if (res.status == 200) {
              this.searchProperties();
              setTimeout(() => this.toastr.success('Selected properties were successfully ' + msg + '.', 'Success!', {
                timeOut: 4000
              }));
            }
          },
          err => this.toastError(err)
        );
    } else {
      Object.keys(this.inactiveForm2.controls).forEach(field => { // {1}
        const control = this.inactiveForm2.get(field);            // {2}
        control.markAsTouched({ onlySelf: true });       // {3}
      });
    }
  }

  public searchProperties(): void {
    this.propList = [];
    const formData = this.inactiveForm.value;
    let value = '';
    if (this.serachType == 'propId') {
      value = formData.propId;
    } else if (this.serachType == 'dateRange') {
      let start = '';
      let end = '';
      if (formData.date.begin != undefined) {
        let startStr = new Date(formData.date.begin).toLocaleString('en-US', {
          day: 'numeric',
          month: 'numeric',
          year: 'numeric'
        }).replace(/[^ -~]/g, '');
        //console.log('startStr', startStr);
        let tokens = startStr.split("/");
        let mon = '';
        let day = '';
        if (parseInt(tokens[1]) < 10) {
          day = '0' + tokens[1];
        } else {
          day = tokens[1];
        }
        if (parseInt(tokens[0]) < 10) {
          mon = '0' + tokens[0]
        } else {
          mon = tokens[0]
        }
        start = tokens[2] + '-' + mon + '-' + day + ' 00:00:00';
        //console.log('start', start);  
      }

      if (formData.date.end != undefined) {
        let endStr = new Date(formData.date.end).toLocaleString('en-US', {
          day: 'numeric',
          month: 'numeric',
          year: 'numeric'
        }).replace(/[^ -~]/g, '');
        let tokens = endStr.split("/");
        let mon = '';
        let day = '';
        if (parseInt(tokens[1]) < 10) {
          day = '0' + tokens[1];
        } else {
          day = tokens[1];
        }
        if (parseInt(tokens[0]) < 10) {
          mon = '0' + tokens[0]
        } else {
          mon = tokens[0]
        }
        end = tokens[2] + '-' + mon + '-' + day + ' 23:59:59';
        //console.log('end', end);        
      }
      value = start + '_' + end;
    }
    this.spinner.show();
    this.service.searchPropertyByType(this.serachType, value)
      .subscribe(
        res => {
          this.spinner.hide();
          this.resultsFound = true;
          if (res.status == 200) {
            //console.log('res', res);
            this.propList = res.body;
            //console.log('this.propList', this.propList);
            this.addCheckboxes();
            this.dataSource = new MatTableDataSource<any>(this.propList);
          } else {
            //console.log('this.propList204', this.propList);
          }
        },
        err => this.toastError(err)
      );
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

  minSelectedCheckboxes(min = 1) {
    const validator: ValidatorFn = (formArray: FormArray) => {
      const totalSelected = formArray.controls
        .map(control => control.value)
        .reduce((prev, next) => next ? prev + next : prev, 0);
      return totalSelected >= min ? null : { required: true };
    };
    return validator;
  }

  private addCheckboxes() {
    this.propList.map((o, i) => {
      const control = new FormControl();
      (this.inactiveForm2.controls.propChks as FormArray).push(control);
    });
  }

  openDialog(id: number, isActive: number): void {
    let title = '';
    let msg = '';
    let active = 0;
    if (isActive == 1) {
      title = 'Inactivate Property';
      msg = 'Do you confirm the inactivation of this property?';
      active = 0;
    } else if (isActive == 0) {
      title = 'Activate Property';
      msg = 'Do you confirm the activation of this property?';
      active = 1;
    }
    const dialogRef = this.dialog.open(ConfirmPopupComponent, {
      width: '350px',
      data: {
        title: title,
        message: msg
      }
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.spinner.show();
        this.statusService.toggleStatus(id, active, 'inquiry')
          .subscribe(
            res => {
              this.spinner.hide();
              if (res.status == 200) {
                this.searchProperties();
                this.toastr.success("Property verified successfully.", "Success", {
                  timeOut: 4000
                });
              } else {
                this.toastr.error("Please try again after some time!", "Oops something went wrong...", {
                  timeOut: 4000
                });
              }
            },
            err => this.toastError(err)
          );
      }
    });
  }

}

