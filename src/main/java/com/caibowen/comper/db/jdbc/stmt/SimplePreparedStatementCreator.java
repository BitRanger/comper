
package com.caibowen.comper.db.jdbc.stmt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class SimplePreparedStatementCreator implements StatementCreator {

  String sql;

  public SimplePreparedStatementCreator(String sql) {
    this.sql = sql;
  }

  @Override
  public PreparedStatement createStatement(Connection con)
      throws SQLException {
    return con.prepareStatement(sql);
  }

}
