import { HitDice } from "../../shared/enums/hit-dice";
import { ProfType } from "../../shared/enums/prof-enums/prof-type";

export class CommonMethods {
    static random(min: number, max:number): number {
        return 1+Math.floor(Math.random()*(max-min));
    }
    static getProfTypes(): string[] {
        return Object.values(ProfType).map(proftype => proftype.toString()).splice(0, Object.values(ProfType).length/2);
    }
    static getHitDices(): string[] {
        return Object.values(HitDice).map(hitDice => hitDice.toString());
    }
}