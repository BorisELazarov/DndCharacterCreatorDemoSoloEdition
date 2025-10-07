import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import {MatStepperModule} from '@angular/material/stepper';
import { MatSelectModule } from '@angular/material/select';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatListModule } from '@angular/material/list';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { Router } from '@angular/router';
import { MatIconModule } from '@angular/material/icon';
import { Subject, takeUntil } from 'rxjs';

import { ClassService } from '../../../shared/services/class-service/class.service';
import { SpellService } from '../../../shared/services/spell-service/spell.service';
import { DndClass } from '../../../shared/interfaces/dnd-class';
import { Spell } from '../../../shared/interfaces/spell';
import { HitDice } from '../../../shared/enums/hit-dice';
import { Proficiency } from '../../../shared/interfaces/proficiency';
import { CharacterService } from '../service/character.service';
import { Character } from '../interfaces/character';
import { CharacterProficiency } from '../interfaces/character-proficiency';
import { StatsSelect } from '../../../shared/enums/stats-select';
import { MiscHolder } from '../../../core/misc/misc-holder';
import { StatType } from '../../../shared/enums/stat-type';

@Component({
  selector: 'app-character-creation',
  standalone: true,
  imports: [MatButtonModule, MatStepperModule,
    MatFormFieldModule, MatInputModule, MatSelectModule,
    FormsModule, CommonModule, ReactiveFormsModule,
    MatCardModule, MatListModule, MatCheckboxModule,
    MatIconModule],
  templateUrl: './character-creation.component.html',
  styleUrl: './character-creation.component.css'
})
export class CharacterCreationComponent implements OnInit, OnDestroy{

  protected disabled: boolean = true;
  protected upDownButtonHolderColor: string = MiscHolder.upDownButtonColor;

  private destroy=new Subject<void>();

  protected strength:number=8;
  protected dexterity:number=8;
  protected constitution:number=8;
  protected intelligence:number=8;
  protected wisdom:number=8;
  protected charisma:number=8;
  protected statPoints: number = 27;

  protected spellLevel:number;
  protected spellName:string='';

  protected weapons:Proficiency[]=[];
  protected armors:Proficiency[]=[];
  protected skills:Proficiency[]=[];
  protected languages:Proficiency[]=[];
  protected tools:Proficiency[]=[];
  protected proficiencies:CharacterProficiency[]=[];
  protected nameList:string[]=[];

  protected statsType: StatsSelect = StatsSelect.CUSTOM;

  selectedClass:DndClass={
    name:'',
    hitDice:HitDice.NONE,
    description:'',
    proficiencies:[]
  };
  classList:DndClass[]=[];
  spellList:Spell[]=[];
  createFormGroup: FormGroup;
  
  constructor (private classService:ClassService,
    private spellService:SpellService, fb:FormBuilder,
    private characterService:CharacterService, private router:Router
  ){
    this.spellLevel=0;
    this.createFormGroup =fb.group({
      spells: [[]],
      name:['',Validators.required],
      level:[1,[Validators.required,Validators.min(1),Validators.max(20)]]
    });
  }

  ngOnInit(): void {
    this.classService.getAllUnfiltered().pipe(
      takeUntil(this.destroy)
    ).subscribe(response=>
    {
      this.classList=response.body??[];
    });
    this.spellService.getAllUnfiltered().pipe(
      takeUntil(this.destroy)
    ).subscribe(response=>
    {
      this.spellList=response.body??[];
    });
  }

  loadDndClass():void{
    this.selectedClass=this.classList.find(x=>x.name===this.selectedClass.name)??{
      name:'',
      hitDice:HitDice.NONE,
      description:'',
      proficiencies:[]
    };
    this.armors=this.selectedClass.proficiencies.filter(x=>x.type==='Armor');
    this.weapons=this.selectedClass.proficiencies.filter(x=>x.type==='Weapon');
    this.skills=this.selectedClass.proficiencies.filter(x=>x.type==='Skill');
    this.languages=this.selectedClass.proficiencies.filter(x=>x.type=='Language');
    this.tools=this.selectedClass.proficiencies.filter(x=>x.type=='Tools');
  }

  preparePointBuy(): void {
    if (this.isPointBuy()) {
      return;
    }
    this.statPoints = 27;
    this.strength = 8;
    this.dexterity = 8;
    this.constitution = 8;
    this.intelligence = 8;
    this.wisdom = 8;
    this.charisma = 8;
  }

  isPointBuy(): boolean {
    return this.statsType === StatsSelect.POINT_BUY;
  }

  isNotCustom(): boolean {
    return this.statsType !== StatsSelect.CUSTOM;
  }

  getPointsCostForIncr(stat: number): number {
    return (stat > 12 ? 2 : 1);
  }

  canIncrement(stat: number): boolean {
    return this.statPoints >= this.getPointsCostForIncr(stat) && stat < 15;
  }

