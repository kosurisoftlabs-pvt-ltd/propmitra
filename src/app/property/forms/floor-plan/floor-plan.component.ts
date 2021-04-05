import { Component, OnInit } from '@angular/core';
import { NgxGalleryImage, NgxGalleryOptions, NgxGalleryAnimation } from 'ngx-gallery';
import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';
import { Subscription } from 'rxjs';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { PropertyService } from 'app/property/property.service';
import { ActivatedRoute } from '@angular/router';
import { FileUploader } from 'ng2-file-upload';
import { FloorModel } from 'app/property/model/floor';

@Component({
  selector: 'app-floor-plan',
  templateUrl: './floor-plan.component.html',
  styleUrls: ['./floor-plan.component.scss']
})
export class FloorPlanComponent implements OnInit {

  isViewRequest = false;
  isFloorCompleted: boolean = false;

  galleryOptions: NgxGalleryOptions[];
  galleryImages: NgxGalleryImage[] = [];
  public uploader: FileUploader = new FileUploader({
    isHTML5: true
  });

  propId: number = 0;
  propertyId: string = "";
  sub: Subscription;

  floorForm: FormGroup;
  noBeds = new FormControl('', [Validators.required]);
  floorModel: FloorModel = new FloorModel({});

  constructor(private _formBuilder: FormBuilder, private spinner: NgxSpinnerService, private toastr: ToastrService,
    private service: PropertyService, private route: ActivatedRoute) {
    this.sub = this.route.params.subscribe(params => {
      if(params.type != undefined && params.type === 'view') {
        this.isViewRequest = true;
        this.getFloorInfo(params.id);
      } else if (params.id != null && params.id != undefined && params.id != 'new') {
        this.propId = params.id;
        this.getFloorInfo(params.id);
      } else {
        this.propId = 0;
        this.isFloorCompleted = false;
      }
    });
    this.floorForm = this._formBuilder.group({
      noFloors: ['', Validators.required],
      noBeds: this.noBeds,
      floorNo: ['', Validators.required],
      areaSft: ['', Validators.required],
      priceSft: ['', Validators.required],
      document: [null, null],
    });
  }

  ngOnInit() {
    this.galleryOptions = [
      {
        width: '600px',
        height: '400px',
        thumbnailsColumns: 4,
        imageAnimation: NgxGalleryAnimation.Slide
      },
      // max-width 800
      {
        breakpoint: 800,
        width: '100%',
        height: '600px',
        imagePercent: 80,
        thumbnailsPercent: 20,
        thumbnailsMargin: 20,
        thumbnailMargin: 20
      },
      // max-width 400
      {
        breakpoint: 400,
        preview: false
      }
    ];
  }

  getFloorInfo(pid: number) {
    this.spinner.show();
    let msg1 = "";
    let msg2 = "";
    this.service.getFloorPlan(pid)
      .subscribe(
        res => {
          //console.log('getFloorInfo response', res);
          if (res.body != null && res.body != undefined) {
            this.floorModel = res.body;
            this.isFloorCompleted = true;
            if (this.floorModel.attachments != null && this.floorModel.attachments.length > 0) {
              let attachments = this.floorModel.attachments;
              for (var i = 0; i < attachments.length; i++) {
                var bytes = attachments[i].data;
                var imgUrl = 'data:' + attachments[i].fileType + ';base64,' + bytes; // use this in <img src="..."> binding
                //console.log('imgUrl', imgUrl);
                this.galleryImages.push({
                  small: imgUrl,
                  medium: imgUrl,
                  big: imgUrl
                });
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

  public submitFloorData(): void {
    //console.log('this.floorModel', this.floorModel);
    let formData: FormData = new FormData();
    for (var i = 0; i < this.uploader.queue.length; i++) {
      let fileItem = this.uploader.queue[i]._file;
      if (fileItem.size > 10000000) {
        this.toastr.error("File: " + fileItem.name + " should be less than 1 MB of size.", "Validation Error!", {
          timeOut: 4000
        });
        return;
      }
    }
    for (var j = 0; j < this.uploader.queue.length; j++) {
      let fileItem = this.uploader.queue[j]._file;
      //console.log(fileItem.name);++
      formData.append('files', fileItem, fileItem.name);
    }
    this.uploader.clearQueue();
    this.floorModel.pId = this.propId;
    let keys = Object.keys(this.floorModel);
    for (let key in keys) {
      let modelKey = keys[key];
      formData.append(modelKey, this.floorModel[modelKey]);
    }
    if (this.floorForm.valid) {
      this.spinner.show();
      this.service.processFloorInfo(formData)
        .subscribe(
          res => {
            //console.log("res", res);
            this.spinner.hide();
            if (res.status == 200) {
              if (res.body != null) {
                this.isFloorCompleted = true;
                this.galleryImages = [];
                this.floorModel = res.body;
                if (this.floorModel.attachments != null && this.floorModel.attachments.length > 0) {
                  let attachments = this.floorModel.attachments;
                  for (var i = 0; i < attachments.length; i++) {
                    var bytes = attachments[i].data;
                    var imgUrl = 'data:' + attachments[i].fileType + ';base64,' + bytes;
                    this.galleryImages.push({
                      small: imgUrl,
                      medium: imgUrl,
                      big: imgUrl
                    });
                  }

                }
                this.propId = this.floorModel.pId;
              }

              setTimeout(() => this.toastr.success('Floor Plan Submitted Successfully.', 'Success!', {
                timeOut: 4000
              }));
            }
          },
          err => this.toastError(err)
        );
    } else {
      Object.keys(this.floorForm.controls).forEach(field => { // {1}
        const control = this.floorForm.get(field);            // {2}
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

  ngOnDestroy() {
    //console.log("ngOnDestroy");
    this.sub.unsubscribe();
  }

}
