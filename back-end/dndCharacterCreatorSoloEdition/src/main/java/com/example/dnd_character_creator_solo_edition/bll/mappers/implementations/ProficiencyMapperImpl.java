package com.example.dnd_character_creator_solo_edition.bll.mappers.implementations;
import com.example.dnd_character_creator_solo_edition.bll.dtos.proficiencies.ProficiencyDTO;
import com.example.dnd_character_creator_solo_edition.bll.mappers.interfaces.ProficiencyMapper;
import com.example.dnd_character_creator_solo_edition.dal.entities.ProfType;
import com.example.dnd_character_creator_solo_edition.dal.entities.Proficiency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProficiencyMapperImpl implements ProficiencyMapper {

    @Override
    public Proficiency fromDto(ProficiencyDTO proficiencyDTO, ProfType type) {
        if(proficiencyDTO==null)
            return null;
        Proficiency proficiency=new Proficiency();
        proficiencyDTO.id().ifPresent(proficiency::setId);
        proficiency.setIsDeleted(proficiencyDTO.isDeleted());
        proficiency.setName(proficiencyDTO.name());
        proficiency.setType(type);
        return proficiency;
    }

    @Override
    public ProficiencyDTO toDto(Proficiency proficiency) {
        if(proficiency==null)
            return null;
        return new ProficiencyDTO(proficiency.getId().describeConstable(),
                proficiency.getIsDeleted(), proficiency.getName(),
                proficiency.getType().getName());
    }

    @Override
    public List<ProficiencyDTO> toDTOs(List<Proficiency> proficiencies){
        return  proficiencies.stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public List<Proficiency> fromDTOs(List<ProficiencyDTO> proficiencyDTOS){
        return  proficiencyDTOS.stream()
                .map(dto -> this.fromDto(dto, null))
                .toList();
    }
}
