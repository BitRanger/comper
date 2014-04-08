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


import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.wangk.comper.feature.model.Group;
import org.wangk.comper.util.Assert;
import org.wangk.comper.util.Pair;


/**
 * 训练场，接受设定值，并进行训练
 * 
 * @author BowenCai
 *
 */
public class TrainingField {
	
	@Inject private ITrainer 			trainer;
	@Inject private IEvaluator		 	evaluator;
	@Inject private IRandomGenerator 	randomGenerator;
	
	private Config 	config;
	public void setConfig(Config config) {
		this.config = config;
	}
	
	List<Group> currentGroupList;
	int trainingCount = 0;
	List<Group> resultGroupList;
	
	public void prepare() {
		
		Assert.notNull(trainer);
		Assert.notNull(evaluator);
		Assert.notNull(randomGenerator);
		Assert.notNull(config);
		
		currentGroupList = null;
		trainer.getInitGroupList(config.internal.numGroup);
		trainingCount = 0;
		resultGroupList = null;
	}
	
	/**
	 * 遗传算法核心步骤
	 * 
	 */
	public void train() {		
		
		do {
			int crossCount = 0;
			while (crossCount <= config.internal.numCrossOver) {
				Pair<Group, Group> pair = randomGenerator.pickFrom(currentGroupList);
				trainer.crossOver(pair);
			}
			
			if (evaluator.isQualified(currentGroupList)) {
				resultGroupList = currentGroupList;
				break;
			}
			
			Set<Group> toVariant = randomGenerator
									.pickFrom(currentGroupList, 
											config.internal.ratioVariantGroup);
			
			trainer.bulkVariate(toVariant, config.internal.ratioVariant);
			
			if (evaluator.isQualified(currentGroupList)) {
				resultGroupList = currentGroupList;
				break;
			}
			
		} while (!evaluator.isQualified(currentGroupList)
				&& trainingCount <= config.internal.maxTraining);
	}
	
	
	public void reTrain(Config config) {
		this.config = config;
		this.trainer.refresh(config);
		this.evaluator.refresh(config);
		this.randomGenerator.refresh(config);
		List<Group> oldGroups = currentGroupList;
		prepare();
		if (oldGroups != null) {
			currentGroupList = oldGroups;
			trainer.teamUp(currentGroupList, oldGroups);
		}
		train();
	}
	
	
	/**
	 * 
	 * @return 排好序的结果集，第一个是最佳结果
	 */
	public List<Group> getSortedResult() {
		return resultGroupList;
	}
	
	
	public Config getConfig() {
		return config;
	}


	public ITrainer getTrainer() {
		return trainer;
	}



	public void setTrainer(ITrainer trainer) {
		this.trainer = trainer;
	}

	public IEvaluator getEvaluator() {
		return evaluator;
	}

	public void setEvaluator(IEvaluator evaluator) {
		this.evaluator = evaluator;
	}

	public IRandomGenerator getRandomGenerator() {
		return randomGenerator;
	}

	public void setRandomGenerator(IRandomGenerator randomGenerator) {
		this.randomGenerator = randomGenerator;
	}
}
