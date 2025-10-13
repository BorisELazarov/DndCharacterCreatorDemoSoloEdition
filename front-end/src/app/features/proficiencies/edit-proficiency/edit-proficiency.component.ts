import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ProficiencyService } from '../../../shared/services/proficiency-service/proficiency.service';
import { Proficiency } from '../../../shared/interfaces/proficiency';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router} from '@angular/router';
import { Subject, takeUntil } from 'rxjs';
import { IftaLabelModule } from "primeng/iftalabel";
import { ProfSubtype } from '../../../shared/enums/prof-enums/prof-subtype';
import { ProfType } from '../../../shared/enums/prof-enums/prof-type';
import { Select } from 'primeng/select';
import { InputGroupModule } from "primeng/inputgroup";

@Component({
  selector: 'app-edit-proficiency',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, IftaLabelModule, Select, InputGroupModule],
  templateUrl: './edit-proficiency.component.html',
  styleUrl: './edit-proficiency.component.css'
})
export class EditProficiencyComponent implements OnInit, OnDestroy {
  private destroy=new Subject<void>();
  protected editForm :FormGroup;
  protected proficiency:Proficiency|undefined;
  constructor(private proficiencyService: ProficiencyService,
    fb: FormBuilder, route: ActivatedRoute, private router:Router) {
    let id=Number(route.snapshot.params['id']);
    this.editForm = fb.group({
      id: [id],
      name: ['',Validators.required],
      type: ['',Validators.required],
      subtype: ['',Validators.required]
    });
    
  }

  ngOnInit(): void {
    this.proficiencyService.getById(this.editForm.controls['id'].value).pipe(
      takeUntil(this.destroy)
    ).subscribe(response=>{
      this.proficiency=response.body??undefined;
      this.editForm.controls['name'].setValue(this.proficiency?.name);
      this.editForm.controls['type'].setValue(this.proficiency?.type);
      this.editForm.controls['subtype'].setValue(this.proficiency?.subtype);
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
    let id=this.editForm.controls['id'].value;
    let name=this.editForm.controls['name'].value;
    let type=this.editForm.controls['type'].value;
    let subtype=this.editForm.controls['subtype'].value;
    let proficiency:Proficiency={
      id: id,
      name: name,
      type: type,
      subtype: subtype
    };
    if(this.editForm.valid){
      this.proficiencyService.edit(proficiency).pipe(
        takeUntil(this.destroy)
      ).subscribe();
      this.router.navigateByUrl('/proficiencies/'+id);
    }
    else{
      alert('Invalid input!');
    }
  }

  ngOnDestroy(): void {
    this.destroy.complete();
  }
}
