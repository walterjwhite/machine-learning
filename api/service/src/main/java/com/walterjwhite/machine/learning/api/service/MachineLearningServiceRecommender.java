package com.walterjwhite.machine.learning.api.service;

import com.walterjwhite.machine.learning.api.model.data.DataView;
import com.walterjwhite.machine.learning.api.model.data.RawEntityPreference;
import com.walterjwhite.machine.learning.api.model.data.Recommendation;
import java.util.stream.Stream;

public interface MachineLearningServiceRecommender {
  //    void process(Algorithm algorithm, Datastore datastore, ClassifierContext classifierContext);
  //
  //    void train(Path input, Path output, Parameters parameters);

  DataView create(
      Stream<RawEntityPreference> rawEntityPreferenceStream,
      String viewName,
      String viewDescription);
  //
  //  DataView createView(Stream<DataElement> dataView, String viewName, String viewDescription);
  //
  //  //  void train(Stream<DataElement> data);

  Recommendation recommend(DataView dataView, long userId, int quantity) throws Exception;
}
