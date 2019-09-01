package com.walterjwhite.machine.learning.api.service;

import com.walterjwhite.datastore.api.repository.GenericEntityRepository;
import com.walterjwhite.datastore.modules.criteria.CriteriaQueryConfiguration;
import com.walterjwhite.machine.learning.api.model.data.item.MappedEntityReferenceItem;
import com.walterjwhite.machine.learning.api.model.data.item.MappedEntityReferenceItem_;
import javax.inject.Inject;

public class EntityReferenceMapEntityRepository
    extends GenericEntityRepository<MappedEntityReferenceItem> {
  @Inject
  public EntityReferenceMapEntityRepository(
      EntityManager entityManager, CriteriaBuilder criteriaBuilder) {
    super(entityManager, criteriaBuilder, MappedEntityReferenceItem.class);
  }

  /**
   * This should exist prior to data retrieval, at the time we create the view, we are providing
   * data.
   *
   * <p>at that point, we will be creating these records
   */
  public MappedEntityReferenceItem findByItemId(final Long itemId) {
    final CriteriaQueryConfiguration<MappedEntityReferenceItem> resourceCriteriaQueryConfiguration =
        getCriteriaQuery();

    Predicate itemIdPredicate =
        criteriaBuilder.equal(
            resourceCriteriaQueryConfiguration.getRoot().get(MappedEntityReferenceItem_.itemId),
            itemId);

    resourceCriteriaQueryConfiguration.getCriteriaQuery().where(itemIdPredicate);

    return (entityManager
        .createQuery(resourceCriteriaQueryConfiguration.getCriteriaQuery())
        .getSingleResult());
  }
}
