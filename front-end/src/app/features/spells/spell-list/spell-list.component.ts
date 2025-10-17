import { CommonModule } from '@angular/common';
import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { Filter } from '../filter';
import { Sort } from '../../../core/sort';
import { Spell } from '../../../shared/interfaces/spell';
import { SpellService } from '../../../shared/services/spell-service/spell.service';
import { Subject, takeUntil } from 'rxjs';
import { InputGroupModule } from "primeng/inputgroup";
import { TableModule } from "primeng/table";
import { SelectModule } from 'primeng/select';

@Component({
  selector: 'app-spell-list',
  standalone: true,
  imports: [
    CommonModule, RouterLink, FormsModule, InputGroupModule, TableModule, SelectModule
  ],
  templateUrl: './spell-list.component.html',
  styleUrl: './spell-list.component.css'
})
export class SpellListComponent implements OnInit, OnDestroy{
  private destroy=new Subject<void>();

  protected rangeText:string='';
  protected data: Spell[] = [];
  
  protected sort:Sort;
  protected filter:Filter;
  protected levelSearch: string[] = [
    'None', 'Cantrip', '1st lvl', '2nd lvl', '3rd lvl', '4th lvl',
    '5th lvl', '6th lvl', '7th lvl', '8th lvl', '9th lvl'
  ];
  protected selectedLevel: string = "None";
  
  constructor(private spellService:SpellService){
    this.sort={
      sortBy:'',
      ascending: true
    };
    this.filter={
      name:'',
      castingTime:'',
    };
   }
  
   ngOnInit(): void {
     this.spellService.getAll(this.sort,this.filter).pipe(
      takeUntil(this.destroy)
    ).subscribe(response=>{
        this.data=response.body??[];
    })
   }

   getLevels(): number[] {
    const levels: number[] = [];
    for (let index = 0; index < 11; index++) {
        levels.push(index-1);
    }
    return levels;
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
    } else {
        this.filter.range=parseInt(this.rangeText);
    }
    this.spellService.getAll(this.sort,this.filter).pipe(
      takeUntil(this.destroy)
    ).subscribe(response=>{
        this.data=response.body??[];
    });
   }

   ngOnDestroy(): void {
     this.destroy.complete();
   }
}
