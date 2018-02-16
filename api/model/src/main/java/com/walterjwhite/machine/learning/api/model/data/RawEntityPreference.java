package com.walterjwhite.machine.learning.api.model.data;

import com.walterjwhite.datastore.api.model.entity.AbstractEntity;
import java.time.LocalDateTime;

// DTO to capture a preference between 2 entities
public class RawEntityPreference {
  protected final AbstractEntity source;
  protected final AbstractEntity target;
  protected final double preference;
  protected final LocalDateTime dateTime = LocalDateTime.now();

  public RawEntityPreference(AbstractEntity source, AbstractEntity target, double preference) {
    super();
    this.source = source;
    this.target = target;
    this.preference = preference;
  }

  public AbstractEntity getSource() {
    return source;
  }

  public AbstractEntity getTarget() {
    return target;
  }

  public double getPreference() {
    return preference;
  }

  public LocalDateTime getDateTime() {
    return dateTime;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    RawEntityPreference that = (RawEntityPreference) o;

    if (source != null ? !source.equals(that.source) : that.source != null) return false;
    if (target != null ? !target.equals(that.target) : that.target != null) return false;
    return dateTime != null ? dateTime.equals(that.dateTime) : that.dateTime == null;
  }

  @Override
  public int hashCode() {
    int result = source != null ? source.hashCode() : 0;
    result = 31 * result + (target != null ? target.hashCode() : 0);
    result = 31 * result + (dateTime != null ? dateTime.hashCode() : 0);
    return result;
  }
}
