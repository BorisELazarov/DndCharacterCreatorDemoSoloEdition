import { CommonModule } from '@angular/common';

import { Component, OnInit, ViewChild } from '@angular/core';

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

selector: 'app-proficiency',

standalone: true,

imports: [CommonModule, RouterLink, MatTableModule,
  MatPaginatorModule, MatSelectModule, MatButtonModule,
  MatInputModule, MatIconModule, FormsModule],
template:'<h1>Proficiencies:</h1>',
 templateUrl: './proficiency-list.component.html',
styleUrl: './proficiency-list.component.css'

})

export class ProficiencyListComponent implements OnInit{
protected dataSource:MatTableDataSource<Proficiency>=new MatTableDataSource<Proficiency>([]);
columnsToDisplay : string[] = ['name', 'type' ,'actions'];

@ViewChild(MatPaginator) paginator!: MatPaginator;
protected sort:Sort;
protected filter:Filter;

constructor(private proficiencyService:ProficiencyService){
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
   this.proficiencyService.getAll(this.sort,this.filter).subscribe(response=>{
   this.dataSource.data=response.body??[];
   this.dataSource.paginator=this.paginator;
  });
 }

 search():void {
  this.proficiencyService.getAll(this.sort,this.filter).subscribe(response=>{
   this.dataSource.data=response.body??[];
  });
 }


}