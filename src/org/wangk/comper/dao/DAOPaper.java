package org.wangk.comper.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import javax.inject.Inject;

import org.wangk.comper.db.jdbc.JdbcAux;
import org.wangk.comper.db.jdbc.JdbcUtil;
import org.wangk.comper.db.jdbc.stmt.StatementCreator;
import org.wangk.comper.db.orm.RowMapping;
import org.wangk.comper.model.WKPaper;

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
				"update wk_paper set name=?,description=?,score=?,name_publisher=?");
				ps.setString(1, paper.name);
				ps.setString(2, paper.description);
				ps.setInt(3, paper.score);
				ps.setString(4, paper.name_publisher);
				return ps;
			}
		});
	}
	public void delete(int id) {
		jdbcAux.execute("DELETE from wk_paper where id = ?", id);
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



