package com.example.dnd_character_creator_solo_edition.bll.services.implementations;

import com.example.dnd_character_creator_solo_edition.bll.dtos.dnd_classes.ClassFeatureDTO;
import com.example.dnd_character_creator_solo_edition.bll.dtos.features.FeatureDTO;
import com.example.dnd_character_creator_solo_edition.bll.dtos.features.SearchFeatureDTO;
import com.example.dnd_character_creator_solo_edition.bll.mappers.interfaces.ClassFeatureMapper;
import com.example.dnd_character_creator_solo_edition.bll.mappers.interfaces.FeatureMapper;
import com.example.dnd_character_creator_solo_edition.bll.services.interfaces.FeatureService;
import com.example.dnd_character_creator_solo_edition.dal.entities.Feature;
import com.example.dnd_character_creator_solo_edition.dal.repos.features.ClassFeatureRepo;
import com.example.dnd_character_creator_solo_edition.dal.repos.features.FeatureRepo;
import com.example.dnd_character_creator_solo_edition.exceptions.customs.NameAlreadyTakenException;
import com.example.dnd_character_creator_solo_edition.exceptions.customs.NotFoundException;
import com.example.dnd_character_creator_solo_edition.exceptions.customs.NotSoftDeletedException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FeatureServiceImpl implements FeatureService {

    private static final String NOT_FOUND_LABEL = "Feature not found!";
    private static final String NAME_ALREADY_FOUND = "There is already feature with such name!";
    private static final String NOT_SOFT_DELETED_LABEL = "This feature is not soft deleted!";

    private final FeatureRepo repo;
    private final FeatureMapper mapper;
    private final ClassFeatureRepo classFeatureRepo;
    private final ClassFeatureMapper classFeatureMapper;

    @PersistenceContext
    private final EntityManager em;

    @Autowired
    public FeatureServiceImpl(FeatureRepo repo, FeatureMapper mapper, EntityManager em,
                              ClassFeatureMapper classFeatureMapper, ClassFeatureRepo classFeatureRepo) {
        this.repo = repo;
        this.mapper = mapper;
        this.em = em;
        this.classFeatureMapper = classFeatureMapper;
        this.classFeatureRepo = classFeatureRepo;
    }

    @Override
    public List<FeatureDTO> getAll(boolean deleted) {
        return this.mapper.toDTOs(this.repo.findAll(deleted));
    }

    @Override
    public List<FeatureDTO> getAll(SearchFeatureDTO search, boolean deleted) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Feature> criteriaQuery = cb.createQuery(Feature.class);
        Root<Feature> root = criteriaQuery.from(Feature.class);
        criteriaQuery.select(root)
                .where(cb.and
                        (cb.and(
                                        cb.equal(root.get("isDeleted"), deleted),
                                        cb.like(root.get("name"), cb.parameter(String.class, "name"))
                                )
                        ));
        String sortBy = search.sort().sortBy();
        if (sortBy.isEmpty()) {
            sortBy = "id";
        }
        if (search.sort().ascending()) {
            criteriaQuery.orderBy(cb.asc(root.get(sortBy)));
        } else {
            criteriaQuery.orderBy(cb.desc(root.get(sortBy)));
        }
        TypedQuery<Feature> query = em.createQuery(criteriaQuery);
        query.setParameter("name", "%" + search.filter().name() + "%");
        List<Feature> features = query.getResultList();
        return mapper.toDTOs(features);
    }

    @Override
    public FeatureDTO getFeature(Long id) {
        return this.mapper.toDto(this.repo.findById(id).orElseThrow(() -> new NotFoundException(NOT_FOUND_LABEL)));
    }

    @Override
    public FeatureDTO addFeature(FeatureDTO featureDTO) {
        checkForExistingFeatureWithSameName(featureDTO.name());
        return this.mapper.toDto(this.repo.save(this.mapper.fromDto(featureDTO)));
    }

    @Override
    public FeatureDTO updateFeature(FeatureDTO featureDTO) {
        if (featureDTO.id().isEmpty() || !this.repo.existsById(featureDTO.id().get())) {
            throw new NotFoundException(NOT_FOUND_LABEL);
        }
        return this.mapper.toDto(this.repo.save(this.mapper.fromDto(featureDTO)));
    }

    @Override
    public void restoreFeature(Long id) {
        Optional<Feature> feature = this.repo.findById(id);
        if (feature.isEmpty()) {
            throw new NotFoundException(NOT_FOUND_LABEL);
        }
        Feature foundFeature = feature.get();
        if (!foundFeature.getIsDeleted()) {
            throw new NotSoftDeletedException(NOT_SOFT_DELETED_LABEL);
        }
        checkForExistingFeatureWithSameName(foundFeature.getName());
        foundFeature.setIsDeleted(true);
        this.repo.save(foundFeature);
    }

    @Override
    public void softDeleteFeature(Long id) {
        Optional<Feature> feature = this.repo.findById(id);
        if (feature.isEmpty()) {
            throw new NotFoundException(NOT_FOUND_LABEL);
        }
        Feature foundFeature = feature.get();
        foundFeature.setIsDeleted(true);
        this.repo.save(foundFeature);
    }

    @Override
    public void hardDeleteFeature(Long id) {
        Optional<Feature> feature = this.repo.findById(id);
        if (feature.isEmpty()) {
            throw new NotFoundException(NOT_FOUND_LABEL);
        }
        Feature foundFeature = feature.get();
        if (!foundFeature.getIsDeleted()) {
            throw new NotSoftDeletedException(NOT_SOFT_DELETED_LABEL);
        }
        foundFeature.setIsDeleted(true);
        this.repo.save(foundFeature);
    }

    @Override
    public List<ClassFeatureDTO> getFeaturesForClass(Long classId) {
        return this.classFeatureMapper.toDTOs(this.classFeatureRepo.findByClassId(classId));
    }

    private void checkForExistingFeatureWithSameName(String name) {
        if (this.repo.existsByName(name)) {
            throw new NameAlreadyTakenException(NAME_ALREADY_FOUND);
        }
    }
}
