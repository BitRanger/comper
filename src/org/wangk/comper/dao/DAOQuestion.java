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
package org.wangk.comper.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import org.wangk.comper.db.jdbc.JdbcAux;
import org.wangk.comper.db.jdbc.JdbcUtil;
import org.wangk.comper.db.jdbc.stmt.StatementCreator;
import org.wangk.comper.db.orm.RowMapping;
import org.wangk.comper.feature.model.QuestionType;
import org.wangk.comper.model.WKQuestionContent;
import org.wangk.comper.model.WKQuestionMeta;
import org.wangk.comper.util.Triple;



public class DAOQuestion {

	//	difficulty
	private static final String SELECT = 
		"SELECT id, id_paper, id_chapter, type, difficulty, score FROM wk_question_meta ";
	
	@Inject JdbcAux jdbcAux;
	
	
	public Triple<String, String, String> getContent(int id) {
		
		return jdbcAux.queryForObject(
		"select * from wk_question where id_meta = " + id, 
		
		new RowMapping<Triple<String, String, String>>() {
			@Override
			public Triple<String, String, String> rowToObjec(ResultSet arg0) throws SQLException {
				Triple<String, String, String> t = new Triple<>();
				t.first = arg0.getString("content");
				t.second = arg0.getString("answer");
				t.third = arg0.getString("comment");
				return t;
			}
		});
	}
	
	
	public List<WKQuestionMeta> getAll() {
		List<WKQuestionMeta> list = jdbcAux.queryForList(new StatementCreator() {
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
	
	public void saveWithContent(final WKQuestionMeta meta, 
								final WKQuestionContent qContent) {
		try {
			Connection connection = jdbcAux.getConnection();
			PreparedStatement st = getInsertStmt(meta).createStatement(connection);
			st.execute();
			int id = 0;
			ResultSet rs = st.getGeneratedKeys();
			while (rs.next()) {
				id = rs.getInt(1);
			}
			JdbcUtil.closeResultSet(rs);
			JdbcUtil.closeStatement(st);
			JdbcUtil.closeConnection(connection);
			saveQuestionContent(id, qContent);
		} catch (SQLException e) {
			throw new RuntimeException(e.getSQLState() + "\nCaused by\n"
					+ e.getCause(), e);
		}
	}
	public void saveQuestionContent(final int id, final WKQuestionContent qContent) {
		jdbcAux.execute(new StatementCreator() {
			@Override
			public PreparedStatement createStatement(Connection con)
					throws SQLException {
				PreparedStatement st = con.prepareStatement(
						"insert into wk_question(id_meta, content, answer, comment)values(?,?,?,?)");
						st.setInt(1, id);
						st.setString(2, qContent.content);
						st.setString(3, qContent.answer);
						st.setString(4, qContent.comment);
				return st;
			}
		});
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
						"update wk_question_meta "
						+ "set id_paper=?, id_chapter=?, type=?,"
						+ "difficulty=?, score=?");
				ps.setInt(1, meta.id_paper);
				ps.setInt(2, meta.id_chapter);
				ps.setInt(3, meta.type.intValue());
				ps.setDouble(4, meta.difficulty);
				ps.setInt(5, meta.score);
				return ps;
			}
		};
	}
	

	public static final String[] RET_ID = {"id"};
	
	public static StatementCreator getInsertStmt(final WKQuestionMeta meta ) {
		
		return new StatementCreator() {
			@Override
			public PreparedStatement createStatement(Connection con)
					throws SQLException {
				PreparedStatement ps = con.prepareStatement(
						"INSERT INTO wk_question_meta"
						+ "(id_paper, id_chapter, type, difficulty, score)"
						+ "VALUES(?,?,?,?,?)", RET_ID);
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
