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
 输入:
1. 试卷总分，难度值，考察的知识点。
2. 哪些题型（选择，填空，简答附加...）
3. 各题型的数量（选择10道，填空10道...），分数（选择20分，填空20分...）

输出：
一套题，总分，难度值。
对于卷子中的每道题，显示题型，难度，分值，知识点，这道题在哪个卷子里。

 */
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

		System.out.print("TrainingField.adjust()  " + config.getTolerance());
		if (count * 5 / 10 > config.internal.maxTraining) {
			config.setTolerance(config.getTolerance() + 0.0005F);
			System.out.println(config.getTolerance());
		} else if (count * 8 / 10 > config.internal.maxTraining) {
			config.setTolerance(config.getTolerance() + 0.008F);
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
