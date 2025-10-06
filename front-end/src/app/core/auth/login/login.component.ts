import { Component } from '@angular/core';
import { FormGroup, FormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';

import { CommonModule } from '@angular/common';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { LocalStorageService } from '../../services/local-storage-service/local-storage.service';
import { AuthService } from '../../services/auth-service/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, MatSlideToggleModule,
    MatFormFieldModule, MatInputModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  protected loginForm :FormGroup;
  constructor(private authService: AuthService,
    fb :FormBuilder, private router:Router,
   private localStorageService:LocalStorageService) {
    this.loginForm = fb.group({
      email: ['',Validators.required],
      password: ['',Validators.required]
    });
  }

  submit() {
    let email=this.loginForm.controls['email'].value;
    let password=this.loginForm.controls['password'].value;
    if(this.loginForm.valid){
      this.authService.login(email,password).subscribe(response=>{
        let user=response.user;
        if(user.id!==undefined){
          this.localStorageService.setItem("id",user.id.toString()??"");
          this.localStorageService.setItem("role",user.role);
          let isDeleted:string="true";
          if (user.isDeleted===undefined || user.isDeleted===false) {
            isDeleted="false";
          }
          this.localStorageService.setItem("deleted",isDeleted);
          this.authService.setAuthToken(response.token);
          this.router.navigateByUrl('/profile');
        }
      });
    }
    else{
      alert('Invalid input!');
    }
  }
}
