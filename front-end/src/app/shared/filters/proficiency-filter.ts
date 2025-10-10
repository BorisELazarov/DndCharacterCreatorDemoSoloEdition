import { ProfSubtype } from "../enums/prof-enums/prof-subtype";
import { ProfType } from "../enums/prof-enums/prof-type";

export interface ProficiencyFilter {
    name: string,
    type?: ProfType,
    subtype?: ProfSubtype
}
