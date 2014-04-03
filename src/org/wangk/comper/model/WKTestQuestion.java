package org.wangk.comper.model;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.wangk.comper.db.orm.RowMapping;

public class WKTestQuestion implements Serializable {

	public static class Type {
		public static final int MULTI_CHOICE 	= 0;
		public static final int FILL_BLANKS 	= 1;
		public static final int SIMPLE_QA 		= 2;
		public static final int ADDITIONAL 		= 3;
	}
	
	public int				id;
	public int				type;
	public int				credit;
	public float			difficulty;
	public String			description;
	public int				id_paper;
	
	
	
	
	private static final long serialVersionUID = -6048408374797399234L;
	
	public static final RowMapping<WKTestQuestion> MAPPING = new RowMapping<WKTestQuestion>() {
		@Override
		public WKTestQuestion rowToObjec(ResultSet rs) throws SQLException {
			WKTestQuestion q = new WKTestQuestion();
			return q;
		}
	};
}
