
package com.caibowen.comper.db.orm;

import com.caibowen.comper.db.jdbc.JdbcUtil;

import java.sql.ResultSet;
import java.sql.SQLException;


public class SingleColumMapper<T> implements RowMapping<T> {

  Class<T> type;

  public SingleColumMapper(Class<T> type) {
    this.type = type;
  }

  public Class<T> getType() {
    return type;
  }

  public void setType(Class<T> type) {
    this.type = type;
  }

  @SuppressWarnings("unchecked")
  @Override
  public T rowToObjec(ResultSet rs) throws SQLException {

    assert (rs.getMetaData().getColumnCount() == 1);

    T o = (T) JdbcUtil.getResultSetValue(rs, 1, type);
    if (o != null && type.isInstance(o)) {
      return o;
    } else {
      throw new RuntimeException(
          "failed to get requested value: type mismatch");
    }
  }

}




