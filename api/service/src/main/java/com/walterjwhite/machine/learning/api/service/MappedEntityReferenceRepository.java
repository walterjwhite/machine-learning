package com.walterjwhite.machine.learning.api.service;

import com.walterjwhite.datastore.api.repository.Repository;
import com.walterjwhite.machine.learning.api.model.data.item.MappedEntityReferenceItem;

public interface MappedEntityReferenceRepository extends Repository {
  MappedEntityReferenceItem findOrCreateBy(EntityReference entityReference);
}
