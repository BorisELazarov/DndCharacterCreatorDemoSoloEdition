import { Component, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { DndClass } from '../../../shared/interfaces/dnd-class';
import { Proficiency } from '../../../shared/interfaces/proficiency';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ClassService } from '../../../shared/services/class-service/class.service';
import { Subject, takeUntil } from 'rxjs';
import { TableModule } from "primeng/table";
import { ProfType } from '../../../shared/enums/prof-enums/prof-type';

@Component({
  selector: 'app-class-details',
  standalone: true,
  imports: [RouterLink, CommonModule, FormsModule, TableModule],
  templateUrl: './class-details.component.html',
  styleUrl: './class-details.component.css'
})
export class ClassDetailsComponent implements OnDestroy{
  private destroy=new Subject<void>();

  protected dndClass: DndClass | undefined;
  protected data: Proficiency[] = [];
  columnsToDisplay : string[] = ['name', 'type'];
  
  constructor(private classService:ClassService,
    private route: ActivatedRoute, private router:Router){
  }
  ngOnInit(): void {
    const id=Number(this.route.snapshot.params['id']);
    this.classService.getById(id).pipe(
      takeUntil(this.destroy)
    ).subscribe(response=>{
      this.dndClass=response.body??undefined;
      this.data=this.dndClass?.proficiencies ?? [];
    });
  }

  getTypeAsString(profType: ProfType) : string{
      return profType.toString().charAt(0) + profType.toString().substring(1).toLowerCase();
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
