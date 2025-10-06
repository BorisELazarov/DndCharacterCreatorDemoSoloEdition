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
  selector: 'app-change-username',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, MatSlideToggleModule,
    MatFormFieldModule, MatInputModule],
  templateUrl: './change-username.component.html',
  styleUrl: './change-username.component.css'
})
export class ChangeUsernameComponent {
  protected usernameForm :FormGroup;
  constructor(private userService: UsersService,
    fb :FormBuilder, private router:Router,
   private localStorageService:LocalStorageService) {
    this.usernameForm = fb.group({
      username: ['',Validators.required]
    });
  }

  submit() {
    if(this.usernameForm.valid){
      let username=this.usernameForm.controls['username'].value;
      this.userService.changeUsername(
        Number(this.localStorageService.getItem("id")),
        username
      ).subscribe(x=>{
        this.router.navigateByUrl('/profile');
      });
    }
    else{
      alert('Invalid input!');
    }
  }
}
