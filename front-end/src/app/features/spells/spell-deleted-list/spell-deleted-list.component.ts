import { CommonModule } from '@angular/common';
import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { Filter } from '../filter';
import { Sort } from '../../../core/sort';
import { Spell } from '../../../shared/interfaces/spell';
import { SpellService } from '../../../shared/services/spell-service/spell.service';
import { Subject, takeUntil } from 'rxjs';
import { TableModule } from "primeng/table";
import { InputGroupModule } from "primeng/inputgroup";
import { SelectModule } from 'primeng/select';

@Component({
  selector: 'app-spell-deleted-list',
  standalone: true,
  imports: [CommonModule, RouterLink, FormsModule, TableModule, InputGroupModule, SelectModule],
  templateUrl: './spell-deleted-list.component.html',
  styleUrl: './spell-deleted-list.component.css'
})
export class SpellDeletedListComponent implements OnInit, OnDestroy{
  private destroy=new Subject<void>();

  protected rangeText:string='';
  protected data: Spell[] = [];
  protected levelSearch: string[] = [
    'None', 'Cantrip', '1st lvl', '2nd lvl', '3rd lvl', '4th lvl',
    '5th lvl', '6th lvl', '7th lvl', '8th lvl', '9th lvl'
  ];
  protected selectedLevel: string = "None";
  
  protected sort:Sort;
  protected filter:Filter;
  
  constructor(private spellService:SpellService){
    this.sort={
      sortBy:'',
      ascending: true
    };
    this.filter={
      name: '',
      level: -1,
      castingTime: '',
      range: -1
    };
   }
  
   ngOnInit(): void {
     this.spellService.getAllDeleted(this.sort,this.filter).pipe(
      takeUntil(this.destroy)
    ).subscribe(response=>{
     this.data=response.body??[];
    });
   }

   openDialog(id: number) {
    if(confirm("Are you sure to delete this once and for all?")) {
      this.spellService.confirmedDelete(id).subscribe();
      this.removeFromdata(id);
    }
   }
   
   restore(id:number):void {
    this.spellService.restore(id).pipe(
      takeUntil(this.destroy)
    ).subscribe();
    this.removeFromdata(id);
   }

   removeFromdata(id:number):void{
    this.data=this.data.filter(x=>x.id!=id);
   }
  
   search():void {
    switch (this.selectedLevel) {
      case 'None':
        this.filter.level = -1;
        break;

      case 'Cantrip':
        this.filter.level = 0;
        break;
    
      default:
        this.filter.level = Number.parseInt(this.selectedLevel.charAt(0));
        break;
    }
    if(this.rangeText===null||this.rangeText==='')
    {
      this.filter.range=-1;
    }
    else{
      this.filter.range=parseInt(this.rangeText);
    }
    this.spellService.getAllDeleted(this.sort,this.filter).pipe(
      takeUntil(this.destroy)
    ).subscribe(response=>{
     this.data=response.body??[];
    });
   }

   ngOnDestroy(): void {
     this.destroy.complete();
   }
}
