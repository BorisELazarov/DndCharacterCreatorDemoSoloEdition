import { CommonModule } from '@angular/common';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { Subject, takeUntil } from 'rxjs';

import { IftaLabelModule } from "primeng/iftalabel";
import { Select } from "primeng/select";
import { TableModule } from 'primeng/table'

import { Proficiency } from '../../../shared/interfaces/proficiency';
import { ProficiencyService } from '../../../shared/services/proficiency-service/proficiency.service';
import { Sort } from '../../../core/sort';
import { ProficiencyFilter } from '../../../shared/filters/proficiency-filter';
import { PaginatorState } from "primeng/paginator";
import { InputText } from "primeng/inputtext";
import { ProfType } from '../../../shared/enums/prof-enums/prof-type';
import { CommonMethods } from '../../../core/misc/common-methods';
import { InputGroupModule } from "primeng/inputgroup";
  

@Component({

selector: 'app-proficiency',

standalone: true,

imports: [CommonModule, RouterLink, FormsModule, IftaLabelModule, Select, TableModule, InputText, InputGroupModule],
template:'<h1>Proficiencies:</h1>',
 templateUrl: './proficiency-list.component.html',
styleUrl: './proficiency-list.component.css'

})

export class ProficiencyListComponent implements OnInit, OnDestroy{

private destroy = new Subject<void>();

protected data: Proficiency[] =[];
protected sort:Sort;
protected filter:ProficiencyFilter;

constructor(private proficiencyService:ProficiencyService){
  this.sort={
    sortBy:'',
    ascending: true
  };
  this.filter={
    name:''
  };
 }

  ngOnInit(): void {
    this.proficiencyService.getAll(this.sort, this.filter).pipe(
      takeUntil(this.destroy)
    ).subscribe(response=>{
      this.data=response.body??[];
    });
  }

  getTypes(): string[] {
    return CommonMethods.getProfTypes();
  }

  getTypeAsString(profType: ProfType) : string{
    return profType.toString().charAt(0) + profType.toString().substring(1).toLowerCase();
  }

  clearType() {
    this.filter.type = undefined;
  }

 search():void {
  this.proficiencyService.getAll(this.sort,this.filter).pipe(
    takeUntil(this.destroy)
  ).subscribe(response=>{
    this.data=response.body??[];
  });
 }

 ngOnDestroy(): void {
   this.destroy.complete();
 }
}