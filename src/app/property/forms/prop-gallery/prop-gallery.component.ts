import { Component, OnInit } from '@angular/core';
import { NgxGalleryImage, NgxGalleryOptions, NgxGalleryAnimation } from 'ngx-gallery';
import { FileUploader } from 'ng2-file-upload';
import { Subscription } from 'rxjs';
import { Gallery } from 'app/property/model/gallery';
import { FormGroup, FormBuilder } from '@angular/forms';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { PropertyService } from 'app/property/property.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-prop-gallery',
  templateUrl: './prop-gallery.component.html',
  styleUrls: ['./prop-gallery.component.scss']
})
export class PropGalleryComponent implements OnInit {

  isViewRequest: boolean = false;
  isGalleryCompleted: boolean = false;

  galleryOptions: NgxGalleryOptions[];
  imageGallery: NgxGalleryImage[] = [];
  public uploader: FileUploader = new FileUploader({
    isHTML5: true
  });

  propId: number = 0;
  propertyId: string = "";
  sub: Subscription;

  galleryModel: Gallery = new Gallery({ attachments: null });
  galleryForm: FormGroup;

  constructor(private _formBuilder: FormBuilder, private spinner: NgxSpinnerService, private toastr: ToastrService,
    private service: PropertyService, private route: ActivatedRoute) {
    this.sub = this.route.params.subscribe(params => {
      if (params.type != undefined && params.type === 'view') {
        this.isViewRequest = true;
        this.getGallery(params.id);
      } else if (params.id != null && params.id != undefined && params.id != 'new') {
        this.propId = params.id;
        this.getGallery(params.id);
      } else {
        this.propId = 0;
        this.isGalleryCompleted = false;
      }
    });
    this.galleryForm = this._formBuilder.group({
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

  getGallery(pid: number) {
    this.spinner.show();
    let msg1 = "";
    let msg2 = "";
    this.service.getGallery(pid)
      .subscribe(
        res => {
          //console.log('getGallery response', res);
          if (res.body != null && res.body != undefined) {
            this.galleryModel = res.body;
            this.isGalleryCompleted = true;
            if (this.galleryModel.attachments != null && this.galleryModel.attachments.length > 0) {
              let attachments = this.galleryModel.attachments;
              for (var i = 0; i < attachments.length; i++) {
                var bytes = attachments[i].data;
                var imgUrl = 'data:' + attachments[i].fileType + ';base64,' + bytes; // use this in <img src="..."> binding
                this.imageGallery.push({
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

  public submitGallery(): void {
    let formData: FormData = new FormData();
    for (var i = 0; i < this.uploader.queue.length; i++) {
      let fileItem = this.uploader.queue[i]._file;
      if (fileItem.size > 20000000) {
        this.toastr.error("File: " + fileItem.name + " should be less than 2 MB of size.", "Validation Error!", {
          timeOut: 4000
        });
        return;
      }
    }
    for (var j = 0; j < this.uploader.queue.length; j++) {
      let fileItem = this.uploader.queue[j]._file;
      formData.append('files', fileItem, fileItem.name);
    }
    this.uploader.clearQueue();
    
    this.galleryModel.pId = this.propId;
    let keys = Object.keys(this.galleryModel);
    for (let key in keys) {
      let modelKey = keys[key];
      formData.append(modelKey, this.galleryModel[modelKey]);
    }
    if (this.galleryForm.valid) {
      this.spinner.show();
      this.service.processGallery(formData)
        .subscribe(
          res => {
            //console.log("res", res);
            this.spinner.hide();
            if (res.status == 200) {
              if (res.body != null) {
                this.imageGallery = [];
                this.galleryModel = res.body;
                this.isGalleryCompleted = true;
                if (this.galleryModel.attachments != null && this.galleryModel.attachments.length > 0) {
                  let attachments = this.galleryModel.attachments;
                  for (var i = 0; i < attachments.length; i++) {
                    var bytes = attachments[i].data;
                    var imgUrl = 'data:' + attachments[i].fileType + ';base64,' + bytes;
                    this.imageGallery.push({
                      small: imgUrl,
                      medium: imgUrl,
                      big: imgUrl
                    });
                  }

                }
              }
              this.propId = res.body.pId;
              setTimeout(() => this.toastr.success('Gallery Updated Successfully.', 'Success!', {
                timeOut: 4000
              }));
            }
          },
          err => this.toastError(err)
        );
    } else {
      Object.keys(this.galleryForm.controls).forEach(field => { // {1}
        const control = this.galleryForm.get(field);            // {2}
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
