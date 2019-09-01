package com.walterjwhite.machine.learning.providers.mahout.service;

import com.walterjwhite.datastore.api.repository.Repository;
import com.walterjwhite.machine.learning.api.model.data.EntityPreference;
import com.walterjwhite.machine.learning.api.model.data.Recommendation;
import com.walterjwhite.machine.learning.api.service.*;
import com.walterjwhite.machine.learning.impl.service.AbstractMachineLearningServiceRecommender;
import com.walterjwhite.machine.learning.providers.mahout.service.mahout.DataElementDataModel;
import com.walterjwhite.machine.learning.providers.mahout.service.mahout.property.UserNeighborhoodProperty;
import com.walterjwhite.machine.learning.providers.mahout.service.mahout.property.UserSimilarityProperty;
import com.walterjwhite.property.impl.annotation.Property;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.jdo.annotations.Transactional;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

public class MahoutMachineLearningRecommenderService
    extends AbstractMachineLearningServiceRecommender {

  protected final Provider<EntityReferenceMapRepository> entityReferenceMapRepositoryProvider;
  protected final Provider<DataElementRepository> dataElementRepositoryProvider;
  protected final Provider<DataElementAggregationRepository>
      dataElementAggregationRepositoryProvider;

  protected final Repository repository;
  protected final String similarityClassName;
  protected final String neighborhoodClassName;

  @Inject
  public MahoutMachineLearningRecommenderService(
      @Property(UserSimilarityProperty.class) String similarityClassName,
      @Property(UserNeighborhoodProperty.class) String neighborhoodClassName,
      Provider<Repository> repositoryProvider,
      Provider<RecommendationRepository> recommendationRepositoryProvider,
      Provider<MappedEntityReferenceRepository> mappedEntityReferenceRepositoryProvider,
      Provider<EntityReferenceMapRepository> entityReferenceMapRepositoryProvider,
      Provider<DataElementRepository> dataElementRepositoryProvider,
      Provider<DataElementAggregationRepository> dataElementAggregationRepositoryProvider,
      Repository repository) {
    super(
        repositoryProvider,
        recommendationRepositoryProvider,
        mappedEntityReferenceRepositoryProvider);
    this.similarityClassName = similarityClassName;
    this.neighborhoodClassName = neighborhoodClassName;
    this.entityReferenceMapRepositoryProvider = entityReferenceMapRepositoryProvider;
    this.dataElementRepositoryProvider = dataElementRepositoryProvider;
    this.dataElementAggregationRepositoryProvider = dataElementAggregationRepositoryProvider;
    this.repository = repository;
  }

  protected void doRecommend(Recommendation recommendation)
      throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException,
          InvocationTargetException, InstantiationException, TasteException {

    // TODO: persist the data elements to the jdbc connection we have stored
    // check if we have already persisted the view or not
    // create an instance of DataElementDataModel that is backed by JDBC
    DataModel dataModel =
        new DataElementDataModel(
            recommendation.getDataView(),
            dataElementRepositoryProvider,
            dataElementAggregationRepositoryProvider);

    UserSimilarity similarity = getUserSimilarity(dataModel);
    UserNeighborhood neighborhood = getUserNeighborhood(similarity, dataModel);

    handleRecommendations(
        recommendation,
        getUserBasedRecommender(dataModel, neighborhood, similarity)
            .recommend(recommendation.getUserId(), (int) recommendation.getNumberRequested()));
  }

  @Transactional
  protected void handleRecommendations(
      Recommendation recommendation, final List<RecommendedItem> recommendedItems) {
    if (recommendedItems == null) return;

    // TODO: figure out how to map between a long and string here ...
    recommendedItems.forEach(
        rI ->
            recommendation
                .getEntityPreferences()
                .add(
                    new EntityPreference(
                        entityReferenceMapRepositoryProvider
                            .get()
                            .findByItemId(rI.getItemID())
                            .getEntityReference(),
                        rI.getValue(),
                        recommendation)));

    repository.persist(
        recommendation); // TODO: re-add support for differentiating create/update operations
  }

  protected UserSimilarity getUserSimilarity(DataModel dataModel)
      throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException,
          InvocationTargetException, InstantiationException {
    return ((UserSimilarity)
        Class.forName(similarityClassName).getConstructor(DataModel.class).newInstance(dataModel));
  }

  protected UserNeighborhood getUserNeighborhood(UserSimilarity userSimilarity, DataModel dataModel)
      throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException,
          InvocationTargetException, InstantiationException {
    return ((UserNeighborhood)
        Class.forName(neighborhoodClassName)
            .getConstructor(double.class, UserSimilarity.class, DataModel.class)
            .newInstance(0.1, userSimilarity, dataModel));
  }

  protected UserBasedRecommender getUserBasedRecommender(
      DataModel dataModel, UserNeighborhood userNeighborhood, UserSimilarity userSimilarity) {
    return (new GenericUserBasedRecommender(dataModel, userNeighborhood, userSimilarity));
  }
}
