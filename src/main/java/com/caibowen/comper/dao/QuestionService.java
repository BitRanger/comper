
package com.caibowen.comper.dao;

import com.caibowen.comper.feature.model.QuestionType;
import com.caibowen.comper.feature.model.TestPaper;
import com.caibowen.comper.feature.model.TestQuestion;
import com.caibowen.comper.model.BWQuestionMeta;
import com.caibowen.comper.util.Pair;
import com.caibowen.comper.util.Triple;
import com.caibowen.comper.AppContext;
import com.caibowen.comper.feature.model.Group;
import com.caibowen.comper.util.Assert;

import javax.inject.Inject;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author BowenCai
 *
 */
public class QuestionService {

  @Inject
  DAOQuestion daoQuestion;
  @Inject
  DAOChapter daoChapter;
  @Inject
  DAOPaper daoPaper;
//	ConcurrentHashMap<Integer, BWQuestionMeta> AllQuestion;

  public EnumMap<QuestionType, ConcurrentHashMap<Integer, BWQuestionMeta>> typeMap;

  public QuestionService() {
    typeMap = new EnumMap<QuestionType, ConcurrentHashMap<Integer, BWQuestionMeta>>(QuestionType.class);

    typeMap.put(QuestionType.MULTI_CHOICE, new ConcurrentHashMap<Integer, BWQuestionMeta>(256));
    typeMap.put(QuestionType.FILL_BLANKS, new ConcurrentHashMap<Integer, BWQuestionMeta>(256));
    typeMap.put(QuestionType.TRUE_FALSE, new ConcurrentHashMap<Integer, BWQuestionMeta>(256));
    typeMap.put(QuestionType.SIMPLE_QA, new ConcurrentHashMap<Integer, BWQuestionMeta>(256));
    typeMap.put(QuestionType.ANALYZE, new ConcurrentHashMap<Integer, BWQuestionMeta>(256));
    typeMap.put(QuestionType.APPLICATION, new ConcurrentHashMap<Integer, BWQuestionMeta>(256));

  }

  /**
   * 从数据库里读出所有题，分类存到槽里
   */
  private boolean loaded = false;

  public void loadAll(boolean force) {
    if (force || !loaded) {
      List<BWQuestionMeta> all = daoQuestion.getAll();
      for (BWQuestionMeta meta : all) {
        typeMap.get(meta.type).put(meta.id, meta);
      }
    }
  }

  public void loadAll() {
    Assert.notNull(daoQuestion);
    if (loaded) {
      return;
    }
    loadAll(true);
    loaded = true;
  }

  public Map<Integer, TestQuestion> getFullQuestion() {
    HashMap<Integer, TestQuestion> mp = new HashMap<>(2048);
    for (ConcurrentHashMap<Integer, BWQuestionMeta> typeQ : typeMap.values()) {
      for (BWQuestionMeta meta : typeQ.values()) {
        mp.put(meta.id, getQuestion(meta));
      }
    }
    return mp;
  }


  public TestQuestion getQuestion(BWQuestionMeta meta) {
    if (daoChapter == null) {
      daoChapter = AppContext.beanAssembler.getBean("daoChapter");
//			System.out.println("QuestionService.getQuestion()");
//			System.out.println(daoChapter);
    }
    if (daoPaper == null) {
      daoPaper = AppContext.beanAssembler.getBean("daoPaper");
    }
    TestQuestion q = new TestQuestion();
    Triple<String, String, String> t = daoQuestion.getContent(meta.id);
    q.answer = t.second;
    q.comment = t.third;
    q.content = t.first;
    q.difficulty = meta.difficulty;
    q.chapter = daoChapter.get(meta.id_chapter);
    q.paper = daoPaper.get(meta.id_paper);
    q.score = meta.score;

    return q;
  }

  public void delete(BWQuestionMeta meta) {
    daoQuestion.delete(meta.id);
//		loadAll(true);
    typeMap.get(meta.type).remove(meta.id);
  }

  /**
   * 选出Type一致，Points相同的题
   * @param questionMeta
   * @return a set of questions of the same type and score
   */
  public Set<BWQuestionMeta> getCandidates(BWQuestionMeta questionMeta) {
    Assert.notNull(questionMeta);
    ConcurrentHashMap<Integer, BWQuestionMeta> oneType = typeMap.get(questionMeta.type);
    Set<BWQuestionMeta> cadi = new HashSet<BWQuestionMeta>(64);
    for (BWQuestionMeta qMeta : oneType.values()) {
      if (qMeta.score == questionMeta.score
          && !qMeta.equals(questionMeta)) {
        cadi.add(qMeta);
      }
    }
    return cadi;
  }

  public DAOQuestion getDaoQuestion() {
    return daoQuestion;
  }

  public void setDaoQuestion(DAOQuestion daoQuestion) {
    this.daoQuestion = daoQuestion;
  }


  public TestPaper toPaper(Group group) {
    TestPaper paper = new TestPaper();
    paper.adaptability = group.summary.adaptability;
    paper.coverage = group.summary.coverage;
    paper.difficulty = group.summary.difficulty;

    for (Map.Entry<QuestionType, ArrayList<BWQuestionMeta>> e : group.typeMap.entrySet()) {
      if (e.getValue().size() > 0) {
        ArrayList<TestQuestion> qs = new ArrayList<>();
        Pair<QuestionType, ArrayList<TestQuestion>> p = new Pair<>();
        p.first = e.getKey();
        for (BWQuestionMeta m : e.getValue()) {
          qs.add(getQuestion(m));
        }
        p.second = qs;
        paper.add(p);
      }
    }
    return paper;
  }

  /**
   * @return the daoChapter
   */
  public DAOChapter getDaoChapter() {
    return daoChapter;
  }

  /**
   * @param daoChapter the daoChapter to set
   */
  public void setDaoChapter(DAOChapter daoChapter) {
    this.daoChapter = daoChapter;
  }

  /**
   * @return the daoPaper
   */
  public DAOPaper getDaoPaper() {
    return daoPaper;
  }

  /**
   * @param daoPaper the daoPaper to set
   */
  public void setDaoPaper(DAOPaper daoPaper) {
    this.daoPaper = daoPaper;
  }
}



