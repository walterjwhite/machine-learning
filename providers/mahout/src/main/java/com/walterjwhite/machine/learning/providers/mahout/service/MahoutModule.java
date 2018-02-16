package com.walterjwhite.machine.learning.providers.mahout.service;

import com.google.inject.AbstractModule;
import com.walterjwhite.machine.learning.api.service.MachineLearningServiceRecommender;
import com.walterjwhite.machine.learning.impl.service.MachineLearningModule;

public class MahoutModule extends AbstractModule {
  @Override
  protected void configure() {
    bind(MachineLearningServiceRecommender.class).to(MahoutMachineLearningRecommenderService.class);

    install(new MachineLearningModule());
  }
}
