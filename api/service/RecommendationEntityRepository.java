package com.walterjwhite.machine.learning.api.service;

import com.walterjwhite.datastore.api.repository.GenericEntityRepository;
import com.walterjwhite.machine.learning.api.model.data.DataView;
import com.walterjwhite.machine.learning.api.model.data.Recommendation;
import com.walterjwhite.machine.learning.api.model.data.Recommendation_;
import java.time.LocalDateTime;
import javax.inject.Inject;

public class RecommendationEntityRepository extends GenericEntityRepository<Recommendation> {
  @Inject
  public RecommendationEntityRepository(
      EntityManager entityManager, CriteriaBuilder criteriaBuilder) {
    super(entityManager, criteriaBuilder, Recommendation.class);
  }

  public Recommendation findByDataViewAndUserIdAndDateTime(
      DataView dataView, long userId, LocalDateTime dateTime) {

    CriteriaQuery<Recommendation> criteriaQuery = criteriaBuilder.createQuery(entityTypeClass);
    Root<Recommendation> root = criteriaQuery.from(entityTypeClass);

    Predicate dataViewCondition =
        criteriaBuilder.equal(root.get(Recommendation_.dataView), dataView);
    Predicate userIdCondition = criteriaBuilder.equal(root.get(Recommendation_.userId), userId);
    Predicate dateTimeCondition =
        criteriaBuilder.equal(root.get(Recommendation_.dateTime), dateTime);

    criteriaQuery.where(criteriaBuilder.and(dataViewCondition, userIdCondition, dateTimeCondition));

    TypedQuery<Recommendation> typedQuery = entityManager.createQuery(criteriaQuery);
    return (typedQuery.getSingleResult());
  }
}
