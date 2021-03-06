/*******************************************************************************
 * Copyright (c) 2014 Cai Bowen, Zhou Liangpeng.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v2.1
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Cai Bowen,  Zhou Liangpeng. - initial API and implementation
 ******************************************************************************/
package test.gen;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.blacklancer.comper.feature.Config;
import org.blacklancer.comper.feature.IEvaluator;
import org.blacklancer.comper.feature.IRandomGenerator;
import org.blacklancer.comper.feature.SystemConfig;
import org.blacklancer.comper.feature.TrainingField;
import org.blacklancer.comper.feature.impl.Evaluator;
import org.blacklancer.comper.feature.impl.RandomGenerator;
import org.blacklancer.comper.feature.impl.Trainer;
import org.blacklancer.comper.feature.model.Group;
import org.blacklancer.comper.feature.model.QuestionType;
import org.blacklancer.comper.util.Pair;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import test.jdbc.DBConnection;

public class T1 extends DBConnection {

	TrainingField field = new TrainingField();
	
	@Before
	public void setUp() throws Exception {
		connect();
		IEvaluator evaluator = new Evaluator();
		Trainer trainer = new Trainer();
		trainer.setQuestionSerive(questionService);
		
		IRandomGenerator rGenerator = new RandomGenerator();
		
		field.setEvaluator(evaluator);
		field.setRandomGenerator(rGenerator);
		field.setTrainer(trainer);
	}
//	public static Config build(float overallDifficulty,
//			Set<Integer> totalChapters,
//			Map<QuestionType, Pair<Integer, Integer>> section,
//			int numberOfResult,
//			float tolerance,
//			SystemConfig inner) {
	@Test
	public void test() {
		Random random = new Random();
		Set<Integer> chaps = new HashSet<>();
		//考察8个知识点
		while (chaps.size() < 10) {
			chaps.add(random.nextInt(16));
		}
		chaps.remove(0);
		
		Map<QuestionType, Pair<Integer, Integer>> sec = new HashMap<QuestionType, Pair<Integer,Integer>>();
		//10道选择题， 共20分
		sec.put(QuestionType.MULTI_CHOICE, new Pair<Integer, Integer>(30, 15));

		//15道填空题， 共15
		sec.put(QuestionType.FILL_BLANKS, new Pair<Integer, Integer>(15, 15));
		// 3道解释题，共15分
		sec.put(QuestionType.EXPLAINATION, new Pair<>(15, 3));
		
		//6道简答， 30分
		sec.put(QuestionType.SIMPLE_QA, new Pair<>(15, 3));
		
		//1 道应用题
		sec.put(QuestionType.APPLICATION, new Pair<>(20, 1));
		
		// 生成本次的设置（难度 0.6，容忍度0.4
		Config config = 
		Config.build(0.6F, chaps, sec, 3, 0.4F, SystemConfig.getDefault());
		
		field.setConfig(config);
		field.prepare();
		field.train();
		List<Group> groups = field.getSortedResult();

		//输出最佳的一组
		System.out.println(groups.get(groups.size() - 1));

	}
	
	
	@After
	public void tearDown() throws Exception {
	}



}
