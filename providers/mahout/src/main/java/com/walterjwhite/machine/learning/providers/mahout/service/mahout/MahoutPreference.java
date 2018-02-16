package com.walterjwhite.machine.learning.providers.mahout.service.mahout;

import org.apache.mahout.cf.taste.model.Preference;

public class MahoutPreference implements Preference {
  protected final long userId;
  protected final long itemId;
  protected float value;

  public MahoutPreference(long userId, long itemId, float value) {
    super();
    this.userId = userId;
    this.itemId = itemId;
    this.value = value;
  }

  @Override
  public long getUserID() {
    return userId;
  }

  @Override
  public long getItemID() {
    return itemId;
  }

  @Override
  public float getValue() {
    return value;
  }

  @Override
  public void setValue(float value) {
    this.value = value;
  }
}
