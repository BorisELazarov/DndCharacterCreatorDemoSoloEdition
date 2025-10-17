import { CommonModule } from '@angular/common';
import { Component, OnDestroy } from '@angular/core';
import { FormBuilder, FormGroup, NumberValueAccessor, ReactiveFormsModule, Validators } from '@angular/forms';
import { Spell } from '../../../shared/interfaces/spell';
import { Router } from '@angular/router';
import { SpellService } from '../../../shared/services/spell-service/spell.service';
import { Subject, takeUntil } from 'rxjs';
import { InputGroupModule } from "primeng/inputgroup";
import { SelectModule } from 'primeng/select';

@Component({
  selector: 'app-spell-create',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, InputGroupModule, SelectModule],
  templateUrl: './spell-create.component.html',
  styleUrl: './spell-create.component.css'
})
export class SpellCreateComponent implements OnDestroy {
  private destroy= new Subject<void>();

  protected levelOptions: string[] = [
    'Cantrip', '1st lvl', '2nd lvl', '3rd lvl', '4th lvl',
    '5th lvl', '6th lvl', '7th lvl', '8th lvl', '9th lvl'
  ];
  protected selectedLevel: number = 0;
  protected durationsTypes: string[] = [ "Instantenous", "Rounds", "Minutes", "Hours", "Days" ];

  protected createForm :FormGroup;
  constructor(private spellService: SpellService,
    fb :FormBuilder, private router:Router) {
    this.createForm = fb.group({
      name: ['',Validators.required],
      castingRange: ['',Validators.required],
      castingTime: ['',Validators.required],
      components: ['',Validators.required],
      description: ['',Validators.required],
      durationType: ['instant'],
      durationValue: ['0', Validators.required],
      level: ['Cantrip'],
      target: ['',Validators.required]
    });
  }

  setLevel(): void {
    if (this.createForm.controls['level'].value === 'Cantrip') {
      this.selectedLevel = 0;
    } else {
      this.selectedLevel = this.createForm.controls['level'].value[0];
    }
  }

  submit() {
    let name=this.createForm.controls['name'].value;
    let castingRange=this.createForm.controls['castingRange'].value;
    let castingTime=this.createForm.controls['castingTime'].value;
    let components=this.createForm.controls['components'].value;
    let description=this.createForm.controls['description'].value;
    let durationValue:number=this.createForm.controls['durationValue'].value;
    let duration:number;
    let level=this.selectedLevel;
    let target=this.createForm.controls['target'].value;;
    switch (this.createForm.controls['durationType'].value) {
      case "rounds":
        duration=durationValue*6;
        break;

      case "minutes":
          duration=durationValue*60;
          break;
      
      case "hours":
          duration=durationValue*60*60;
          break;
      
      case "days":
        duration=durationValue*24*60*60;
        break;

      default:
        duration=0;
        this.createForm.controls['durationValue'].setValue(0);
        break;
    }
    let spell:Spell={
      name: name,
      castingRange:castingRange,
      castingTime:castingTime,
      components:components,
      description:description,
      duration:duration,
      level:level,
      target:target
    };
    if(this.createForm.valid){
      this.spellService.create(spell).pipe(
        takeUntil(this.destroy)
      ).subscribe();
      this.router.navigateByUrl('/spells');
    }
    else{
      alert('Invalid input!');
    }
  }

  ngOnDestroy(): void {
    this.destroy.complete();
  }
}
