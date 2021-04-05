import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { config } from 'config/config';
import 'rxjs/add/operator/shareReplay';
import 'rxjs/add/operator/do';

@Injectable({
  providedIn: 'root'
})
export class ReportService {

  constructor(private http: HttpClient) { }

  getPropertyReport(reportType: string, type: string, value: string, page: string, size: string) {
    let uri = '/report/propertyby/' + reportType;
    if(page != null && size != null && value != null) {
      uri = uri + '?page=' + page + '&size=' + size + '&type=' + type + '&value=' + value;
    } else if(page != null && size != null) {
      uri = uri + '?page=' + page + '&size=' + size;
    } else if(value != null) {
      uri = uri + '?type=' + type + '&value=' + value;
    }

    return this.http.get<any>(config.API_URL + uri, { observe: 'response' })
      .shareReplay();
  }

  getInquiryReport(reportType: string, type: string, value: string, page: string, size: string) {
    let uri = '/report/inquiry/' + reportType;
    if(page != null && size != null && value != null) {
      uri = uri + '?page=' + page + '&size=' + size + '&type=' + type + '&value=' + value;
    } else if(page != null && size != null) {
      uri = uri + '?page=' + page + '&size=' + size;
    } else if(value != null) {
      uri = uri + '?type=' + type + '&value=' + value;
    }

    return this.http.get<any>(config.API_URL + uri, { observe: 'response' })
      .shareReplay();
  }
}
