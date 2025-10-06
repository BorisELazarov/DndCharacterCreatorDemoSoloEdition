package com.example.dndcharactercreatordemo.bll.mappers;

import com.example.dndcharactercreatordemo.bll.dtos.proficiencies.CreateProficiencyDTO;
import com.example.dndcharactercreatordemo.bll.dtos.proficiencies.ReadProficiencyDTO;
import com.example.dndcharactercreatordemo.bll.dtos.proficiencies.SaveProficiencyDTO;
import com.example.dndcharactercreatordemo.dal.entities.Proficiency;

import java.util.List;

public class ProficiencyMapper implements IMapper<CreateProficiencyDTO, SaveProficiencyDTO, ReadProficiencyDTO, Proficiency>{
    @Override
    public Proficiency fromCreateDto(CreateProficiencyDTO proficiencyDTO) {
        if(proficiencyDTO==null)
            return null;
        Proficiency proficiency=new Proficiency();
        proficiency.setIsDeleted(proficiencyDTO.isDeleted());
        proficiency.setName(proficiencyDTO.name());
        proficiency.setType(proficiencyDTO.type());
        return proficiency;
    }

    @Override
    public Proficiency fromSaveDto(SaveProficiencyDTO proficiencyDTO) {
        if(proficiencyDTO==null)
            return null;
        Proficiency proficiency=new Proficiency();
        if (proficiencyDTO.id().isPresent())
            proficiency.setId(proficiencyDTO.id().get());
        proficiency.setIsDeleted(proficiencyDTO.isDeleted());
        proficiency.setName(proficiencyDTO.name());
        proficiency.setType(proficiencyDTO.type());
        return proficiency;
    }

    @Override
    public ReadProficiencyDTO toDto(Proficiency proficiency) {
        if(proficiency==null)
            return null;
        return new ReadProficiencyDTO(proficiency.getId(),
                proficiency.getIsDeleted(), proficiency.getName(),
                proficiency.getType());
    }

    @Override
    public List<ReadProficiencyDTO> toDTOs(List<Proficiency> proficiencies){
        return  proficiencies.stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public List<Proficiency> fromSaveDTOs(List<SaveProficiencyDTO> proficiencyDTOS){
        return  proficiencyDTOS.stream()
                .map(this::fromSaveDto)
                .toList();
    }

    @Override
    public List<Proficiency> fromCreateDTOs(List<CreateProficiencyDTO> proficiencyDTOS) {
        return  proficiencyDTOS.stream()
                .map(this::fromCreateDto)
                .toList();
    }
}
