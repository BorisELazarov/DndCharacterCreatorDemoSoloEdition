import { CommonModule } from '@angular/common';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatChipsModule } from '@angular/material/chips';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatListModule } from '@angular/material/list';
import { MatSelectModule } from '@angular/material/select';
import { ActivatedRoute, Router } from '@angular/router';
import { DndClass } from '../../../shared/interfaces/dnd-class';
import { Proficiency } from '../../../shared/interfaces/proficiency';
import { ProficiencyService } from '../../../shared/services/proficiency-service/proficiency.service';
import { HitDice } from '../../../shared/enums/hit-dice';
import { ClassService } from '../../../shared/services/class-service/class.service';
import { Subject, takeUntil } from 'rxjs';

@Component({
  selector: 'app-class-edit',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, MatCardModule,
    MatFormFieldModule, MatInputModule, MatSelectModule,
     MatChipsModule, MatIconModule, MatListModule],
  templateUrl: './class-edit.component.html',
  styleUrl: './class-edit.component.css'
})
export class ClassEditComponent  implements OnInit, OnDestroy {
  private destroy= new Subject<void>();

  protected disabled:boolean=true;
  protected type:string='';

  protected editForm :FormGroup;
  protected dndClass:DndClass;
  protected proficiency:Proficiency;

  protected typeList:string[]=[];
  protected nameList:string[]=[];
  protected proficiencies:Proficiency[]=[];

  constructor(private classService: ClassService,
    private proficiencyService:ProficiencyService,
    fb :FormBuilder, private router:Router,
    route:ActivatedRoute) {
    let id=Number(route.snapshot.params['id']);
    this.editForm = fb.group({
      id: [id],
      name: ['',Validators.required],
      hitDice: [HitDice.D6],
      description: ['',Validators.required]
    });
    this.dndClass={
      id:id,
      name:'',
      hitDice:HitDice.D6,
      description:'',
      proficiencies:[]
    }
    this.proficiency={
      id:id,
      name:'',
      type:''
    }
  }

  ngOnInit(): void {
    this.classService.getById(this.editForm.controls['id'].value).pipe(
      takeUntil(this.destroy)
    ).subscribe(response=>{
        this.dndClass=response.body??this.dndClass;
        this.editForm.controls['name'].setValue(this.dndClass.name);
        this.editForm.controls['hitDice'].setValue(this.dndClass.hitDice);
        this.editForm.controls['description'].setValue(this.dndClass.description);
    });

    this.proficiencyService.getAllUnfiltered().pipe(
      takeUntil(this.destroy)
    ).subscribe(response=>{
        this.proficiencies=(response.body??[])
          .filter(el => 
            !this.dndClass.proficiencies.find(element => {
               return element.id === el.id;
            })
          );
        this.typeList = this.proficiencies.flatMap(x=>x.type);
        this.typeList = this.removeDuplicates(this.typeList);
    });
  }

  removeDuplicates(list:any[]):any[]{
    return list.filter((el, i, a) => i === a.indexOf(el));
  }

  submit():void {
    this.dndClass.id=this.editForm.controls['id'].value;
    this.dndClass.name=this.editForm.controls['name'].value;
    this.dndClass.hitDice=this.editForm.controls['hitDice'].value;
    this.dndClass.description=this.editForm.controls['description'].value;
    if(this.editForm.valid){
      this.classService.edit(this.dndClass).pipe(
        takeUntil(this.destroy)
      ).subscribe();
      this.router.navigateByUrl('/classes/'+this.dndClass.id);
    }
    else{
      alert('Invalid input!');
    }
  }

  setType():void{
    this.nameList=this.proficiencies.filter(x=>x.type===this.proficiency.type).flatMap(x=>x.name);
    this.nameList=this.removeDuplicates(this.nameList);
  }

  setName():void{
    this.disabled=false;
  }

  addProficiency():void{
    this.proficiency.id=this.proficiencies.find(x=>x.name===this.proficiency.name
      && this.proficiency.type===x.type
    )?.id;
    this.dndClass.proficiencies.push(this.proficiency);
    this.proficiencies=this.proficiencies.filter(x=>!(x.id===this.proficiency.id));
    this.reset();
  }

  remove(proficiency:Proficiency):void{
    this.proficiencies.push(proficiency);
    this.dndClass.proficiencies=this.dndClass.proficiencies.filter(x=>!(x.id===proficiency.id));
    this.reset();
  }

  private reset():void{
    this.proficiency={
      name:'',
      type:''
    }
    this.disabled=true;
    this.typeList=this.proficiencies.flatMap(x=>x.type);
    this.typeList = this.removeDuplicates(this.typeList);
  }

  ngOnDestroy(): void {
    this.destroy.complete();
  }
}