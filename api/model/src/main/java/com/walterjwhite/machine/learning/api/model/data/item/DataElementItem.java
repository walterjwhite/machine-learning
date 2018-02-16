package com.walterjwhite.machine.learning.api.model.data.item;

import com.walterjwhite.datastore.api.model.entity.AbstractEntity;
import javax.persistence.*;

// @MappedSuperclass
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
public class DataElementItem extends AbstractEntity {
  //  @GeneratedValue
  // for mapped entities, this is a surrogate key
  // for CSV files, this is the row number
  @Column(nullable = false, updatable = false, unique = true)
  protected Long itemId;

  public DataElementItem(Long itemId) {
    super();
    this.itemId = itemId;
  }

  public DataElementItem() {
    super();
  }

  public Long getItemId() {
    return itemId;
  }

  public void setItemId(Long itemId) {
    this.itemId = itemId;
  }
}
