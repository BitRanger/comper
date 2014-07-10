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
package org.blacklancer.comper.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import org.blacklancer.comper.db.jdbc.JdbcAux;
import org.blacklancer.comper.db.jdbc.JdbcUtil;
import org.blacklancer.comper.db.jdbc.stmt.StatementCreator;
import org.blacklancer.comper.db.orm.RowMapping;
import org.blacklancer.comper.model.WKChapter;
import org.blacklancer.comper.model.WKQuestionMeta;

public class DAOChapter {

	@Inject JdbcAux jdbcAux;
	
	private static final String SELECT = 
	"SELECT id, name, description, time_created FROM wk_chapter ";
	
	public List<WKChapter> getAll() {
		return jdbcAux.queryForList(SELECT, MAPPING);
	}
	
	public WKChapter get(int id) {
		return jdbcAux.queryForObject("select * from wk_chapter where id = " + id, MAPPING);
	}
	
	public List<WKChapter> getByPaper(int id) {
		return jdbcAux.queryForList(
				SELECT + " as C inner join r_paper_chapter as R "
				+ " on R.id_chapter = C.id "
				+ " where R.id_paper = ? "
				, MAPPING
				, id);
	}
	
	public void save(final WKChapter chapter) {
		jdbcAux.execute(new StatementCreator() {
			@Override
			public PreparedStatement createStatement(Connection con)
					throws SQLException {
				PreparedStatement ps = con.prepareStatement(
				"insert into wk_chapter(name,description)values(?,?)");
				ps.setString(1, chapter.name);
				ps.setString(2, chapter.description);
				return ps;
			}
		});
	}
	
	public void update(final WKChapter chapter) {
		jdbcAux.update(new StatementCreator() {
			@Override
			public PreparedStatement createStatement(Connection con)
					throws SQLException {
				PreparedStatement ps = con.prepareStatement(
				"update wk_chapter set name=?,description=? where id=?");
				ps.setString(1, chapter.name);
				ps.setString(2, chapter.description);
				ps.setInt(3, chapter.id);
				return ps;
			}
		});
	}
	
	public void delete(int id) {
		jdbcAux.execute("delete from wk_chapter where id = ?", id);
		jdbcAux.execute("DELETE from wk_question_meta where id_chapter = ?", id);
	}
	
	private static final RowMapping<WKChapter> MAPPING = new RowMapping<WKChapter>() {

		@Override
		public WKChapter rowToObjec(ResultSet rs) throws SQLException {
			WKChapter chapter = new WKChapter();
			chapter.id = rs.getInt("id");
			chapter.name = rs.getString("name");
			chapter.description = rs.getString("description");
//			chapter.time_created = rs.getTimestamp("time_created");
			chapter.time_created = JdbcUtil.parseSQLiteTimestamp(rs.getString("time_created"));
			return chapter;
		}
	};

	public JdbcAux getJdbcAux() {
		return jdbcAux;
	}

	public void setJdbcAux(JdbcAux jdbcAux) {
		this.jdbcAux = jdbcAux;
	}
}
