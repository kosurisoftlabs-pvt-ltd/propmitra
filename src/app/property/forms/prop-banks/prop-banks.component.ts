import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { Banks } from 'app/property/model/bank';
import { FormGroup, FormBuilder, FormArray, ValidatorFn, FormControl } from '@angular/forms';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { PropertyService } from 'app/property/property.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-prop-banks',
  templateUrl: './prop-banks.component.html',
  styleUrls: ['./prop-banks.component.scss']
})
export class PropBanksComponent implements OnInit {

  isViewRequest: boolean = false;
  
  propId: number = 0;
  propertyId: string = "";
  sub: Subscription;

  banksModel: Banks = new Banks({ selectedBanks: [] });
  bankLoanForm: FormGroup;
  banks = [];

  constructor(private _formBuilder: FormBuilder, private spinner: NgxSpinnerService, private toastr: ToastrService,
    private service: PropertyService, private route: ActivatedRoute) {
      this.bankLoanForm = this._formBuilder.group({
        banks: new FormArray([], this.minSelectedCheckboxes(1))
      });
      this.banks = this.getBankList();
      this.addCheckboxes();
    this.sub = this.route.params.subscribe(params => {
      if (params.type != undefined && params.type === 'view') {
        this.isViewRequest = true;
        this.getBanks(params.id);
      } else if (params.id != null && params.id != undefined && params.id != 'new') {
        this.propId = params.id;
        this.getBanks(params.id);
      } else {
        this.propId = 0;
      }
    });
  }

  ngOnInit() {}

  getBanks(pid: number) {
    this.spinner.show();
    let msg1 = "";
    let msg2 = "";
    this.service.getBanks(pid)
      .subscribe(
        res => {
          //console.log('getBanks response', res);
          if (res.body != null && res.body != undefined) {
            this.banksModel = res.body;
            if (this.banksModel.selectedBanks != null && this.banksModel.selectedBanks.length > 0) {
              for (let i = 0; i < this.banksModel.selectedBanks.length; i++) {
                for(let j=0;j<this.banks.length;j++){
                  if(this.banksModel.selectedBanks[i] == this.banks[j].name) {
                    //console.log(this.banksModel.selectedBanks[i], this.banks[j].name);
                    this.banks[j].selected = true;
                    break;
                  }
                }
                
              }
            }
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

  submitBanksSelected() {
    const selectedBanks = this.bankLoanForm.value.banks
      .map((v, i) => v ? this.banks[i].name : null)
      .filter(v => v !== null);
      if(selectedBanks.length == 0) {
        this.toastr.error("At least one bank must be selected", "Error!", {
          timeOut: 4000
        });
        return;
      }
    let formData: FormData = new FormData();
    this.banksModel.pId = this.propId;
    this.banksModel.selectedBanks = selectedBanks;
    let keys = Object.keys(this.banksModel);
    for (let key in keys) {
      let modelKey = keys[key];
      formData.append(modelKey, this.banksModel[modelKey]);
    }
    if (this.bankLoanForm.valid) {
      this.spinner.show();
      this.service.processSelectedBanks(formData)
        .subscribe(
          res => {
            //console.log("res", res);
            this.spinner.hide();
            if (res.status == 200) {
              if (res.body != null) {
                this.banksModel = res.body;
                this.propId = this.banksModel.pId;
              }
              setTimeout(() => this.toastr.success('Bank Details Submitted Successfully.', 'Success!', {
                timeOut: 4000
              }));
            }
          },
          err => this.toastError(err)
        );
    } else {
      Object.keys(this.bankLoanForm.controls).forEach(field => { // {1}
        const control = this.bankLoanForm.get(field);            // {2}
        control.markAsTouched({ onlySelf: true });       // {3}
      });
    }
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
    this.banks.map((o, i) => {
      const control = new FormControl();
      (this.bankLoanForm.controls.banks as FormArray).push(control);
    });
  }

  private getBankList() {
    return [
      { id: 1, name: 'ICICI', selected: false },
      { id: 2, name: 'HDFC', selected: false },
      { id: 3, name: 'PNB', selected: false },
      { id: 4, name: 'AXIS', selected: false },
      { id: 5, name: 'SBI', selected: false },
    ];
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

}
