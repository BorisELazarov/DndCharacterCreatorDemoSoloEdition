import { CommonModule } from '@angular/common';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { Router } from '@angular/router';
import { Proficiency } from '../../../shared/interfaces/proficiency';
import { DndClass } from '../../../shared/interfaces/dnd-class';
import { HitDice } from '../../../shared/enums/hit-dice';
import { MatSelectModule } from '@angular/material/select';
import { MatCardModule } from '@angular/material/card';
import { MatChipsModule } from '@angular/material/chips';
import { ProficiencyService } from '../../../shared/services/proficiency-service/proficiency.service';
import { MatIconModule } from '@angular/material/icon';
import {MatListModule} from '@angular/material/list'
import { ClassService } from '../../../shared/services/class-service/class.service';
import { Subject, takeUntil } from 'rxjs';

@Component({
  selector: 'app-class-create',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, MatCardModule,
    MatFormFieldModule, MatInputModule, MatSelectModule,
     MatChipsModule, MatIconModule, MatListModule],
  templateUrl: './class-create.component.html',
  styleUrl: './class-create.component.css'
})
export class ClassCreateComponent implements OnInit, OnDestroy {
  private destroy=new Subject<void>();

  protected disabled:boolean=true;
  protected type:string='';

  protected createForm :FormGroup;
  protected dndClass:DndClass;
  protected proficiency:Proficiency;

  protected typeList:string[]=[];
  protected nameList:string[]=[];
  protected proficiencies:Proficiency[]=[];

  constructor(private classService: ClassService,
    private proficiencyService:ProficiencyService,
    fb :FormBuilder, private router:Router) {
    this.createForm = fb.group({
      name: ['',Validators.required],
      hitDice: [HitDice.D6],
      description: ['',Validators.required]
    });
    this.dndClass={
      name:'',
      hitDice:HitDice.D6,
      description:'',
      proficiencies:[]
    }
    this.proficiency={
      name:'',
      type:''
    }
  }

  ngOnInit(): void {
    this.proficiencyService.getAllUnfiltered().pipe(
      takeUntil(this.destroy)
      ).subscribe(response=>{
        this.proficiencies=response.body??[];
        this.typeList=this.proficiencies.flatMap(x=>x.type);
        this.typeList = this.removeDuplicates(this.typeList);
      });
  }

  removeDuplicates(list:any[]):any[]{
    return list.filter((el, i, a) => i === a.indexOf(el));
  }

  submit():void {
    this.dndClass.name=this.createForm.controls['name'].value;
    this.dndClass.hitDice=this.createForm.controls['hitDice'].value;
    this.dndClass.description=this.createForm.controls['description'].value;
    if(this.createForm.valid){
      this.classService.create(this.dndClass).pipe(
        takeUntil(this.destroy)
      ).subscribe();
      this.router.navigateByUrl('/classes');
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
