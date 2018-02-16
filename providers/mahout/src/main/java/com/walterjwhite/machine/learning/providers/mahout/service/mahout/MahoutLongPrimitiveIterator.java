package com.walterjwhite.machine.learning.providers.mahout.service.mahout;

import com.walterjwhite.machine.learning.api.service.DataElementAggregationRepository;
import javax.inject.Provider;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Selection;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Used to get all user ids and all item ids. */
public class MahoutLongPrimitiveIterator implements LongPrimitiveIterator {
  private static final Logger LOGGER = LoggerFactory.getLogger(MahoutLongPrimitiveIterator.class);

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
    this.count = dataElementAggregationRepositoryProvider.get().count(criteriaQuery);

    // reset the selection back to the implicit root object
    criteriaQuery.select(priorSelection);

    LOGGER.info("MahoutLongPrimitiveIterator:count:" + count + ":" + criteriaQuery);
  }

  @Override
  public long nextLong() {
    return next();
  }

  @Override
  public long peek() {
    return ((Long) dataElementAggregationRepositoryProvider.get().get(criteriaQuery, offset));
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
    LOGGER.info("next");
    return ((Long) dataElementAggregationRepositoryProvider.get().get(criteriaQuery, offset++));
  }
}
