package com.walterjwhite.machine.learning.providers.mahout.service.archived;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.util.Version;
import org.apache.mahout.classifier.naivebayes.BayesUtils;
import org.apache.mahout.classifier.naivebayes.NaiveBayesModel;
import org.apache.mahout.classifier.naivebayes.StandardNaiveBayesClassifier;
import org.apache.mahout.common.Pair;
import org.apache.mahout.common.iterator.sequencefile.SequenceFileIterable;
import org.apache.mahout.math.Vector;
import org.apache.mahout.math.Vector.Element;

public class MahoutMachineLearningClassifierService {

  public int classify() throws IOException {
    final String modelPath = "";
    final String labelIndexPath = "";
    final String dictionaryPath = "";
    final String documentFrequencyPath = "";

    Configuration configuration = new Configuration();

    // labels is a map label => classId
    Map<Integer, String> labels =
        BayesUtils.readLabelIndex(configuration, new Path(labelIndexPath));
    Map<String, Integer> dictionary = readDictionnary(configuration, new Path(dictionaryPath));
    Map<Integer, Long> documentFrequency =
        readDocumentFrequency(configuration, new Path(documentFrequencyPath));

    // analyzer used to extract word from tweet
    try (Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_43)) {

      // model is a matrix (wordId, labelId) => probability score
      NaiveBayesModel model = NaiveBayesModel.materialize(new Path(modelPath), configuration);

      StandardNaiveBayesClassifier classifier = new StandardNaiveBayesClassifier(model);

      Vector vector = null;
      Vector resultVector = classifier.classifyFull(vector);

      return (selectBestClassifierId(labels, resultVector));
    }
  }

  public static Map<String, Integer> readDictionnary(Configuration conf, Path dictionnaryPath) {
    Map<String, Integer> dictionnary = new HashMap<String, Integer>();
    for (Pair<Text, IntWritable> pair :
        new SequenceFileIterable<Text, IntWritable>(dictionnaryPath, true, conf)) {
      dictionnary.put(pair.getFirst().toString(), pair.getSecond().get());
    }
    return dictionnary;
  }

  public static Map<Integer, Long> readDocumentFrequency(
      Configuration conf, Path documentFrequencyPath) {
    Map<Integer, Long> documentFrequency = new HashMap<Integer, Long>();
    for (Pair<IntWritable, LongWritable> pair :
        new SequenceFileIterable<IntWritable, LongWritable>(documentFrequencyPath, true, conf)) {
      documentFrequency.put(pair.getFirst().get(), pair.getSecond().get());
    }
    return documentFrequency;
  }

  protected int selectBestClassifierId(Map<Integer, String> labels, Vector resultVector) {
    double bestScore = -Double.MAX_VALUE;
    int bestCategoryId = -1;
    for (Element element : resultVector.all()) {
      int categoryId = element.index();
      double score = element.get();
      if (score > bestScore) {
        bestScore = score;
        bestCategoryId = categoryId;
      }
    }

    return (bestCategoryId);
  }
}
