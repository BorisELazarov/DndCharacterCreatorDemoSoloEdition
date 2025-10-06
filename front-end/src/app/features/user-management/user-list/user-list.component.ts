import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { Sort } from '../../../core/sort';
import { Filter } from './filter';
import { LocalStorageService } from '../../../core/services/local-storage-service/local-storage.service';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { UserListItem } from './user-list-item';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { Subject, takeUntil } from 'rxjs';
import { UserManagementService } from '../service/user-management.service';

@Component({
  selector: 'app-user-list',
  standalone: true,
  imports: [CommonModule, RouterLink, MatTableModule,
    MatPaginatorModule, MatSelectModule, MatButtonModule,
    MatInputModule, MatIconModule, FormsModule],
  templateUrl: './user-list.component.html',
  styleUrl: './user-list.component.css'
})
export class UserListComponent implements OnInit, OnDestroy {
  protected rangeText:string='';
  protected dataSource:MatTableDataSource<UserListItem> = new MatTableDataSource<UserListItem>([]);
  protected columnsToDisplay : string[] = ['username', 'email','role', 'actions'];
  
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  protected sort:Sort;
  protected filter:Filter;
  private destroy=new Subject<void>();
  
  constructor(private userService:UserManagementService,
    private localStorageService: LocalStorageService,
     private route:ActivatedRoute){
    this.sort={
      sortBy:'',
      ascending: true
    };
    this.filter={
      username:'',
      email:'',
      roleTitle:''
    };
   }
  
   ngOnInit(): void {
    this.userService.getAll(this.sort,this.filter).pipe(
      takeUntil(this.destroy)
    ).subscribe(response=>{
    this.dataSource.data=response.body?.filter(
     x=>
       x.id!==(Number(this.localStorageService.getItem("id")))
       &&
       x.role!=='admin'
   )??[];
    this.dataSource.paginator=this.paginator;
   });
   }

   search():void {
    this.userService.getAll(this.sort,this.filter).pipe(
      takeUntil(this.destroy)
    ).subscribe(response=>{
      this.dataSource.data=response.body?.filter(
       x=>
         x.id!==(Number(this.localStorageService.getItem("id")))
         &&
         x.role!=='admin'
     )??[];
      this.dataSource.paginator=this.paginator;
     });
   }

   ngOnDestroy(): void {
    this.destroy.complete();
   }
}
