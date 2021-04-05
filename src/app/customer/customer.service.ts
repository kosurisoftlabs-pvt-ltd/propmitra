import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { config } from 'config/config';
import 'rxjs/add/operator/shareReplay';
import 'rxjs/add/operator/do';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  constructor(private http: HttpClient) { }

  getRegistrations() {
    return this.http.get<any>(config.API_URL + '/user/registrations', { observe: 'response' })
      .shareReplay();
  }

  getAllUsers() {
    return this.http.get<any>(config.API_URL + '/user/all', { observe: 'response' })
      .shareReplay();
  }

  activeUpdate(formData: FormData) {
    return this.http.post<any>(config.API_URL + '/user/activeUpdate', formData, { observe: 'response' })
      .shareReplay();
  }

}
