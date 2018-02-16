package com.walterjwhite.machine.learning.api.model.data;

import com.walterjwhite.datastore.api.model.entity.AbstractEntity;
import com.walterjwhite.datastore.api.model.entity.EntityReference;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class EntityPreference extends AbstractEntity {
  @ManyToOne(optional = false)
  @JoinColumn(nullable = false, updatable = false)
  protected EntityReference entityReference;

  @Column protected double preference;

  @ManyToOne(optional = false)
  @JoinColumn(nullable = false, updatable = false)
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

  public EntityReference getEntityReference() {
    return entityReference;
  }

  public void setEntityReference(EntityReference entityReference) {
    this.entityReference = entityReference;
  }

  public double getPreference() {
    return preference;
  }

  public void setPreference(double preference) {
    this.preference = preference;
  }

  public Recommendation getRecommendation() {
    return recommendation;
  }

  public void setRecommendation(Recommendation recommendation) {
    this.recommendation = recommendation;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    EntityPreference that = (EntityPreference) o;

    if (entityReference != null
        ? !entityReference.equals(that.entityReference)
        : that.entityReference != null) return false;
    return recommendation != null
        ? recommendation.equals(that.recommendation)
        : that.recommendation == null;
  }

  @Override
  public int hashCode() {
    int result = entityReference != null ? entityReference.hashCode() : 0;
    result = 31 * result + (recommendation != null ? recommendation.hashCode() : 0);
    return result;
  }
}
