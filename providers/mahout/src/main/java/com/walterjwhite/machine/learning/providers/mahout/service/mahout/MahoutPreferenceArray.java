package com.walterjwhite.machine.learning.providers.mahout.service.mahout;

import com.walterjwhite.machine.learning.api.model.data.DataElement;
import com.walterjwhite.machine.learning.api.service.DataElementRepository;
import java.util.Iterator;
import javax.inject.Provider;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Selection;
import org.apache.mahout.cf.taste.model.Preference;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MahoutPreferenceArray implements PreferenceArray {
  private static final Logger LOGGER = LoggerFactory.getLogger(MahoutPreferenceArray.class);

  protected final Provider<DataElementRepository> dataElementRepositoryProvider;
  protected final CriteriaQuery<DataElement> criteriaQuery;

  protected final int count;

  public MahoutPreferenceArray(
      Provider<DataElementRepository> dataElementRepositoryProvider,
      CriteriaQuery<DataElement> criteriaQuery) {
    super();
    this.dataElementRepositoryProvider = dataElementRepositoryProvider;
    this.criteriaQuery = criteriaQuery;
    Selection selection = criteriaQuery.getSelection();
    this.count = dataElementRepositoryProvider.get().count(criteriaQuery);

    LOGGER.info("MahoutPreferenceArray:count:" + count + ":" + criteriaQuery.toString());

    criteriaQuery.select(selection);
  }

  @Override
  public int length() {
    return count;
  }

  @Override
  public Preference get(int i) {
    DataElement dataElement =
        (DataElement) dataElementRepositoryProvider.get().get(criteriaQuery, i);
    return new MahoutPreference(
        dataElement.getSource().getItemId(),
        dataElement.getTarget().getItemId(),
        (float) dataElement.getPreference());
  }

  @Override
  public long getUserID(int i) {
    return ((DataElement) dataElementRepositoryProvider.get().get(criteriaQuery, i))
        .getSource()
        .getItemId();
  }

  @Override
  public long getItemID(int i) {
    return ((DataElement) dataElementRepositoryProvider.get().get(criteriaQuery, i))
        .getTarget()
        .getItemId();
  }

  @Override
  public float getValue(int i) {
    return (float)
        ((DataElement) dataElementRepositoryProvider.get().get(criteriaQuery, i)).getPreference();
  }

  @Override
  public Iterator<Preference> iterator() {
    return (new DataElementIterator(criteriaQuery, dataElementRepositoryProvider, count));
  }

  // below are unimplemented
  @Override
  public long[] getIDs() {
    throw (new UnsupportedOperationException("Is this required, for userIds or itemIds, or both?"));
  }

  @Override
  public void set(int i, Preference pref) {
    throw (new UnsupportedOperationException("this is read-only"));
  }

  @Override
  public void setUserID(int i, long userID) {
    throw (new UnsupportedOperationException("this is read-only"));
  }

  @Override
  public void setItemID(int i, long itemID) {
    throw (new UnsupportedOperationException("this is read-only"));
  }

  @Override
  public void setValue(int i, float value) {
    throw (new UnsupportedOperationException("this is read-only"));
  }

  @Override
  public PreferenceArray clone() {
    throw (new UnsupportedOperationException("this is read-only"));
  }

  @Override
  public void sortByUser() {
    throw (new UnsupportedOperationException("this is read-only"));
  }

  @Override
  public void sortByItem() {
    throw (new UnsupportedOperationException("this is read-only"));
  }

  @Override
  public void sortByValue() {
    throw (new UnsupportedOperationException("this is read-only"));
  }

  @Override
  public void sortByValueReversed() {
    throw (new UnsupportedOperationException("this is read-only"));
  }

  @Override
  public boolean hasPrefWithUserID(long userID) {
    //    return false;
    throw (new UnsupportedOperationException("is this needed?"));
  }

  @Override
  public boolean hasPrefWithItemID(long itemID) {
    //    return false;
    throw (new UnsupportedOperationException("is this needed?"));
  }
}
