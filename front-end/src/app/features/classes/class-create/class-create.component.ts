import { CommonModule } from '@angular/common';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Proficiency } from '../../../shared/interfaces/proficiency';
import { DndClass } from '../../../shared/interfaces/dnd-class';
import { HitDice } from '../../../shared/enums/hit-dice';
import { ProficiencyService } from '../../../shared/services/proficiency-service/proficiency.service';
import { ClassService } from '../../../shared/services/class-service/class.service';
import { Subject, takeUntil } from 'rxjs';
import { ProfType } from '../../../shared/enums/prof-enums/prof-type';
import { ProfSubtype } from '../../../shared/enums/prof-enums/prof-subtype';
import { InputGroupModule } from "primeng/inputgroup";
import { CommonMethods } from '../../../core/misc/common-methods';
import { CardModule } from "primeng/card";
import { InputText } from "primeng/inputtext";
import { SelectModule } from 'primeng/select';
import { PanelModule } from 'primeng/panel';
import { InputGroupAddonModule } from "primeng/inputgroupaddon";

@Component({
  selector: 'app-class-create',
  standalone: true,
  imports: [
    ReactiveFormsModule, CommonModule, InputGroupModule, CardModule,
    InputText, SelectModule, PanelModule,
    InputGroupAddonModule
],
  templateUrl: './class-create.component.html',
  styleUrl: './class-create.component.css'
})
export class ClassCreateComponent implements OnInit, OnDestroy {

  protected disabled:boolean=true;
  protected type:string='';

  protected createForm :FormGroup;
  protected proficiencyForm: FormGroup;
  protected dndClass:DndClass;

  protected typeList:ProfType[]=[];
  protected nameList:string[]=[];
  protected proficiencies:Proficiency[]=[];

  private destroy=new Subject<void>();

  constructor(private classService: ClassService,
    private proficiencyService:ProficiencyService,
    fb :FormBuilder, private router:Router) {
    this.createForm = fb.group({
      name: ['',Validators.required],
      hitDice: [HitDice.D6],
      description: ['',Validators.required]
    });
    this.proficiencyForm = fb.group({
      id: 0,
      name: '',
      type: ProfType.NONE,
      subtype: ProfSubtype.NONE
    });
    this.dndClass={
      name:'',
      hitDice:HitDice.D6,
      description:'',
      proficiencies:[]
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
  
  getHitDices(): string[] {
    return CommonMethods.getHitDices();
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

  getProfTypes(): string[] {
    return CommonMethods.getProfTypes();
  }

  setType():void{
    this.nameList=this.proficiencies.filter(x=>x.type===this.proficiencyForm.controls["type"].value).flatMap(x=>x.name);
    this.nameList=this.removeDuplicates(this.nameList);
  }

  setName():void{
    this.disabled=false;
  }

  addProficiency():void{
    this.proficiencyForm.controls["id"].setValue(this.proficiencies.find(x=>x.name===this.proficiencyForm.controls["name"].value
      && this.proficiencyForm.controls["type"].value===x.type
    )?.id);
    this.dndClass.proficiencies.push({
      id: this.proficiencyForm.controls["id"].value,
      name: this.proficiencyForm.controls["name"].value,
      type: this.proficiencyForm.controls["type"].value,
      subtype: ProfSubtype.NONE
    });
    this.proficiencies=this.proficiencies.filter(x=>!(x.id===this.proficiencyForm.controls["id"].value));
    this.reset();
  }

  remove(proficiency:Proficiency):void{
    this.proficiencies.push(proficiency);
    this.dndClass.proficiencies=this.dndClass.proficiencies.filter(x=>!(x.id===proficiency.id));
    this.reset();
  }

  private reset():void{
    this.proficiencyForm.controls["name"].setValue("");
    this.proficiencyForm.controls["type"].setValue(ProfType.NONE);
    this.disabled=true;
    this.typeList=this.proficiencies.flatMap(x=>x.type);
    this.typeList = this.removeDuplicates(this.typeList);
  }

  ngOnDestroy(): void {
    this.destroy.complete();
  }
}
