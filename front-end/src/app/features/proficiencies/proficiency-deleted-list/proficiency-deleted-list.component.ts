import { CommonModule } from '@angular/common';

import { Component, ViewChild } from '@angular/core';

import { Proficiency } from '../../../shared/interfaces/proficiency';

import { ProficiencyService } from '../service/proficiency.service';

import { RouterLink } from '@angular/router';

import {MatTableDataSource, MatTableModule } from '@angular/material/table';

import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { Sort } from '../../../core/sort';


import { MatSelectModule } from '@angular/material/select';
import {MatButtonModule} from '@angular/material/button';
import { Filter } from '../common/filter';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-proficiency-deleted-list',
  standalone: true,
  imports: [CommonModule, RouterLink, MatTableModule,
    MatPaginatorModule, MatSelectModule, MatButtonModule,
    MatInputModule, MatIconModule, FormsModule],
  templateUrl: './proficiency-deleted-list.component.html',
  styleUrl: './proficiency-deleted-list.component.css'
})
export class ProficiencyDeletedListComponent {
  protected dataSource:MatTableDataSource<Proficiency>=new MatTableDataSource<Proficiency>([]);
  columnsToDisplay : string[] = ['name', 'type' ,'actions'];
  
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  protected sort:Sort;
  protected filter:Filter;
  
  constructor(private proficiencyService:ProficiencyService
  ){
    this.sort={
      sortBy:'',
      ascending: true
    };
    this.filter={
      name:'',
      type:''
    };
   }
  
   ngOnInit(): void {
     this.proficiencyService.getAllDeleted(this.sort,this.filter).subscribe(response=>{
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
    this.proficiencyService.restore(id);
    this.removeFromDataSource(id);
   }

   private delete(id:number):void {
    this.proficiencyService.confirmedDelete(id);
    this.removeFromDataSource(id);
   }

   removeFromDataSource(id:number):void{
    this.dataSource.data=this.dataSource.data.filter(x=>x.id!=id);
   }
  
   search():void {
    this.proficiencyService.getAllDeleted(this.sort,this.filter).subscribe(response=>{
     this.dataSource.data=response.body??[];
    });
   }
  
  }
