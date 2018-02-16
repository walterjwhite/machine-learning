package com.walterjwhite.machine.learning.providers.mahout.service.archived;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.mahout.cf.taste.impl.model.GenericDataModel;
import org.apache.mahout.cf.taste.model.DataModel;

public class JDBCMachineLearningHelper {

  protected final String query;

  public JDBCMachineLearningHelper(String query) {
    this.query = query;
  }

  public DataModel run() throws SQLException {
    final GenericDataModel dataModel = null; // new GenericDataModel(null);

    try (final Connection connection = null) {
      try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        try (final ResultSet resultSet = preparedStatement.executeQuery()) {
          while (resultSet.next()) {
            // dataModel.getRawUserData().a
          }
        }
      }
    }

    return (dataModel);
  }
}
