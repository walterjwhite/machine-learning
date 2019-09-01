package com.walterjwhite.machine.learning.providers.mahout.service.mahout.property;

import com.walterjwhite.property.api.annotation.DefaultValue;
import com.walterjwhite.property.api.property.ConfigurableProperty;

public interface UserSimilarityProperty extends ConfigurableProperty {
  @DefaultValue
  String DefaultUserSimilarityClassName =
      org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity.class.getName();
}
