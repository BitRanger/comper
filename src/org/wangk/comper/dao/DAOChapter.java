package org.wangk.comper.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import org.wangk.comper.db.jdbc.JdbcAux;
import org.wangk.comper.db.jdbc.stmt.StatementCreator;
import org.wangk.comper.db.orm.RowMapping;
import org.wangk.comper.model.WKChapter;

public class DAOChapter {

	@Inject JdbcAux jdbcAux;
	
	private static final String SELECT = 
	"SELECT id, name, description, time_created FROM wk_chapter ";
	
	public List<WKChapter> getAll() {
		return jdbcAux.queryForList(SELECT, MAPPING);
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
	
	public void delete(int id) {
		jdbcAux.execute("delete from wk_chapter where id = ?", id);
	}
	
	private static final RowMapping<WKChapter> MAPPING = new RowMapping<WKChapter>() {

		@Override
		public WKChapter rowToObjec(ResultSet rs) throws SQLException {
			WKChapter chapter = new WKChapter();
			chapter.id = rs.getInt("id");
			chapter.name = rs.getString("name");
			chapter.description = rs.getString("description");
			chapter.time_created = rs.getTimestamp("time_created");
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
