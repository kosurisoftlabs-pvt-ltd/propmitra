import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { MatTableDataSource, PageEvent } from '@angular/material';
import { Subscription } from 'rxjs';
import { FormGroup, FormBuilder } from '@angular/forms';
import { RangesFooter } from 'app/components/ranges-footer/ranges-footer.component';
import { UserService } from 'app/service/user.service';
import { MatDialog } from '@angular/material';
import { ConfirmPopupComponent } from 'app/popup/confirm-popup/confirm-popup.component';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.scss']
})
export class UsersComponent implements OnInit {

  serachType: string = "username";
  resultsFound: boolean = false;
  userForm: FormGroup;
  rangesFooter = RangesFooter;
  userList: any[] = [];
  dataSource: MatTableDataSource<any>;
  resultsLength: number = 0;
  pageEvent: PageEvent;
  pageIndex: number;
  currentPage: string = null;
  pageSize: string = null;
  value: string = null;
  displayedColumns: string[] = ['name', 'contact', 'isActive', 'email', 'username', 'city', 'actions'];
  sub: Subscription;
  constructor(fb: FormBuilder, private route: ActivatedRoute, private spinner: NgxSpinnerService,
    private service: UserService, private toastr: ToastrService, public dialog: MatDialog) {
    this.sub = this.route.params.subscribe(params => {
      this.value = null;
      this.serachType = "username";
      this.currentPage = null;
      this.pageSize = null;
      this.getAllUsers();
    });
    this.userForm = fb.group({
      date: [{ begin: null, end: null }],
      username: [''],
    });
  }

  ngOnInit() {
  }

  ngOnDestroy() {
    this.sub.unsubscribe();
  }

  public handlePage(e: any): any {
    this.currentPage = e.pageIndex;
    this.pageSize = e.pageSize;
    this.getAllUsers();
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
    this.userList = [];
    const formData = this.userForm.value;
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
        }).replace(/[^ -~]/g, '');
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
        }).replace(/[^ -~]/g, '');
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
    this.getAllUsers();
  }

  getAllUsers() {
    this.spinner.show();
    this.service.getAllUsers(this.serachType, this.value, this.currentPage, this.pageSize)
      .subscribe(
        res => {
          //console.log('response', res);
          this.spinner.hide();
          this.resultsFound = true;
          if (res.status == 200) {
            this.userList = res.body.content;
            //console.log('this.userList', this.userList);
            this.resultsLength = res.body.totalElements;
            this.pageIndex = res.body.page;
            //console.log('this.resultsLength', this.resultsLength);
            this.dataSource = new MatTableDataSource<any>(this.userList);
          } else {
            //console.log('this.userList204', this.userList);
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

  openDialog(id: number, isActive: number): void {
    let title = '';
    let msg = '';
    let active = 0;
    if (isActive == 1) {
      title = 'Inactivate User';
      msg = 'Do you confirm the inactivation of this user?';
      active = 0;
    } else if (isActive == 0) {
      title = 'Activate User';
      msg = 'Do you confirm the activation of this user?';
      active = 1;
    }
    const dialogRef = this.dialog.open(ConfirmPopupComponent, {
      width: '350px',
      data: {
        title: title,
        message: msg
      }
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.spinner.show();
        this.service.toggleStatus(id, active)
          .subscribe(
            res => {
              this.spinner.hide();
              if (res.status == 200) {
                this.getAllUsers();
                this.toastr.success("User status updated successfully.", "Success", {
                  timeOut: 4000
                });
              } else {
                this.toastr.error("Please try again after some time!", "Oops something went wrong...", {
                  timeOut: 4000
                });
              }
            },
            err => this.toastError(err)
          );
      }
    });
  }

}
