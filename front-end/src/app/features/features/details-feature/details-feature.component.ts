import { Component, OnDestroy, ViewChild } from '@angular/core';
import { MatPaginator, MatPaginatorModule } from "@angular/material/paginator";
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { Subject, takeUntil } from 'rxjs';
import { FeatureService } from '../../../shared/services/feature-service/feature.service';
import { Feature } from '../../../shared/interfaces/feature';

@Component({
  selector: 'app-details-feature',
  standalone: true,
  imports: [MatPaginatorModule, RouterLink],
  templateUrl: './details-feature.component.html',
  styleUrl: './details-feature.component.css'
})
export class DetailsFeatureComponent  implements OnDestroy{
  private destroy=new Subject<void>();

  protected feature: Feature | undefined;
  
  constructor(private service:FeatureService,
    private route: ActivatedRoute, private router:Router){
  }
  ngOnInit(): void {
    const id=Number(this.route.snapshot.params['id']);
    this.service.getById(id).pipe(
      takeUntil(this.destroy)
    ).subscribe(response=>{
      this.feature=response.body??undefined;
    });
  }
  openDialog() {
   if(confirm("Are you sure to delete?")) {
     this.delete();
   }
  }
  private delete():void{
    this.service.delete(this.feature?.id??0).pipe(
      takeUntil(this.destroy)
    ).subscribe();
    this.router.navigateByUrl('/classes');
  }

  ngOnDestroy(): void {
    this.destroy.complete();
  }
}
