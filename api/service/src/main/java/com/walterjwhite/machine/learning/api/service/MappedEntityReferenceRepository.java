package com.walterjwhite.machine.learning.api.service;

import com.google.inject.persist.Transactional;
import com.walterjwhite.datastore.api.model.entity.EntityReference;
import com.walterjwhite.datastore.criteria.AbstractRepository;
import com.walterjwhite.datastore.criteria.CriteriaQueryConfiguration;
import com.walterjwhite.datastore.criteria.EntityReferenceRepository;
import com.walterjwhite.machine.learning.api.model.data.item.MappedEntityReferenceItem;
import com.walterjwhite.machine.learning.api.model.data.item.MappedEntityReferenceItem_;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;

public class MappedEntityReferenceRepository extends AbstractRepository<MappedEntityReferenceItem> {
  protected final EntityReferenceRepository entityReferenceRepository;

  @Inject
  public MappedEntityReferenceRepository(
      EntityManager entityManager,
      CriteriaBuilder criteriaBuilder,
      EntityReferenceRepository entityReferenceRepository) {
    super(entityManager, criteriaBuilder, MappedEntityReferenceItem.class);
    this.entityReferenceRepository = entityReferenceRepository;
  }

  @Transactional
  public MappedEntityReferenceItem findOrCreateBy(EntityReference entityReference) {
    final CriteriaQueryConfiguration<MappedEntityReferenceItem> resourceCriteriaQueryConfiguration =
        getCriteriaQuery();

    Predicate entityReferencePredicate =
        criteriaBuilder.equal(
            resourceCriteriaQueryConfiguration
                .getRoot()
                .get(MappedEntityReferenceItem_.entityReference),
            entityReference);

    resourceCriteriaQueryConfiguration.getCriteriaQuery().where(entityReferencePredicate);

    try {
      return (entityManager
          .createQuery(resourceCriteriaQueryConfiguration.getCriteriaQuery())
          .getSingleResult());
    } catch (NoResultException e) {
      return (persist(new MappedEntityReferenceItem(entityReference)));
    }
  }
}
