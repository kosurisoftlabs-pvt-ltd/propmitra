import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { Nearby } from 'app/property/model/nearby';
import { FormGroup, FormBuilder, FormControl, Validators, FormArray } from '@angular/forms';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { ActivatedRoute } from '@angular/router';
import { PropertyService } from 'app/property/property.service';

@Component({
  selector: 'app-prop-nearby',
  templateUrl: './prop-nearby.component.html',
  styleUrls: ['./prop-nearby.component.scss']
})
export class PropNearbyComponent implements OnInit {

  isViewRequest: boolean = false;
  isNearbyCompleted: boolean = false;

  propId: number = 0;
  propertyId: string = "";
  sub: Subscription;

  nearbyModel: Nearby = new Nearby({ hospitals: [], schools: [] });
  nearbyForm: FormGroup;

  constructor(private _formBuilder: FormBuilder, private spinner: NgxSpinnerService, private toastr: ToastrService,
    private service: PropertyService, private route: ActivatedRoute) {
    this.sub = this.route.params.subscribe(params => {
      if (params.type != undefined && params.type === 'view') {
        this.isViewRequest = true;
        this.getNearby(params.id);
      } else if (params.id != null && params.id != undefined && params.id != 'new') {
        this.propId = params.id;
        this.getNearby(params.id);
      } else {
        this.propId = 0;
        this.isNearbyCompleted = false;
      }
    });
  }

  ngOnInit() {
    this.nearbyForm = this._formBuilder.group({
      hospitals: this._formBuilder.array([]),
      schools: this._formBuilder.array([])
    });
    this.onAddHospitals();
    this.onAddSchools();
  }

  getNearby(pid: number) {
    this.spinner.show();
    let msg1 = "";
    let msg2 = "";
    this.service.getNearby(pid)
      .subscribe(
        res => {
          //console.log('getNearby response', res);
          if (res.body != null && res.body != undefined) {
            this.nearbyModel = res.body;
            this.isNearbyCompleted = true;
            if (this.nearbyModel.hospitals != null && this.nearbyModel.hospitals.length > 0) {
              for (let i = 0; i < this.nearbyModel.hospitals.length - 1; i++) {
                const control = new FormGroup({
                  'hospital': new FormControl('', [Validators.required])
                });
                (<FormArray>this.nearbyForm.get('hospitals')).push(control);
              }
            }

            if (this.nearbyModel.schools != null && this.nearbyModel.schools.length > 0) {
              for (let i = 0; i < this.nearbyModel.schools.length - 1; i++) {
                const control = new FormGroup({
                  'school': new FormControl('', [Validators.required])
                });
                (<FormArray>this.nearbyForm.get('schools')).push(control);
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

  submitNearbyData() {
    //console.log('nearbyModel', this.nearbyModel);
    let formData: FormData = new FormData();
    this.nearbyModel.pId = this.propId;
    let keys = Object.keys(this.nearbyModel);
    for(let key in keys) {
      let modelKey = keys[key];
      formData.append(modelKey, this.nearbyModel[modelKey]);
    }
    if (this.nearbyForm.valid) {
      this.spinner.show();
      this.service.processNearbyInfo(formData)
        .subscribe(
          res => {
            //console.log("res", res);
            this.spinner.hide();
            if (res.status == 200) {
              if (res.body != null) {
                this.nearbyModel = res.body;
                this.propId = this.nearbyModel.pId;
                this.isNearbyCompleted = true;
              }
              setTimeout(() => this.toastr.success('Nearby Info Submitted Successfully.', 'Success!', {
                timeOut: 4000
              }));
            }
          },
          err => this.toastError(err)
        );
    } else {
      Object.keys(this.nearbyForm.controls).forEach(field => { // {1}
        const control = this.nearbyForm.get(field);            // {2}
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

  private onAddHospitals() {
    this.nearbyModel.hospitals.push('');
    const control = new FormGroup({
      'hospital': new FormControl('', [Validators.required])
    });
    (<FormArray>this.nearbyForm.get('hospitals')).push(control);
  }

  private deleteHospital(index: number) {
    (<FormArray>this.nearbyForm.get('hospitals')).removeAt(index);
    this.nearbyModel.hospitals.splice(index, 1);
  }

  private onAddSchools() {
    this.nearbyModel.schools.push('');
    const control = new FormGroup({
      'school': new FormControl('', [Validators.required])
    });
    (<FormArray>this.nearbyForm.get('schools')).push(control);
  }

  private deleteSchool(index: number) {
    (<FormArray>this.nearbyForm.get('schools')).removeAt(index);
    this.nearbyModel.schools.splice(index, 1);
  }

}
