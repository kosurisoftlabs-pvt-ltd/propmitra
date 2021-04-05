import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { Material } from 'app/property/model/material';
import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { ActivatedRoute } from '@angular/router';
import { PropertyService } from 'app/property/property.service';

@Component({
  selector: 'app-prop-material',
  templateUrl: './prop-material.component.html',
  styleUrls: ['./prop-material.component.scss']
})
export class PropMaterialComponent implements OnInit {

  isViewRequest: boolean = false;
  isMaterialCompleted: boolean = false;

  propId: number = 0;
  propertyId: string = "";
  sub: Subscription;

  materialModel: Material = new Material({});
  materialsForm: FormGroup;
  doors = new FormControl('', [Validators.required]);
  windows = new FormControl('', [Validators.required]);
  cupboards = new FormControl('', [Validators.required]);

  constructor(private _formBuilder: FormBuilder, private spinner: NgxSpinnerService, private toastr: ToastrService,
    private service: PropertyService, private route: ActivatedRoute) {
    this.sub = this.route.params.subscribe(params => {
      if (params.type != undefined && params.type === 'view') {
        this.isViewRequest = true;
        this.getMaterials(params.id);
      } else if (params.id != null && params.id != undefined && params.id != 'new') {
        this.propId = params.id;
        this.getMaterials(params.id);
      } else {
        this.propId = 0;
        this.isMaterialCompleted = false;
      }
    });

  }

  ngOnInit() {
    this.materialsForm = this._formBuilder.group({
      wallPaint: ['', Validators.required],
      floor: ['', Validators.required],
      kitchen: ['', Validators.required],
      wiring: ['', Validators.required],
      toilet: ['', Validators.required],
      locks: ['', Validators.required],
      electricalSwitches: ['', Validators.required],
      waterPipes: ['', Validators.required],
      sink: ['', Validators.required],
      washBasin: ['', Validators.required],
      doors: this.doors,
      windows: this.windows,
      cupboards: this.cupboards,
    });
  }

  getMaterials(pid: number) {
    this.spinner.show();
    let msg1 = "";
    let msg2 = "";
    this.service.getMaterials(pid)
      .subscribe(
        res => {
          //console.log('getAmenities response', res);
          if (res.body != null && res.body != undefined) {
            this.materialModel = res.body;
            this.isMaterialCompleted = true;
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

  public submitMaterials(): void {
    const formData = new FormData();
    this.materialModel.pId = this.propId;
    let keys = Object.keys(this.materialModel);
    for (let key in keys) {
      let modelKey = keys[key];
      formData.append(modelKey, this.materialModel[modelKey]);
    }
    if (this.materialsForm.valid) {
      this.spinner.show();
      this.service.processMaterialInfo(formData)
        .subscribe(
          res => {
            //console.log("res", res);
            this.spinner.hide();
            if (res.status == 200) {
              if (res.body != null) {
                this.materialModel = res.body;
                this.propId = this.materialModel.pId;
                this.isMaterialCompleted = true;
              }
              setTimeout(() => this.toastr.success('Materials Info Submitted Successfully.', 'Success!', {
                timeOut: 4000
              }));
            }
          },
          err => this.toastError(err)
        );
    } else {
      Object.keys(this.materialsForm.controls).forEach(field => { // {1}
        const control = this.materialsForm.get(field);            // {2}
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

}
