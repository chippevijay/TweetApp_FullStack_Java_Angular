import { formatDate } from '@angular/common';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-all-tweets',
  templateUrl: './all-tweets.component.html',
  styleUrls: ['./all-tweets.component.css']
})
export class AllTweetsComponent implements OnInit {
  usertoken: any;
  loginId: any;
  allTweets: any;
  replyData: any;
  replyTweetData: any = [];
  likeTweetData: any = [];

  constructor(private http: HttpClient, private toastr: ToastrService) { }

  ngOnInit(): void {
    this.loginId = localStorage.getItem('loginId');
    this.usertoken=localStorage.getItem('token');
    console.log(this.loginId);
    let getTweetUrl = 'http://tweetapp-env.eba-cxikpwpd.us-east-2.elasticbeanstalk.com/tweets/all';
    var headers_object = new HttpHeaders({"Authorization":this.usertoken});
    this.http.get(getTweetUrl,{ headers : headers_object  }).subscribe(result => {
      this.allTweets = result;
      console.log("AllTweets::", this.allTweets);
    })
  }

  getAllTweets() {
    let getTweetUrl = 'http://tweetapp-env.eba-cxikpwpd.us-east-2.elasticbeanstalk.com/tweets/all';
    var headers_object = new HttpHeaders({"Authorization":this.usertoken});
    this.http.get(getTweetUrl,{ headers : headers_object  }).subscribe(result => {
      this.allTweets = result;
      console.log("AllTweets::", this.allTweets);
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
      this.getAllTweets()
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
        this.getAllTweets()
      })
  }
}
