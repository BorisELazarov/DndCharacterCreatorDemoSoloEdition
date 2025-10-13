import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { Filter } from '../filter';
import { Sort } from '../../../core/sort';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ClassService } from '../../../shared/services/class-service/class.service';
import { Subject, takeUntil } from 'rxjs';
import { ClassListItem } from '../../../shared/services/class-service/class-list-item';
import { HitDice } from '../../../shared/enums/hit-dice';
import { TableModule } from "primeng/table";
import { IftaLabelModule } from "primeng/iftalabel";
import { Select } from 'primeng/select';
import { InputGroupModule } from "primeng/inputgroup";

@Component({
  selector: 'app-class-deleted-list',
  standalone: true,
  imports: [CommonModule, FormsModule, TableModule, IftaLabelModule, Select, InputGroupModule],
  templateUrl: './class-deleted-list.component.html',
  styleUrl: './class-deleted-list.component.css'
})
export class ClassDeletedListComponent implements OnInit, OnDestroy{
    private destroy=new Subject<void>();

    protected data: ClassListItem[] = [];
    
    protected sort: Sort;
    protected filter: Filter;
    
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
       this.data=response.body??[];
      });
    }

    getHitDices(): string[] {
      return Object.values(HitDice).map(hitDice => hitDice.toString());
    }

    openDialog(id: number) {
      if (confirm("Are you sure to delete this once and for all?")) {
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
      this.data=this.data.filter(x=>x.id!=id);
     }
    
     search():void {
      if (this.filter.hitDice?.toString() === "None") {
        this.filter.hitDice = undefined;
      }
      this.classService.getAllDeleted(this.sort,this.filter).pipe(
        takeUntil(this.destroy)
      ).subscribe(response=>{
       this.data=response.body??[];
      });
     }

     ngOnDestroy(): void {
       this.destroy.complete();
     }
    }
