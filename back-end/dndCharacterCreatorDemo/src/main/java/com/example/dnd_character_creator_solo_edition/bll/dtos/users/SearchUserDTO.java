package com.example.dnd_character_creator_solo_edition.bll.dtos.users;

import com.example.dnd_character_creator_solo_edition.common.Sort;
import com.example.dnd_character_creator_solo_edition.filters.UserFilter;

/**
 * @author boriselazarov@gmail
 */
public record SearchUserDTO(UserFilter filter, Sort sort) {
}
