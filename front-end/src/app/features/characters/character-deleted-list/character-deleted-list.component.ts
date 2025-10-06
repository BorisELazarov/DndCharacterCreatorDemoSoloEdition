import { CommonModule } from '@angular/common';
import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatSelectModule } from '@angular/material/select';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { CharacterListItem } from '../list-items/character-list-item';
import { Filter } from '../filter';
import { CharacterService } from '../service/character.service';
import { Sort } from '../../../core/sort';
import { LocalStorageService } from '../../../core/services/local-storage-service/local-storage.service';
import { Subject, takeUntil } from 'rxjs';

@Component({
  selector: 'app-character-deleted-list',
  standalone: true,
  imports: [CommonModule, RouterLink, MatTableModule,
    MatPaginatorModule, MatSelectModule, MatButtonModule,
    MatInputModule, MatIconModule, FormsModule],
  templateUrl: './character-deleted-list.component.html',
  styleUrl: './character-deleted-list.component.css'
})
export class CharacterDeletedListComponent  implements OnInit, OnDestroy {
  protected rangeText:string='';
  protected dataSource:MatTableDataSource<CharacterListItem> = new MatTableDataSource<CharacterListItem>([]);
  protected columnsToDisplay : string[] = ['name', 'level','dndClass', 'actions'];

  private destroy=new Subject<void>();
  
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  protected sort:Sort;
  protected filter:Filter;
  
  constructor(private characterService:CharacterService,
    private localStorageService:LocalStorageService
  ){
    this.sort={
      sortBy:'',
      ascending: true
    };
    this.filter={
      name:'',
      dndClassName:''
    };
   }
  
   ngOnInit(): void {
     this.characterService.getAllDeleted(this.sort,this.filter,
      this.localStorageService.getItem("id")??'').pipe(
        takeUntil(this.destroy)
      ).subscribe(response=>{
     this.dataSource.data=response.body??[];
     this.dataSource.paginator=this.paginator;
    });
   }

   openDialog(id: number) {
    let character:CharacterListItem|undefined=this.dataSource.data.find(x=>x.id==id);
    if (character===undefined) {
      return;
    }
    if(
      confirm("Are you sure to destroy "
        +character.name+" the Lvl"+character.level
        +" "+character.dndClass.name+" once and for all?")
    ) {
      this.characterService.confirmedDelete(id).pipe(
        takeUntil(this.destroy)
      ).subscribe();
      this.removeFromDataSource(id);
    }
   }
   
   restore(id:number):void {
    this.characterService.restore(id).pipe(
      takeUntil(this.destroy)
    ).subscribe();
    this.removeFromDataSource(id);
   }

   removeFromDataSource(id:number):void{
    this.dataSource.data=this.dataSource.data.filter(x=>x.id!=id);
   }
  
   search():void {
    this.characterService.getAll(this.sort,this.filter,
      this.localStorageService.getItem("id")??""
    ).pipe(
      takeUntil(this.destroy)
    ).subscribe(response=>{
     this.dataSource.data=response.body??[];
    });
   }

   ngOnDestroy(): void {
     this.destroy.complete();
   }
}
