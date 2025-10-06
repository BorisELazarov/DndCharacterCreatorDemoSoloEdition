import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { Router } from '@angular/router';
import { LocalStorageService } from '../../services/local-storage-service/local-storage.service';
import { UsersService } from '../../services/user-service/users.service';

@Component({
  selector: 'app-change-password',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, MatSlideToggleModule,
    MatFormFieldModule, MatInputModule],
  templateUrl: './change-password.component.html',
  styleUrl: './change-password.component.css'
})
export class ChangePasswordComponent {
  protected passwordForm :FormGroup;
  constructor(private userService: UsersService,
    fb :FormBuilder, private router:Router,
   private localStorageService:LocalStorageService) {
    this.passwordForm = fb.group({
      oldPassword: ['',Validators.required],
      newPassword: ['',Validators.required]
    });
  }

  submit() {
    if(this.passwordForm.valid){
      let oldPassword=this.passwordForm.controls['oldPassword'].value;
      let newPassword=this.passwordForm.controls['newPassword'].value;
      this.userService.changePassword(
        Number(this.localStorageService.getItem("id")),
        oldPassword, newPassword
      ).subscribe(x=>{
        this.router.navigateByUrl('/profile');
      });
    }
    else{
      alert('Invalid input!');
    }
  }

}
