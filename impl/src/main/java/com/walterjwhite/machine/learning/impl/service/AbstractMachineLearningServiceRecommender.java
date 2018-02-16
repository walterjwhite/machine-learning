package com.walterjwhite.machine.learning.impl.service;

import com.google.inject.persist.Transactional;
import com.walterjwhite.datastore.api.model.entity.AbstractEntity;
import com.walterjwhite.datastore.criteria.EntityReferenceRepository;
import com.walterjwhite.datastore.criteria.Repository;
import com.walterjwhite.machine.learning.api.model.data.DataElement;
import com.walterjwhite.machine.learning.api.model.data.DataView;
import com.walterjwhite.machine.learning.api.model.data.RawEntityPreference;
import com.walterjwhite.machine.learning.api.model.data.Recommendation;
import com.walterjwhite.machine.learning.api.model.data.item.MappedEntityReferenceItem;
import com.walterjwhite.machine.learning.api.service.MachineLearningServiceRecommender;
import com.walterjwhite.machine.learning.api.service.MappedEntityReferenceRepository;
import com.walterjwhite.machine.learning.api.service.RecommendationRepository;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.stream.Stream;
import javax.inject.Provider;
import javax.persistence.NoResultException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractMachineLearningServiceRecommender
    implements MachineLearningServiceRecommender {
  private static final Logger LOGGER =
      LoggerFactory.getLogger(AbstractMachineLearningServiceRecommender.class);
  protected final Provider<Repository> repositoryProvider;
  protected final Provider<RecommendationRepository> recommendationRepositoryProvider;
  protected final Provider<EntityReferenceRepository> entityReferenceRepositoryProvider;
  protected final Provider<MappedEntityReferenceRepository> mappedEntityReferenceRepositoryProvider;

  public AbstractMachineLearningServiceRecommender(
      Provider<Repository> repositoryProvider,
      Provider<RecommendationRepository> recommendationRepositoryProvider,
      Provider<EntityReferenceRepository> entityReferenceRepositoryProvider,
      Provider<MappedEntityReferenceRepository> mappedEntityReferenceRepositoryProvider) {
    super();
    this.repositoryProvider = repositoryProvider;
    this.recommendationRepositoryProvider = recommendationRepositoryProvider;
    this.entityReferenceRepositoryProvider = entityReferenceRepositoryProvider;
    this.mappedEntityReferenceRepositoryProvider = mappedEntityReferenceRepositoryProvider;
  }

  /**
   * Persist these values to the database and create the new view For really large datasets, this
   * won't work as it puts all objects in memory convert this to JDBC.
   */
  @Override
  public DataView create(
      Stream<RawEntityPreference> rawEntityPreferenceStream,
      String viewName,
      String viewDescription) {
    try {
      DataView dataView = createView(new DataView(viewName, viewDescription));
      // TODO: 1. use JDBC for bulk insert OR 2. commit the transaction via JPA every N records AND
      // do NOT reference back to parent
      rawEntityPreferenceStream.forEach(de -> addDataElement(dataView, de));

      return dataView;
    } finally {
      // finally, close the stream
      rawEntityPreferenceStream.close();
    }
  }

  @Transactional
  protected DataView createView(DataView dataView) {
    return ((DataView) repositoryProvider.get().persist(dataView));
  }

  //  @Transactional
  protected void addDataElement(DataView dataView, RawEntityPreference rawEntityPreference) {
    MappedEntityReferenceItem sourceMappedEntityReferenceItem =
        createMappedEntityReference(rawEntityPreference.getSource());
    MappedEntityReferenceItem targetMappedEntityReferenceItem =
        createMappedEntityReference(rawEntityPreference.getTarget());

    createNewDataElement(
        sourceMappedEntityReferenceItem,
        targetMappedEntityReferenceItem,
        rawEntityPreference,
        dataView);
  }

  @Transactional
  protected MappedEntityReferenceItem createMappedEntityReference(AbstractEntity entity) {
    return mappedEntityReferenceRepositoryProvider
        .get()
        .findOrCreateBy(entityReferenceRepositoryProvider.get().findOrCreate(entity));
  }

  @Transactional
  protected void createNewDataElement(
      MappedEntityReferenceItem sourceMappedEntityReferenceItem,
      MappedEntityReferenceItem targetMappedEntityReferenceItem,
      RawEntityPreference rawEntityPreference,
      DataView dataView) {
    LOGGER.info(
        "creating data element:"
            + sourceMappedEntityReferenceItem
            + ":"
            + targetMappedEntityReferenceItem
            + ":"
            + dataView
            + ":"
            + rawEntityPreference.getDateTime());
    repositoryProvider
        .get()
        .persist(
            new DataElement(
                sourceMappedEntityReferenceItem,
                targetMappedEntityReferenceItem,
                rawEntityPreference.getPreference(),
                rawEntityPreference.getDateTime(),
                dataView));
  }

  /** Used for creating a mahout data view for CSV files */
  //  @Override
  public DataView create(final File csvFile) throws IOException {
    //        try {
    //          DataView dataView = createView(new DataView(csvFile.getAbsolutePath(), "CSV:" +
    // csvFile.getAbsolutePath()));
    //
    //          // for each line, create a data element
    //          Files.lines(Paths.get(csvFile.getAbsolutePath())).forEach(l ->
    // ).map(externalToMyLocation));
    //
    //
    //          // TODO: 1. use JDBC for bulk insert OR 2. commit the transaction via JPA every N
    // records AND do NOT reference back to parent
    //          rawEntityPreferenceStream.forEach(de -> addDataElement(dataView, de));
    //
    //          return dataView;
    //        } finally {
    //          // finally, close the stream
    //          rawEntityPreferenceStream.close();
    //        }

    return (null);
  }

  //  public static void get(DataView dataView, final String line) {
  //    LOGGER.info("processing:" + line);
  //
  //    addDataElement(dataView, new CSVDataElementItem())
  //
  //    final String[] values = line.split(",");
  //    return (new DataElement(
  //            Long.valueOf(values[0]), Long.valueOf(values[1]), Double.valueOf(values[2]), null));
  //  }

  public Recommendation recommend(DataView dataView, long userId, int quantity) throws Exception {
    // TODO: check if the recommendation already exists and is <= 30 minutes old
    // if it is older, then create a new recommendation
    try {
      return (recommendationRepositoryProvider
          .get()
          .findByDataViewAndUserIdAndDateTime(dataView, userId, LocalDateTime.now()));
    } catch (NoResultException e) {
      Recommendation recommendation = new Recommendation(dataView, userId, quantity);
      recommendationRepositoryProvider.get().persist(recommendation);

      doRecommend(recommendation);
      recommendationRepositoryProvider.get().merge(recommendation);
      return (recommendation);
    }
  }

  protected abstract void doRecommend(Recommendation recommendation) throws Exception;
}
