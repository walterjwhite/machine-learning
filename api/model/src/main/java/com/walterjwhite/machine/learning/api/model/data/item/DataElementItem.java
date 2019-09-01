package com.walterjwhite.machine.learning.api.model.data.item;

import com.walterjwhite.datastore.api.model.entity.AbstractEntity;
import javax.jdo.annotations.PersistenceCapable;
import lombok.Data;
import lombok.ToString;

// @MappedSuperclass
@Data
@ToString(doNotUseGetters = true)
@PersistenceCapable
public class DataElementItem extends AbstractEntity {
  //  @GeneratedValue
  // for mapped entities, this is a surrogate key
  // for CSV files, this is the row number

  protected Long itemId;

  public DataElementItem(Long itemId) {
    super();
    this.itemId = itemId;
  }

  public DataElementItem() {
    super();
  }
}
