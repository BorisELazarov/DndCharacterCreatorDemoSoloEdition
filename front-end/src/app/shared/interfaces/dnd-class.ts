import { Proficiency } from "./proficiency";
import { HitDice } from "../../features/classes/hit-dice";

export interface DndClass {
    id?:number,
    name:string,
    description:string,
    hitDice:HitDice,
    proficiencies:Proficiency[]
}
