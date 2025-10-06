import { CommonModule } from '@angular/common';
import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatSelectModule } from '@angular/material/select';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { RouterLink } from '@angular/router';
import { Filter } from '../filter';
import { Sort } from '../../../core/sort';
import { Spell } from '../../../shared/interfaces/spell';
import { SpellService } from '../../../shared/services/spell-service/spell.service';
import { Subject, takeUntil } from 'rxjs';

@Component({
  selector: 'app-spell-deleted-list',
  standalone: true,
  imports: [CommonModule, RouterLink, MatTableModule,
    MatPaginatorModule, MatSelectModule, MatButtonModule,
    MatInputModule, MatIconModule, FormsModule],
  templateUrl: './spell-deleted-list.component.html',
  styleUrl: './spell-deleted-list.component.css'
})
export class SpellDeletedListComponent implements OnInit, OnDestroy{
  private destroy=new Subject<void>();

  protected rangeText:string='';
  protected dataSource:MatTableDataSource<Spell> = new MatTableDataSource<Spell>([]);
  protected columnsToDisplay : string[] = ['name', 'level','castingTime','castingRange', 'actions'];
  
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  protected sort:Sort;
  protected filter:Filter;
  
  constructor(private spellService:SpellService){
    this.sort={
      sortBy:'',
      ascending: true
    };
    this.filter={
      name:'',
      level:-1,
      castingTime:'',
      range:-1
    };
   }
  
   ngOnInit(): void {
     this.spellService.getAllDeleted(this.sort,this.filter).pipe(
      takeUntil(this.destroy)
    ).subscribe(response=>{
     this.dataSource.data=response.body??[];
     this.dataSource.paginator=this.paginator;
    });
   }

   openDialog(id: number) {
    if(confirm("Are you sure to delete this once and for all?")) {
      this.spellService.confirmedDelete(id).subscribe();
      this.removeFromDataSource(id);
    }
   }
   
   restore(id:number):void {
    this.spellService.restore(id).pipe(
      takeUntil(this.destroy)
    ).subscribe();
    this.removeFromDataSource(id);
   }

   removeFromDataSource(id:number):void{
    this.dataSource.data=this.dataSource.data.filter(x=>x.id!=id);
   }
  
   search():void {
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
     this.dataSource.data=response.body??[];
    });
   }

   ngOnDestroy(): void {
     this.destroy.complete();
   }
}
