import { formatDate } from '@angular/common';
import { HttpClient , HttpHeaders} from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-post-tweet',
  templateUrl: './post-tweet.component.html',
  styleUrls: ['./post-tweet.component.css']
})
export class PostTweetComponent implements OnInit {

  usertoken: string;
  dateFormat:any;
  tweetData='';
  loginId:any;
  userData:any;
  mockTweet:any=[];

  constructor(private http:HttpClient, private toastr:ToastrService, private router: Router) { }

  ngOnInit(): void {
    this.usertoken=localStorage.getItem('token');
    var headers_object = new HttpHeaders({"Authorization":this.usertoken});
    this.loginId=localStorage.getItem('loginId');
    console.log(this.loginId);
    let userNameUrl='http://tweetapp-env.eba-cxikpwpd.us-east-2.elasticbeanstalk.com/tweets/getUserByloginId';
    console.log(userNameUrl);
    this.http.get<any>(userNameUrl,{ headers : headers_object  }).subscribe(result=>{
      this.userData=result;
      console.log("UserData:",this.userData);
    })
  }


  postTweet(){
    let dateTime = new Date().getTime();
    this.dateFormat=formatDate(dateTime, 'yyyy-MM-dd', 'en');
    this.mockTweet=[]
    this.mockTweet=
      {
        "message":this.tweetData,
        "tweetLikes":[],
        "tweetReply":[]
   
      }
    
    let postTweetUrl='http://tweetapp-env.eba-cxikpwpd.us-east-2.elasticbeanstalk.com/tweets/'+this.loginId+'/addnewtweet'
    var headers_object = new HttpHeaders({"Authorization":this.usertoken});
    this.http.post(postTweetUrl,this.mockTweet,{
      headers : headers_object
    }).subscribe(result=>
      {
        this.toastr.success('Tweet Posted Successfully!!!')
        
        this.router.navigate(['/all-tweets']);
      },
      error=>{
        this.toastr.error('Cannot Post Tweet due to some technical error');
      })
    this.tweetData='';
    
    console.log("tweet-data:::",this.mockTweet);
    
  }
}
