import { DndClass } from "../../../shared/interfaces/dnd-class";
import { Spell } from "../../../shared/interfaces/spell";
import { User } from "../../../core/interfaces/user";
import { CharacterProficiency } from "./character-proficiency";


export interface Character {
    id?:number,
    name:string,
    user:User,
    level:number,
    baseStr:number,
    baseDex:number,
    baseCon:number,
    baseInt:number,
    baseWis:number,
    baseCha:number,
    proficiencies:CharacterProficiency[],
    dndClass:DndClass,
    spells:Spell[]
}
