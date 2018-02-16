package com.walterjwhite.machine.learning.api.model.data;

import com.walterjwhite.datastore.api.model.entity.AbstractEntity;
import com.walterjwhite.machine.learning.api.model.data.item.DataElementItem;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class DataElement extends AbstractEntity {
  @ManyToOne(optional = false)
  @JoinColumn(nullable = false, updatable = false)
  protected DataElementItem source;

  @ManyToOne(optional = false)
  @JoinColumn(nullable = false, updatable = false)
  protected DataElementItem target;

  /** Association between source and target */
  @Column(nullable = false, updatable = false)
  protected double preference;

  @Column(nullable = false, updatable = false)
  protected LocalDateTime dateTime;

  @ManyToOne @JoinColumn protected DataView dataView;

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

  public DataElementItem getSource() {
    return source;
  }

  public void setSource(DataElementItem source) {
    this.source = source;
  }

  public DataElementItem getTarget() {
    return target;
  }

  public void setTarget(DataElementItem target) {
    this.target = target;
  }

  public double getPreference() {
    return preference;
  }

  public void setPreference(double preference) {
    this.preference = preference;
  }

  public LocalDateTime getDateTime() {
    return dateTime;
  }

  public void setDateTime(LocalDateTime dateTime) {
    this.dateTime = dateTime;
  }

  public DataView getDataView() {
    return dataView;
  }

  public void setDataView(DataView dataView) {
    this.dataView = dataView;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    DataElement that = (DataElement) o;

    if (source != null ? !source.equals(that.source) : that.source != null) return false;
    if (target != null ? !target.equals(that.target) : that.target != null) return false;
    if (dateTime != null ? !dateTime.equals(that.dateTime) : that.dateTime != null) return false;
    return dataView != null ? dataView.equals(that.dataView) : that.dataView == null;
  }

  @Override
  public int hashCode() {
    int result = source != null ? source.hashCode() : 0;
    result = 31 * result + (target != null ? target.hashCode() : 0);
    result = 31 * result + (dateTime != null ? dateTime.hashCode() : 0);
    result = 31 * result + (dataView != null ? dataView.hashCode() : 0);
    return result;
  }
}
