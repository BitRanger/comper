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
package org.blacklancer.comper.db.jdbc.stmt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class SimplePreparedStatementCreator implements StatementCreator {

	String sql;

	public SimplePreparedStatementCreator(String sql) {
		this.sql = sql;
	}

	@Override
	public PreparedStatement createStatement(Connection con)
			throws SQLException {
		return con.prepareStatement(sql);
	}

}
