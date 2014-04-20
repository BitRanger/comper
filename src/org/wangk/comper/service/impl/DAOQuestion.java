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
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.wangk.comper.db.jdbc.JdbcAux;
import org.wangk.comper.db.jdbc.stmt.StatementCreator;
import org.wangk.comper.db.orm.RowMapping;
import org.wangk.comper.feature.model.QuestionType;
import org.wangk.comper.model.WKQuestionMeta;

public class DAOQuestion {

	//																			 difficulty
	private static final String SELECT = 
		"SELECT id, id_paper, id_chapter, type, difficulty, score FROM wk_question_meta";
	
	@Inject JdbcAux jdbcAux;
	
	public List<WKQuestionMeta> getAll() {
		List<WKQuestionMeta> list = new ArrayList<>(48);
		jdbcAux.queryForList(new StatementCreator() {
			@Override
			public PreparedStatement createStatement(Connection con)
					throws SQLException {
				PreparedStatement ps = con.prepareStatement(
						SELECT);
				return ps;
			}
		}, MAPPING);
		return list;
	}
	
	public void save(final WKQuestionMeta meta) {
		jdbcAux.execute(getInsertStmt(meta));
	}
	
	public void update(final WKQuestionMeta meta) {
		jdbcAux.execute(getUpdateStmt(meta));
	}
	
	public void delete(int id) {
		jdbcAux.execute("DELETE FROM wk_question_meta where id = ?", id);
	}
	
	public JdbcAux getJdbcAux() {
		return jdbcAux;
	}

	public void setJdbcAux(JdbcAux jdbcAux) {
		this.jdbcAux = jdbcAux;
	}

	public static StatementCreator getUpdateStmt(final WKQuestionMeta meta) {
		
		return new StatementCreator() {
			
			@Override
			public PreparedStatement createStatement(Connection con)
					throws SQLException {
				PreparedStatement ps = con.prepareStatement(
						"update wk_question_meta(id_paper, id_chapter, type, difficulty, score)"
						+ "values(?,?,?,?,?)");
				ps.setInt(1, meta.id_paper);
				ps.setInt(2, meta.id_chapter);
				ps.setInt(3, meta.type.intValue());
				ps.setDouble(4, meta.difficulty);
				ps.setInt(5, meta.score);
				return ps;
			}
		};
	}
	
	public static StatementCreator getInsertStmt(final WKQuestionMeta meta ) {
		
		return new StatementCreator() {
			@Override
			public PreparedStatement createStatement(Connection con)
					throws SQLException {
				PreparedStatement ps = con.prepareStatement(
						"INSERT INTO wk_question_meta"
						+ "(id_paper, id_chapter, type, difficulty, score)"
						+ "VALUES(?,?,?,?,?)");
				ps.setInt(1, meta.id_paper);
				ps.setInt(2, meta.id_chapter);
				ps.setInt(3, meta.type.intValue());
				ps.setDouble(4, meta.difficulty);
				ps.setInt(5, meta.score);
				return ps;
			}
		};
	}


	
	
	public static final RowMapping<WKQuestionMeta> MAPPING = new RowMapping<WKQuestionMeta>() {
		@Override
		public WKQuestionMeta rowToObjec(ResultSet rs) throws SQLException {
			WKQuestionMeta q = new WKQuestionMeta();
			q.id = rs.getInt(1);
			q.id_paper = rs.getInt(2);
			q.id_chapter = rs.getInt(3);
			q.type = QuestionType.lookup(rs.getInt(4));
			q.difficulty = rs.getFloat(5);
			q.score = rs.getInt(6);
			return q;
		}
	};

}
