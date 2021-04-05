/// <reference types="@types/googlemaps" />
import { Component, OnInit, Output, EventEmitter, ElementRef, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import { FileUploader } from "ng2-file-upload";
import { ToastrService } from 'ngx-toastr';
import { NgxSpinnerService } from 'ngx-spinner';
import { NgxGalleryOptions, NgxGalleryImage, NgxGalleryAnimation } from 'ngx-gallery';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { PropertyService } from 'app/property/property.service';
import { BasicModel } from 'app/property/model/basic';
import { Attachment } from 'app/property/model/attachment';
import { config } from 'config/config';
import { MapsAPILoader } from '@agm/core';

@Component({
  selector: 'app-basic',
  templateUrl: './basic.component.html',
  styleUrls: ['./basic.component.scss']
})
export class BasicComponent implements OnInit {

  isInitialized: boolean = false;
  isViewRequest: boolean = false;
  isBasicCompleted: boolean = false;

  propId: number = 0;
  sub: Subscription;
  basic: BasicModel;
  frontImage: NgxGalleryImage[];
  galleryOptions: NgxGalleryOptions[];

  basicForm: FormGroup;
  postedBy = new FormControl('', [Validators.required]);
  rentSale = new FormControl('Sale', [Validators.required]);
  propAge = new FormControl('', [Validators.required]);
  propType = new FormControl('', [Validators.required]);
  propStatus = new FormControl('', [Validators.required]);
  completionType = new FormControl('');
  public uploader: FileUploader;

  @Output() onPropIdChange: EventEmitter<any> = new EventEmitter<any>();

  @ViewChild("search")
  public searchElementRef: ElementRef;

  constructor(private _formBuilder: FormBuilder, private spinner: NgxSpinnerService, private toastr: ToastrService,
    private service: PropertyService, private route: ActivatedRoute, private router: Router, 
    private mapsAPILoader: MapsAPILoader) {
    this.sub = this.route.params.subscribe(params => {
      if(params.type != undefined && params.type === 'view') {
        this.isViewRequest = true;
        this.getBasicInfo(params.id);        
      } else if (params.id != null && params.id != undefined && params.id != 'new') {
        this.propId = params.id;
        this.getBasicInfo(params.id);
      } else {
        this.propId = 0;
        this.isBasicCompleted = false;
        this.onPropIdChange.emit('');
        this.isInitialized = false;
        this.ngOnInit();
        /*this.basicForm.reset();
        this.frontImage = [];
        this.basic = new BasicModel({rentSale: 'Sale', frontImage: new Attachment({fileName: ''})});*/
      }
    });
  }

  ngOnInit() {
    if(!this.isInitialized){
      this.isInitialized = true;
      this.uploader = new FileUploader({
        isHTML5: true
      });
      this.frontImage = [];
      this.basic = new BasicModel({pId: 0, rentSale: 'Sale', isGated: 'N', frontImage: new Attachment({fileName: ''})});
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
  
      this.basicForm = this._formBuilder.group({
        completion: [''],
        projectName: ['', Validators.required],
        propLocation: ['', Validators.required],
        pinCode: ['', Validators.required],
        description: ['', Validators.required],
        leaseTerm: [''],
        advanceAmount: [''],
        rentAmount: [''],
        furnishedOrSemi: [''],
        noBathRooms: [''],
        landMark: [''],
        placesNear: [''],
        floorSft: [''],
        floorNo: [''],
        isGated: [''],
        postedBy: this.postedBy,
        rentSale: this.rentSale,
        propAge: this.propAge,
        propType: this.propType,
        propStatus: this.propStatus,
        completionType: this.completionType,
        document: [null, null],
      });
  
      //enable disable form validations
      this.basicForm.get("rentSale").valueChanges.subscribe((rentSale) => {
        if (rentSale === 'Sale') {
          this.basicForm.get("propAge").enable();
          config.RENT_SALE = 'Sale';
          //this.basicForm.get("propType").enable();
          //this.basicForm.get("propStatus").enable();
          //this.basicForm.get("projectName").enable();
          //this.basicForm.get("propLocation").enable();
          //this.basicForm.get("pinCode").enable();
          //this.basicForm.get("description").enable();
        } else {
          this.basicForm.get("propAge").disable();
          config.RENT_SALE = 'Rent';
          //this.basicForm.get("propType").disable();
          //this.basicForm.get("propStatus").disable();
          //this.basicForm.get("projectName").disable();
          //this.basicForm.get("propLocation").disable();
          //this.basicForm.get("pinCode").disable();
          //this.basicForm.get("description").disable();
        }
      });
    }

    this.mapsAPILoader.load().then(() => {
      let autocomplete = new google.maps.places.Autocomplete(this.searchElementRef.nativeElement, {
        types: ["geocode"],
        componentRestrictions: {country: "IN"}
      });

      autocomplete.addListener("place_changed", () => {
        //handle place change event
        //console.log('autocomplete.getPlace()', autocomplete.getPlace().formatted_address);
        this.basic.propLocation = autocomplete.getPlace().formatted_address;
      });

    });
    
  }

  getBasicInfo(pid: number) {
    this.spinner.show();
    let msg1 = "";
    let msg2 = "";
    this.service.getBasicInfo(pid)
      .subscribe(
        res => {
          //console.log('response', res);
          if (res.body != null && res.body != undefined) {
            this.basic = res.body;
            config.RENT_SALE = this.basic.rentSale;
            this.onPropIdChange.emit(this.basic.propId);
            this.isBasicCompleted = true;
            if(this.basic.frontImage != null) {
              var bytes = this.basic.frontImage.data;
              var imgUrl = 'data:' + this.basic.frontImage.fileType + ';base64,' + bytes;
              this.frontImage.push({
                small: imgUrl,
                medium: imgUrl,
                big: imgUrl
              });
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

  public submitBasic(): void {
    let formData: FormData = new FormData();
    this.basic.pId = this.propId;
    let keys = Object.keys(this.basic);
    for(let key in keys) {
      let modelKey = keys[key];
      if(this.basic[modelKey] != null) {
        formData.append(modelKey, this.basic[modelKey]) ;
      }      
    }
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
      formData.append('file', fileItem, fileItem.name);
    }    
    this.basic.pId = this.propId;
    if (this.basicForm.valid) {
      //console.log('this.basic', this.basic);
      this.spinner.show();
      this.service.processBasicInfo(formData)
        .subscribe(
          res => {
            //console.log("res", res);
            this.spinner.hide();
            if (res.status == 200) {
              if (res.body != null && res.body != undefined) {
                this.onPropIdChange.emit(res.body.propId);
                this.isBasicCompleted = true;
                this.router.navigate(['submit-listing', res.body.pId]);            
              }
              setTimeout(() => this.toastr.success('Basic Info Submitted Successfully.', 'Success!', {
                timeOut: 4000
              }));
            }
            this.uploader.clearQueue();
          },
          err => this.toastError(err)
        );
    } else {
      Object.keys(this.basicForm.controls).forEach(field => { // {1}
        const control = this.basicForm.get(field);            // {2}
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
