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
package org.wangk.comper.model;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.wangk.comper.db.orm.RowMapping;

public class WKPaper implements Serializable {
	
	private static final long serialVersionUID = 4110862638764903675L;
	
	public static final RowMapping<WKPaper> MAPPING = new RowMapping<WKPaper>() {
		@Override
		public WKPaper rowToObjec(ResultSet rs) throws SQLException {
			WKPaper paper = new WKPaper();
			return paper;
		}
	};

}
