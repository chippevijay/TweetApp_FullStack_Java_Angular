import { formatDate } from '@angular/common';
import { HttpClient , HttpHeaders} from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { MatDialog } from '@angular/material/dialog';
import { EditTweetPopupComponent } from '../edit-tweet-popup/edit-tweet-popup.component';
import { Router } from '@angular/router';
import { Options } from 'selenium-webdriver';

@Component({
  selector: 'app-my-tweets',
  templateUrl: './my-tweets.component.html',
  styleUrls: ['./my-tweets.component.css']
})
export class MyTweetsComponent implements OnInit {

  usertoken: any;
  userName: any;
  myTweets:any;
  replyData:any;
  replyTweetData:any=[];
  userData:any;

  constructor(private dialog: MatDialog, private http:HttpClient, private router: Router, private toastr:ToastrService) { }

  ngOnInit(): void {
    this.userName = localStorage.getItem('loginId');
    this.usertoken=localStorage.getItem('token');
    console.log(this.userName);
    this.getMyTweets();
    this.getUserDetails();
  }

  getUserDetails(){
    let getUserDetailUrl = 'http://tweetapp-env.eba-cxikpwpd.us-east-2.elasticbeanstalk.com/tweets/getUserByloginId';
    var headers_object = new HttpHeaders({"Authorization":this.usertoken});
    this.http.get<any>(getUserDetailUrl,{ headers : headers_object  }).subscribe(result=>{
      this.userData=result;
      console.log("UserData:",this.userData);
    })

  }

  getMyTweets(){
    let getTweetUrl='http://tweetapp-env.eba-cxikpwpd.us-east-2.elasticbeanstalk.com/tweets/getTweetsByloginId/'+ this.userName; 
    var headers_object = new HttpHeaders({"Authorization":this.usertoken});
    this.http.get(getTweetUrl,{ headers : headers_object  }).subscribe(result=>{
      this.myTweets=result;
      console.log("MyTweets::",this.myTweets);
      console.log("My Tweets length : " ,this.myTweets.length);
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
      this.getMyTweets()
    })
    this.replyData = ''
    console.log("replied::", this.replyTweetData)
  }

  
  openEditTweetModal(tweet: any){
    // this.editTweet=tweet;
    const dialogRef = this.dialog.open(EditTweetPopupComponent, {
      width: '700px',
      minHeight: 'calc(45vh - 60px)',
      height: 'auto',
      data: {
        modalData: tweet
      }
    });
  }

  likeTweet(tweetId) {
    console.log("likeTweet Id", tweetId)
    let likeTweetUrl = 'http://tweetapp-env.eba-cxikpwpd.us-east-2.elasticbeanstalk.com/tweets/like/' +tweetId;
    var headers_object = new HttpHeaders({"Authorization":this.usertoken});
    this.http.get<any>(likeTweetUrl,  { headers : headers_object }).subscribe(result => {
      // this.toastr.success('You liked the tweet')
      this.getMyTweets()
    })
  }

  deleteTweet(tweet:any){
    console.log(tweet);
    if(confirm('Do you want to delete the tweet "'+tweet.message+'" ?') == true){
      const options = {headers:{'Content-Type':'application/Json'},body:tweet};
      let deleteTweetUrl='http://tweetapp-env.eba-cxikpwpd.us-east-2.elasticbeanstalk.com/tweets/deletetweet/'+tweet.tweetId
      var headers_object = new HttpHeaders({"Authorization":this.usertoken});
      this.http.post<any>(deleteTweetUrl,{},{
        headers : headers_object
      }).subscribe(result=>{
        this.toastr.success('Tweet deleted successfully');
        this.getMyTweets();
      },
      error => {
        this.toastr.error(error.error.text);
      });
    }
    else{
      return;
    }
  }
}
