import { Component, OnInit} from '@angular/core';
import { Proficiency } from '../../../shared/interfaces/proficiency';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ProficiencyService } from '../service/proficiency.service';

@Component({
  selector: 'app-proficiency-details',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './proficiency-details.component.html',
  styleUrl: './proficiency-details.component.css'
})
export class ProficiencyDetailsComponent implements OnInit {
  protected proficiency: Proficiency|undefined;
  constructor(private proficiencyService:ProficiencyService,
    private route: ActivatedRoute, private router:Router){
  }
  ngOnInit(): void {
    const id=Number(this.route.snapshot.params['id']);
    this.proficiencyService.getById(id).subscribe(response=>{
      this.proficiency=response.body??undefined;
    });
  }
  openDialog() {
   if(confirm("Are you sure to delete this once and for all?")) {
     this.delete();
   }
  }
  private delete():void{
    this.proficiencyService.delete(this.proficiency?.id??0);
    this.router.navigateByUrl('/proficiencies');
  }
}
