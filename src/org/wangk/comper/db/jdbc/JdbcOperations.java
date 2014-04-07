/*******************************************************************************
 * Copyright (c) 2014 WangKang.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *    WangKang. - initial API and implementation
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
