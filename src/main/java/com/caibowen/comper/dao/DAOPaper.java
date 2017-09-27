
package com.caibowen.comper.dao;

import com.caibowen.comper.db.jdbc.JdbcAux;
import com.caibowen.comper.db.jdbc.JdbcUtil;
import com.caibowen.comper.db.orm.RowMapping;
import com.caibowen.comper.model.BWPaper;
import com.caibowen.comper.db.jdbc.stmt.StatementCreator;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DAOPaper {
  //	public int				id;
//	
//	public String			name;
//	public String			description;
//	public int 				score;
//	public String			name_publisher;
//	public Timestamp 		time_published;
  @Inject
  JdbcAux jdbcAux;

  private static final String SELECT =
      " SELECT id,name,description,score,name_publisher,time_published FROM bw_paper ";

  public List<BWPaper> getAll() {
    return jdbcAux.queryForList(SELECT, MAPPING);
  }

  public BWPaper get(int id) {
    return jdbcAux.queryForObject("select * from bw_paper where id = " + id, MAPPING);
  }

  public void addChapter(int paperId, int chapterId) {
    jdbcAux.execute("insert into r_paper_chapter(id_paper, id_chapter)values(?,?)",
        paperId, chapterId);
  }


  public void save(final BWPaper paper) {
    jdbcAux.execute(new StatementCreator() {
      @Override
      public PreparedStatement createStatement(Connection con)
          throws SQLException {
        PreparedStatement ps = con.prepareStatement(
            "insert into bw_paper(name, description, score, name_publisher)values(?,?,?,?)");
        ps.setString(1, paper.name);
        ps.setString(2, paper.description);
        ps.setInt(3, paper.score);
        ps.setString(4, paper.name_publisher);
        return ps;
      }
    });
  }

  public void update(final BWPaper paper) {
    jdbcAux.execute(new StatementCreator() {

      @Override
      public PreparedStatement createStatement(Connection con)
          throws SQLException {
        PreparedStatement ps = con.prepareStatement(
            "update bw_paper set name=?,description=?,score=?,name_publisher=? where id=?");
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
    jdbcAux.execute("DELETE from bw_paper where id = ?", id);
    jdbcAux.execute("DELETE from bw_question_meta where id_paper = ?", id);
  }

  private static final RowMapping<BWPaper> MAPPING = new RowMapping<BWPaper>() {
    @Override
    public BWPaper rowToObjec(ResultSet rs) throws SQLException {
      BWPaper paper = new BWPaper();
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



