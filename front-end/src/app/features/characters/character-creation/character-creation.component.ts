import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatStepperModule } from '@angular/material/stepper';
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
import { CommonMethods } from '../../../core/misc/common-methods';
import { Stat } from '../misc/stat';

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

  protected strength:Stat;
  protected dexterity:Stat;
  protected constitution:Stat;
  protected intelligence:Stat;
  protected wisdom:Stat;
  protected charisma:Stat;

  protected statPoints: number = 27;
  protected statRolls: number[] = [];

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
    this.strength = new Stat();
    this.dexterity = new Stat();
    this.constitution = new Stat();
    this.intelligence = new Stat();
    this.wisdom = new Stat();
    this.charisma = new Stat();
  }

  resetStats(): void {
    this.strength.value = 8;
    this.dexterity.value = 8;
    this.constitution.value = 8;
    this.intelligence.value = 8;
    this.wisdom.value = 8;
    this.charisma.value = 8;
  }

  ngOnInit(): void {
    this.resetStats();
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
    this.resetStats();
  }

  rollD6(): number {
    return CommonMethods.random(1,6);
  }

  roll4D6(): number[] {
    const d6s: number[] = [];
    for (let index = 0; index < 4; index++) {
      d6s.push(this.rollD6());
    } 
    return d6s;
  }

  rollStats(): void {
    this.statRolls = [];
    for (let index = 0; index < 6; index++) {
      const d6s = this.roll4D6().sort((a,b) => b-a);
      d6s.pop();
      this.statRolls.push(d6s.reduce((a,b) => a+b));
    }
    this.statRolls.sort();
    this.prepareRandomStat(this.strength);
    this.prepareRandomStat(this.dexterity);
    this.prepareRandomStat(this.constitution);
    this.prepareRandomStat(this.intelligence);
    this.prepareRandomStat(this.wisdom);
    this.prepareRandomStat(this.charisma);
  }

  prepareRandomStat(stat: Stat): void {
    stat.value = 0;
    stat.prevValue = 0;
    stat.options.splice(0);
    this.statRolls.forEach(
      function (item:number) {
        stat.options.push(item);
      }
    );
    this.statRolls.sort();
  }

  prepareRandomRoll(): void {
    if (this.isRoll()) {
      return;
    }
    this.rollStats();
  }

  removeFirstFromArray(array: number[], value: number): void {
    array.splice(array.indexOf(value), 1);
  }

  removeFromAllRollOptions(value: number): void {
    this.removeFirstFromArray(this.strength.options, value);
    this.removeFirstFromArray(this.dexterity.options, value);
    this.removeFirstFromArray(this.constitution.options, value);
    this.removeFirstFromArray(this.intelligence.options, value);
    this.removeFirstFromArray(this.wisdom.options, value);
    this.removeFirstFromArray(this.charisma.options, value);
  }

  addToAllRollOptions(value:number): void {
    this.strength.options.push(value);
    this.dexterity.options.push(value);
    this.constitution.options.push(value);
    this.intelligence.options.push(value);
    this.wisdom.options.push(value);
    this.charisma.options.push(value);
  }

  chooseRoll(stat: Stat): void {
    if (stat.value === stat.prevValue) {
      return;
    }
    if (stat.prevValue > 0) {
      this.addToAllRollOptions(stat.prevValue);
    }
    this.removeFromAllRollOptions(stat.value);
    stat.prevValue = stat.value;
  }

  isRoll(): boolean {
    return this.statsType === StatsSelect.ROLL;
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

  increment(stat: Stat): void {
    if (this.canIncrement(stat.value)) {
     this.statPoints -= this.getPointsCostForIncr(stat.value);
       stat.value++;
    }
  } 

  getPointsCostForDecr(stat: number): number {
    return (stat < 14 ? 1 : 2);
  }

  canDecrement(stat:number): boolean {
    return stat > 8;
  }

  decrement(stat: Stat): void {
    if (this.canDecrement(stat.value)) {
      this.statPoints += this.getPointsCostForDecr(stat.value);
      stat.value--;
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
    this.nameList=CommonMethods.removeDuplicates(this.nameList);
    if (this.nameList.length<1) {
      this.reset();
    }
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
    this.nameList=CommonMethods.removeDuplicates(this.nameList);
  }

  submit():void {
    if(this.createFormGroup.valid){
      let character:Character={
        name:this.createFormGroup.controls['name'].value,
        level:this.createFormGroup.controls['level'].value,
        baseStr:this.strength.value,
        baseDex:this.dexterity.value,
        baseCon:this.constitution.value,
        baseInt:this.intelligence.value,
        baseWis:this.wisdom.value,
        baseCha:this.charisma.value,
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
