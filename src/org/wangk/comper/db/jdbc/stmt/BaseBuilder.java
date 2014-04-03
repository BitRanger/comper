/*******************************************************************************
 * Copyright (c) 2014 WangKang.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributor:
 *     WangKang. - initial API and implementation
 ******************************************************************************/
package org.wangk.comper.db.jdbc.stmt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;




public  class BaseBuilder {
	
	StringBuilder builder = null;
	String sqlCache = null;
	
	ArrayList<Object> 	parameters = null;

	
	
	public BaseBuilder() {
		builder = new StringBuilder(512);
		parameters = new ArrayList<>(8);
	}

	
	public void reset() {
		this.builder = new StringBuilder(128);
		this.sqlCache = null;
	}
	
	
	public PreparedStatement build(Connection connection) throws SQLException {
		
		if (this.sqlCache == null) {
			this.sqlCache = builder.toString();
		}
		
		PreparedStatement preparedStatement = connection.prepareStatement(sqlCache);
		for(int i = 0; i != parameters.size(); ++i) {
			preparedStatement.setObject(i + 1, parameters.get(i));
		}
		return preparedStatement;
	}
	
	public String getUnprepared() {
		if (this.sqlCache == null) {
			this.sqlCache = builder.toString();
		}
		return sqlCache;
	}
	
	public String toSQL() {
		if (this.hasParameters()) {
			throw new RuntimeException("parameters in SQL not set");
		} else {
			if (this.sqlCache == null) {
				this.sqlCache = builder.toString();
			}
			return sqlCache;
		}
	}
	@Override
	public String toString() {
		return builder.toString();
	}
	public ArrayList<Object> getParameters() {
		return parameters;
	}
	
	public boolean hasParameters() {
		return this.parameters.size() == 0;
	}
	
	@Override
	public int 
	hashCode() {
		return builder.hashCode() * 31 + parameters.hashCode();
	}
	
}
//	
//	/**
//	 *  prevent SQL injection
//	 * @param parameter to be inserted into SQL statement
//	 * @return a quoted string
//	 */
//	public static final String quote(final String param) {
//		
//		if (param == null) {
//			throw new NullPointerException("");
//		} else {
//
//			int paraLen = param.length();
//			StringBuffer buf = new StringBuffer((int) (paraLen * 6 / 5));
//			buf.append('\'');
//			//
//			// Note: buf.append(char) is _faster_ than
//			// appending in blocks, because the block
//			// append requires a System.arraycopy()....
//			// go figure...
//			//
//			for (int i = 0; i < paraLen; ++i) {
//				char c = param.charAt(i);
//
//				switch (c) {
////				case 0: /* Must be escaped for 'mysql' */
////					buf.append('\\');
////					buf.append('0');
////
////					break;
//
//				case '\n': /* Must be escaped for logs */
//					buf.append('\\');
//					buf.append('n');
//
//					break;
//
//				case '\r':
//					buf.append('\\');
//					buf.append('r');
//
//					break;
//
//				case '\\':
//					buf.append('\\');
//					buf.append('\\');
//
//					break;
//
//				case '\'':
//					buf.append('\\');
//					buf.append('\'');
//
//					break;
//
////this.usingAnsiMode = !this.connection.useAnsiQuotedIdentifiers();
//					
//				case '"': /* Better safe than sorry */
////					if (this.usingAnsiMode) {
////						buf.append('\\');
////					}
//
//					buf.append('"');
//
//					break;
//
//				case '\032': /* This gives problems on Win32 */
//					buf.append('\\');
//					buf.append('Z');
//
//					break;
//
//				default:
//					buf.append(c);
//				} // switch
//			} // for
//
//			buf.append('\'');
//			return buf.toString();
//		} // else
//	} // func: quote


