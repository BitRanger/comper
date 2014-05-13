/*******************************************************************************
 * Copyright (c) 2014 WangKang.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *    WangKang. - initial API and implementation
 ******************************************************************************/
package org.wangk.comper.feature;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

import org.wangk.comper.feature.model.QuestionType;
import org.wangk.comper.util.Assert;
import org.wangk.comper.util.Pair;

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
	 * @param diffi
	 * @param totalSc
	 * @param capLs
	 * @param section
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
	
	private Config() {}
	/**
	 * 系统设定的值
	 */
	public SystemConfig internal;
	
	/**
	 * 用户设定的值
	 */
	private float						difficulty; // 期望的总体难度
	private Set<Integer> 				chapterIdSet;//期望覆盖的章节（知识点）

	//每种题型的总的分值和总题量题量，
	//例如选择题 20分，共4道，该pair值为 pair.first == 20; pair.second == 4 
	private Map<QuestionType, Pair<Integer, Integer>>	
									typeScoreAndNum;
	
	/**
	 *  结束条件
	 */
	private int numResult;// 结果数量
	private float tolerance;//最佳group与期望的误差
	
	/**
	 * 根据每种题型的分值，
	 * 程序自动累加，计算出总分
	 */
	int	totalScore = -1;
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




