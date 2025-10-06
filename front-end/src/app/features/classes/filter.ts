import { HitDice } from "../../shared/enums/hit-dice";

export interface Filter {
    name: string,
    hitDice?: HitDice
}
