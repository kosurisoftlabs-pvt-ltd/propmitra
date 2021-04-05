import { Component, OnInit } from '@angular/core';
import { PropertyService } from '../property.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { MatTableDataSource, PageEvent } from '@angular/material';

@Component({
  selector: 'app-property-list',
  templateUrl: './property-list.component.html',
  styleUrls: ['./property-list.component.scss']
})
export class PropertyListComponent implements OnInit {
  propList: any[];
  dataSource: MatTableDataSource<any>;
  //displayedColumns: string[] = ['propImg', 'propId', 'propertyBasic.rentSale', 'propertyBasic.propAge', 'propertyBasic.propStatus', 'isActive', 'isVerified', 'progress'];
  displayedColumns: string[] = ['propId', 'rentSale', 'noBeds', 'projectName', 'totalValue', 'isActive', 'isVerified', 'createdAt', 'progress'];
  resultsLength: number = 0;
  pageEvent: PageEvent;
  pageIndex: number;
  currentPage: string = null;
  pageSize: string = null;
  
  constructor(private spinner: NgxSpinnerService, private service: PropertyService, private toastr: ToastrService) { }

  ngOnInit() {
    this.getPropertyList();
  }

  public handlePage(e: any): any {
    this.currentPage = e.pageIndex;
    this.pageSize = e.pageSize;
    //console.log('this.currentPage', this.currentPage);
    //console.log('this.pageSize', this.pageSize);
    this.getPropertyList();
  }

  getPropertyList() {
    this.spinner.show();
    let msg1 = "";
    let msg2 = "";
    this.service.getUserPropertyList(this.currentPage, this.pageSize)
      .subscribe(
        res => {
          //console.log('response', res);
          if (res.body != null && res.body.content != undefined) {
            this.propList = res.body.content;
            this.resultsLength = res.body.totalElements;
            this.pageIndex = res.body.page;
              //console.log('this.propList', this.propList);
              this.dataSource = new MatTableDataSource<any>(this.propList);
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
