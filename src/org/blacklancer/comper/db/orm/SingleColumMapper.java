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
package org.blacklancer.comper.db.orm;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.blacklancer.comper.db.jdbc.JdbcUtil;


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




