package com.walterjwhite.machine.learning.providers.mahout.service.mahout.property;

import com.walterjwhite.property.api.annotation.DefaultValue;
import com.walterjwhite.property.api.property.ConfigurableProperty;

public interface UserNeighborhoodProperty extends ConfigurableProperty {
  @DefaultValue
  String DefaultUserNeighborhoodClassName =
      org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood.class.getName();
}
