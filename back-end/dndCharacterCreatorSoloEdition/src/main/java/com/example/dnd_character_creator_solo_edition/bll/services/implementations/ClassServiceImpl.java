package com.example.dnd_character_creator_solo_edition.bll.services.implementations;

import com.example.dnd_character_creator_solo_edition.bll.dtos.dnd_classes.SearchClassDTO;
import com.example.dnd_character_creator_solo_edition.bll.mappers.interfaces.ClassMapper;
import com.example.dnd_character_creator_solo_edition.bll.services.interfaces.ClassService;
import com.example.dnd_character_creator_solo_edition.dal.entities.ProfType;
import com.example.dnd_character_creator_solo_edition.dal.entities.Proficiency;
import com.example.dnd_character_creator_solo_edition.dal.repos.proficiencies.ProfTypeRepo;
import com.example.dnd_character_creator_solo_edition.enums.HitDiceEnum;
import com.example.dnd_character_creator_solo_edition.bll.dtos.dnd_classes.ClassDTO;
import com.example.dnd_character_creator_solo_edition.dal.entities.DNDclass;
import com.example.dnd_character_creator_solo_edition.dal.repos.ClassRepo;
import com.example.dnd_character_creator_solo_edition.dal.repos.proficiencies.ProficiencyRepo;
import com.example.dnd_character_creator_solo_edition.exceptions.customs.NameAlreadyTakenException;
import com.example.dnd_character_creator_solo_edition.exceptions.customs.NotFoundException;
import com.example.dnd_character_creator_solo_edition.exceptions.customs.NotSoftDeletedException;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ClassServiceImpl implements ClassService {
    private static final String NOT_FOUND_MESSAGE = "The class is not found!";
    private static final String NAME_TAKEN_MESSAGE = "There is already class named like that!";
    private final ClassRepo classRepo;
    private final ProficiencyRepo proficiencyRepo;
    private final ClassMapper mapper;
    private final ProfTypeRepo profTypeRepo;
    @PersistenceContext
    private EntityManager em;

    @Autowired
    public ClassServiceImpl(ClassRepo classRepo, ProficiencyRepo proficiencyRepo, ClassMapper mapper, ProfTypeRepo profTypeRepo) {
        this.classRepo = classRepo;
        this.proficiencyRepo = proficiencyRepo;
        this.mapper = mapper;
        this.profTypeRepo = profTypeRepo;
    }

    @PostConstruct
    private void seedData() {
        if (proficiencyRepo.count() > 0 || classRepo.count() > 0)
            return;

        seedProficiencies();
        seedClasses(new HashSet<>(proficiencyRepo.findAll()));
    }

    private void seedClasses(Set<Proficiency> proficiencies) {
        Set<DNDclass> dnDclasses = new LinkedHashSet<>();
        dnDclasses.add(getDNDclass("Fighter", "Fights", HitDiceEnum.D10, proficiencies));
        dnDclasses.add(getDNDclass("Wizard", "Magic", HitDiceEnum.D6, proficiencies));
        dnDclasses.add(getDNDclass("Rogue", "Sneak", HitDiceEnum.D8, proficiencies));
        dnDclasses.add(getDNDclass("Barbarian", "Tank", HitDiceEnum.D12, proficiencies));
        classRepo.saveAll(dnDclasses);
    }

    private DNDclass getDNDclass(String name, String description, HitDiceEnum hitDiceEnum,
                                 Set<Proficiency> proficiencies) {
        DNDclass dnDclass = new DNDclass();
        dnDclass.setName(name);
        dnDclass.setHitDice(hitDiceEnum);
        dnDclass.setProficiencies(proficiencies);
        dnDclass.setDescription(description);
        return dnDclass;
    }

    private ProfType addProfType(String language) {
        ProfType profType = new ProfType();
        profType.setName(language);
        return profTypeRepo.save(profType);
    }

    private void seedProficiencies() {
        if (proficiencyRepo.count() > 0)
            return;
        String language = "Language";
        ProfType type = profTypeRepo.findByName(language).orElseGet(() -> addProfType(language));
        List<Proficiency> proficiencies = new ArrayList<>();
        proficiencies.add(getProficiency("Common", type));
        proficiencies.add(getProficiency("Elven", type));
        proficiencies.add(getProficiency("Dwarvish", type));
        proficiencies.add(getProficiency("Orcish", type));
        proficiencies.add(getProficiency("Celestial", type));
        proficiencies.add(getProficiency("Infernal", type));
        String skill = "Skill";
        type = profTypeRepo.findByName(skill).orElseGet(() -> addProfType(skill));
        proficiencies.add(getProficiency("Athletics", type));
        proficiencies.add(getProficiency("Acrobatics", type));
        proficiencies.add(getProficiency("Arcana", type));
        proficiencies.add(getProficiency("Sleight of hand", type));
        proficiencies.add(getProficiency("Religion", type));
        proficiencies.add(getProficiency("Perception", type));
        proficiencyRepo.saveAll(proficiencies);
    }

    private Proficiency getProficiency(String name, ProfType type) {
        Proficiency proficiency = new Proficiency();
        proficiency.setName(name);
        proficiency.setType(type);
        return proficiency;
    }

    @Override
    public List<ClassDTO> getClassesUnfiltered() {
        return mapper.toDTOs(this.classRepo.findAll(false));
    }

    @Override
    public List<ClassDTO> getClasses(boolean isDeleted,
                                     SearchClassDTO searchClassDTO) {
        CriteriaBuilder cb= em.getCriteriaBuilder();
        CriteriaQuery<DNDclass> criteriaQuery= cb.createQuery(DNDclass.class);
        Root<DNDclass> root= criteriaQuery.from(DNDclass.class);
        HitDiceEnum hitDice=searchClassDTO.filter().hitDice().orElse(HitDiceEnum.NONE);
        criteriaQuery.select(root)
                .where(cb.and(
                        cb.and(
                                cb.equal(root.get("isDeleted"),isDeleted),
                                cb.like(root.get("name"),cb.parameter(String.class,"name"))
                        ),
                        cb.or(
                                cb.equal(root.get("hitDice"),hitDice),
                                cb.isTrue(cb.literal(hitDice==HitDiceEnum.NONE))
                        )
                ));
        String sortBy= searchClassDTO.sort().sortBy();
        if (sortBy.isEmpty()){
            sortBy="id";
        }
        if (searchClassDTO.sort().ascending()){
            criteriaQuery.orderBy(cb.asc(root.get(sortBy)));
        }else {
            criteriaQuery.orderBy(cb.desc(root.get(sortBy)));
        }
        TypedQuery<DNDclass> query = em.createQuery(criteriaQuery);
        query.setParameter("name","%"+searchClassDTO.filter().name()+"%");
        List<DNDclass> dnDclasses=query.getResultList();
        return mapper.toDTOs(dnDclasses);
    }

    @Override
    public ClassDTO addClass(ClassDTO classDTO) {
        if (classRepo.existsByName(classDTO.name())) {
            throw new NameAlreadyTakenException(NAME_TAKEN_MESSAGE);
        }
        DNDclass dndClass = mapper.fromDto(classDTO);
        proficiencyRepo.saveAll(dndClass.getProficiencies());
        return mapper.toDto(classRepo.save(dndClass));
    }

    @Override
    @Transactional
    public ClassDTO updateClass(ClassDTO classDTO) {
        Optional<Long> optionalId = classDTO.id();
        if (optionalId.isEmpty()) {
            throw new NotFoundException(NOT_FOUND_MESSAGE);
        }
        Long id = optionalId.get();
        Optional<DNDclass> dnDclass = classRepo.findById(id);

        if (dnDclass.isPresent()) {
            classRepo.findByName(classDTO.name()).ifPresent(
                    x -> {
                        if (!x.getId().equals(id)) {
                            throw new NameAlreadyTakenException(NAME_TAKEN_MESSAGE);
                        }
                    }
            );
            classRepo.save(mapper.fromDto(classDTO));
        } else {
            throw new NotFoundException(NOT_FOUND_MESSAGE);
        }

        return classDTO;
    }

    @Override
    public void restoreClass(Long id) {
        Optional<DNDclass> optionalDNDclass = classRepo.findById(id);
        if (optionalDNDclass.isPresent()) {
            DNDclass dnDclass = optionalDNDclass.get();
            proficiencyRepo.findByName(dnDclass.getName())
                    .ifPresent(x -> {
                        throw new NameAlreadyTakenException(NAME_TAKEN_MESSAGE);
                    });
            dnDclass.setIsDeleted(false);
            classRepo.save(dnDclass);
        } else {
            throw new NotFoundException(NOT_FOUND_MESSAGE);
        }
    }

    @Override
    public void softDeleteClass(Long id) {
        Optional<DNDclass> optionalClass = classRepo.findById(id);
        if (optionalClass.isPresent()) {

            DNDclass dndClass = optionalClass.get();
            dndClass.setIsDeleted(true);
            classRepo.save(dndClass);
        } else {
            throw new NotFoundException(NOT_FOUND_MESSAGE);
        }
    }

    @Override
    public void hardDeleteClass(Long id) {
        Optional<DNDclass> optionalClass = classRepo.findById(id);
        if (optionalClass.isPresent()) {
            DNDclass foundClass = optionalClass.get();
            if (foundClass.getIsDeleted()) {
                classRepo.delete(foundClass);
            } else {
                throw new NotSoftDeletedException("The class must be soft deleted first!");
            }
        } else {
            throw new NotFoundException(NOT_FOUND_MESSAGE);
        }
    }

    @Override
    public ClassDTO getClassById(Long id) {
        Optional<DNDclass> dndClass = classRepo.findById(id);
        if (dndClass.isPresent()) {
            return mapper.toDto(dndClass.get());
        }
        throw new NotFoundException(NOT_FOUND_MESSAGE);
    }
}
