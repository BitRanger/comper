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
package org.wangk.comper.db.jdbc.stmt;

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
