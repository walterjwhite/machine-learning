package com.walterjwhite.machine.learning.api.service;

import com.google.inject.persist.Transactional;
import com.walterjwhite.datastore.api.repository.EntityReferenceEntityRepository;
import com.walterjwhite.datastore.api.repository.GenericEntityRepository;
import com.walterjwhite.datastore.modules.criteria.CriteriaQueryConfiguration;
import com.walterjwhite.machine.learning.api.model.data.item.MappedEntityReferenceItem;
import com.walterjwhite.machine.learning.api.model.data.item.MappedEntityReferenceItem_;
import javax.inject.Inject;

public class MappedEntityReferenceEntityRepository
    extends GenericEntityRepository<MappedEntityReferenceItem> {
  protected final EntityReferenceEntityRepository entityReferenceRepository;

  @Inject
  public MappedEntityReferenceEntityRepository(
      EntityManager entityManager,
      CriteriaBuilder criteriaBuilder,
      EntityReferenceEntityRepository entityReferenceRepository) {
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
