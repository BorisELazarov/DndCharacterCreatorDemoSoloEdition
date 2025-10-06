package com.example.dnd_character_creator_solo_edition.bll.dtos.dnd_classes;

import com.example.dnd_character_creator_solo_edition.common.Sort;
import com.example.dnd_character_creator_solo_edition.filters.DndClassFilter;

/**
 * @author boriselazarov@gmail
 */
public record SearchClassDTO(DndClassFilter filter, Sort sort){
}
