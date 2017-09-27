
package com.caibowen.comper.db.jdbc.stmt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface UpdateStatementCreator extends StatementCreator {


  PreparedStatement createUpdate(Connection con, final String[] cols) throws SQLException;
}
