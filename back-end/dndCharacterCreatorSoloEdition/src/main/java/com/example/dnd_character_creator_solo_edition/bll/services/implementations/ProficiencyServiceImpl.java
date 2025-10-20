package com.example.dnd_character_creator_solo_edition.bll.services.implementations;

import com.example.dnd_character_creator_solo_edition.bll.dtos.proficiencies.ProficiencyDTO;
import com.example.dnd_character_creator_solo_edition.bll.dtos.proficiencies.SearchProficiencyDTO;
import com.example.dnd_character_creator_solo_edition.bll.mappers.interfaces.ProficiencyMapper;
import com.example.dnd_character_creator_solo_edition.bll.services.interfaces.ProficiencyService;
import com.example.dnd_character_creator_solo_edition.dal.entities.Proficiency;
import com.example.dnd_character_creator_solo_edition.dal.repos.ProficiencyRepo;
import com.example.dnd_character_creator_solo_edition.exceptions.customs.NameAlreadyTakenException;
import com.example.dnd_character_creator_solo_edition.exceptions.customs.NotFoundException;
import com.example.dnd_character_creator_solo_edition.exceptions.customs.NotSoftDeletedException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProficiencyServiceImpl implements ProficiencyService {
    private static final String NOT_FOUND_MESSAGE="The proficiency is not found!";
    private static final String NAME_TAKEN_MESSAGE="There is already proficiency named like that!";
    private final ProficiencyRepo proficiencyRepo;
    private final ProficiencyMapper mapper;
    @PersistenceContext
    private EntityManager em;
    public ProficiencyServiceImpl(@NotNull ProficiencyRepo proficiencyRepo, @NotNull ProficiencyMapper mapper) {
        this.proficiencyRepo = proficiencyRepo;
        this.mapper = mapper;
    }

    @Override
    public List<ProficiencyDTO> getProficienciesUnfiltered() {
        return mapper.toDTOs(this.proficiencyRepo.findAll(false));
    }

    @Override
    public List<ProficiencyDTO> getProficiencies(boolean isDeleted,
                                                 SearchProficiencyDTO searchProficiencyDTO) {
        CriteriaBuilder cb= em.getCriteriaBuilder();
        CriteriaQuery<Proficiency> criteriaQuery= cb.createQuery(Proficiency.class);
        Root<Proficiency> root= criteriaQuery.from(Proficiency.class);
        criteriaQuery.select(root)
                .where(cb.and
                        (cb.and(
                        cb.equal(root.get("isDeleted"),isDeleted),
                        cb.like(root.get("name"),cb.parameter(String.class,"name"))
                        ),
                        cb.like(root.get("type"),cb.parameter(String.class,"type"))
                ));
        String sortBy=searchProficiencyDTO.sort().sortBy();
        if (sortBy.isEmpty()){
            sortBy="id";
        }
        if (searchProficiencyDTO.sort().ascending()){
            criteriaQuery.orderBy(cb.asc(root.get(sortBy)));
        }else {
            criteriaQuery.orderBy(cb.desc(root.get(sortBy)));
        }
        TypedQuery<Proficiency> query = em.createQuery(criteriaQuery);
        query.setParameter("name","%"+searchProficiencyDTO.filter().name()+"%");
        query.setParameter("type","%"+searchProficiencyDTO.filter().type()+"%");
        List<Proficiency> proficiencies=query.getResultList();
        return mapper.toDTOs(proficiencies);
    }

    @Override
    public ProficiencyDTO addProficiency(ProficiencyDTO proficiencyDTO) {
        Optional<Proficiency> proficiencyByName=proficiencyRepo.findByName(proficiencyDTO.name());
        if (proficiencyByName.isPresent()) {
            throw new NameAlreadyTakenException(NAME_TAKEN_MESSAGE);
        }
        return mapper.toDto(proficiencyRepo.save(mapper.fromDto(proficiencyDTO)));
    }

    @Override
    public ProficiencyDTO getProficiency(Long id) {
        Optional<Proficiency> proficiency=proficiencyRepo.findById(id);
        if (proficiency.isPresent()){
            return mapper.toDto(proficiency.get());
        }
        throw new NotFoundException(NOT_FOUND_MESSAGE);
    }

    @Override
    public ProficiencyDTO updateProficiency(ProficiencyDTO proficiencyDTO) {
        Optional<Long> optionalId=proficiencyDTO.id();
        if (optionalId.isEmpty()) {
            throw new NotFoundException(NOT_FOUND_MESSAGE);
        }
        Long id= optionalId.get();
        Optional<Proficiency> proficiency = proficiencyRepo.findById(id);
        if (proficiency.isPresent()) {
            proficiencyRepo.findByName(proficiencyDTO.name()).ifPresent(
                    x -> {
                        if (!x.getId().equals(id)) {
                            throw new NameAlreadyTakenException(NAME_TAKEN_MESSAGE);
                        }
                    }
            );
            proficiencyRepo.save(mapper.fromDto(proficiencyDTO));
        } else {
            throw new NotFoundException(NOT_FOUND_MESSAGE);
        }
        return proficiencyDTO;
    }

    @Override
    public void restoreProficiency(Long id) {
        Optional<Proficiency> optionalProficiency=proficiencyRepo.findById(id);
        if (optionalProficiency.isPresent()){
            Proficiency proficiency= optionalProficiency.get();
            proficiencyRepo.findByName(proficiency.getName())
                    .ifPresent(x->{ throw new NameAlreadyTakenException(NAME_TAKEN_MESSAGE);});
            proficiency.setIsDeleted(false);
            proficiencyRepo.save(proficiency);
        }
        else {
            throw new NotFoundException(NOT_FOUND_MESSAGE);
        }
    }

    @Override
    public void softDeleteProficiency(Long id) {
        Optional<Proficiency> optionalProficiency=proficiencyRepo.findById(id);
        if (optionalProficiency.isEmpty()){
            throw new NotFoundException(NOT_FOUND_MESSAGE);
        }
        else {
            Proficiency proficiency=optionalProficiency.get();
            proficiency.setIsDeleted(true);
            proficiencyRepo.save(proficiency);
        }
    }

    @Override
    public void hardDeleteProficiency(Long id) {
        Optional<Proficiency> optionalProficiency=proficiencyRepo.findById(id);
        if (optionalProficiency.isEmpty()){
            throw new NotFoundException(NOT_FOUND_MESSAGE);
        }
        else {
            Proficiency proficiency=optionalProficiency.get();
            if (proficiency.getIsDeleted()){
                proficiencyRepo.delete(proficiency);
            } else {
                throw new NotSoftDeletedException("The proficiency must be soft deleted first!");
            }
        }
    }
}
