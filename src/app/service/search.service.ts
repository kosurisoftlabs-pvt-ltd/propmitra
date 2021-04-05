import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { config } from 'config/config';
import 'rxjs/add/operator/shareReplay';
import 'rxjs/add/operator/do';

@Injectable({
  providedIn: 'root'
})
export class SearchService {

  constructor(private http: HttpClient) { }

  searchPropertyByType(type: string, value: string) {
    return this.http.get<any>(config.API_URL + '/search/type?type=' + type + '&value=' + value, { observe: 'response' })
      .shareReplay();
  }

}
