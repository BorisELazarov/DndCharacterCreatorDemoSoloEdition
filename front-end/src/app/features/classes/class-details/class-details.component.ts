import { Component, OnDestroy, ViewChild } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { DndClass } from '../../../shared/interfaces/dnd-class';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { Proficiency } from '../../../shared/interfaces/proficiency';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { ClassService } from '../../../shared/services/class-service/class.service';
import { Subject, takeUntil } from 'rxjs';

@Component({
  selector: 'app-class-details',
  standalone: true,
  imports: [RouterLink,CommonModule, MatTableModule,
    MatPaginatorModule, MatSelectModule, MatButtonModule,
    MatInputModule, MatIconModule, FormsModule],
  templateUrl: './class-details.component.html',
  styleUrl: './class-details.component.css'
})
export class ClassDetailsComponent implements OnDestroy{
  private destroy=new Subject<void>();

  protected dndClass: DndClass|undefined;
  protected dataSource:MatTableDataSource<Proficiency>=new MatTableDataSource<Proficiency>([]);
  columnsToDisplay : string[] = ['name', 'type'];
  
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  constructor(private classService:ClassService,
    private route: ActivatedRoute, private router:Router){
  }
  ngOnInit(): void {
    const id=Number(this.route.snapshot.params['id']);
    this.classService.getById(id).pipe(
      takeUntil(this.destroy)
    ).subscribe(response=>{
      this.dndClass=response.body??undefined;
      this.dataSource.data=this.dndClass?.proficiencies??[];
      this.dataSource.paginator=this.paginator;
    });
  }
  openDialog() {
   if(confirm("Are you sure to delete?")) {
     this.delete();
   }
  }
  private delete():void{
    this.classService.delete(this.dndClass?.id??0).pipe(
      takeUntil(this.destroy)
    ).subscribe();
    this.router.navigateByUrl('/classes');
  }

  ngOnDestroy(): void {
    this.destroy.complete();
  }
}
