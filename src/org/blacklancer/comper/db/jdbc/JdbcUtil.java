/*******************************************************************************
 * Copyright (c) 2014 Cai Bowen, Zhou Liangpeng.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v2.1
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Cai Bowen,  Zhou Liangpeng. - initial API and implementation
 ******************************************************************************/
package org.blacklancer.comper.db.jdbc;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class JdbcUtil {

	public static final SimpleDateFormat SQLITE_DATE_FMT = 
			new SimpleDateFormat("YYYY-MM-DD HH:MM:SS");
	public static Timestamp parseSQLiteTimestamp(String dd) {
		try {
			return new Timestamp(SQLITE_DATE_FMT.parse(dd).getTime());
		} catch (Exception e) {
			return new Timestamp(System.currentTimeMillis());
		}
	}
	public static void closeConnection(Connection con) {
		
		if (con != null) {
			try {
				con.close();
			}
			catch (SQLException e) {
				throw new RuntimeException(e.getSQLState() 
										+ "\nCaused by\n"
										+ e.getCause(), e);
			}
			finally {
				try {
					con.close();
				} catch (SQLException e) {
					throw new RuntimeException(e.getSQLState() 
						+ "\nCaused by\n"
						+ e.getCause(), e);
				}
			}
		}
	}


	public static void closeStatement(Statement stmt) {

		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {				
				throw new RuntimeException(e.getSQLState() 
					+ "\nCaused by\n"
					+ e.getCause(), e);
			}
		}
	}


	public static void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new RuntimeException(e.getSQLState() 
						+ "\nCaused by\n"
						+ e.getCause(), e);
			}
		}
	}
	/**
	 *  adopted from springframework
	 * @param con
	 * @return
	 */
	public static boolean supportsBatchUpdates(Connection con) {
		try {
			DatabaseMetaData dbmd = con.getMetaData();
			if (dbmd != null) {
				return dbmd.supportsBatchUpdates();
			}
		}
		catch (SQLException e) {
			throw new RuntimeException(e.getSQLState() 
					+ "\nCaused by\n"
					+ e.getCause(), e);
		}
		return false;
	}
	/**
	 *  adopted from springframework
	 * @param con
	 * @return
	 */
	public static String lookupColumnName(ResultSetMetaData resultSetMetaData, int columnIndex) throws SQLException {
		String name = resultSetMetaData.getColumnLabel(columnIndex);
		if (name == null || name.length() < 1) {
			name = resultSetMetaData.getColumnName(columnIndex);
		}
		return name;
	}
	/**
	 *  adopted from springframework
	 * @param con
	 * @return
	 */
	public static Object getResultSetValue(ResultSet rs, int index) throws SQLException {
		Object obj = rs.getObject(index);
		String className = null;
		if (obj != null) {
			className = obj.getClass().getName();
		}
		if (obj instanceof Blob) {
			obj = rs.getBytes(index);
		}
		else if (obj instanceof Clob) {
			obj = rs.getString(index);
		}
		else if (className != null &&
				("oracle.sql.TIMESTAMP".equals(className) ||
				"oracle.sql.TIMESTAMPTZ".equals(className))) {
			obj = rs.getTimestamp(index);
		}
		else if (className != null && className.startsWith("oracle.sql.DATE")) {
			String metaDataClassName = rs.getMetaData().getColumnClassName(index);
			if ("java.sql.Timestamp".equals(metaDataClassName) ||
					"oracle.sql.TIMESTAMP".equals(metaDataClassName)) {
				obj = rs.getTimestamp(index);
			}
			else {
				obj = rs.getDate(index);
			}
		}
		else if (obj != null && obj instanceof java.sql.Date) {
			if ("java.sql.Timestamp".equals(rs.getMetaData().getColumnClassName(index))) {
				obj = rs.getTimestamp(index);
			}
		}
		return obj;
	}
	
	/**
	 *  adopted from springframework
	 * @param con
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "boxing" })
	public static Object getResultSetValue(ResultSet rs, 
											int index, 
											Class requiredType) throws SQLException {
		if (requiredType == null) {
			return getResultSetValue(rs, index);
		}

		Object value = null;
		boolean wasNullCheck = false;

		// Explicitly extract typed value, as far as possible.
		if (String.class.equals(requiredType)) {
			value = rs.getString(index);
		}
		else if (boolean.class.equals(requiredType) 
					|| Boolean.class.equals(requiredType)) {
			value = rs.getBoolean(index);
			wasNullCheck = true;
		}
		else if (byte.class.equals(requiredType) 
					|| Byte.class.equals(requiredType)) {
			value = rs.getByte(index);
			wasNullCheck = true;
		}
		else if (short.class.equals(requiredType) 
					|| Short.class.equals(requiredType)) {
			value = rs.getShort(index);
			wasNullCheck = true;
		}
		else if (int.class.equals(requiredType) 
					|| Integer.class.equals(requiredType)) {
			value = rs.getInt(index);
			wasNullCheck = true;
		}
		else if (long.class.equals(requiredType) 
					|| Long.class.equals(requiredType)) {
			value = rs.getLong(index);
			wasNullCheck = true;
		}
		else if (float.class.equals(requiredType) 
					|| Float.class.equals(requiredType)) {
			value = rs.getFloat(index);
			wasNullCheck = true;
		}
		else if (double.class.equals(requiredType) 
					|| Double.class.equals(requiredType) 
					|| Number.class.equals(requiredType)) {
			value = rs.getDouble(index);
			wasNullCheck = true;
		}
		else if (byte[].class.equals(requiredType)) {
			value = rs.getBytes(index);
		}
		else if (java.sql.Date.class.equals(requiredType)) {
			value = rs.getDate(index);
		}
		else if (java.sql.Time.class.equals(requiredType)) {
			value = rs.getTime(index);
		}
		else if (java.sql.Timestamp.class.equals(requiredType) 
					|| java.util.Date.class.equals(requiredType)) {
			value = rs.getTimestamp(index);
		}
		else if (BigDecimal.class.equals(requiredType)) {
			value = rs.getBigDecimal(index);
		}
		else if (Blob.class.equals(requiredType)) {
			value = rs.getBlob(index);
		}
		else if (Clob.class.equals(requiredType)) {
			value = rs.getClob(index);
		}
		else {
			// Some unknown type desired -> rely on getObject.
			value = getResultSetValue(rs, index);
		}

		// Perform was-null check if demanded (for results that the
		// JDBC driver returns as primitives).
		if (wasNullCheck && value != null && rs.wasNull()) {
			value = null;
		}
		return value;
	}
}
