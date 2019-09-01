package com.walterjwhite.machine.learning.api.service;

import com.walterjwhite.datastore.api.repository.Repository;
import com.walterjwhite.machine.learning.api.model.data.DataView;

public interface DataElementAggregationRepository extends Repository {

  long count(DataView dataView);

  long countItems(DataView dataView);

  long countUsers(DataView dataView);

  long countUsersWithPreferenceFor(DataView dataView, long itemId);

  long countUsersWithPreferenceFor(DataView dataView, long itemId1, long itemId2);

  Double getMaxPreference(DataView dataView);

  Double getMinPreference(DataView dataView);
}
