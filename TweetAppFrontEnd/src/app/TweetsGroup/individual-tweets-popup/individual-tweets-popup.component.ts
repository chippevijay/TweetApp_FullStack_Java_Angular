import { formatDate } from '@angular/common';
import { HttpClient , HttpHeaders } from '@angular/common/http';
import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-individual-tweets-popup',
  templateUrl: './individual-tweets-popup.component.html',
  styleUrls: ['./individual-tweets-popup.component.css']
})
export class IndividualTweetsPopupComponent implements OnInit {

  individualTweets:any=[];
  userName:any;
  loginId: any;
  myTweets:any;
  replyData:any;
  replyTweetData:any=[];
  likeTweetData: any = [];
  usertoken: any;

  constructor(private http: HttpClient, private toastr: ToastrService, @Inject(MAT_DIALOG_DATA) public data) {
    this.loginId = data.LoginId;
  }

  ngOnInit(): void {
    this.userName = localStorage.getItem('loginId');
    this.usertoken=localStorage.getItem('token');
    console.log(this.userName);
    this.getTweetsByLoginId(this.loginId);
    console.log(this.individualTweets);
  }

  
  getTweetsByLoginId(loginId:any){
    let getTweetUrl='http://tweetapp-env.eba-cxikpwpd.us-east-2.elasticbeanstalk.com/tweets/getTweetsByloginId/'+loginId; 
    var headers_object = new HttpHeaders({"Authorization":this.usertoken});
    this.http.get(getTweetUrl,{ headers : headers_object  }).subscribe(result=>{
      this.individualTweets=result;
      console.log("Tweets of "+loginId+"::",this.individualTweets);
    })
  }

  replyTweet(tweetId, replyData) {
    console.log("reply-data", replyData)
    this.replyTweetData =
    {
      "replyMsg": replyData
    }
    let replyUrl = 'http://tweetapp-env.eba-cxikpwpd.us-east-2.elasticbeanstalk.com/tweets/replytweet/' + tweetId;
    var headers_object = new HttpHeaders({"Authorization":this.usertoken});
    this.http.post<any>(replyUrl, this.replyTweetData, {
      headers : headers_object
    }).subscribe(result => {
      this.toastr.success('You replied to the tweet')
      this.getTweetsByLoginId(this.loginId)
    })
    this.replyData = ''
    console.log("replied::", this.replyTweetData)
  }

  likeTweet(tweetId) {
    console.log("likeTweet Id", tweetId)
    let likeTweetUrl = 'http://tweetapp-env.eba-cxikpwpd.us-east-2.elasticbeanstalk.com/tweets/like/' +tweetId;
    var headers_object = new HttpHeaders({"Authorization":this.usertoken});
    this.http.get<any>(likeTweetUrl,  { headers : headers_object }).subscribe(result => {
      // this.toastr.success('You liked the tweet')
      this.getTweetsByLoginId(this.loginId)
    })
  }
}
