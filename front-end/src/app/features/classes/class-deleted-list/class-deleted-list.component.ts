import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { DndClass } from '../../../shared/interfaces/dnd-class';
import { Filter } from '../filter';
import { Sort } from '../../../core/sort';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { RouterLink } from '@angular/router';
import { ClassService } from '../../../shared/services/class-service/class.service';
import { Subject, takeUntil } from 'rxjs';
import { ClassListItem } from '../../../shared/services/class-service/class-list-item';

@Component({
  selector: 'app-class-deleted-list',
  standalone: true,
  imports: [CommonModule, RouterLink, MatTableModule,
    MatPaginatorModule, MatSelectModule, MatButtonModule,
    MatInputModule, MatIconModule, FormsModule],
  templateUrl: './class-deleted-list.component.html',
  styleUrl: './class-deleted-list.component.css'
})
export class ClassDeletedListComponent implements OnInit, OnDestroy{
    private destroy=new Subject<void>();

    protected dataSource:MatTableDataSource<ClassListItem>=new MatTableDataSource<ClassListItem>([]);
    protected columnsToDisplay : string[] = ['name', 'hitDice' ,'actions'];
    
    @ViewChild(MatPaginator) paginator!: MatPaginator;
    protected sort:Sort;
    protected filter:Filter;
    
    constructor(private classService:ClassService){
      this.sort={
        sortBy:'',
        ascending: true
      };
      this.filter={
        name:''
      };
     }
    
     ngOnInit(): void {
       this.classService.getAllDeleted(this.sort,this.filter).pipe(
        takeUntil(this.destroy)
      ).subscribe(response=>{
       this.dataSource.data=response.body??[];
       this.dataSource.paginator=this.paginator;
      });
     }

     openDialog(id: number) {
      if(confirm("Are you sure to delete this once and for all?")) {
        this.delete(id);
      }
     }
     
     restore(id:number):void {
      this.classService.restore(id).pipe(
        takeUntil(this.destroy)
      ).subscribe();
      this.removeFromDataSource(id);
     }
  
     private delete(id:number):void {
      this.classService.confirmedDelete(id).pipe(
        takeUntil(this.destroy)
      ).subscribe();
      this.removeFromDataSource(id);
     }
  
     removeFromDataSource(id:number):void{
      this.dataSource.data=this.dataSource.data.filter(x=>x.id!=id);
     }
    
     search():void {
      this.classService.getAllDeleted(this.sort,this.filter).pipe(
        takeUntil(this.destroy)
      ).subscribe(response=>{
       this.dataSource.data=response.body??[];
      });
     }

     ngOnDestroy(): void {
       this.destroy.complete();
     }
    }
