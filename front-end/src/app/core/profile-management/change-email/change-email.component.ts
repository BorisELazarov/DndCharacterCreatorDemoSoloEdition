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
  selector: 'app-change-email',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, MatSlideToggleModule,
    MatFormFieldModule, MatInputModule],
  templateUrl: './change-email.component.html',
  styleUrl: './change-email.component.css'
})
export class ChangeEmailComponent {
  protected emailForm :FormGroup;
  constructor(private userService: UsersService,
    fb :FormBuilder, private router:Router,
   private localStorageService:LocalStorageService) {
    this.emailForm = fb.group({
      email: ['example@mail.com',Validators.required]
    });
  }

  submit() {
    if(this.emailForm.valid){
      let email=this.emailForm.controls['email'].value;
      this.userService.changeEmail(
        Number(this.localStorageService.getItem("id")),
        email
      ).subscribe(x=>{
        this.router.navigateByUrl('/profile');
      });
    }
    else{
      alert('Invalid input!');
    }
  }
}
