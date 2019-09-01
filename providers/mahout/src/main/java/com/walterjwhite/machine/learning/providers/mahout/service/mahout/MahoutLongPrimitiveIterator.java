package com.walterjwhite.machine.learning.providers.mahout.service.mahout;

import com.walterjwhite.machine.learning.api.service.DataElementAggregationRepository;
import java.util.NoSuchElementException;
import javax.inject.Provider;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;

/** Used to get all user ids and all item ids. */
public class MahoutLongPrimitiveIterator implements LongPrimitiveIterator {
  protected final Provider<DataElementAggregationRepository>
      dataElementAggregationRepositoryProvider;
  protected final CriteriaQuery<Long> criteriaQuery;
  protected int offset = 0;
  protected int count = -1;

  //  @Inject
  public MahoutLongPrimitiveIterator(
      Provider<DataElementAggregationRepository> dataElementAggregationRepositoryProvider,
      CriteriaQuery<Long> criteriaQuery) {
    super();
    this.criteriaQuery = criteriaQuery;
    this.dataElementAggregationRepositoryProvider = dataElementAggregationRepositoryProvider;

    Selection priorSelection = criteriaQuery.getSelection();
    this.count = -1; // dataElementAggregationRepositoryProvider.get().count(criteriaQuery);

    // reset the selection back to the implicit root object
    criteriaQuery.select(priorSelection);
  }

  @Override
  public long nextLong() {
    return next();
  }

  @Override
  public long peek() {
    return -1; /*((Long) dataElementAggregationRepositoryProvider.get().get(criteriaQuery, offset))*/
  }

  @Override
  public void skip(int n) {
    offset += n;
  }

  @Override
  public boolean hasNext() {
    return (offset < count - 1);
  }

  @Override
  public Long next() {
    if (!hasNext()) throw new NoSuchElementException("No more elements");

    return Long.valueOf(
        -1) /*((Long) dataElementAggregationRepositoryProvider.get().get(criteriaQuery, offset++))*/;
  }
}
