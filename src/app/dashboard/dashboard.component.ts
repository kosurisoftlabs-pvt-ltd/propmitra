import { Component, OnInit } from '@angular/core';
import * as Chartist from 'chartist';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  constructor(private spinner: NgxSpinnerService) {
       /** spinner starts on init */
      this.spinner.show();

      setTimeout(() => {
          /** spinner ends after 5 seconds */
          this.spinner.hide();
      }, 1000);
   }

  ngOnInit() {}
   
}
