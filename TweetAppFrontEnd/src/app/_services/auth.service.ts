import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map } from 'rxjs/operators';
import { JwtHelperService } from '@auth0/angular-jwt';
import { ReplaySubject } from 'rxjs';
import { ToastrService } from 'ngx-toastr';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  baseUrl: 'http://tweetapp-env.eba-cxikpwpd.us-east-2.elasticbeanstalk.com/tweets/';
  decodedToken: any;

  constructor(private http: HttpClient,  private toastr: ToastrService) { }
  login(model: any) {    
    return this.http.post<any>('http://tweetapp-env.eba-cxikpwpd.us-east-2.elasticbeanstalk.com/tweets/login', model).pipe(
      map(response => {
          console.log("Error:::",response);
          
          let userToken = response.authToken;
          let loginId = response.loginId;
          console.log("token",userToken);
          if (userToken) {
            localStorage.setItem('token', userToken);
            localStorage.setItem('loginId',loginId);
            this.decodedToken = localStorage.getItem('token');

            console.log(this.decodedToken);
          }
      })
    );
  }

  loggedIn() {
    const token = localStorage.getItem('token');
    if (token != null) {
      // this.currentUserSource.next(user)
      return true;

    }
    else {
      return false;
    }
  }

}

