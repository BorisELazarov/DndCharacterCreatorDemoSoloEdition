package com.example.dndcharactercreatordemo.bll.services;

import com.example.dndcharactercreatordemo.bll.dtos.dnd_classes.CreateClassDTO;
import com.example.dndcharactercreatordemo.bll.dtos.dnd_classes.ReadClassDTO;
import com.example.dndcharactercreatordemo.enums.HitDiceEnum;
import com.example.dndcharactercreatordemo.bll.dtos.dnd_classes.SaveClassDTO;
import com.example.dndcharactercreatordemo.bll.mappers.ClassMapper;
import com.example.dndcharactercreatordemo.bll.mappers.IMapper;
import com.example.dndcharactercreatordemo.dal.entities.DNDclass;
import com.example.dndcharactercreatordemo.dal.repos.ClassRepo;
import com.example.dndcharactercreatordemo.dal.repos.ProficiencyRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClassService {
    private final ClassRepo classRepo;
    private final ProficiencyRepo proficiencyRepo;
    private final IMapper<CreateClassDTO, SaveClassDTO, ReadClassDTO, DNDclass> mapper;

    @Autowired
    public ClassService(ClassRepo classRepo,ProficiencyRepo proficiencyRepo) {
        this.classRepo = classRepo;
        this.proficiencyRepo=proficiencyRepo;
        mapper=new ClassMapper();
    }

    public List<ReadClassDTO> getClasses() {
        return mapper.toDTOs(classRepo.findAll());
    }

    public void addClass(CreateClassDTO classDTO) {
        if (classRepo.existsByName(classDTO.name())) {
            throw new IllegalArgumentException("Error: there is already dndClass with such name");
        }
        DNDclass dndClass=mapper.fromCreateDto(classDTO);
        proficiencyRepo.saveAll(dndClass.getProficiencies());
        classRepo.save(dndClass);
    }

    @Transactional
    public void updateClass(Long id, String username, String description, HitDiceEnum hitDice) {
        Optional<DNDclass> dndClass= classRepo.findById(id);
        if (dndClass.isEmpty()) {
            dndClassNotFound();
        }
        DNDclass foundDNDClass=dndClass.get();
        if (username!=null &&
                username.length()>0 &&
                !username.equals(foundDNDClass.getName())) {
            Optional<DNDclass> userByUsername= classRepo.findAll()
                    .stream()
                    .filter(x->x.getName().equals(username))
                    .findFirst();
            if(userByUsername.isPresent())
            {
                throw new IllegalArgumentException("Error: there is already dndClass with such name");
            }
            foundDNDClass.setName(username);
        }
        if (description!=null &&
                description.length()>0 &&
                !description.equals(foundDNDClass.getDescription())) {
            foundDNDClass.setDescription(description);
        }
        if (hitDice!=null && !hitDice.equals(foundDNDClass.getHitDice()))
        {
            foundDNDClass.setHitDice(hitDice);
        }
        classRepo.save(foundDNDClass);
    }

    public void deleteClass(Long id) {
        Optional<DNDclass> optionalClass=classRepo.findById(id);
        if (optionalClass.isPresent()){

            DNDclass dndClass= optionalClass.get();
            dndClass.setIsDeleted(true);
            classRepo.save(dndClass);
        }
        else {

            dndClassNotFound();
        }
    }

    private void dndClassNotFound(){
        throw new IllegalArgumentException("DND class not found!");
    }

    public ReadClassDTO getClass(Long id) {
        Optional<DNDclass> dndClass= classRepo.findById(id);
        if (dndClass.isEmpty()) {
            dndClassNotFound();
        }
        return mapper.toDto(dndClass.get());
    }
}
