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
package org.wangk.comper.db.orm;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Map;

import org.wangk.comper.db.jdbc.JdbcUtil;
import org.wangk.comper.db.jdbc.collection.NoCaseMap;


public class ColumnMapper implements RowMapping<Map<String, Object>>{


	@Override
	public Map<String, Object> rowToObjec(ResultSet rs) throws SQLException {
		ResultSetMetaData md = rs.getMetaData();
		int columnCount = md.getColumnCount();


//		NoCaseMap m = new NoCaseMap(columnCount);
//		NoCaseMap<Object> m = new NoCaseMap<>(columnCount);
		NoCaseMap<Object> m = new NoCaseMap<Object>(columnCount);
		
		for (int i = 1; i <= columnCount; ++i) {
			String key = JdbcUtil.lookupColumnName(md, i);
			Object var = JdbcUtil.getResultSetValue(rs, i);
			m.put(key, var);
		}
		return m;
	}

}


//public class ColumnMapRowMapper implements RowMapper<Map<String, Object>> {
//
//	@Override
//	public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
//		ResultSetMetaData rsmd = rs.getMetaData();
//		int columnCount = rsmd.getColumnCount();
//		Map<String, Object> mapOfColValues = createColumnMap(columnCount);
//		for (int i = 1; i <= columnCount; i++) {
//			String key = getColumnKey(JdbcUtils.lookupColumnName(rsmd, i));
//			Object obj = getColumnValue(rs, i);
//			mapOfColValues.put(key, obj);
//		}
//		return mapOfColValues;
//	}
//
//	/**
//	 * Create a Map instance to be used as column map.
//	 * <p>By default, a linked case-insensitive Map will be created.
//	 * @param columnCount the column count, to be used as initial
//	 * capacity for the Map
//	 * @return the new Map instance
//	 * @see org.springframework.util.LinkedCaseInsensitiveMap
//	 */
//	protected Map<String, Object> createColumnMap(int columnCount) {
//		return new LinkedCaseInsensitiveMap<Object>(columnCount);
//	}
//
//	/**
//	 * Determine the key to use for the given column in the column Map.
//	 * @param columnName the column name as returned by the ResultSet
//	 * @return the column key to use
//	 * @see java.sql.ResultSetMetaData#getColumnName
//	 */
//	protected String getColumnKey(String columnName) {
//		return columnName;
//	}
//
//	/**
//	 * Retrieve a JDBC object value for the specified column.
//	 * <p>The default implementation uses the {@code getObject} method.
//	 * Additionally, this implementation includes a "hack" to get around Oracle
//	 * returning a non standard object for their TIMESTAMP datatype.
//	 * @param rs is the ResultSet holding the data
//	 * @param index is the column index
//	 * @return the Object returned
//	 * @see org.springframework.jdbc.support.JdbcUtils#getResultSetValue
//	 */
//	protected Object getColumnValue(ResultSet rs, int index) throws SQLException {
//		return JdbcUtils.getResultSetValue(rs, index);
//	}
//
//}
