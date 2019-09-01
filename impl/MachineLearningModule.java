package com.walterjwhite.machine.learning.impl.service;

import com.google.inject.AbstractModule;
import com.walterjwhite.machine.learning.api.service.DataElementEntityRepository;
import com.walterjwhite.machine.learning.api.service.EntityReferenceMapEntityRepository;
import com.walterjwhite.machine.learning.api.service.RecommendationEntityRepository;

public class MachineLearningModule extends AbstractModule {
  @Override
  protected void configure() {
    bind(DataElementEntityRepository.class);
    bind(RecommendationEntityRepository.class);
    bind(EntityReferenceMapEntityRepository.class);
  }
}
