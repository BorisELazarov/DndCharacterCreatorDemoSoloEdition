package com.example.dndcharactercreatordemo.bll.mappers;

import com.example.dndcharactercreatordemo.bll.dtos.dnd_classes.CreateClassDTO;
import com.example.dndcharactercreatordemo.bll.dtos.dnd_classes.ReadClassDTO;
import com.example.dndcharactercreatordemo.bll.dtos.dnd_classes.SaveClassDTO;
import com.example.dndcharactercreatordemo.bll.dtos.proficiencies.CreateProficiencyDTO;
import com.example.dndcharactercreatordemo.bll.dtos.proficiencies.ReadProficiencyDTO;
import com.example.dndcharactercreatordemo.bll.dtos.proficiencies.SaveProficiencyDTO;
import com.example.dndcharactercreatordemo.dal.entities.DNDclass;
import com.example.dndcharactercreatordemo.dal.entities.Proficiency;

import java.util.LinkedHashSet;
import java.util.List;

public class ClassMapper implements IMapper<CreateClassDTO, SaveClassDTO, ReadClassDTO, DNDclass>{
    private final IMapper<CreateProficiencyDTO, SaveProficiencyDTO, ReadProficiencyDTO, Proficiency> proficiencyMapper;
    public ClassMapper(){
        proficiencyMapper=new ProficiencyMapper();
    }
    @Override
    public DNDclass fromCreateDto(CreateClassDTO classDTO) {
        if(classDTO==null)
            return null;
        DNDclass dndClass=new DNDclass();
        dndClass.setName(classDTO.name());
        dndClass.setHitDice(classDTO.hitDice());
        dndClass.setDescription(classDTO.description());
        dndClass.setIsDeleted(classDTO.isDeleted());
        dndClass.setProficiencies(
                new LinkedHashSet<>(proficiencyMapper.fromSaveDTOs(classDTO.proficiencies()))
        );
        return dndClass;
    }

    @Override
    public DNDclass fromSaveDto(SaveClassDTO classDTO) {
        if(classDTO==null)
            return null;
        DNDclass dndClass=new DNDclass();
        if (classDTO.id().isPresent())
            dndClass.setId(classDTO.id().get());
        dndClass.setName(classDTO.name());
        dndClass.setHitDice(classDTO.hitDice());
        dndClass.setDescription(classDTO.description());
        dndClass.setIsDeleted(classDTO.isDeleted());
        dndClass.setProficiencies(
                new LinkedHashSet<>(proficiencyMapper.fromSaveDTOs(classDTO.proficiencies()))
        );
        return dndClass;
    }

    @Override
    public ReadClassDTO toDto(DNDclass dndClass) {
        if(dndClass==null)
            return null;
        return new ReadClassDTO(dndClass.getId(),dndClass.getIsDeleted(),
                dndClass.getName(),
                dndClass.getDescription(),
                dndClass.getHitDice(),
                proficiencyMapper.toDTOs(
                        dndClass.getProficiencies()
                                .stream().toList()
                )
        );
    }

    @Override
    public List<DNDclass> fromSaveDTOs(List<SaveClassDTO> classDTOS) {
        return classDTOS.stream()
                .map(this::fromSaveDto)
                .toList();
    }

    @Override
    public List<DNDclass> fromCreateDTOs(List<CreateClassDTO> classDTOS) {
        return classDTOS.stream()
                .map(this::fromCreateDto)
                .toList();
    }

    @Override
    public List<ReadClassDTO> toDTOs(List<DNDclass> dndClasses) {
        return dndClasses.stream()
                .map(this::toDto)
                .toList();
    }
}
