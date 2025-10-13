import { CommonModule } from '@angular/common';

import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';

import { Proficiency } from '../../../shared/interfaces/proficiency';

import { ProficiencyService } from '../../../shared/services/proficiency-service/proficiency.service';
import { Sort } from '../../../core/sort';

import { FormsModule } from '@angular/forms';
import { Subject, takeUntil } from 'rxjs';
import { ProfType } from '../../../shared/enums/prof-enums/prof-type';
import { ProficiencyFilter } from '../../../shared/filters/proficiency-filter';
import { IftaLabelModule } from "primeng/iftalabel";
import { TableModule } from "primeng/table";
import { Select } from 'primeng/select';
import { InputGroupModule } from "primeng/inputgroup";

@Component({
  selector: 'app-proficiency-deleted-list',
  standalone: true,
  imports: [CommonModule, FormsModule, IftaLabelModule, TableModule, Select, InputGroupModule],
  templateUrl: './proficiency-deleted-list.component.html',
  styleUrl: './proficiency-deleted-list.component.css'
})
export class ProficiencyDeletedListComponent implements OnInit, OnDestroy{
  private destroy=new Subject<void>();
  protected data: Proficiency[] = [];
  columnsToDisplay : string[] = ['name', 'type' ,'actions'];
  
  protected filter:ProficiencyFilter;
  protected sort: Sort;
  
  constructor(private proficiencyService:ProficiencyService
  ){
    this.sort={
      sortBy:'',
      ascending: true
    };
    this.filter = {
      name:''
    };
   }
  
   ngOnInit(): void {
     this.proficiencyService.getAllDeleted(this.sort,this.filter).pipe(
      takeUntil(this.destroy)
    ).subscribe(response=>{
      this.data=response.body??[];
    });
   }

   openDialog(id: number) {
    if(confirm("Are you sure to delete this once and for all?")) {
      this.delete(id);
    }
   }
   
   restore(id:number):void {
    this.proficiencyService.restore(id).pipe(
      takeUntil(this.destroy)
    ).subscribe();
    this.removeFromDataSource(id);
   }

   private delete(id:number):void {
    this.proficiencyService.confirmedDelete(id).pipe(
      takeUntil(this.destroy)
    ).subscribe();
    this.removeFromDataSource(id);
   }

   removeFromDataSource(id:number):void{
    this.data=this.data.filter(x=>x.id!=id);
   }

  getTypes(): string[] {
    return Object.values(ProfType).map(proftype => proftype.toString()).splice(0, Object.values(ProfType).length/2);
  }
  
   clearType(): void {
    this.filter.type = ProfType.NONE;
   }

  getTypeAsString(profType: ProfType) : string{
    return profType.toString().charAt(0) + profType.toString().substring(1).toLowerCase();
  }

   search():void {
    this.proficiencyService.getAllDeleted(this.sort,this.filter).pipe(
      takeUntil(this.destroy)
    ).subscribe(response=>{
     this.data=response.body??[];
    });
   }
  
   ngOnDestroy(): void {
     this.destroy.complete();
   }
  }
