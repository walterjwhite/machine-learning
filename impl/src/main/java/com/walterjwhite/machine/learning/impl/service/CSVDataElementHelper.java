package com.walterjwhite.machine.learning.impl.service;

import com.walterjwhite.datastore.api.repository.Repository;
import com.walterjwhite.machine.learning.api.model.data.DataElement;
import com.walterjwhite.machine.learning.api.model.data.DataView;
import com.walterjwhite.machine.learning.api.model.data.item.CSVDataElementItem;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import javax.inject.Provider;

public class CSVDataElementHelper {
  protected final File sourceFile;
  protected final DataView dataView;
  protected final Provider<Repository> repositoryProvider;

  protected int rowNumber;

  protected CSVDataElementHelper(
      File sourceFile, DataView dataView, Provider<Repository> repositoryProvider) {
    super();
    this.sourceFile = sourceFile;
    this.dataView = dataView;
    this.repositoryProvider = repositoryProvider;
  }

  public void prepare() throws IOException {
    try (final Stream<String> lines = Files.lines(Paths.get(sourceFile.getAbsolutePath()))) {
      lines.forEach(l -> processLine(l));
    }
  }

  // TODO: add guice transactions here ...
  protected void processLine(final String line) {
    final String[] values = line.split(",");

    final Long userId = Long.valueOf(values[0]);
    final Long itemId = Long.valueOf(values[1]);

    final CSVDataElementItem userIdCSVElementItem = new CSVDataElementItem(userId, rowNumber);
    final CSVDataElementItem itemIdCSVElementItem = new CSVDataElementItem(itemId, rowNumber);

    final Double preference = Double.valueOf(values[2]);

    DataElement dataElement =
        new DataElement(userIdCSVElementItem, itemIdCSVElementItem, preference, dataView);
    repositoryProvider.get().persist(dataElement);

    rowNumber++;
  }
}
