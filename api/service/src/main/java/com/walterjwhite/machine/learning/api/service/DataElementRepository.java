package com.walterjwhite.machine.learning.api.service;

import com.walterjwhite.datastore.api.repository.Repository;
import com.walterjwhite.machine.learning.api.model.data.DataElement;
import com.walterjwhite.machine.learning.api.model.data.DataView;
import java.time.LocalDateTime;
import java.util.List;

public interface DataElementRepository extends Repository {

  CriteriaQuery<Long> getUserIdsForDataView(DataView dataView);

  CriteriaQuery<DataElement> getDataElementsForUser(DataView dataView, long userId);

  CriteriaQuery<DataElement> getDataElementsForItem(DataView dataView, long itemId);

  List<Long> getItemIdsForUser(DataView dataView, long userId);

  CriteriaQuery<Long> getItemIdsForDataView(DataView dataView);

  // TODO: this *MAY* have multiple records because we support having a time element as well (a
  // user's preferences may change with respect to time)
  Double getPreferenceForUserAndItem(DataView dataView, long userId, long itemId);

  // TODO: this *MAY* have multiple records because we support having a time element as well (a
  // user's preferences may change with respect to time)
  LocalDateTime getPreferenceTimeForUserAndItem(DataView dataView, long userId, long itemId);
}
