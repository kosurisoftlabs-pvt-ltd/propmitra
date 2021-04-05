import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { config } from 'config/config';
import 'rxjs/add/operator/shareReplay';
import 'rxjs/add/operator/do';

@Injectable({
  providedIn: 'root'
})
export class RegisterService {

  constructor(private http: HttpClient) { }

  register(request: any) {
    return this.http.post<any>(config.API_URL + '/auth/signup', request, { observe: 'response' })
      .shareReplay();
  }
}
