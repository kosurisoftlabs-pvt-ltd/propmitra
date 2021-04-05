import { Component, OnInit } from '@angular/core';
import { PropertyService } from '../property.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { MatTableDataSource, PageEvent } from '@angular/material';

@Component({
  selector: 'app-inquiry',
  templateUrl: './inquiry.component.html',
  styleUrls: ['./inquiry.component.scss']
})
export class InquiryComponent implements OnInit {

  inqList: any[];
  dataSource: MatTableDataSource<any>;
  displayedColumns: string[] = ['propId', 'name', 'email', 'contact', 'message', 'createdAt'];
  resultsLength: number = 0;
  pageEvent: PageEvent;
  pageIndex: number;
  currentPage: string = null;
  pageSize: string = null;
  
  constructor(private spinner: NgxSpinnerService, private service: PropertyService, private toastr: ToastrService) { }

  ngOnInit() {
    this.getInquiryList();
  }

  public handlePage(e: any): any {
    this.currentPage = e.pageIndex;
    this.pageSize = e.pageSize;
    this.getInquiryList();
  }

  getInquiryList() {
    this.spinner.show();
    let msg1 = "";
    let msg2 = "";
    this.service.getInquiryList(this.currentPage, this.pageSize)
      .subscribe(
        res => {
          //console.log('response', res);
          if (res.body != null && res.body.content != undefined) {
            this.inqList = res.body.content;
            this.resultsLength = res.body.totalElements;
            this.pageIndex = res.body.page;
              //console.log('this.inqList', this.inqList);
              this.dataSource = new MatTableDataSource<any>(this.inqList);
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

  applyFilter(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

}
