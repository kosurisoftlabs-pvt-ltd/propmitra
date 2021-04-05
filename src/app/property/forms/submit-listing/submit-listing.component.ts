import { Component, OnInit } from '@angular/core';
import { config } from 'config/config';
import { ChangeDetectorRef } from '@angular/core';

@Component({
  selector: 'app-submit-listing',
  templateUrl: './submit-listing.component.html',
  styleUrls: ['./submit-listing.component.scss']
})
export class SubmitListingComponent implements OnInit {

  propertyId: string;
  selectedStep: number;
  
  constructor(private cdref: ChangeDetectorRef) {
  }

  ngOnInit() {
    this.selectedStep = 0;
  }

  ngAfterContentChecked() {
    this.cdref.detectChanges();
  }

  getPropertyId(propId: any): void {
    if (propId == '') {
      this.selectedStep = 0;
    }
    this.propertyId = propId;
  }

  setSelectedStep(event: any): void {  
    this.selectedStep = event.selectedIndex;   
    //console.log('this.selectedStep', this.selectedStep);
  }

  getRentOrSale() {
    return config.RENT_SALE;
  }

}
