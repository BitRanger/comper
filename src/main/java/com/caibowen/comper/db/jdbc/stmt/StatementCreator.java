
package com.caibowen.comper.db.jdbc.stmt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface StatementCreator {


  PreparedStatement createStatement(Connection con) throws SQLException;
}
