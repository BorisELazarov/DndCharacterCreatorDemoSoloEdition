import { Component, OnDestroy, OnInit, signal, ViewChild } from '@angular/core';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatButtonModule } from '@angular/material/button';
import { Character } from '../interfaces/character';
import { CharacterService } from '../service/character.service';
import { ActivatedRoute, Router } from '@angular/router';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule, MatLabel } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { HitDice } from '../../../shared/enums/hit-dice';
import { Proficiency } from '../../../shared/interfaces/proficiency';
import { MatListModule } from '@angular/material/list';
import { FormsModule } from '@angular/forms';
import { ProficiencyService } from '../../../shared/services/proficiency-service/proficiency.service';
import { ProficiencyFilter } from '../../../shared/filters/proficiency-filter';
import { Sort } from '../../../core/sort';
import { ProficiencyDegree } from './proficiency-degree';
import { CharacterProficiency } from '../interfaces/character-proficiency';
import { MatSelectModule } from '@angular/material/select';
import { MatTabsModule } from '@angular/material/tabs';
import { MatExpansionModule } from '@angular/material/expansion';
import { Spell } from '../../../shared/interfaces/spell';
import { SpellService } from '../../../shared/services/spell-service/spell.service';
import { NgTemplateOutlet } from '@angular/common';
import { Subject, takeUntil } from 'rxjs';

@Component({
  selector: 'app-character-sheet',
  standalone: true,
  imports: [MatGridListModule, MatButtonModule, MatIconModule,
    MatCardModule, MatLabel, MatInputModule, MatListModule,
    FormsModule, MatFormFieldModule, MatSelectModule, MatTabsModule,
    MatExpansionModule, NgTemplateOutlet],
  templateUrl: './character-sheet.component.html',
  styleUrl: './character-sheet.component.css'
})
export class CharacterSheetComponent implements OnInit, OnDestroy{
  protected showBtn:boolean=true;

  private destroy=new Subject<void>();

  protected spellLevel:number=0;
  protected spellName:string=''

  protected tabLevel:number=0;

  protected character:Character|null;
  protected maxHealth:number=0;
  
  protected nameList:string[]=[];

  protected weapons:Proficiency[]=[];
  protected armors:Proficiency[]=[];
  protected skills:Proficiency[]=[];
  protected languages:Proficiency[]=[];
  protected tools:Proficiency[]=[];
  protected spellList:Spell[]=[];

  constructor(private characterService:CharacterService,
    private route:ActivatedRoute, private router:Router,
    private proficiencyService:ProficiencyService,
    private spellService:SpellService
  ) {
    this.character=null;
  }
  ngOnInit(): void {
    this.characterService.getById(Number(this.route.snapshot.params['id'])).pipe(
      takeUntil(this.destroy)
    ).subscribe(
      response=>{
        this.character=response.body!;
        this.setMaxHealth();
        this.armors=this.character.dndClass.proficiencies.filter(x=>x.type==='Armor');
        this.weapons=this.character.dndClass.proficiencies.filter(x=>x.type==='Weapon');
        this.languages=this.character.dndClass.proficiencies.filter(x=>x.type=='Language');
        this.tools=this.character.dndClass.proficiencies.filter(x=>x.type=='Tools');
      }
    );
    let filter:ProficiencyFilter={
      name:'',
      type:'Skill'
    };
    let sort:Sort={
      sortBy:'name',
      ascending:true
    };
    this.proficiencyService.getAll(sort,filter).pipe(
      takeUntil(this.destroy)
    ).subscribe(
      response=>{
        this.skills=response.body??[];
      }
    )
    this.spellService.getAllUnfiltered().pipe(
      takeUntil(this.destroy)
    ).subscribe(
      response=>{
        this.spellList=response.body??[];
        let idList:(number|undefined)[]=this.character!.spells.flatMap(x=>x.id)??[];
        this.spellList=this.spellList.filter(
          x=>{
            if(x.id===undefined){
              return false;
            }
            return !idList.includes(x.id);
          }
        );
      }
    );
  
  }
  

