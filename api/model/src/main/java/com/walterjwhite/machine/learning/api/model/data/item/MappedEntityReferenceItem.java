package com.walterjwhite.machine.learning.api.model.data.item;

import com.walterjwhite.datastore.api.model.entity.EntityReference;
import javax.jdo.annotations.PersistenceCapable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(doNotUseGetters = true)
@PersistenceCapable
public class MappedEntityReferenceItem extends DataElementItem {

  protected EntityReference entityReference;

  // TODO: why is this here and not in the super class?
  @Override
  public void jdoPreStore() {
    super.jdoPreStore();

    itemId = (long) id;
  }
}
