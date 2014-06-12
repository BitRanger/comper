/*******************************************************************************
 * Copyright 2014 Cai Bowen Zhou Liangpeng
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
		
		int currentStamp = (int) (group.hashCode()
									* config.internal.weightCoverage 
									* config.internal.weightDifficulty);
		
		if (group.summary.stamp != currentStamp) {
			final float coverage = getCoverage(group);
			final float difficulty = getDifficulty(group);
//			System.out.println("Evaluator.evaluate()");
//			System.out.println("diff " + (difficulty) + "    cover " + coverage );
			final float diff = Math.abs(difficulty - config.getDifficulty())
								/ config.getDifficulty();
//System.out.println("Evaluator.evaluate()");
//System.out.println(difficulty + " / " + config.getDifficulty() + "  " + diff + "   " + coverage);
			final float adap = 
				coverage * config.internal.weightCoverage
						+ diff * config.internal.weightDifficulty;
			
			
			group.summary.adaptability = adap;
			group.summary.difficulty = difficulty;
			group.summary.coverage = coverage;

//			System.out.print("Evaluator.evaluate()  ");
//			System.out.println(group.summary.coverage);
			
			currentStamp = (int) (group.hashCode()
					* config.internal.weightCoverage 
					* config.internal.weightDifficulty);
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
		float rs = config.getNumResult();
		rs *= 0.5;
		float adap = 1.0F - groups.get(groups.size() - (int)rs).summary.adaptability;
		System.out.print("Evaluator.isQualified()\t\t Best Adap");
		System.out.println(groups.get(groups.size() - 1).summary.adaptability);
		
		return adap < config.getTolerance();
	}
	//获得覆盖率
	float getCoverage(Group group) {
		
		Set<Integer> chapterCovered = new HashSet<Integer>();
		for (List<WKQuestionMeta> ls : group.typeMap.values()) {
			if (ls.size() > 0) {
				for (WKQuestionMeta meta : ls) {
					chapterCovered.add(meta.id_chapter);
				}
			}
		}
		
		Set<Integer> cp = new HashSet<Integer>(config.getChapterIdSet());
		cp.retainAll(chapterCovered);
		float foo = cp.size();
		return foo / config.getChapterIdSet().size();
	}
	
	float getDifficulty(Group group) {
		float diffi = 0.0F;
		for (List<WKQuestionMeta> ls : group.typeMap.values()) {
			if (ls.size() > 0) {
				for (WKQuestionMeta meta : ls) {
					diffi += meta.score * meta.difficulty;
				}
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
