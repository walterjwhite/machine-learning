package com.walterjwhite.machine.learning.api.model.data;

import com.walterjwhite.datastore.api.model.entity.AbstractEntity;
import com.walterjwhite.machine.learning.api.model.data.item.DataElementItem;
import java.time.LocalDateTime;
import javax.jdo.annotations.PersistenceCapable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(doNotUseGetters = true)
@PersistenceCapable
public class DataElement extends AbstractEntity {
  protected DataElementItem source;

  protected DataElementItem target;

  /** Association between source and target */
  @EqualsAndHashCode.Exclude protected double preference;

  protected LocalDateTime dateTime;

  protected DataView dataView;

  public DataElement(
      DataElementItem source, DataElementItem target, double preference, DataView dataView) {
    this(source, target, preference, LocalDateTime.now(), dataView);
  }

  public DataElement(
      DataElementItem source,
      DataElementItem target,
      double preference,
      LocalDateTime dateTime,
      DataView dataView) {
    super();
    this.source = source;
    this.target = target;
    this.preference = preference;
    this.dateTime = dateTime;
    this.dataView = dataView;
  }

  public DataElement() {
    super();
  }
}
