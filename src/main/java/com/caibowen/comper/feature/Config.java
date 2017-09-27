
package com.caibowen.comper.feature;

import com.caibowen.comper.feature.model.QuestionType;
import com.caibowen.comper.util.Pair;
import com.caibowen.comper.util.Assert;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author BowenCai
 *
 */
public class Config implements Serializable {

  private static final long serialVersionUID = -3851614412874393196L;

  /**
   * build a new config, the total score will be calculated automatically
   *
   * @return
   */
  public static Config build(float overallDifficulty,
                             Set<Integer> totalChapters,
                             Map<QuestionType, Pair<Integer, Integer>> section,
                             int numberOfResult,
                             float tolerance,
                             SystemConfig inner) {

    Config cg = new Config();
    cg.internal = inner;

    cg.difficulty = overallDifficulty;
    cg.chapterIdSet = totalChapters;
    cg.typeScoreAndNum = section;
    cg.tolerance = tolerance;
    cg.numResult = numberOfResult;
    cg.updateTotalScore();
    return cg;
  }

  public static Config buildDefault() {

    Config cg = new Config();
    cg.internal =  SystemConfig.getDefault();

    cg.tolerance = SystemConfig.TOLERANCE;
    cg.numResult =  SystemConfig.NUM_RESULT;

    return cg;
  }

  public void update(float overallDifficulty,
                     Set<Integer> totalChapters,
                     Map<QuestionType, Pair<Integer, Integer>> section) {

    this.difficulty = overallDifficulty;
    this.chapterIdSet = totalChapters;
    this.typeScoreAndNum = section;
    updateTotalScore();
  }

  private Config() {
  }

  /**
   * 系统设定的值
   */
  public SystemConfig internal;

  /**
   * 用户设定的值
   */
  private float difficulty; // 期望的总体Difficulty
  private Set<Integer> chapterIdSet;//期望覆盖的Chapter（Chapter）

  //每种Type的总的Points和总题量题量，
  //例如M-Choice 20分，共4道，该pair值为 pair.first == 20; pair.second == 4
  public Map<QuestionType, Pair<Integer, Integer>>
      typeScoreAndNum;

  /**
   *  结束条件
   */
  public int numResult;// 结果数量
  public float tolerance;//最佳group与期望的误差

  /**
   * 根据每种Type的Points，
   * 程序自动累加，计算出Total Score
   */
  int totalScore = -1;

  public int getTotalScore() {
    return totalScore;
  }

  public void updateTotalScore() {
    Assert.notNull(typeScoreAndNum);
    totalScore = 0;
    for (Pair<Integer, Integer> p : typeScoreAndNum.values()) {
      totalScore += p.first;
    }
  }

  public float getDifficulty() {
    return difficulty;
  }

  public Set<Integer> getChapterIdSet() {
    return chapterIdSet;
  }

  public Map<QuestionType, Pair<Integer, Integer>> getTypeScoreAndNum() {
    return typeScoreAndNum;
  }

  public int getNumResult() {
    return numResult;
  }

  public void setTolerance(float t) {
    this.tolerance = t;
  }

  public float getTolerance() {
    return tolerance;
  }

  public SystemConfig getInternal() {
    return internal;
  }

  public void setInternal(SystemConfig internal) {
    this.internal = internal;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "Config [difficulty=" + difficulty + ", chapterIdSet="
        + chapterIdSet + ", typeScoreAndNum=" + typeScoreAndNum
        + ", totalScore=" + totalScore + "]";
  }


}




