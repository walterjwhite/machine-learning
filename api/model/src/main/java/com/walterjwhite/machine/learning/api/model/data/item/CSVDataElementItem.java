package com.walterjwhite.machine.learning.api.model.data.item;

import javax.jdo.annotations.PersistenceCapable;
import lombok.Data;
import lombok.ToString;

/** Pointer to a specific row in a CSV file. */
@Data
@ToString(doNotUseGetters = true)
@PersistenceCapable
public class CSVDataElementItem extends DataElementItem {
  // filename should be from the dataview

  protected long rowNumber;

  public CSVDataElementItem(Long itemId, long rowNumber) {
    super(itemId);
    this.rowNumber = rowNumber;
  }
}
