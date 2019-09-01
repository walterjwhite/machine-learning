package com.walterjwhite.machine.learning.api.model.data;

import com.walterjwhite.datastore.api.model.entity.AbstractEntity;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.jdo.annotations.PersistenceCapable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(doNotUseGetters = true)
@PersistenceCapable
public class Recommendation extends AbstractEntity {
  protected DataView dataView;

  protected long userId;

  @EqualsAndHashCode.Exclude protected long numberRequested;

  // @NOTE: this shouldn't matter really because if the dataset is the same, re-analyzing the data
  // again should yield identical results

  protected LocalDateTime dateTime;

  @EqualsAndHashCode.Exclude protected List<EntityPreference> entityPreferences = new ArrayList<>();

  public Recommendation(DataView dataView, long userId, long numberRequested) {
    super();
    this.dataView = dataView;
    this.userId = userId;
    this.numberRequested = numberRequested;
    this.dateTime = LocalDateTime.now();
  }

  public Recommendation() {
    super();
  }
}
