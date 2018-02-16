package com.walterjwhite.machine.learning.impl.service;

import com.google.inject.AbstractModule;
import com.walterjwhite.machine.learning.api.service.DataElementRepository;
import com.walterjwhite.machine.learning.api.service.EntityReferenceMapRepository;
import com.walterjwhite.machine.learning.api.service.RecommendationRepository;

public class MachineLearningModule extends AbstractModule {
  @Override
  protected void configure() {
    bind(DataElementRepository.class);
    bind(RecommendationRepository.class);
    bind(EntityReferenceMapRepository.class);
  }
}
