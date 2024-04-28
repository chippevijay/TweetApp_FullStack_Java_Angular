import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, ValidatorFn, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';


@Component({
  selector: 'app-forget-pass-popup',
  templateUrl: './forget-pass-popup.component.html',
  styleUrls: ['./forget-pass-popup.component.css'],
  
})
export class ForgetPassPopupComponent implements OnInit {
  forgetForm: FormGroup;
  baseUrl: 'http://localhost:8082/tweets/';
  forgetPasswordStatus: any
  
  validityFlag: Boolean = false
  showOtherModal: Boolean = true
  changePasswordForm: FormGroup;



  constructor(private dialog: MatDialog, private http: HttpClient, private toastr: ToastrService, private route: Router) { }

  ngOnInit(): void {
    this.initializeForm();
  }

  initializeForm() {
    this.forgetForm = new FormGroup({
      loginId: new FormControl('', Validators.required),
      contactNumber: new FormControl('', [Validators.required, Validators.pattern("^((\\+91-?)|0)?[0-9]{10}$")]),
      dateOfBirth : new FormControl('', Validators.required)
    })
    this.changePasswordForm = new FormGroup({
      password: new FormControl('', [Validators.required, Validators.minLength(4), Validators.maxLength(8)]),
      confirmPassword: new FormControl('', [Validators.required, this.matchValues('password')])
    })
    this.changePasswordForm.controls.password.valueChanges.subscribe(() => {
      this.changePasswordForm.controls.confirmPassword.updateValueAndValidity();
    })
  }
  matchValues(matchTo: string): ValidatorFn {
    return (control: AbstractControl) => {
      return control?.value === control?.parent?.controls[matchTo].value ? null : { isMatching: true }
    }
  }
  
  validatecredentials() {
    
    if (this.forgetForm.valid) {
      loginId : this.forgetForm.value?.loginId;
      this.http.post<any>('http://localhost:8082/tweets/'+this.forgetForm.value?.loginId+'/forgotpassword', 
                this.forgetForm.value).subscribe((result) => {
        this.forgetPasswordStatus = result;
        this.toastr.success('Credentials Validated successfully')
        this.validityFlag = true;
        this.showOtherModal = false;
        localStorage.setItem('username', this.forgetForm.value?.loginId);
    },
        error => {
          // console.log(error);
          this.toastr.error(error.error);
        })
    }
  }
  
 
  changePassword() {
    let updatePasswordUrl = 'http://localhost:8082/tweets/'+localStorage.getItem('username')+'/updatepassword'
    let loginId = localStorage.getItem('username');
    let jsonPayload = {
      loginId: loginId,
      password: this.changePasswordForm.value.password
    }
    if (this.changePasswordForm.valid) {
      this.http.post<any>(updatePasswordUrl, jsonPayload).subscribe(result => {
        this.toastr.success('Updated Successfully');

        localStorage.removeItem('username');
        let dialogRef = this.dialog.closeAll()
      },
        error => {
          this.toastr.error(error.error);
        })
    }

    // console.log("Password Value::::",this.changePasswordForm.value.password)

  }

  getColor(){
    if(this.forgetForm.valid){
      return 'green';
    }
  }

}
