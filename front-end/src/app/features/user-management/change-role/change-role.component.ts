import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router} from '@angular/router';
import { User } from '../../../core/interfaces/user';
import { LocalStorageService } from '../../../core/services/local-storage-service/local-storage.service';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { Subject, takeUntil } from 'rxjs';
import { UsersService } from '../../../core/services/user-service/users.service';

@Component({
  selector: 'app-change-role',
  standalone: true,
  imports: [MatSelectModule, MatButtonModule, MatIconModule],
  templateUrl: './change-role.component.html',
  styleUrl: './change-role.component.css'
})
export class ChangeRoleComponent implements OnInit, OnDestroy{
  protected user:User|undefined;
  private destroy=new Subject<void>();

  constructor(private localStorageService:LocalStorageService,
    private userService:UsersService,private route: ActivatedRoute,
     private router:Router
  ) {
  }

  ngOnInit(): void {
    this.userService.getById(this.route.snapshot.params['id']).pipe(
      takeUntil(this.destroy)
    ).subscribe(
      response=>{
        this.user=response.body??undefined;
      }
    );
  }

   protected restore():void{
    this.userService.restore(this.user?.id??0).pipe(
      takeUntil(this.destroy)
    ).subscribe(
      ()=>{
        this.localStorageService.setItem('deleted','false');
        this.router.navigateByUrl('/user');
      }
     );
   }

   protected alert():void{
    if(
      confirm("Are you sure you want to change this user's role to "
      +(this.user?.role==="data manager"?"data manager":"user")
    )+"?"
    ){
      this.userService.changeRole(this.user!.id,this.user!.role).pipe(
        takeUntil(this.destroy)
      ).subscribe(
        ()=>{
          this.router.navigateByUrl("users");
        }
      );
    }
   }

   ngOnDestroy(): void {
     this.destroy.complete();
   }
}