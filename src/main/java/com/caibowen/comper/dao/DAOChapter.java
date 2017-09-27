package com.caibowen.comper.dao;

import com.caibowen.comper.db.jdbc.JdbcAux;
import com.caibowen.comper.db.jdbc.JdbcUtil;
import com.caibowen.comper.db.jdbc.stmt.StatementCreator;
import com.caibowen.comper.db.orm.RowMapping;
import com.caibowen.comper.model.BWChapter;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class DAOChapter {

  @Inject
  JdbcAux jdbcAux;

  private static final String SELECT =
      "SELECT id, name, description, time_created FROM bw_chapter ";

  public List<BWChapter> getAll() {
    return jdbcAux.queryForList(SELECT, MAPPING);
  }

  public BWChapter get(int id) {
    return jdbcAux.queryForObject("select * from bw_chapter where id = " + id, MAPPING);
  }

  public List<BWChapter> getByPaper(int id) {
    return jdbcAux.queryForList(
        SELECT + " as C inner join r_paper_chapter as R "
            + " on R.id_chapter = C.id "
            + " where R.id_paper = ? "
        , MAPPING
        , id);
  }

  // other chapters thant depend on this one
  public List<BWChapter> getDependBy(int id) {
    return jdbcAux.queryForList(
        SELECT + " as C1 join bw_chapter_depend as CD on CD.id_depend = C1.id " +
            " join bw_chapter as C2 on C2.id = CD.id_chapter where C1.id = ? ",
        MAPPING, id
    );
  }

  // chapters that this one is depended on
  public List<BWChapter> getDependance(int id) {
    return jdbcAux.queryForList(
        SELECT + "as C1 join bw_chapter_depend as CD on CD.id_chapter = C1.id " +
            " join bw_chapter as C2 on C2.id = CD.id_depend where C1.id = ? ",
        MAPPING, id
    );
  }


  public void save(final BWChapter chapter) {
    jdbcAux.execute(new StatementCreator() {
      @Override
      public PreparedStatement createStatement(Connection con)
          throws SQLException {
        PreparedStatement ps = con.prepareStatement(
            "insert into bw_chapter(name,description)values(?,?)");
        ps.setString(1, chapter.name);
        ps.setString(2, chapter.description);
        return ps;
      }
    });
  }

  public void update(final BWChapter chapter) {
    jdbcAux.update(new StatementCreator() {
      @Override
      public PreparedStatement createStatement(Connection con)
          throws SQLException {
        PreparedStatement ps = con.prepareStatement(
            "update bw_chapter set name=?,description=? where id=?");
        ps.setString(1, chapter.name);
        ps.setString(2, chapter.description);
        ps.setInt(3, chapter.id);
        return ps;
      }
    });
  }

  public void delete(int id) {
    jdbcAux.execute("delete from bw_chapter where id = ?", id);
    jdbcAux.execute("DELETE from bw_question_meta where id_chapter = ?", id);
  }

  private static final RowMapping<BWChapter> MAPPING = new RowMapping<BWChapter>() {

    @Override
    public BWChapter rowToObjec(ResultSet rs) throws SQLException {
      BWChapter chapter = new BWChapter();
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