  increment(statType: string): void {
    switch (statType) {
      case StatType.STR:
          if (this.canIncrement(this.strength)) {
            this.statPoints -= this.getPointsCostForIncr(this.strength);
            this.strength++;
          }
        break;
        
      case StatType.DEX:
          if (this.canIncrement(this.dexterity)) {
            this.statPoints -= this.getPointsCostForIncr(this.dexterity);
            this.dexterity++;
          }
        break;
        
      case StatType.CON:
          if (this.canIncrement(this.constitution)) {
            this.statPoints -= this.getPointsCostForIncr(this.constitution);
            this.constitution++;
          }
        break;
        
      case StatType.INT:
          if (this.canIncrement(this.intelligence)) {
            this.statPoints -= this.getPointsCostForIncr(this.intelligence);
            this.intelligence++;
          }
        break;
        
      case StatType.WIS:
          if (this.canIncrement(this.wisdom)) {
            this.statPoints -= this.getPointsCostForIncr(this.wisdom);
            this.wisdom++;
          }
        break;

      case StatType.CHA:
          if (this.canIncrement(this.charisma)) {
            this.statPoints -= this.getPointsCostForIncr(this.charisma);
            this.charisma++;
          }
        break;
    
      default:
        break;
    }
  } 

  getPointsCostForDecr(stat: number): number {
    return (stat < 14 ? 1 : 2);
  }

  canDecrement(stat:number): boolean {
    return stat > 8;
  }

  decrement(statType: string): void {
    switch (statType) {
      case StatType.STR:
          if (this.canDecrement(this.strength)) {
            this.statPoints += this.getPointsCostForDecr(this.strength);
            this.strength--;
          }
        break;
        
      case StatType.DEX:
          if (this.canDecrement(this.dexterity)) {
            this.statPoints += this.getPointsCostForDecr(this.dexterity);
            this.dexterity--;
          }
        break;
        
      case StatType.CON:
          if (this.canDecrement(this.constitution)) {
            this.statPoints += this.getPointsCostForDecr(this.constitution);
            this.constitution--;
          }
        break;
        
      case StatType.INT:
          if (this.canDecrement(this.intelligence)) {
            this.statPoints += this.getPointsCostForDecr(this.intelligence);
            this.intelligence--;
          }
        break;
        
      case StatType.WIS:
          if (this.canDecrement(this.wisdom)) {
            this.statPoints += this.getPointsCostForDecr(this.wisdom);
            this.wisdom--;
          }
        break;

      case StatType.CHA:
          if (this.canDecrement(this.charisma)) {
            this.statPoints += this.getPointsCostForDecr(this.charisma);
            this.charisma--;
          }
        break;
    
      default:
        break;
    }
  }

  addProf(proficiency:Proficiency):void{
    if(this.proficiencies.filter(x=>x.proficiency.id===proficiency.id).length===0){
      this.proficiencies.push(
        {
          proficiency:proficiency,
          expertise:false
        }
      );
    }
    else{
      this.proficiencies=this.proficiencies.filter(x=>!(x.proficiency.id===proficiency.id));
    }
  }

  addExpertise(proficiency:Proficiency):void{
    let characterProficiency:CharacterProficiency|undefined=this.proficiencies.find(x=>x.proficiency===proficiency);
    if(!(characterProficiency===undefined)){
      let index=this.proficiencies.indexOf(characterProficiency);
      characterProficiency.expertise=true;
      this.proficiencies.splice(index,1,characterProficiency);
    }
  }

  
  checkIfSelected(proficiency:Proficiency):boolean {
    if(this.proficiencies.filter(x=>x.proficiency===proficiency).length===0)
    {
      return false;
    }
    return true;
  }

  setLevel():void{
    let spells:Spell[]=this.spellList.filter(x=>x.level===this.spellLevel);
    this.nameList=spells.flatMap(x=>x.name);
    this.nameList=this.removeDuplicates(this.nameList);
    if (this.nameList.length<1) {
      this.reset();
    }
  }

  removeDuplicates(list:any[]):any[]{
    return list.filter((el, i, a) => i === a.indexOf(el));
  }

  setName():void{
    this.disabled=false;
  }

  addSpell():void{
    let spell=this.spellList.find(x=>x.name===this.spellName
      && this.spellLevel.toString()===x.level.toString()
    );
    let spells=this.createFormGroup.controls['spells'].value;
    spells.push(spell);
    this.createFormGroup.controls['spells'].setValue(spells);
    this.spellList=this.spellList.filter(x=>!(x.id===spell?.id));
    this.reset();
  }

  remove(spell:Spell):void{
    this.spellList.push(spell);
    let spells:Spell[]=this.createFormGroup.controls['spells'].value;
    spells=spells.filter(x=>!(x.id===spell.id));
    this.createFormGroup.controls['spells'].setValue(spells);
    this.reset();
  }

  private reset():void{
    this.spellLevel=0;
    this.spellName='';
    this.disabled=true;
    let spells:Spell[]=this.spellList.filter(x=>x.level.toString()===this.spellLevel.toString());

    this.nameList=spells.flatMap(x=>x.name);
    this.nameList=this.removeDuplicates(this.nameList);
  }

  submit():void {
    if(this.createFormGroup.valid){
      let character:Character={
        name:this.createFormGroup.controls['name'].value,
        level:this.createFormGroup.controls['level'].value,
        baseStr:this.strength,
        baseDex:this.dexterity,
        baseCon:this.constitution,
        baseInt:this.intelligence,
        baseWis:this.wisdom,
        baseCha:this.charisma,
        dndClass:this.selectedClass,
        proficiencies:this.proficiencies,
        spells:this.createFormGroup.controls['spells'].value
      }
      this.characterService.create(character).pipe(
        takeUntil(this.destroy)
      ).subscribe(
        response=>{
          this.router.navigateByUrl('characters/sheet/'+response.id);
        }
      );
    }
  }

  ngOnDestroy(): void {
    this.destroy.complete();
  }
}
