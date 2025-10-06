import { Proficiency } from "./proficiency";
import { HitDice } from "../enums/hit-dice";

export interface DndClass {
    id?:number,
    name:string,
    description:string,
    hitDice:HitDice,
    proficiencies:Proficiency[]
}
