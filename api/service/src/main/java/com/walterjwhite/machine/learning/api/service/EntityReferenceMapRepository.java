package com.walterjwhite.machine.learning.api.service;

import com.walterjwhite.datastore.api.repository.Repository;
import com.walterjwhite.machine.learning.api.model.data.item.MappedEntityReferenceItem;

public interface EntityReferenceMapRepository extends Repository {

  /**
   * This should exist prior to data retrieval, at the time we create the view, we are providing
   * data.
   *
   * <p>at that point, we will be creating these records
   */
  MappedEntityReferenceItem findByItemId(final Long itemId);
}
