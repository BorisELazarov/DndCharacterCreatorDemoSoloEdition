import { Component, OnDestroy, OnInit} from '@angular/core';
import { Proficiency } from '../../../shared/interfaces/proficiency';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ProficiencyService } from '../../../shared/services/proficiency-service/proficiency.service';
import { Subject, takeUntil } from 'rxjs';

@Component({
  selector: 'app-proficiency-details',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './proficiency-details.component.html',
  styleUrl: './proficiency-details.component.css'
})
export class ProficiencyDetailsComponent implements OnInit, OnDestroy {
  private destroy=new Subject<void>();
  protected proficiency: Proficiency|undefined;
  constructor(private proficiencyService:ProficiencyService,
    private route: ActivatedRoute, private router:Router){
  }
  ngOnInit(): void {
    const id=Number(this.route.snapshot.params['id']);
    this.proficiencyService.getById(id).pipe(
      takeUntil(this.destroy)
    ).subscribe(response=>{
      this.proficiency=response.body??undefined;
    });
  }
  openDialog() {
   if(confirm("Are you sure to delete this?")) {
     this.delete();
   }
  }
  private delete():void{
    this.proficiencyService.delete(this.proficiency?.id??0).pipe(
      takeUntil(this.destroy)
    ).subscribe();
    this.router.navigateByUrl('/proficiencies');
  }

  ngOnDestroy(): void {
    this.destroy.complete();
  }
}
