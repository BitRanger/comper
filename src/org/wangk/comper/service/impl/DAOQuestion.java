/*******************************************************************************
 * Copyright (c) 2014 WangKang.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *    WangKang. - initial API and implementation
 ******************************************************************************/
package org.wangk.comper.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.wangk.comper.db.jdbc.stmt.StatementCreator;
import org.wangk.comper.db.orm.RowMapping;
import org.wangk.comper.model.WKQuestionMeta;

public class DAOQuestion {

	
	
	public StatementCreator getUpdateStmt() {
		
		return new StatementCreator() {
			
			@Override
			public PreparedStatement createStatement(Connection con)
					throws SQLException {
				PreparedStatement ps = con.prepareStatement(
						"");
				return ps;
			}
		};
	}
	
//	public StatementCreator getInsertStmt() {
//		
//		return new StatementCreator() {
//			@Override
//			public PreparedStatement createStatement(Connection con)
//					throws SQLException {
//				PreparedStatement ps = con.prepareStatement(
//						"INSERT INTO wk_question"
//						+ "(id_paper, type, credit, difficulty, comment, content)"
//						+ "VALUES(?,?,?,?,?,?)");
//				ps.setInt(1, WKQuestion.this.id_paper);
////				ps.setInt(2, WKQuestionMeta.this.type);
//				ps.setInt(3, WKQuestion.this.score);
//				ps.setFloat(4, WKQuestion.this.difficulty);
//				ps.setString(5, WKQuestion.this.comment);
//				ps.setString(6, WKQuestion.this.content);
//				return ps;
//			}
//		};
//	}

//	public static final String SELECT = "SELECT id, id_paper, type, credit ";
//	public int				id;
//	public int				id_paper;
//	public int				type;
//	public int				credit;
//	public float			difficulty;
//	public String			comment;
//	public String			content;
	
	
	
	public static final RowMapping<WKQuestionMeta> MAPPING = new RowMapping<WKQuestionMeta>() {
		@Override
		public WKQuestionMeta rowToObjec(ResultSet rs) throws SQLException {
			WKQuestionMeta q = new WKQuestionMeta();
//			q.id = rs.getInt(1);
//			q.id_paper = rs.getInt(2);
////			q.type = rs.getInt(3);
//			q.score = rs.getInt(4);
//			q.difficulty = rs.getFloat(5);
//			q.comment = rs.getString(6);
//			q.content = rs.getString(7);
			return q;
		}
	};

}
