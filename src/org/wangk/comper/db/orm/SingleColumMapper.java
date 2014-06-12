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
import java.sql.SQLException;

import org.wangk.comper.db.jdbc.JdbcUtil;


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

assert(rs.getMetaData().getColumnCount() == 1);

		T o = (T) JdbcUtil.getResultSetValue(rs, 1, type);
		if (o != null && type.isInstance(o)) {
			return o;
		} else {
			throw new RuntimeException(
					"failed to get requested value: type mismatch");
		}
	}

}




