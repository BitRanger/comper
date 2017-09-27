
package com.caibowen.comper.db.orm;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface RowMapping<T> {

  T rowToObjec(ResultSet rs) throws SQLException;
}
