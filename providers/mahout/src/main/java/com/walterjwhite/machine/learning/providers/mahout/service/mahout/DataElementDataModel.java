package com.walterjwhite.machine.learning.providers.mahout.service.mahout;

import com.walterjwhite.machine.learning.api.model.data.DataView;
import com.walterjwhite.machine.learning.api.service.DataElementAggregationRepository;
import com.walterjwhite.machine.learning.api.service.DataElementRepository;
import java.time.ZoneOffset;
import java.util.Collection;
import javax.inject.Provider;
import lombok.AllArgsConstructor;
import org.apache.mahout.cf.taste.common.Refreshable;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.FastIDSet;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.PreferenceArray;

/** Use JDBC to make things quick */
@AllArgsConstructor
public class DataElementDataModel implements DataModel {
  // TODO: inject this
  protected final DataView dataView;
  protected final Provider<DataElementRepository> dataElementRepositoryProvider;
  protected final Provider<DataElementAggregationRepository>
      dataElementAggregationRepositoryProvider;

  @Override
  public LongPrimitiveIterator getUserIDs() throws TasteException {
    return (new MahoutLongPrimitiveIterator(
        dataElementAggregationRepositoryProvider,
        dataElementRepositoryProvider.get().getUserIdsForDataView(dataView)));
  }

  @Override
  public LongPrimitiveIterator getItemIDs() throws TasteException {
    return (new MahoutLongPrimitiveIterator(
        dataElementAggregationRepositoryProvider,
        dataElementRepositoryProvider.get().getItemIdsForDataView(dataView)));
  }

  @Override
  public FastIDSet getItemIDsFromUser(long userID) throws TasteException {
    return (MahoutUtil.build(
        dataElementRepositoryProvider.get().getItemIdsForUser(dataView, userID)));
  }

  @Override
  public Float getPreferenceValue(long userID, long itemID) throws TasteException {
    // what if the user has multiple preferences (for different date/times?)
    return (dataElementRepositoryProvider
        .get()
        .getPreferenceForUserAndItem(dataView, userID, itemID)
        .floatValue());
  }

  @Override
  public Long getPreferenceTime(long userID, long itemID) throws TasteException {
    // what if the user has multiple preferences (for different date/times?)
    return (dataElementRepositoryProvider
        .get()
        .getPreferenceTimeForUserAndItem(dataView, userID, itemID)
        .toEpochSecond(ZoneOffset.UTC));
  }

  @Override
  public int getNumItems() throws TasteException {
    return (int) dataElementAggregationRepositoryProvider.get().countItems(dataView);
  }

  @Override
  public int getNumUsers() throws TasteException {
    return (int) dataElementAggregationRepositoryProvider.get().countUsers(dataView);
  }

  @Override
  public int getNumUsersWithPreferenceFor(long itemID) throws TasteException {
    return (int)
        dataElementAggregationRepositoryProvider
            .get()
            .countUsersWithPreferenceFor(dataView, itemID);
  }

  @Override
  public int getNumUsersWithPreferenceFor(long itemID1, long itemID2) throws TasteException {
    return (int)
        dataElementAggregationRepositoryProvider
            .get()
            .countUsersWithPreferenceFor(dataView, itemID1, itemID2);
  }

  @Override
  public boolean hasPreferenceValues() {
    return true;
  }

  @Override
  public float getMaxPreference() {
    return dataElementAggregationRepositoryProvider.get().getMaxPreference(dataView).floatValue();
  }

  @Override
  public float getMinPreference() {
    return dataElementAggregationRepositoryProvider.get().getMinPreference(dataView).floatValue();
  }

  @Override
  public PreferenceArray getPreferencesFromUser(long userID) throws TasteException {
    return (new MahoutPreferenceArray(
        dataElementRepositoryProvider,
        dataElementRepositoryProvider.get().getDataElementsForUser(dataView, userID)));
  }

  @Override
  public PreferenceArray getPreferencesForItem(long itemID) throws TasteException {
    return (new MahoutPreferenceArray(
        dataElementRepositoryProvider,
        dataElementRepositoryProvider.get().getDataElementsForItem(dataView, itemID)));
  }

  // Below are not implemented
  @Override
  public void setPreference(long userID, long itemID, float value) throws TasteException {
    throw (new UnsupportedOperationException("Not supported - create a new view"));
  }

  @Override
  public void removePreference(long userID, long itemID) throws TasteException {
    throw (new UnsupportedOperationException("Not supported - create a new view"));
  }

  @Override
  public void refresh(Collection<Refreshable> alreadyRefreshed) {
    throw (new UnsupportedOperationException("Not supported - use the new view"));
  }
}
