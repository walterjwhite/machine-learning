package com.walterjwhite.machine.learning.providers.mahout.service.mahout.property;

import com.walterjwhite.google.guice.property.property.DefaultValue;
import com.walterjwhite.google.guice.property.property.GuiceProperty;

public interface UserNeighborhoodProperty extends GuiceProperty {
  @DefaultValue
  String DefaultUserNeighborhoodClassName =
      org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood.class.getName();
}
