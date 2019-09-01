package com.walterjwhite.machine.learning.api.model.data;

import com.walterjwhite.datastore.api.model.entity.AbstractEntity;
import com.walterjwhite.datastore.api.model.entity.EntityReference;
import javax.jdo.annotations.PersistenceCapable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(doNotUseGetters = true)
@PersistenceCapable
public class EntityPreference extends AbstractEntity {
  protected EntityReference entityReference;

  @EqualsAndHashCode.Exclude protected double preference;

  protected Recommendation recommendation;

  public EntityPreference(
      EntityReference entityReference, double preference, Recommendation recommendation) {
    super();
    this.entityReference = entityReference;
    this.preference = preference;
    this.recommendation = recommendation;
  }

  public EntityPreference() {
    super();
  }
}
