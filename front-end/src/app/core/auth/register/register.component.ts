import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { Router } from '@angular/router';
import { LocalStorageService } from '../../services/local-storage-service/local-storage.service';
import { Register } from '../../interfaces/register';
import { AuthService } from '../../services/auth-service/auth.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, MatSlideToggleModule,
    MatFormFieldModule, MatInputModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
  protected registerForm :FormGroup;
  constructor(private authService: AuthService,
    fb :FormBuilder, private router:Router,
   private localStorageService:LocalStorageService) {
    this.registerForm = fb.group({
      email: ['',Validators.required],
      username: ['',Validators.required],
      password: ['',Validators.required],
      confirmPassword: ['',Validators.required]
    });
  }

  submit() {
    let email=this.registerForm.controls['email'].value;
    let username=this.registerForm.controls['username'].value;
    let password=this.registerForm.controls['password'].value;
    let confirmPassword=this.registerForm.controls['confirmPassword'].value;
    if(this.registerForm.valid && password===confirmPassword){
      let register:Register={
        email:email,
        username:username,
        password:password
      };
      this.authService.register(register).subscribe(response=>{
        let user=response.user;
        if(!(user.id===undefined)){
          this.localStorageService.setItem("id",user.id.toString());
          this.localStorageService.setItem("role",user.role);
          let isDeleted:string="true";
          if (user.isDeleted!==undefined || user.isDeleted===false) {
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
