
package com.caibowen.comper.db.orm;

import com.caibowen.comper.db.jdbc.stmt.UpdateStatementCreator;


public interface ObjectMapping<T> {


  UpdateStatementCreator insert(T obj);

  UpdateStatementCreator update(T obj);

}
