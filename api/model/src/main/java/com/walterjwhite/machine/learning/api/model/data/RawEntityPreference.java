package com.walterjwhite.machine.learning.api.model.data;

import com.walterjwhite.datastore.api.model.entity.AbstractEntity;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

// DTO to capture a preference between 2 entities
@Data
@ToString(doNotUseGetters = true)
public class RawEntityPreference {
  protected final AbstractEntity source;
  protected final AbstractEntity target;
  @EqualsAndHashCode.Exclude protected final double preference;
  protected final LocalDateTime dateTime = LocalDateTime.now();

  public RawEntityPreference(AbstractEntity source, AbstractEntity target, double preference) {
    super();
    this.source = source;
    this.target = target;
    this.preference = preference;
  }
}
