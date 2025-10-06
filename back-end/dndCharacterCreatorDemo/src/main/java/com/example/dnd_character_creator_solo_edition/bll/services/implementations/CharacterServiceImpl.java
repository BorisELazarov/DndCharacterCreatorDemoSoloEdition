package com.example.dnd_character_creator_solo_edition.bll.services.implementations;

import com.example.dnd_character_creator_solo_edition.bll.dtos.characters.CharacterDTO;
import com.example.dnd_character_creator_solo_edition.bll.dtos.characters.SearchCharacterDTO;
import com.example.dnd_character_creator_solo_edition.bll.mappers.interfaces.CharacterMapper;
import com.example.dnd_character_creator_solo_edition.bll.services.interfaces.CharacterService;
import com.example.dnd_character_creator_solo_edition.dal.entities.Character;
import com.example.dnd_character_creator_solo_edition.dal.entities.DNDclass;
import com.example.dnd_character_creator_solo_edition.dal.entities.User;
import com.example.dnd_character_creator_solo_edition.dal.repos.CharacterRepo;
import com.example.dnd_character_creator_solo_edition.dal.repos.RoleRepo;
import com.example.dnd_character_creator_solo_edition.exceptions.customs.NameAlreadyTakenException;
import com.example.dnd_character_creator_solo_edition.exceptions.customs.NotFoundException;
import com.example.dnd_character_creator_solo_edition.exceptions.customs.NotSoftDeletedException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CharacterServiceImpl implements CharacterService {
    private static final String NOT_FOUND_MESSAGE = "The character is not found!";
    private static final String NAME_TAKEN_MESSAGE = "There is already character named like that!";
    private final RoleRepo roleRepo;
    private final CharacterRepo characterRepo;
    private final CharacterMapper mapper;
    @PersistenceContext
    private EntityManager em;

    public CharacterServiceImpl(@NotNull CharacterRepo characterRepo,
                                @NotNull CharacterMapper mapper,
                                @NotNull RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
        this.characterRepo = characterRepo;
        this.mapper = mapper;
    }

    @Override
    public List<CharacterDTO> getCharacters(Long userId,
                                            boolean isDeleted,
                                            SearchCharacterDTO searchCharacterDTO) {
        CriteriaBuilder cb= em.getCriteriaBuilder();
        CriteriaQuery<Character> criteriaQuery= cb.createQuery(Character.class);
        Root<Character> root= criteriaQuery.from(Character.class);
        Byte levelParam= searchCharacterDTO.filter().level().orElse((byte)0);
        Join<Character, DNDclass> joinClasses= root.join("dndClass", JoinType.INNER);
        Join<Character, User> joinUsers= root.join("user", JoinType.INNER);
        criteriaQuery.select(root)
                .where(cb.and(
                        cb.and(
                            cb.like(root.get("name"),cb.parameter(String.class,"name")),
                            cb.like(joinClasses.get("name"),cb.parameter(String.class,"dndClassName"))
                        ),
                        cb.and(
                                cb.or(
                                        cb.isTrue(
                                                cb.literal(
                                                        searchCharacterDTO.filter().level().isEmpty()
                                                )
                                        ),
                                        cb.equal(
                                                root.get("level"),
                                                levelParam
                                        )
                                ),
                                cb.and(
                                        cb.equal(joinUsers.get("id"),userId),
                                        cb.equal(root.get("isDeleted"),isDeleted)
                                )
                        )
                ));
        String sortBy= searchCharacterDTO.sort().sortBy();
        if (sortBy.isEmpty()){
            sortBy="id";
        }
        Path<Object> path;
        if (sortBy.equals("className")){
            path=joinClasses.get("name");
        }
        else {
            path=root.get(sortBy);
        }
        if (searchCharacterDTO.sort().ascending()){
            criteriaQuery.orderBy(cb.asc(path));
        }else {
            criteriaQuery.orderBy(cb.desc(path));
        }
        TypedQuery<Character> query = em.createQuery(criteriaQuery);
        query.setParameter("name","%"+searchCharacterDTO.filter().name()+"%");
        query.setParameter("dndClassName","%"+searchCharacterDTO.filter().dndClassName()+"%");
        List<Character> characters=  query.getResultList();
        return mapper.toDTOs(characters);
    }

    @Override
    public CharacterDTO addCharacter(CharacterDTO dcharacterDTO) {
        if (characterRepo.findByName(dcharacterDTO.name()).isPresent())
            throw new NameAlreadyTakenException(NAME_TAKEN_MESSAGE);
        Character character = characterRepo.save(mapper.fromDto(
                dcharacterDTO,
                roleRepo.findByTitle(dcharacterDTO.user().role())
        ));
        return mapper.toDto(character);
    }

    @Override
    @Transactional
    public CharacterDTO editCharacter(CharacterDTO characterDTO) {
        Optional<Long> optionalId = characterDTO.id();
        if (optionalId.isEmpty()) {
            throw new NotFoundException(NOT_FOUND_MESSAGE);
        }
        Long id = optionalId.get();
        Optional<Character> character = characterRepo.findById(id);
        if (character.isPresent()) {
            characterRepo.findByName(characterDTO.name()).ifPresent(
                    x -> {
                        if (!x.getId().equals(id)) {
                            throw new NameAlreadyTakenException(NAME_TAKEN_MESSAGE);
                        }
                    }
            );
            characterRepo.save(
                    mapper.fromDto(characterDTO,
                            Optional.of(character.get().getUser().getRole())
                    ));
        } else {
            throw new NotFoundException(NOT_FOUND_MESSAGE);
        }
        return characterDTO;
    }

    @Override
    public void restoreCharacter(Long id) {
        Optional<Character> optionalCharacter = characterRepo.findById(id);
        if (optionalCharacter.isPresent()) {
            Character character = optionalCharacter.get();
            characterRepo.findByName(character.getName())
                    .ifPresent(x -> {
                        throw new NameAlreadyTakenException(NAME_TAKEN_MESSAGE);
                    });
            character.setIsDeleted(false);
            characterRepo.save(character);
        } else {
            throw new NotFoundException(NOT_FOUND_MESSAGE);
        }
    }

    @Override
    public void softDeleteCharacter(Long id) {
        Optional<Character> character = characterRepo.findById(id);

        if (character.isEmpty()) {
            throw new NotFoundException(NOT_FOUND_MESSAGE);
        } else {
            character.get().setIsDeleted(true);
            characterRepo.save(character.get());
        }
    }

    @Override
    public void hardDeleteCharacter(Long id) {
        Optional<Character> character = characterRepo.findById(id);

        if (character.isEmpty()) {
            throw new NotFoundException(NOT_FOUND_MESSAGE);

        } else {
            Character foundCharacter = character.get();
            if (foundCharacter.getIsDeleted()) {
                characterRepo.delete(foundCharacter);
            } else {
                throw new NotSoftDeletedException("The character must be soft deleted first!");
            }
        }
    }

    @Override
    public CharacterDTO getCharacterById(Long id) {
        Optional<Character> character = characterRepo.findById(id);

        if (character.isPresent()) {
            return mapper.toDto(character.get());
        }
        throw new NotFoundException(NOT_FOUND_MESSAGE);
    }


}
