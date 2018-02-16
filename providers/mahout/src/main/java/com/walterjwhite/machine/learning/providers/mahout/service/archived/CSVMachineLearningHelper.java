package com.walterjwhite.machine.learning.providers.mahout.service.archived;

import java.io.File;
import java.io.IOException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.model.DataModel;

public class CSVMachineLearningHelper {
  protected final String csvFilename;

  public CSVMachineLearningHelper(String csvFilename) {
    super();
    this.csvFilename = csvFilename;
  }

  public DataModel run() throws IOException {
    return (new FileDataModel(new File(csvFilename)));
  }
}
