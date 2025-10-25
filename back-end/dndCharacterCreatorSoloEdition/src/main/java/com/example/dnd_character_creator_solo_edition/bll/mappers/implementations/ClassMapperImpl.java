package com.example.dnd_character_creator_solo_edition.bll.mappers.implementations;

import com.example.dnd_character_creator_solo_edition.bll.dtos.dnd_classes.ClassDTO;
import com.example.dnd_character_creator_solo_edition.bll.mappers.interfaces.ClassFeatureMapper;
import com.example.dnd_character_creator_solo_edition.bll.mappers.interfaces.ClassMapper;
import com.example.dnd_character_creator_solo_edition.bll.mappers.interfaces.ProficiencyMapper;
import com.example.dnd_character_creator_solo_edition.dal.entities.DNDclass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.List;

@Component
public class ClassMapperImpl implements ClassMapper {
    private final ProficiencyMapper proficiencyMapper;
    private final ClassFeatureMapper classFeatureMapper;

    @Autowired
    public ClassMapperImpl(ProficiencyMapper proficiencyMapper, ClassFeatureMapper classFeatureMapper) {
        this.proficiencyMapper = proficiencyMapper;
        this.classFeatureMapper = classFeatureMapper;
    }

    @Override
    public DNDclass fromDto(ClassDTO classDTO) {
        if(classDTO==null)
            return null;
        DNDclass dndClass=new DNDclass();
        classDTO.id().ifPresent(dndClass::setId);
        dndClass.setName(classDTO.name());
        dndClass.setHitDice(classDTO.hitDice());
        dndClass.setDescription(classDTO.description());
        dndClass.setIsDeleted(classDTO.isDeleted());
        dndClass.setProficiencies(
                new LinkedHashSet<>(proficiencyMapper.fromDTOs(classDTO.proficiencies()))
        );
        dndClass.setClassFeatures(classFeatureMapper.fromDTOs(classDTO.features(), dndClass));
        return dndClass;
    }

    @Override
    public ClassDTO toDto(DNDclass dndClass) {
        if(dndClass==null)
            return null;
        return new ClassDTO(
                dndClass.getId().describeConstable(),
                dndClass.getIsDeleted(),
                dndClass.getName(),
                dndClass.getDescription(),
                dndClass.getHitDice(),
                List.copyOf(
                        proficiencyMapper.toDTOs(
                        dndClass.getProficiencies()
                                .stream().toList()
                        )
                ),
                List.copyOf(classFeatureMapper.toDTOs(dndClass.getClassFeatures()))
        );
    }

    @Override
    public List<DNDclass> fromDTOs(List<ClassDTO> classDTOS) {
        return classDTOS.stream()
                .map(this::fromDto)
                .toList();
    }

    @Override
    public List<ClassDTO> toDTOs(List<DNDclass> dndClasses) {
        return dndClasses.stream()
                .map(this::toDto)
                .toList();
    }
}
