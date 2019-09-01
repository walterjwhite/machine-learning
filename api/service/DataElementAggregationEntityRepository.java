package com.walterjwhite.machine.learning.api.service;

import com.walterjwhite.datastore.api.repository.GenericEntityRepository;
import com.walterjwhite.machine.learning.api.model.data.DataElement;
import com.walterjwhite.machine.learning.api.model.data.DataElement_;
import com.walterjwhite.machine.learning.api.model.data.DataView;
import com.walterjwhite.machine.learning.api.model.data.item.DataElementItem;
import com.walterjwhite.machine.learning.api.model.data.item.DataElementItem_;
import javax.inject.Inject;

public class DataElementAggregationEntityRepository extends GenericEntityRepository<DataElement> {

  @Inject
  public DataElementAggregationEntityRepository(
      EntityManager entityManager, CriteriaBuilder criteriaBuilder) {
    super(entityManager, criteriaBuilder, DataElement.class);
  }

  public long count(DataView dataView) {
    CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
    Root<DataElement> root = criteriaQuery.from(DataElement.class);
    Predicate dataViewCondition = criteriaBuilder.equal(root.get(DataElement_.dataView), dataView);
    criteriaQuery.where(dataViewCondition);

    // return (count(criteriaQuery, root));
    return 0;
  }

  public long countItems(DataView dataView) {
    CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
    Root<DataElement> root = criteriaQuery.from(entityTypeClass);

    Predicate dataViewCondition = criteriaBuilder.equal(root.get(DataElement_.dataView), dataView);
    //    criteriaQuery.groupBy(root.get(DataElement_.userId));

    criteriaQuery.where(dataViewCondition);

    Join<DataElement, DataElementItem> targetJoin = root.join(DataElement_.target);
    criteriaQuery.select(criteriaBuilder.countDistinct(targetJoin.get(DataElementItem_.itemId)));
    TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery);
    return (typedQuery.getSingleResult().intValue());
  }

  public long countUsers(DataView dataView) {
    CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
    Root<DataElement> root = criteriaQuery.from(entityTypeClass);

    Predicate dataViewCondition = criteriaBuilder.equal(root.get(DataElement_.dataView), dataView);
    //    criteriaQuery.groupBy(root.get(DataElement_.userId));

    criteriaQuery.where(dataViewCondition);

    Join<DataElement, DataElementItem> sourceJoin = root.join(DataElement_.source);
    criteriaQuery.select(criteriaBuilder.countDistinct(sourceJoin.get(DataElementItem_.itemId)));
    TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery);
    return (typedQuery.getSingleResult().intValue());
  }

  public long countUsersWithPreferenceFor(DataView dataView, long itemId) {
    CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
    Root<DataElement> root = criteriaQuery.from(entityTypeClass);

    Join<DataElement, DataElementItem> targetJoin = root.join(DataElement_.target);

    Predicate dataViewCondition = criteriaBuilder.equal(root.get(DataElement_.dataView), dataView);
    Predicate itemIdCondition =
        criteriaBuilder.equal(targetJoin.get(DataElementItem_.itemId), itemId);

    Join<DataElement, DataElementItem> sourceJoin = root.join(DataElement_.source);
    //    criteriaQuery.groupBy(root.get(DataElement_.userId));

    criteriaQuery.where(criteriaBuilder.and(dataViewCondition, itemIdCondition));
    criteriaQuery.select(criteriaBuilder.countDistinct(sourceJoin.get(DataElementItem_.itemId)));
    TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery);
    return (typedQuery.getSingleResult().intValue());
  }

  public long countUsersWithPreferenceFor(DataView dataView, long itemId1, long itemId2) {
    CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
    Root<DataElement> root = criteriaQuery.from(entityTypeClass);
    Predicate dataViewCondition = criteriaBuilder.equal(root.get(DataElement_.dataView), dataView);

    Join<DataElement, DataElementItem> targetJoin = root.join(DataElement_.target);
    Predicate itemId1Condition =
        criteriaBuilder.equal(targetJoin.get(DataElementItem_.itemId), itemId1);
    Predicate itemId2Condition =
        criteriaBuilder.equal(targetJoin.get(DataElementItem_.itemId), itemId2);
    Join<DataElement, DataElementItem> sourceJoin = root.join(DataElement_.source);
    criteriaQuery.where(
        criteriaBuilder.and(
            dataViewCondition, criteriaBuilder.or(itemId1Condition, itemId2Condition)));
    criteriaQuery.select(criteriaBuilder.countDistinct(sourceJoin.get(DataElementItem_.itemId)));
    TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery);
    return (typedQuery.getSingleResult().intValue());
  }

  public Double getMaxPreference(DataView dataView) {
    CriteriaQuery<Double> criteriaQuery = criteriaBuilder.createQuery(Double.class);
    Root<DataElement> root = criteriaQuery.from(entityTypeClass);

    Predicate dataViewCondition = criteriaBuilder.equal(root.get(DataElement_.dataView), dataView);

    criteriaQuery.where(dataViewCondition);
    criteriaQuery.select(criteriaBuilder.max(root.get(DataElement_.preference)));

    return (entityManager.createQuery(criteriaQuery).getSingleResult());
  }

  public Double getMinPreference(DataView dataView) {
    CriteriaQuery<Double> criteriaQuery = criteriaBuilder.createQuery(Double.class);
    Root<DataElement> root = criteriaQuery.from(entityTypeClass);

    Predicate dataViewCondition = criteriaBuilder.equal(root.get(DataElement_.dataView), dataView);

    criteriaQuery.where(dataViewCondition);
    criteriaQuery.select(criteriaBuilder.min(root.get(DataElement_.preference)));

    return (entityManager.createQuery(criteriaQuery).getSingleResult());
  }
}
