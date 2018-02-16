package com.walterjwhite.machine.learning.api.model.data.item;

import javax.persistence.Column;
import javax.persistence.Entity;

/** Pointer to a specific row in a CSV file. */
@Entity
public class CSVDataElementItem extends DataElementItem {
  // filename should be from the dataview
  @Column(nullable = false, updatable = false)
  protected long rowNumber;

  public CSVDataElementItem(Long itemId, long rowNumber) {
    super(itemId);
    this.rowNumber = rowNumber;
  }

  public long getRowNumber() {
    return rowNumber;
  }

  public void setRowNumber(long rowNumber) {
    this.rowNumber = rowNumber;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    CSVDataElementItem that = (CSVDataElementItem) o;

    return rowNumber == that.rowNumber;
  }

  @Override
  public int hashCode() {
    return (int) (rowNumber ^ (rowNumber >>> 32));
  }
}
