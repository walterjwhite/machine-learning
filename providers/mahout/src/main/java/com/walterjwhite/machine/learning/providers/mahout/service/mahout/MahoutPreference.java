package com.walterjwhite.machine.learning.providers.mahout.service.mahout;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.apache.mahout.cf.taste.model.Preference;

@AllArgsConstructor
@Data
@ToString(doNotUseGetters = true)
public class MahoutPreference implements Preference {
  protected final long userID;
  protected final long itemID;
  protected float value;
}
