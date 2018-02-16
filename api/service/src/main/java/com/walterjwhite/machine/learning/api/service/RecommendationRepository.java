package com.walterjwhite.machine.learning.api.service;

import com.walterjwhite.datastore.criteria.AbstractRepository;
import com.walterjwhite.machine.learning.api.model.data.DataView;
import com.walterjwhite.machine.learning.api.model.data.Recommendation;
import com.walterjwhite.machine.learning.api.model.data.Recommendation_;
import java.time.LocalDateTime;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class RecommendationRepository extends AbstractRepository<Recommendation> {
  @Inject
  public RecommendationRepository(EntityManager entityManager, CriteriaBuilder criteriaBuilder) {
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
