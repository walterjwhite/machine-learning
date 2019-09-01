package com.walterjwhite;

import com.walterjwhite.datastore.api.model.entity.Tag;
import com.walterjwhite.datastore.api.repository.AbstractEntityRepository;
import com.walterjwhite.google.guice.GuiceHelper;
import com.walterjwhite.machine.learning.api.model.data.DataView;
import com.walterjwhite.machine.learning.api.model.data.RawEntityPreference;
import com.walterjwhite.machine.learning.api.model.data.Recommendation;
import com.walterjwhite.machine.learning.api.service.MachineLearningServiceRecommender;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MahoutRecommenderTest {
  @Before
  public void onBefore() throws Exception {
    GuiceHelper.addModules(new MahoutTestModule(getClass()));

    GuiceHelper.setup();

    // write sample file
    //    IOUtils.copy(
    //        MahoutRecommenderTest.class.getResourceAsStream("sample-1.csv"),
    //        new FileWriter(new File("/tmp/data-set.1")));

    //    IOUtils.copy(
    //        MahoutRecommenderTest.class.getResourceAsStream("sample-1.csv"),
    //        new FileWriter(new File("/tmp/data-set.1")));

    // https://mahout.apache.org/users/recommender/userbased-5-minutes.html
  }

  @After
  public void onAfter() throws Exception {
    GuiceHelper.stop();
  }

  @Test
  public void testBasics() throws Exception {
    MachineLearningServiceRecommender machineLearningServiceRecommender =
        GuiceHelper.getGuiceApplicationInjector()
            .getInstance(MachineLearningServiceRecommender.class);

    // final FileDataModel model = new FileDataModel(new File("/tmp/data-set.1"));
    final File dataFile = new File("/tmp/data-get.1");

    //    DataView dataViewI =
    // (DataView)GuiceHelper.getGuiceApplicationInjector().getInstance(Repository.class).get(DataView.class,
    // 0);
    //    DataElementRepository dataElementRepository =
    // GuiceHelper.getGuiceApplicationInjector().getInstance(DataElementRepository.class);
    //    CriteriaQuery<DataElement> criteriaQuery =
    // dataElementRepository.getDataElementsForUser(dataViewI, 1);
    ////    criteriaQuery.select(dataElementRepository.getCriteriaQuery().getRoot());
    //    DataElement dataElement = (DataElement)dataElementRepository.get(criteriaQuery, 0);
    ////    List<DataElement> dataElements =
    // (List<DataElement>)dataElementRepository.get(criteriaQuery);

    //    DataView dataView =
    //        machineLearningServiceRecommender.createView(
    //            CSVDataElementHelper.get(dataFile), "test view", "test view");
    final List<RawEntityPreference> preferences = createSampleData();

    DataView dataView =
        machineLearningServiceRecommender.create(preferences.stream(), "test view", "test view");

    recommend(machineLearningServiceRecommender, dataView, -1362804551, 3);
    recommend(machineLearningServiceRecommender, dataView, -1362804552, 3);
    recommend(machineLearningServiceRecommender, dataView, -1362804550, 3);
    recommend(machineLearningServiceRecommender, dataView, -1362804549, 3);
  }

  protected List<RawEntityPreference> createSampleData() {
    List<RawEntityPreference> dataElements = new ArrayList<>();
    Tag tag1 = new Tag("red");
    Tag tag2 = new Tag("orange");
    Tag tag3 = new Tag("yellow");
    Tag tag4 = new Tag("green");
    Tag tag5 = new Tag("blue");
    Tag tag6 = new Tag("purple");
    Tag tag7 = new Tag("white");
    Tag tag8 = new Tag("brown");
    Tag tag9 = new Tag("black");

    Tag operator1 = new Tag("operator1");
    Tag operator2 = new Tag("operator2");
    Tag operator3 = new Tag("operator3");
    Tag operator4 = new Tag("operator4");

    // create entity references
    // in the real world, this would be a JPA criteria query
    dataElements.add(new RawEntityPreference(operator1, tag1, 1.0));
    dataElements.add(new RawEntityPreference(operator1, tag2, 2.0));
    dataElements.add(new RawEntityPreference(operator1, tag3, 5.0));
    dataElements.add(new RawEntityPreference(operator1, tag4, 5.0));
    dataElements.add(new RawEntityPreference(operator1, tag5, 5.0));
    dataElements.add(new RawEntityPreference(operator1, tag6, 4.0));
    dataElements.add(new RawEntityPreference(operator1, tag7, 5.0));
    dataElements.add(new RawEntityPreference(operator1, tag8, 1.0));
    dataElements.add(new RawEntityPreference(operator1, tag9, 5.0));

    dataElements.add(new RawEntityPreference(operator2, tag1, 1.0));
    dataElements.add(new RawEntityPreference(operator2, tag2, 2.0));
    dataElements.add(new RawEntityPreference(operator2, tag6, 5.0));
    dataElements.add(new RawEntityPreference(operator2, tag7, 4.5));
    dataElements.add(new RawEntityPreference(operator2, tag8, 1.0));
    dataElements.add(new RawEntityPreference(operator2, tag9, 5.0));

    dataElements.add(new RawEntityPreference(operator3, tag2, 2.5));
    dataElements.add(new RawEntityPreference(operator3, tag3, 4.5));
    dataElements.add(new RawEntityPreference(operator3, tag4, 4.0));
    dataElements.add(new RawEntityPreference(operator3, tag5, 3.0));
    dataElements.add(new RawEntityPreference(operator3, tag6, 3.5));
    dataElements.add(new RawEntityPreference(operator3, tag7, 4.5));
    dataElements.add(new RawEntityPreference(operator3, tag8, 4.0));
    dataElements.add(new RawEntityPreference(operator3, tag9, 5.0));

    dataElements.add(new RawEntityPreference(operator4, tag1, 5.0));
    dataElements.add(new RawEntityPreference(operator4, tag2, 5.0));
    dataElements.add(new RawEntityPreference(operator4, tag3, 5.0));
    dataElements.add(new RawEntityPreference(operator4, tag4, 0.0));
    dataElements.add(new RawEntityPreference(operator4, tag5, 2.0));
    dataElements.add(new RawEntityPreference(operator4, tag6, 3.0));
    dataElements.add(new RawEntityPreference(operator4, tag7, 1.0));
    //    dataElements.add(new RawEntityPreference(operator4, tag7, 4.0));
    dataElements.add(new RawEntityPreference(operator4, tag8, 1.0));

    persistDataElements(dataElements);
    return (dataElements);
  }

  protected void persistDataElements(List<RawEntityPreference> dataElements) {
    AbstractEntityRepository entityRepository =
        GuiceHelper.getGuiceApplicationInjector().getInstance(AbstractEntityRepository.class);
    dataElements.stream()
        .forEach(
            p -> {
              entityRepository.persist(p.getSource());
              entityRepository.persist(p.getTarget());
            });
  }

  // file data model format:
  // userid,itemid,preference,timestamp
  // may contain extra columns (those will be ignored)
  // rows starting with # are ignored
  // may refresh data with refresh(collection)
  // for large datasets, a jdbc connection is preferred
  protected void recommend(
      MachineLearningServiceRecommender machineLearningServiceRecommender,
      DataView dataView,
      int userId,
      int quantity)
      throws Exception {
    Recommendation recommendation =
        machineLearningServiceRecommender.recommend(dataView, userId, quantity);
  }
}
