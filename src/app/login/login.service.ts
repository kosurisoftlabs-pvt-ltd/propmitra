import { Injectable } from '@angular/core';
import { Headers, Http, Response } from "@angular/http";
import 'rxjs/add/operator/map'
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/toPromise';

//import { Person } from '../person/person';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

    private apiUrl = 'http://localhost:8080/tmlms';
    private headers = new Headers({'Content-Type': 'application/json'});

    constructor(private http: Http) { }
    
    /*login(person: Person): Promise<Person> {
        return this.http
            .post(this.apiUrl+'/v1/auth/', JSON.stringify(person), {headers: this.headers})
            .toPromise()
            .then(res => {
                    if(res.status == 200) {
                        window.localStorage.setItem('sessionUser', JSON.stringify(res.json()));
                    } else {
                        this.handleError
                    }
                }
            ).catch(this.handleError);
    }*/
 
    logout() {
      window.localStorage.removeItem('sessionUser');
    }
    private handleError(error: any): Promise<any> {
        console.error('Error', error); // for demo purposes only
        return Promise.reject(error.message || error);
      }
}
