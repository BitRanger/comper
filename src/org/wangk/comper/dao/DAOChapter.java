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
import org.wangk.comper.model.WKChapter;
import org.wangk.comper.model.WKQuestionMeta;

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
