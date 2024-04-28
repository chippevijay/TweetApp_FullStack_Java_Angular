import { HttpClient , HttpHeaders } from '@angular/common/http';
import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-edit-tweet-popup',
  templateUrl: './edit-tweet-popup.component.html',
  styleUrls: ['./edit-tweet-popup.component.css']
})
export class EditTweetPopupComponent implements OnInit {
  usertoken: string;
  mockTweet:any;
  tweetData='';
  oldTweetData: any;
  loginId:any;
  updatedTweet:any;

  constructor(private http:HttpClient, private toastr:ToastrService, private router: Router, @Inject(MAT_DIALOG_DATA) public data) { 
    this.mockTweet=data.modalData;
  }

  ngOnInit(): void {
    this.usertoken=localStorage.getItem('token');
    this.loginId=localStorage.getItem('loginId');
    console.log(this.mockTweet);
    this.tweetData=this.mockTweet.message;
    console.log(this.tweetData);
  }

  editTweet(){
    this.updatedTweet=
      {
        "tweetId": this.mockTweet.tweetId,
        "message": this.tweetData,
        "createdById":this.loginId,   
      }
    var headers_object = new HttpHeaders({"Authorization":this.usertoken});
    this.http.post<any>('http://tweetapp-env.eba-cxikpwpd.us-east-2.elasticbeanstalk.com/tweets/'+this.loginId+'/updatetweet', this.updatedTweet, {headers : headers_object})
        .subscribe(result=>{
          this.toastr.success('Tweet updated successfully');
          this.router.navigate(['/my-tweets']);
        },
      error => {
        this.toastr.error(error.error.text);
      });
  }

}
