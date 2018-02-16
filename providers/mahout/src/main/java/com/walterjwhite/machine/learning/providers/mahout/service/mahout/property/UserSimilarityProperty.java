package com.walterjwhite.machine.learning.providers.mahout.service.mahout.property;

import com.walterjwhite.google.guice.property.property.DefaultValue;
import com.walterjwhite.google.guice.property.property.GuiceProperty;

public interface UserSimilarityProperty extends GuiceProperty {
  @DefaultValue
  String DefaultUserSimilarityClassName =
      org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity.class.getName();
}
