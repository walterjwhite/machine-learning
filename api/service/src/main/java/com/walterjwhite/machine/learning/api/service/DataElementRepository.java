package com.walterjwhite.machine.learning.api.service;

import com.walterjwhite.datastore.criteria.AbstractRepository;
import com.walterjwhite.machine.learning.api.model.data.DataElement;
import com.walterjwhite.machine.learning.api.model.data.DataElement_;
import com.walterjwhite.machine.learning.api.model.data.DataView;
import com.walterjwhite.machine.learning.api.model.data.item.DataElementItem;
import com.walterjwhite.machine.learning.api.model.data.item.DataElementItem_;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.*;

public class DataElementRepository extends AbstractRepository<DataElement> {
  @Inject
  public DataElementRepository(EntityManager entityManager, CriteriaBuilder criteriaBuilder) {
    super(entityManager, criteriaBuilder, DataElement.class);
  }

  public CriteriaQuery<Long> getUserIdsForDataView(DataView dataView) {
    CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
    Root<DataElement> root = criteriaQuery.from(DataElement.class);

    Join<DataElement, DataElementItem> sourceJoin = root.join(DataElement_.source);
    criteriaQuery.select(sourceJoin.get(DataElementItem_.itemId));

    Predicate dataViewCondition = criteriaBuilder.equal(root.get(DataElement_.dataView), dataView);
    criteriaQuery.where(dataViewCondition);
    return (criteriaQuery);
  }

  public CriteriaQuery<DataElement> getDataElementsForUser(DataView dataView, long userId) {
    CriteriaQuery<DataElement> criteriaQuery = criteriaBuilder.createQuery(DataElement.class);
    Root<DataElement> root = criteriaQuery.from(DataElement.class);

    Predicate dataViewCondition = criteriaBuilder.equal(root.get(DataElement_.dataView), dataView);

    Join<DataElement, DataElementItem> sourceJoin = root.join(DataElement_.source);
    Predicate userIdCondition =
        criteriaBuilder.equal(sourceJoin.get(DataElementItem_.itemId), userId);

    criteriaQuery.where(criteriaBuilder.and(dataViewCondition, userIdCondition));
    return (criteriaQuery);
  }

  public CriteriaQuery<DataElement> getDataElementsForItem(DataView dataView, long itemId) {
    CriteriaQuery<DataElement> criteriaQuery = criteriaBuilder.createQuery(DataElement.class);
    Root<DataElement> root = criteriaQuery.from(DataElement.class);

    Predicate dataViewCondition = criteriaBuilder.equal(root.get(DataElement_.dataView), dataView);
    Join<DataElement, DataElementItem> targetJoin = root.join(DataElement_.target);
    Predicate itemIdCondition =
        criteriaBuilder.equal(targetJoin.get(DataElementItem_.itemId), itemId);

    criteriaQuery.where(criteriaBuilder.and(dataViewCondition, itemIdCondition));
    return (criteriaQuery);
  }

  public List<Long> getItemIdsForUser(DataView dataView, long userId) {
    CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
    // write the Root, Path elements as usual
    Root<DataElement> root = criteriaQuery.from(DataElement.class);

    //    criteriaQuery.select(root.get(DataElement_.itemId));
    Join<DataElement, DataElementItem> sourceJoin = root.join(DataElement_.source);
    Join<DataElement, DataElementItem> targetJoin = root.join(DataElement_.target);
    criteriaQuery.select(targetJoin.get(DataElementItem_.itemId));

    Predicate dataViewCondition = criteriaBuilder.equal(root.get(DataElement_.dataView), dataView);
    Predicate userIdCondition =
        criteriaBuilder.equal(sourceJoin.get(DataElementItem_.itemId), userId);

    criteriaQuery.where(criteriaBuilder.and(dataViewCondition, userIdCondition));

    List<Long> results = entityManager.createQuery(criteriaQuery).getResultList();
    final List<Long> itemIds = new ArrayList<>();
    results.forEach(t -> itemIds.add(t));

    return (itemIds);
  }

  public CriteriaQuery<Long> getItemIdsForDataView(DataView dataView) {
    CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
    Root<DataElement> root = criteriaQuery.from(DataElement.class);
    Join<DataElement, DataElementItem> targetJoin = root.join(DataElement_.target);
    criteriaQuery.select(targetJoin.get(DataElementItem_.itemId));

    Predicate dataViewCondition = criteriaBuilder.equal(root.get(DataElement_.dataView), dataView);
    criteriaQuery.where(dataViewCondition);
    return (criteriaQuery);
  }

  // TODO: this *MAY* have multiple records because we support having a time element as well (a
  // user's preferences may change with respect to time)
  public Double getPreferenceForUserAndItem(DataView dataView, long userId, long itemId) {
    CriteriaQuery<Double> criteriaQuery = criteriaBuilder.createQuery(Double.class);
    // write the Root, Path elements as usual
    Root<DataElement> root = criteriaQuery.from(DataElement.class);

    criteriaQuery.select(root.get(DataElement_.preference));

    Join<DataElement, DataElementItem> sourceJoin = root.join(DataElement_.source);
    Join<DataElement, DataElementItem> targetJoin = root.join(DataElement_.target);

    Predicate dataViewCondition = criteriaBuilder.equal(root.get(DataElement_.dataView), dataView);
    Predicate userIdCondition =
        criteriaBuilder.equal(sourceJoin.get(DataElementItem_.itemId), userId);
    Predicate itemIdCondition =
        criteriaBuilder.equal(targetJoin.get(DataElementItem_.itemId), itemId);

    criteriaQuery.where(criteriaBuilder.and(dataViewCondition, userIdCondition, itemIdCondition));

    return (entityManager.createQuery(criteriaQuery).getSingleResult());
  }

  // TODO: this *MAY* have multiple records because we support having a time element as well (a
  // user's preferences may change with respect to time)
  public LocalDateTime getPreferenceTimeForUserAndItem(
      DataView dataView, long userId, long itemId) {
    CriteriaQuery<LocalDateTime> criteriaQuery = criteriaBuilder.createQuery(LocalDateTime.class);
    // write the Root, Path elements as usual
    Root<DataElement> root = criteriaQuery.from(DataElement.class);

    Join<DataElement, DataElementItem> sourceJoin = root.join(DataElement_.source);
    Join<DataElement, DataElementItem> targetJoin = root.join(DataElement_.target);

    criteriaQuery.select(root.get(DataElement_.dateTime));

    Predicate dataViewCondition = criteriaBuilder.equal(root.get(DataElement_.dataView), dataView);
    Predicate userIdCondition =
        criteriaBuilder.equal(sourceJoin.get(DataElementItem_.itemId), userId);
    Predicate itemIdCondition =
        criteriaBuilder.equal(targetJoin.get(DataElementItem_.itemId), itemId);

    criteriaQuery.where(criteriaBuilder.and(dataViewCondition, userIdCondition, itemIdCondition));

    return (entityManager.createQuery(criteriaQuery).getSingleResult());
  }
}
