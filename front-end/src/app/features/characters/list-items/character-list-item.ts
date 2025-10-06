import { DndClassListItem } from "./dnd-class-list-item";

export interface CharacterListItem {
    id:number,
    name:string,
    level:number,
    dndClass:DndClassListItem
}
