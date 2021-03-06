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
import org.blacklancer.comper.model.WKPaper;

public class DAOPaper {
//	public int				id;
//	
//	public String			name;
//	public String			description;
//	public int 				score;
//	public String			name_publisher;
//	public Timestamp 		time_published;
	@Inject JdbcAux jdbcAux;
	
	private static final String SELECT = 
	" SELECT id,name,description,score,name_publisher,time_published FROM wk_paper ";
	
	public List<WKPaper> getAll() {
		return jdbcAux.queryForList(SELECT, MAPPING);
	}
	public WKPaper get(int id) {
		return jdbcAux.queryForObject("select * from wk_paper where id = " + id, MAPPING);
	}
	public void addChapter(int paperId, int chapterId) {
		jdbcAux.execute("insert into r_paper_chapter(id_paper, id_chapter)values(?,?)",
				paperId, chapterId);
	}
	
	
	public void save(final WKPaper paper) {
		jdbcAux.execute(new StatementCreator() {
			@Override
			public PreparedStatement createStatement(Connection con)
					throws SQLException {
				PreparedStatement ps = con.prepareStatement(
				"insert into wk_paper(name, description, score, name_publisher)values(?,?,?,?)");
				ps.setString(1, paper.name);
				ps.setString(2, paper.description);
				ps.setInt(3, paper.score);
				ps.setString(4, paper.name_publisher);
				return ps;
			}
		});
	}
	
	public void update(final WKPaper paper) {
		jdbcAux.execute(new StatementCreator() {
			
			@Override
			public PreparedStatement createStatement(Connection con)
					throws SQLException {
				PreparedStatement ps = con.prepareStatement(
				"update wk_paper set name=?,description=?,score=?,name_publisher=? where id=?");
				ps.setString(1, paper.name);
				ps.setString(2, paper.description);
				ps.setInt(3, paper.score);
				ps.setString(4, paper.name_publisher);
				ps.setInt(5, paper.id);
				return ps;
			}
		});
	}
	
	public void delete(int id) {
		jdbcAux.execute("DELETE from wk_paper where id = ?", id);
		jdbcAux.execute("DELETE from wk_question_meta where id_paper = ?", id);
	}
	
	private static final RowMapping<WKPaper> MAPPING = new RowMapping<WKPaper>() {
		@Override
		public WKPaper rowToObjec(ResultSet rs) throws SQLException {
			WKPaper paper = new WKPaper();
			paper.id = rs.getInt("id");
			paper.name = rs.getString("name");
			paper.description = rs.getString("description");
			paper.score = rs.getInt("score");
			paper.name_publisher = rs.getString("name_publisher");
			paper.time_published = JdbcUtil.parseSQLiteTimestamp(rs.getString("time_published"));
			return paper;
		}
	};
	
	public JdbcAux getJdbcAux() {
		return jdbcAux;
	}

	public void setJdbcAux(JdbcAux jdbcAux) {
		this.jdbcAux = jdbcAux;
	}


}



