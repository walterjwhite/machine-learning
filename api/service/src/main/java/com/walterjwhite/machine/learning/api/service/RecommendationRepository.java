package com.walterjwhite.machine.learning.api.service;

import com.walterjwhite.datastore.api.repository.Repository;
import com.walterjwhite.machine.learning.api.model.data.DataView;
import com.walterjwhite.machine.learning.api.model.data.Recommendation;
import java.time.LocalDateTime;

public interface RecommendationRepository extends Repository {
  Recommendation findByDataViewAndUserIdAndDateTime(
      DataView dataView, long userId, LocalDateTime dateTime);
}
