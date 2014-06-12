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
package test.gen;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.wangk.comper.feature.Config;
import org.wangk.comper.feature.IEvaluator;
import org.wangk.comper.feature.IRandomGenerator;
import org.wangk.comper.feature.SystemConfig;
import org.wangk.comper.feature.TrainingField;
import org.wangk.comper.feature.impl.Evaluator;
import org.wangk.comper.feature.impl.RandomGenerator;
import org.wangk.comper.feature.impl.Trainer;
import org.wangk.comper.feature.model.Group;
import org.wangk.comper.feature.model.QuestionType;
import org.wangk.comper.util.Pair;

import test.comper.jdbc.DBConnection;

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
