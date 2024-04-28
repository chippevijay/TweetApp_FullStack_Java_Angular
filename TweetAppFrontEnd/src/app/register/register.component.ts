import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, ValidatorFn, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  registerForm: FormGroup;
  baseUrl: 'http://tweetapp-env.eba-cxikpwpd.us-east-2.elasticbeanstalk.com/tweets/';


  constructor(private router: Router, private toastr: ToastrService, private http: HttpClient) { }

  ngOnInit(): void {
    console.log('inside registers');
    this.initializeForm()
  }
  initializeForm() {
    this.registerForm = new FormGroup({
      firstName: new FormControl('', Validators.required),
      lastName: new FormControl('', Validators.required),
      email: new FormControl('', [Validators.required, Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")]),
      password: new FormControl('', [Validators.required, Validators.minLength(4), Validators.maxLength(8)]),
      confirmPassword: new FormControl('', [Validators.required, this.matchValues('password')]),
      loginId: new FormControl('', Validators.required),
      contactNumber: new FormControl('', [Validators.required, Validators.pattern("^((\\+91-?)|0)?[0-9]{10}$")]),
      gender : new FormControl('', Validators.required),
      dateOfBirth : new FormControl('', Validators.required)
    })
    this.registerForm.controls.password.valueChanges.subscribe(() => {
      this.registerForm.controls.confirmPassword.updateValueAndValidity();
    })
  }

  matchValues(matchTo: string): ValidatorFn {
    return (control: AbstractControl) => {
      return control?.value === control?.parent?.controls[matchTo].value ? null : { isMatching: true }
    }

  }
  register() {
    if (this.registerForm.valid) {

      this.http.post<any>('http://tweetapp-env.eba-cxikpwpd.us-east-2.elasticbeanstalk.com/tweets/register', this.registerForm.value).subscribe((result) => {
        this.toastr.success('Registration Successful!')
        this.router.navigate(['/login'])
      },
        error => {
          // console.log(error);
          this.toastr.error(error.error);
        })

    }
    else {
      this.toastr.error('Registration Failed')

    }
  }

  getColor(){
    if(this.registerForm.valid){
      return 'black';
    }
  }

}
