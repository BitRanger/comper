
package com.caibowen.comper.dao;

import com.caibowen.comper.db.jdbc.JdbcAux;
import com.caibowen.comper.db.jdbc.JdbcUtil;
import com.caibowen.comper.db.jdbc.stmt.StatementCreator;
import com.caibowen.comper.db.orm.RowMapping;
import com.caibowen.comper.feature.model.QuestionType;
import com.caibowen.comper.model.BWQuestionContent;
import com.caibowen.comper.model.BWQuestionMeta;
import com.caibowen.comper.util.Triple;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class DAOQuestion {

  //	difficulty
  private static final String SELECT =
      "SELECT id, id_paper, id_chapter, type, difficulty, score FROM bw_question_meta ";

  @Inject
  JdbcAux jdbcAux;


  public Triple<String, String, String> getContent(int id) {

    return jdbcAux.queryForObject(
        "select * from bw_question where id_meta = " + id,

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


  public List<BWQuestionMeta> getAll() {
    List<BWQuestionMeta> list = jdbcAux.queryForList(new StatementCreator() {
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

  public void save(final BWQuestionMeta meta) {
    jdbcAux.execute(getInsertStmt(meta));
  }

  public void saveWithContent(final BWQuestionMeta meta,
                              final BWQuestionContent qContent) {
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

  public void saveQuestionContent(final int id, final BWQuestionContent qContent) {
    jdbcAux.execute(new StatementCreator() {
      @Override
      public PreparedStatement createStatement(Connection con)
          throws SQLException {
        PreparedStatement st = con.prepareStatement(
            "insert into bw_question(id_meta, content, answer, comment)values(?,?,?,?)");
        st.setInt(1, id);
        st.setString(2, qContent.content);
        st.setString(3, qContent.answer);
        st.setString(4, qContent.comment);
        return st;
      }
    });
  }

  public void update(final BWQuestionMeta meta) {
    jdbcAux.execute(getUpdateStmt(meta));
  }

  public void updateContent(final int metaId, final BWQuestionContent content) {
    jdbcAux.update(new StatementCreator() {
      @Override
      public PreparedStatement createStatement(Connection con)
          throws SQLException {
        PreparedStatement ps = con.prepareStatement(
            "update bw_question set content=?, answer=?,comment=? where id_mate=?");
        ps.setString(1, content.content);
        ps.setString(2, content.answer);
        ps.setString(3, content.comment);
        ps.setInt(4, metaId);
        return ps;
      }
    });
  }

  public void delete(int id) {
    jdbcAux.execute("DELETE FROM bw_question where id_meta = ?", id);
    jdbcAux.execute("DELETE FROM bw_question_meta where id = ?", id);
  }

  public JdbcAux getJdbcAux() {
    return jdbcAux;
  }

  public void setJdbcAux(JdbcAux jdbcAux) {
    this.jdbcAux = jdbcAux;
  }

  public static StatementCreator getUpdateStmt(final BWQuestionMeta meta) {

    return new StatementCreator() {

      @Override
      public PreparedStatement createStatement(Connection con)
          throws SQLException {
        PreparedStatement ps = con.prepareStatement(
            "update bw_question_meta "
                + "set id_paper=?, id_chapter=?, type=?,"
                + "difficulty=?, score=? where id=?");
        ps.setInt(1, meta.id_paper);
        ps.setInt(2, meta.id_chapter);
        ps.setInt(3, meta.type.intValue());
        ps.setDouble(4, meta.difficulty);
        ps.setInt(5, meta.score);
        ps.setInt(6, meta.id);
        return ps;
      }
    };
  }


  public static final String[] RET_ID = {"id"};

  public static StatementCreator getInsertStmt(final BWQuestionMeta meta) {

    return new StatementCreator() {
      @Override
      public PreparedStatement createStatement(Connection con)
          throws SQLException {
        PreparedStatement ps = con.prepareStatement(
            "INSERT INTO bw_question_meta"
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


  public static final RowMapping<BWQuestionMeta> MAPPING = new RowMapping<BWQuestionMeta>() {
    @Override
    public BWQuestionMeta rowToObjec(ResultSet rs) throws SQLException {
      BWQuestionMeta q = new BWQuestionMeta();
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
