import { CommonModule } from '@angular/common';
import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { Sort } from '../../../core/sort';
import { Filter } from '../filter';
import { ClassService } from '../../../shared/services/class-service/class.service';
import { takeUntil, Subject } from 'rxjs';
import { ClassListItem } from '../../../shared/services/class-service/class-list-item';
import { TableModule } from "primeng/table";
import { IftaLabelModule } from "primeng/iftalabel";
import { SelectModule } from "primeng/select";
import { HitDice } from '../../../shared/enums/hit-dice';
import { InputGroupModule } from "primeng/inputgroup";
import { CommonMethods } from '../../../core/misc/common-methods';

@Component({
  selector: 'app-class-list',
  standalone: true,
  imports: [CommonModule, RouterLink, FormsModule, TableModule, IftaLabelModule, SelectModule, InputGroupModule],
  templateUrl: './class-list.component.html',
  styleUrl: './class-list.component.css'
})
export class ClassListComponent  implements OnInit, OnDestroy{
  private destroy=new Subject<void>();

  protected data: ClassListItem[] = [];
  
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
     this.classService.getAll(this.sort,this.filter).pipe(
      takeUntil(this.destroy)
    ).subscribe(response=>{
     this.data=response.body??[];
    });
   }

   getHitDices(): string[] {
      return CommonMethods.getHitDices();
   }
  
   search():void {
    if (this.filter.hitDice?.toString() === "None") {
      this.filter.hitDice = undefined;
    }
    this.classService.getAll(this.sort, this.filter).pipe(
      takeUntil(this.destroy)
    ).subscribe(
      response => {
        this.data = response.body ?? [];
      }
    );
   }

   ngOnDestroy(): void {
     this.destroy.complete();
   }
  }