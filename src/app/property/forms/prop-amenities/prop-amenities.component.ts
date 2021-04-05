import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { Amenities } from 'app/property/model/amenities';
import { FormGroup, FormBuilder, FormControl, Validators, FormArray } from '@angular/forms';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { PropertyService } from 'app/property/property.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-prop-amenities',
  templateUrl: './prop-amenities.component.html',
  styleUrls: ['./prop-amenities.component.scss']
})
export class PropAmenitiesComponent implements OnInit {

  isViewRequest: boolean = false;
  isAmenitiesCompleted: boolean = false;

  propId: number = 0;
  propertyId: string = "";
  sub: Subscription;

  amenitiesModel: Amenities = new Amenities({ specification: [] });
  amenitiesForm: FormGroup;

  constructor(private _formBuilder: FormBuilder, private spinner: NgxSpinnerService, private toastr: ToastrService,
    private service: PropertyService, private route: ActivatedRoute) {
    this.sub = this.route.params.subscribe(params => {
      if (params.type != undefined && params.type === 'view') {
        this.isViewRequest = true;
        this.getAmenities(params.id);
      } else if (params.id != null && params.id != undefined && params.id != 'new') {
        this.propId = params.id;
        this.getAmenities(params.id);
      } else {
        this.propId = 0;
        this.isAmenitiesCompleted = false;
      }
    });

  }

  ngOnInit() {
    this.amenitiesForm = this._formBuilder.group({
      specs: this._formBuilder.array([])
    });
    this.onAddSpecs();
  }

  getAmenities(pid: number) {
    this.spinner.show();
    let msg1 = "";
    let msg2 = "";
    this.service.getAmenities(pid)
      .subscribe(
        res => {
          //console.log('getAmenities response', res);
          if (res.body != null && res.body != undefined) {
            this.amenitiesModel = res.body;
            this.isAmenitiesCompleted = true;
            if (this.amenitiesModel.specification != null && this.amenitiesModel.specification.length > 0) {
              for (let i = 0; i < this.amenitiesModel.specification.length - 1; i++) {
                const control = new FormGroup({
                  'spec': new FormControl('', [Validators.required])
                });
                (<FormArray>this.amenitiesForm.get('specs')).push(control);
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

  submitAmenitiesData() {
    let formData: FormData = new FormData();
    this.amenitiesModel.pId = this.propId;
    let keys = Object.keys(this.amenitiesModel);
    for (let key in keys) {
      let modelKey = keys[key];
      formData.append(modelKey, this.amenitiesModel[modelKey]);
    }

    if (this.amenitiesForm.valid) {
      this.spinner.show();
      this.service.processAmenities(formData)
        .subscribe(
          res => {
            //console.log("res", res);
            this.spinner.hide();
            if (res.status == 200) {
              if (res.body != null) {
                this.amenitiesModel = res.body;
                this.propId = this.amenitiesModel.pId;
                this.isAmenitiesCompleted = true;
              }
              setTimeout(() => this.toastr.success('Amenities Info Submitted Successfully.', 'Success!', {
                timeOut: 4000
              }));
            }
          },
          err => this.toastError(err)
        );
    } else {
      Object.keys(this.amenitiesForm.controls).forEach(field => { // {1}
        const control = this.amenitiesForm.get(field);            // {2}
        control.markAsTouched({ onlySelf: true });       // {3}
      });
    }
  }

  private onAddSpecs() {
    this.amenitiesModel.specification.push('');
    const control = new FormGroup({
      'spec': new FormControl('', [Validators.required])
    });
    (<FormArray>this.amenitiesForm.get('specs')).push(control);
  }

  private deleteSpec(index: number) {
    (<FormArray>this.amenitiesForm.get('specs')).removeAt(index);
    this.amenitiesModel.specification.splice(index, 1);
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
