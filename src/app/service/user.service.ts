import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { config } from 'config/config';
import 'rxjs/add/operator/shareReplay';
import 'rxjs/add/operator/do';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  getAllUsers(type: string, value: string, page: string, size: string) {
    let uri = '/user/all';
    if (page != null && size != null && value != null) {
      uri = uri + '?page=' + page + '&size=' + size + '&type=' + type + '&value=' + value;
    } else if (page != null && size != null) {
      uri = uri + '?page=' + page + '&size=' + size;
    } else if (value != null) {
      uri = uri + '?type=' + type + '&value=' + value;
    }

    return this.http.get<any>(config.API_URL + uri, { observe: 'response' })
      .shareReplay();
  }

  toggleStatus(id: number, active: number) {
    return this.http.get<any>(config.API_URL + '/user/status' + '?id=' + id + '&active=' + active, { observe: 'response' })
      .shareReplay();
  }

}
