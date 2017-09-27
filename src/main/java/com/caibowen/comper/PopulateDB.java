package com.caibowen.comper;

import com.caibowen.comper.config.InputStreamCallback;
import com.caibowen.comper.config.InputStreamProvider;
import com.caibowen.comper.config.InputStreamSupport;
import com.caibowen.comper.dao.DAOChapter;
import com.caibowen.comper.dao.DAOPaper;
import com.caibowen.comper.dao.DAOQuestion;
import com.caibowen.comper.db.jdbc.JdbcAux;
import com.caibowen.comper.feature.impl.RandomGenerator;
import com.caibowen.comper.feature.model.QuestionType;
import com.caibowen.comper.model.BWChapter;
import com.caibowen.comper.model.BWQuestionMeta;
import com.caibowen.comper.util.Pair;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by superbow on 9/15/2017.
 */
public class PopulateDB {

  JdbcAux jdbc;
  DataSource ds;

  DAOPaper daoPaper;
  DAOQuestion daoQuestion;
  DAOChapter daoChapter;

  RandomGenerator rand = new RandomGenerator();

  public void setUp() throws Exception {
    AppContext.setUp();

    jdbc = AppContext.beanAssembler.getBean("jdbcAux");
    ds = AppContext.beanAssembler.getBean("datasource");

    daoPaper = AppContext.beanAssembler.getBean("daoPaper");
    daoQuestion = AppContext.beanAssembler.getBean("daoQuestion");
    daoChapter = AppContext.beanAssembler.getBean("daoChapter");

    createTables();

    jdbc.execute("DELETE FROM bw_tag_question");
    jdbc.execute("DELETE FROM bw_tag");
    jdbc.execute("DELETE FROM bw_question");
    jdbc.execute("DELETE FROM bw_question_meta");
    jdbc.execute("DELETE FROM bw_chapter_depend");
    jdbc.execute("DELETE FROM bw_chapter");
    jdbc.execute("DELETE FROM bw_paper");


    insertTests(40);
    insertChapters(15);
    insertQ();
  }

  public static void main(String[] args) throws Throwable {
    new PopulateDB().setUp();
  }

  void createTables() {
    final ClassLoader loader = Thread.currentThread().getContextClassLoader();
    InputStreamSupport streamSupport = new InputStreamSupport();
    streamSupport.setStreamProvider(new InputStreamProvider() {
      @Override
      public InputStream getStream(String path) {
        return loader.getResourceAsStream(path);
      }

      @Override
      public String getContextPath() {
        return null;
      }
    });

    final StringBuilder builder = new StringBuilder(1024);
    streamSupport.doInStream("create_table_sqlite.sql", new InputStreamCallback() {
      @Override
      public void doWithStream(InputStream stream) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        String line;
        while (null != (line = reader.readLine())) {
          builder.append(line);
        }
      }
    });
    jdbc.execute(builder.toString());
//    for (String s : builder.toString().split(";")) {
//      if (s.length() > 10) {
//        jdbc.execute(s);
//      }
//    }
  }

  void insertTests(final int num) {
    for (int i = 1; i <= num; i++) {
      jdbc.execute("insert into bw_paper(name,description,score,name_publisher)" +
          "values(\"Demo Test " + i + "\", \"This is a demo test\", " + (rand.tlrand().nextBoolean() ? 80 : 100) + ", \"Bowen College\")");
    }
    System.out.println(num + "  test papers inserted");
  }

  void insertChapters(final int num) {
    for (int i = 1; i <= num; i++) {
      jdbc.execute("insert into bw_chapter(name,description)" +
          "values(\"Demo Chapter " + i + "\", \"This is a demo chapter \")");
    }
    System.out.println(num + "  chapters inserted");

    List<BWChapter> chapls = daoChapter.getAll();
    List<Pair<BWChapter, BWChapter> > depends = new ArrayList<>(80);

    TreeMap<Integer, BWChapter> cidmap = new TreeMap<>();
    for (BWChapter c : chapls)
      cidmap.put(c.getId(), c);

    Set<BWChapter> chapd = rand.pickFrom(chapls, 0.85F);
    ArrayList<BWChapter> depls = new ArrayList<>(100);

    for (BWChapter c : chapd) {
      depls.clear();
      SortedMap<Integer, BWChapter> prevch = cidmap.headMap(c.getId());
      depls.addAll(prevch.values());

      int totald = rand.pickInt(7);
      totald = Math.min(totald, depls.size());
      if (totald == 0 || totald > depls.size())
        continue;
      Set<BWChapter> deps = rand.pickFrom(depls, totald);
      for (BWChapter c2 : deps) {
        depends.add(new Pair<>(c, c2));
      }
    }

    for (Pair<BWChapter, BWChapter> p : depends) {
      jdbc.execute("insert into bw_chapter_depend(id_chapter, id_depend)values(" + p.first.id + "," + p.second.id +  ")");
    }

  }

  void insertQ() {
    List<Integer> testIds = jdbc.queryForList("select id from bw_paper", Integer.class);
//    for (int i : testIds)
//      System.out.println(i);

    List<Integer> chapIds = jdbc.queryForList("select id from bw_chapter", Integer.class);
//    for (int i : chapIds)
//      System.out.println(i);
    insQ(QuestionType.MULTI_CHOICE, 200, 2, testIds, chapIds);
    insQ(QuestionType.FILL_BLANKS, 100, 1, testIds, chapIds);
    insQ(QuestionType.TRUE_FALSE, 80, 2, testIds, chapIds);
    insQ(QuestionType.ANALYZE, 60, 5, testIds, chapIds);
    insQ(QuestionType.SIMPLE_QA, 30, 5, testIds, chapIds);

    insQ(QuestionType.APPLICATION, 10, 10, testIds, chapIds);
    insQ(QuestionType.APPLICATION, 6, 15, testIds, chapIds);
    insQ(QuestionType.APPLICATION, 6, 20, testIds, chapIds);

    List<BWQuestionMeta> ms = daoQuestion.getAll();
    int i = 1;
    for (BWQuestionMeta m : ms) {
      jdbc.execute(
          "insert into bw_question(id_meta, content, answer, comment)values(?,?,?,?)",
          m.id,
          m.id + " question content (" + m.type.getName() + ") " + m.id,
          m.id + " answer: demo ",
          "no comment");
      i++;
    }
    int ct = jdbc.queryForObject("select count(1) from bw_question", Integer.class);
    System.out.println(ct + " questions inserted");
  }

  private void insQ(QuestionType type, int number, int score, List<Integer> testIds, List<Integer> chapIds) {

    BWQuestionMeta questionMeta = null;
    for (int i = 0; i < number; i++) {
      questionMeta = new BWQuestionMeta();
      questionMeta.type = type;
      questionMeta.score = score;

      questionMeta.difficulty = rand.tlrand().nextFloat() * 0.8F + 0.2F;
      questionMeta.id_paper = testIds.get(rand.tlrand().nextInt(testIds.size()));
      questionMeta.id_chapter = chapIds.get(rand.tlrand().nextInt(chapIds.size()));
      daoQuestion.save(questionMeta);
    }

  }

}
