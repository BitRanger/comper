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


import java.util.ArrayList;
import java.util.Collections;
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
	List<Group> resultGroupList;
	
	public void prepare() {
		
		Assert.notNull(trainer);
		Assert.notNull(evaluator);
		Assert.notNull(randomGenerator);
		Assert.notNull(config);
		
		trainer.setConfig(config);
		evaluator.setConfig(config);
		trainer.setRandomGenerator(getRandomGenerator());
		
		currentGroupList = trainer.getInitGroupList(config.internal.maxGroup);
		resultGroupList = null;
	}
	
	/**
	 * 遗传算法核心步骤
	 * 
	 */
	public void train() {	

		int trainingCount = 0;
		do {
			trainingCount++;
			trainer.increase(currentGroupList);
			//交叉
			int crossCount = 0;
			while (crossCount <= config.internal.numCrossOver) {
				Pair<Group, Group> pair = randomGenerator.pickFrom(currentGroupList);
				trainer.crossOver(pair);
				crossCount++;
			}
			resultGroupList = currentGroupList;
			if (evaluator.isQualified(resultGroupList)) {
				break;
			}
			//选一部分出来
			Set<Group> toVariant = randomGenerator
									.pickFrom(currentGroupList, 
											config.internal.ratioVariantGroup);

			//进行交叉，也就是从备选库里选同分值，同类型的题进行替换
			trainer.bulkVariate(toVariant, config.internal.ratioVariant);
			trainer.reduct(currentGroupList);
			resultGroupList = currentGroupList;
			System.gc();
			System.gc();
			adjust(trainingCount);
		} while (!evaluator.isQualified(resultGroupList)
				&& trainingCount <= config.internal.maxTraining);
		
		Collections.sort(resultGroupList);
	}
	
	public void reTrain(Config config) {
		this.config = config;
		this.trainer.refresh(config);
		this.evaluator.refresh(config);
		List<Group> oldGroups = currentGroupList;
		prepare();
		if (oldGroups != null) {
			trainer.teamUp(currentGroupList, oldGroups);
		}
		train();
	}
	
	private void adjust(int count) {

//		System.out.print("TrainingField.adjust()  ");
		if (count * 10 / 9 > config.internal.maxGroup) {
			config.setTolerance(config.getTolerance() + 0.0005F);
			System.out.println(config.getTolerance());
		} else if (count * 8 / 7 > config.internal.maxGroup) {
			config.setTolerance(config.getTolerance() + 0.0005F);
//			System.out.println(config.getTolerance() + "   [002");
		}
	}
	
	/**
	 * 
	 * @return 排好序的结果集，第一个是最佳结果
	 */
	public List<Group> getSortedResult() {
		ArrayList<Group> ret = new ArrayList<>(12);
		List<Group> ls = resultGroupList;
		ret.addAll(ls.subList(ls.size() - config.getNumResult(), ls.size()));
//		ls.addAll(resultGroupList.subList(0, config.getNumResult()));
		return ret;
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
