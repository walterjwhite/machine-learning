package com.walterjwhite.machine.learning.providers.mahout.service.mahout;

import com.walterjwhite.machine.learning.api.model.data.DataElement;
import com.walterjwhite.machine.learning.api.service.DataElementRepository;
import java.util.Iterator;
import java.util.NoSuchElementException;
import javax.inject.Provider;
import org.apache.mahout.cf.taste.model.Preference;

public class DataElementIterator implements Iterator<Preference> {
  protected final CriteriaQuery<DataElement> criteriaQuery;
  protected final Provider<DataElementRepository> dataElementRepositoryProvider;
  protected final int count;
  protected int index = 0;

  public DataElementIterator(
      CriteriaQuery<DataElement> criteriaQuery,
      Provider<DataElementRepository> dataElementRepositoryProvider,
      int count) {
    super();
    this.criteriaQuery = criteriaQuery;
    this.dataElementRepositoryProvider = dataElementRepositoryProvider;
    // passed directly from the preference array, no point in recalculating
    this.count = count;
  }

  @Override
  public boolean hasNext() {
    return (index < count);
  }

  @Override
  public Preference next() {
    if (!hasNext()) throw new NoSuchElementException("No more elements.");

    DataElement dataElement = null;
    // (DataElement) dataElementRepositoryProvider.get().get(criteriaQuery, index++);
    return (new MahoutPreference(
        dataElement.getSource().getItemId(),
        dataElement.getTarget().getItemId(),
        (float) dataElement.getPreference()));
  }
}