  integerDelete(x:number,y:number):number{
    return (x)/y -
            (x)%y/y;
  }

  setMaxHealth():void{
    let bonusHealth:number=this.character!.level*this.integerDelete((this.character!.baseCon-10),2);;
    switch (this.character?.dndClass.hitDice) {
      case HitDice.D6:
        this.maxHealth=6+(this.character.level-1)*4;
        break;
        
      case HitDice.D8:
        this.maxHealth=8+(this.character.level-1)*5;
      break;
      
      case HitDice.D10:
        this.maxHealth=10+(this.character.level-1)*6;
        break;

      case HitDice.D12:
        this.maxHealth=12+(this.character.level-1)*7;
        break;
    
      default:
        break;
    }
    this.maxHealth+=bonusHealth;
  }

  levelUp():void{
    let level:number|undefined=this.character?.level??0;
    if (level>0 && level<20) {
      this.character!.level=level+1;
    }
    this.setMaxHealth();
  }

  getProfDegree(proficiency:Proficiency):ProficiencyDegree{
    let skill:CharacterProficiency|undefined
      =this.character?.proficiencies
      .find(x=>x.proficiency.id===proficiency.id);
    if(skill===undefined){
     return ProficiencyDegree.NONE;
    }
    else{
      if (skill.expertise) {
        return ProficiencyDegree.EXPERT;
      } else {
        return ProficiencyDegree.PROFICIENT;
      }
    }
  }

  setProfDegree(proficiency:Proficiency, degree:ProficiencyDegree):void{
    let skill:CharacterProficiency|undefined
      =this.character?.proficiencies
      .find(x=>x.proficiency.id===proficiency.id);
      if (skill===undefined){
        let characterProficiency:CharacterProficiency={
          proficiency:proficiency,
          expertise:false
        };
        if (degree===ProficiencyDegree.EXPERT) {
          characterProficiency.expertise=true;
        }
        this.character?.proficiencies.push(characterProficiency);
      }else {
        let index:number|undefined=this.character?.proficiencies.indexOf(skill!);
        switch (degree) {
          case ProficiencyDegree.NONE:
            this.character?.proficiencies.splice(index!,1);
            return;

          case ProficiencyDegree.PROFICIENT:
                skill.expertise=false;
              break;

          case ProficiencyDegree.EXPERT:
              skill.expertise=false;
            break;
        
          default:
            break;
        }
        this.character?.proficiencies.splice(index!,1,skill);
      }
  }

  getCharacterSpells():Spell[]{
    return this.character?.spells.filter(x=>x.level===this.tabLevel)??[];
  }

  getSpells(level:number):Spell[]{
    return this.character?.spells.filter(x=>x.level===level)??[];
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
    this.showBtn=false;
  }

  addSpell():void{
    let spell=this.spellList.find(x=>x.name===this.spellName
      && this.spellLevel.toString()===x.level.toString()
    );
    if(!(spell===undefined)){
    this.character!.spells.push(spell);
    this.spellList=this.spellList.filter(x=>!(x.id===spell?.id));
    }
    this.reset();
  }

  remove(spell:Spell):void{
    this.spellList.push(spell);
    this.character!.spells=this.character!.spells.filter(x=>!(x.id===spell.id));
    this.reset();
  }

  private reset():void{
    this.spellLevel=0;
    this.spellName='';
    this.showBtn=true;
    let spells:Spell[]=this.spellList.filter(x=>x.level.toString()===this.spellLevel.toString());

    this.nameList=spells.flatMap(x=>x.name);
    this.nameList=this.removeDuplicates(this.nameList);
  }

  back():void{
    this.router.navigateByUrl('/characters');
  }

  save():void{
      if (this.character===null) {
        alert('Character does not exist anymore!');
      } else {
        this.characterService.edit(this.character).pipe(
          takeUntil(this.destroy)
        ).subscribe();
        this.back();
      }
  }

  ngOnDestroy(): void {
    this.destroy.complete();
  }
}

