package com.walterjwhite.machine.learning.api.model.data;

import com.walterjwhite.datastore.api.model.entity.AbstractEntity;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
public class Recommendation extends AbstractEntity {
  @ManyToOne(optional = false)
  @JoinColumn(nullable = false, updatable = false)
  protected DataView dataView;

  @Column(nullable = false, updatable = false)
  protected long userId;

  @Column(nullable = false, updatable = false)
  protected long numberRequested;

  // @NOTE: this shouldn't matter really because if the dataset is the same, re-analyzing the data
  // again should yield identical results
  @Column(nullable = false, updatable = false)
  protected LocalDateTime dateTime;

  @OneToMany(mappedBy = "recommendation", cascade = CascadeType.ALL)
  protected List<EntityPreference> entityPreferences = new ArrayList<>();

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

  public DataView getDataView() {
    return dataView;
  }

  public void setDataView(DataView dataView) {
    this.dataView = dataView;
  }

  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }

  public long getNumberRequested() {
    return numberRequested;
  }

  public void setNumberRequested(long numberRequested) {
    this.numberRequested = numberRequested;
  }

  public LocalDateTime getDateTime() {
    return dateTime;
  }

  public void setDateTime(LocalDateTime dateTime) {
    this.dateTime = dateTime;
  }

  public List<EntityPreference> getEntityPreferences() {
    return entityPreferences;
  }

  public void setEntityPreferences(List<EntityPreference> entityPreferences) {
    this.entityPreferences = entityPreferences;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Recommendation that = (Recommendation) o;

    if (userId != that.userId) return false;
    if (dataView != null ? !dataView.equals(that.dataView) : that.dataView != null) return false;
    return dateTime != null ? dateTime.equals(that.dateTime) : that.dateTime == null;
  }

  @Override
  public int hashCode() {
    int result = dataView != null ? dataView.hashCode() : 0;
    result = 31 * result + (int) (userId ^ (userId >>> 32));
    result = 31 * result + (dateTime != null ? dateTime.hashCode() : 0);
    return result;
  }
}
