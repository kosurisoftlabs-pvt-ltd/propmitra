import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { config } from 'config/config';
import 'rxjs/add/operator/shareReplay';
import 'rxjs/add/operator/do';

@Injectable({
  providedIn: 'root'
})
export class StatusService {

  constructor(private http: HttpClient) { }

  toggleStatus(id: number, active: number, uri: string) {
    return this.http.get<any>(config.API_URL + '/status/'  + uri + '?id=' + id + '&active=' + active, { observe: 'response' })
      .shareReplay();
  }
}
