import { HttpClient ,HttpRequest ,HttpHeaders} from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { IndividualTweetsPopupComponent } from '../TweetsGroup/individual-tweets-popup/individual-tweets-popup.component';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {

  usertoken: string;
  allUsers: any;
  request: HttpRequest<any>;
  

  constructor(private http: HttpClient, private dialog: MatDialog) { }

  ngOnInit(): void {
    this.usertoken=localStorage.getItem('token');
    var headers_object = new HttpHeaders({"Authorization":this.usertoken});
    console.log(this.usertoken);
    let getAllUsersUrl='http://tweetapp-env.eba-cxikpwpd.us-east-2.elasticbeanstalk.com/tweets/users/all'
    this.http.post<any>(getAllUsersUrl,{},{
      headers : headers_object
    }).subscribe(result=>{
      this.allUsers=result;
      console.log("AllUsers::",this.allUsers);
    })
  }

  openIndividualTweetsModal(loginId: any) {
    const dialogRef = this.dialog.open(IndividualTweetsPopupComponent, {
      width: '100%',
      minHeight: 'calc(25vh - 30px)',
      height: '450px',
      data: {
        LoginId: loginId
      }
    })
  }
}
