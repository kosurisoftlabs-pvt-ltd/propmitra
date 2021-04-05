import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { MatTableDataSource, PageEvent } from '@angular/material';
import { ReportService } from 'app/service/report.service';
import { Subscription } from 'rxjs';
import { FormGroup, FormBuilder } from '@angular/forms';
import { RangesFooter } from 'app/components/ranges-footer/ranges-footer.component';

@Component({
  selector: 'app-report',
  templateUrl: './report.component.html',
  styleUrls: ['./report.component.scss']
})
export class ReportComponent implements OnInit {
  serachType: string = "username";
  resultsFound: boolean = false;
  inactiveForm: FormGroup;
  rangesFooter = RangesFooter;
  propList: any[] = [];
  dataSource: MatTableDataSource<any>;
  resultsLength: number = 0;
  pageEvent: PageEvent;
  pageIndex: number;
  currentPage: string = null;
  pageSize: string = null;
  value: string = null;
  displayedColumns: string[] = ['propId', 'rentSale', 'noBeds', 'projectName', 'totalValue', 'isActive', 'isVerified', 'createdAt', 'username'];
  reportType: any;
  sub: Subscription;
  maxDate: Date;
  constructor(fb: FormBuilder, private route: ActivatedRoute, private spinner: NgxSpinnerService,
    private rservice: ReportService, private toastr: ToastrService) {
    this.sub = this.route.params.subscribe(params => {
      this.value = null;
      this.serachType = "username";
      this.currentPage = null;
      this.pageSize = null;
      this.reportType = params.name.toLocaleUpperCase();
      this.getReport();
      //this.getPropertyReport(params.name, this.currentPage,  this.pageSize);
      //this.reportType = params.name;
    });
    this.inactiveForm = fb.group({
      date: [{ begin: new Date(), end: new Date(), max: new Date() }],
      username: [''],
    });
    this.maxDate =  new Date();
  }

  ngOnInit() {
  }

  ngOnDestroy() {
    //console.log("ngOnDestroy");
    this.sub.unsubscribe();
  }

  public handlePage(e: any): any {
    this.currentPage = e.pageIndex;
    this.pageSize = e.pageSize;
    //console.log('this.currentPage', this.currentPage);
    //console.log('this.pageSize', this.pageSize);
    this.getReport();
  }

  applyFilter(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  public searchProperties(): void {
    this.currentPage = null;
    this.pageSize = null;
    this.propList = [];
    const formData = this.inactiveForm.value;
    if (this.serachType == 'username') {
      this.value = formData.username;
    } else if (this.serachType == 'dateRange') {
      let start = '';
      let end = '';
      if (formData.date.begin != undefined) {
        let startStr = new Date(formData.date.begin).toLocaleString('en-US', {
          day: 'numeric',
          month: 'numeric',
          year: 'numeric'
        }).replace(/[^ -~]/g,'');
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
        }).replace(/[^ -~]/g,'');
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
      this.value = start + '_' + end;
    }
    this.getReport();
  }

  getReport() {
    this.spinner.show();
    this.rservice.getPropertyReport(this.reportType, this.serachType, this.value, this.currentPage, this.pageSize)
      .subscribe(
        res => {
          //console.log('response', res);
          this.spinner.hide();
          this.resultsFound = true;
          if (res.status == 200) {
            this.propList = res.body.content;
            //console.log('this.propList', this.propList);
            this.resultsLength = res.body.totalElements;
            this.pageIndex = res.body.page;
            //console.log('this.resultsLength', this.resultsLength);
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

}
