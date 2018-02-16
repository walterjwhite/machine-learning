package com.walterjwhite.machine.learning.api.service;

import com.walterjwhite.datastore.criteria.AbstractRepository;
import com.walterjwhite.datastore.criteria.CriteriaQueryConfiguration;
import com.walterjwhite.machine.learning.api.model.data.item.MappedEntityReferenceItem;
import com.walterjwhite.machine.learning.api.model.data.item.MappedEntityReferenceItem_;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;

public class EntityReferenceMapRepository extends AbstractRepository<MappedEntityReferenceItem> {
  @Inject
  public EntityReferenceMapRepository(
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
