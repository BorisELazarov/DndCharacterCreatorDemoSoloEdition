import { Component, OnDestroy } from '@angular/core';
import { ProficiencyService } from '../../../shared/services/proficiency-service/proficiency.service';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Proficiency } from '../../../shared/interfaces/proficiency';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { Subject, takeUntil } from 'rxjs';
import { IftaLabelModule } from "primeng/iftalabel";
import { ProfType } from '../../../shared/enums/prof-enums/prof-type';
import { Select } from 'primeng/select';
import { ProfSubtype } from '../../../shared/enums/prof-enums/prof-subtype';

@Component({
  selector: 'app-create-proficiency',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, IftaLabelModule, Select],
  templateUrl: './create-proficiency.component.html',
  styleUrl: './create-proficiency.component.css',
})
export class CreateProficiencyComponent implements OnDestroy{
  private destroy=new Subject<void>();
  protected createForm:FormGroup;

  constructor(private proficiencyService: ProficiencyService,
    fb :FormBuilder, private router:Router) {
    this.createForm = fb.group({
      name: ['',Validators.required],
      type: ['',Validators.required],
      subtype: ['', Validators.required]
    });
  }

  getTypes(): string[] {
    return Object.values(ProfType)
      .filter(profType => profType !== ProfType.NONE && profType !== "NONE")
      .map(proftype => proftype.toString())
      .splice(0, Object.values(ProfType).length/2 - 1);
  }

  getSubtypes(): string[] {
    return Object.values(ProfSubtype)
      .map(proftype => proftype.toString())
      .splice(0, Object.values(ProfSubtype).length/2);
  }
  
  submit() {
    let name=this.createForm.controls['name'].value;
    let type=this.createForm.controls['type'].value;
    let subtype=this.createForm.controls['subtype'].value;
    let proficiency:Proficiency={
      name: name,
      type: type,
      subtype: subtype
    };
    if(this.createForm.valid){
      this.proficiencyService.create(proficiency).pipe(
        takeUntil(this.destroy)
      ).subscribe();
      this.router.navigateByUrl('/proficiencies');
    }
    else{
      alert('Invalid input!');
    }
  }

  ngOnDestroy(): void {
    this.destroy.complete();
  }
}
