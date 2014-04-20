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
package org.wangk.comper.feature.impl;


import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.wangk.comper.feature.Config;
import org.wangk.comper.feature.IEvaluator;
import org.wangk.comper.feature.model.Group;
import org.wangk.comper.model.WKQuestionMeta;



public class Evaluator implements IEvaluator{
	
	private Config config;
	
	/**
	 * 对比此卷子，如果发生改变就重新计算，
	 */
	@Override
	public void evaluate(Group group) {
		
		int curHash = group.hashCode();
		int currentStamp = curHash;
		currentStamp *= config.internal.weightCoverage * config.internal.weightDifficulty;
		
		if (group.summary.stamp != currentStamp) {
			final float coverage = getCoverage(group);
			final float difficulty = getDifficulty(group);
			
			final float adap = coverage * config.internal.weightCoverage
					
								+ (config.getDifficulty() - difficulty)
									* config.internal.weightDifficulty;
			
			currentStamp = curHash;
			currentStamp *= config.internal.weightCoverage * config.internal.weightDifficulty;
			
			group.summary.adaptability = adap;
			group.summary.difficulty = difficulty;
			group.summary.coverage = coverage;
			group.summary.stamp = currentStamp;
		}
	}
	
	@Override
	public void bulkEvaluate(List<Group> groups) {
		for (Group group : groups) {
			evaluate(group);
		}
	}
	

	@Override
	public boolean isQualified(List<Group> groups) {
		bulkEvaluate(groups);
		Collections.sort(groups);
		return 1.0F - groups.get(groups.size() - 1).summary.adaptability 
				< config.getTolerance();
	}
	
	float getCoverage(Group group) {
		
		Set<Integer> chapterCovered = new HashSet<Integer>();
		for (List<WKQuestionMeta> ls : group.slots) {
			for (WKQuestionMeta meta : ls) {
				chapterCovered.add(meta.id_chapter);
			}
		}
		Set<Integer> cp = new HashSet<Integer>(config.getChapterIdSet());
		cp.retainAll(chapterCovered);
		float foo = cp.size();
		return foo / config.getChapterIdSet().size();
	}
	

	float getDifficulty(Group group) {
		float diffi = 0.0F;
		for (List<WKQuestionMeta> ls : group.slots) {
			for (WKQuestionMeta meta : ls) {
				diffi += meta.score * meta.difficulty;
			}
		}
		return diffi / config.getTotalScore();
	}
	
	@Override
	public void refresh(Config config) {
		this.config = config;
	}
	
	@Override
	public Config getConfig() {
		return config;
	}

	@Override
	public void setConfig(Config config) {
		this.config = config;
	}



}
