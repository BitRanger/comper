package org.wangk.comper.model;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.wangk.comper.db.orm.RowMapping;

public class WKTestQuestion implements Serializable {

	private static final long serialVersionUID = -6048408374797399234L;
	
	public static final RowMapping<WKTestQuestion> MAPPING = new RowMapping<WKTestQuestion>() {
		@Override
		public WKTestQuestion rowToObjec(ResultSet rs) throws SQLException {
			WKTestQuestion q = new WKTestQuestion();
			return q;
		}
	};
}
