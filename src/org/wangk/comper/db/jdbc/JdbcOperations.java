/*******************************************************************************
 * Copyright 2014 Cai Bowen Zhou Liangpeng
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package org.wangk.comper.db.jdbc;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import org.wangk.comper.db.jdbc.stmt.BaseBuilder;
import org.wangk.comper.db.jdbc.stmt.StatementCreator;
import org.wangk.comper.db.jdbc.stmt.UpdateStatementCreator;
import org.wangk.comper.db.orm.ObjectMapping;
import org.wangk.comper.db.orm.RowMapping;



public interface JdbcOperations {

	boolean execute(final String sql);
	boolean execute(final String sql, Object...params);
	boolean execute(BaseBuilder builder);
	
	/**
	 * basic ();
	 * @param psc
	 * @return
	 */
	boolean execute(StatementCreator psc);
	
	int update(final String sql);
	int update(final String sql, Object...params);
	int update(BaseBuilder builder);
	int update(StatementCreator psc);
	<T> int update(T obj, ObjectMapping<T> mapper);
	
	/**
	 *  for insertion without returning keys, consider update();
	 * @param sql
	 * @param cols
	 * @return
	 */
	Map<String, Object> insert(final String sql, final String[] cols);
	Map<String, Object> insert(final String sql, final String[] cols, Object...params);
	Map<String, Object> insert(BaseBuilder builder, final String[] cols);
	
	/**
	 * T the object type, E: the result type, usually the generated K
	 * @param obj
	 * @param mapper
	 * @return
	 */
	<T> Map<String, Object> insert(final T obj, ObjectMapping<T> mapper, final String[] cols);
	/**
	 * base
	 * @param psc
	 * @param cols
	 * @return
	 */
	<T> Map<String, T> insert(UpdateStatementCreator psc, final String[] cols);
	int[] batchUpdate(final String... sql);
	
	
	
	<T> T queryForObject(final String sql, Class<T> type);
	<T> T queryForObject(final String sql, Class<T> type, final Object...params);
	<T> T queryForObject(BaseBuilder builder, Class<T> type);
	<T> T queryForObject(final StatementCreator psc, Class<T> type);
	
	<T> T queryForObject(final String sql, RowMapping<T>mapper);
	<T> T queryForObject(final String sql, RowMapping<T>mapper, Object...params);
	<T> T queryForObject(BaseBuilder builder, RowMapping<T>mapper);
	<T> T queryForObject(final StatementCreator psc, RowMapping<T>mapper);
	
	
	<T> List<T> queryForList(final String sql, Class<T> type);
	<T> List<T> queryForList(final String sql, Class<T> type, final Object...params);
	<T> List<T> queryForList(BaseBuilder builder, Class<T> type);
	<T> List<T> queryForList(final StatementCreator psc, Class<T> type);
	
	<T> List<T> queryForList(final String sql, RowMapping<T>mapper);
	<T> List<T> queryForList(final String sql, RowMapping<T>mapper, Object...params);
	<T> List<T> queryForList(BaseBuilder builder, RowMapping<T>mapper);
	<T> List<T> queryForList(final StatementCreator psc, RowMapping<T>mapper);
	
	Map<String, Object> queryForMap(final String sql);
	Map<String, Object> queryForMap(final String sql, Object...params);
	Map<String, Object> queryForMap(BaseBuilder builder);
	Map<String, Object> queryForMap(final StatementCreator psc);

	
	ResultSet queryForResultSet(final String sql);
	ResultSet queryForResultSet(final String sql, Object...params);
	ResultSet queryForResultSet(BaseBuilder builder);
	ResultSet queryForResultSet(final StatementCreator psc);
	
}
