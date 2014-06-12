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

import org.wangk.comper.context.AppContext;
import org.wangk.comper.dao.DAOPaper;
import org.wangk.comper.dao.QuestionService;
import org.wangk.comper.feature.Config;
import org.wangk.comper.feature.SystemConfig;
import org.wangk.comper.feature.TrainingField;
import org.wangk.comper.feature.builder.HtmlGenerator;
import org.wangk.comper.feature.model.Group;
import org.wangk.comper.feature.model.QuestionType;
import org.wangk.comper.feature.model.TestPaper;
import org.wangk.comper.model.WKPaper;
import org.wangk.comper.util.Pair;

public class Demo {

	public static void main(String[] args) {
		
		AppContext.setUp();

		/**
		 * 保存试卷
		 */
//		WKPaper paperX = new WKPaper();
//		paperX.name = "ttttest paper";
//		paperX.description = "ttttest discription";
//		DAOPaper daoPaper = AppContext.beanAssembler.getBean("daoPaper");
//		daoPaper.save(paperX);
//		JdbcAux jdbc = AppContext.beanAssembler.getBean("jdbcAux");
//		jdbc.execute("update wk_question_meta set difficulty = difficulty + 0.05 where difficulty < 0.7 and  difficulty > 0.4");
//		jdbc.execute("update wk_question_meta set difficulty = difficulty - 0.05 where difficulty > 0.98 ");
		
		
		/**
		 * 更新试卷
		 */
//		paperX = daoPaper.get(5);
//		paperX.description = "latest  description";
//		daoPaper.update(paperX);
		
		/**
		 * 测试遗传算法
		 */
		TrainingField field = AppContext.beanAssembler.getBean("trainingField");
		
		Random random = new Random();
		Set<Integer> chaps = new HashSet<>();
		//考察10个知识点
		while (chaps.size() < 13) {
			chaps.add(random.nextInt(16));
		}
		chaps.remove(0);
		
		Map<QuestionType, Pair<Integer, Integer>> sec = new HashMap<QuestionType, Pair<Integer,Integer>>();
		//15道选择题， 共30分
		sec.put(QuestionType.MULTI_CHOICE, new Pair<Integer, Integer>(30, 15));//2

		//15道填空题， 共15
		sec.put(QuestionType.FILL_BLANKS, new Pair<Integer, Integer>(15, 15));//1
		
		// 3道解释题，共15分
		sec.put(QuestionType.EXPLAINATION, new Pair<>(15, 3));//5
		
		//4道简答， 20分
		sec.put(QuestionType.SIMPLE_QA, new Pair<>(20, 4));//5
		
		//1 道应用题
		sec.put(QuestionType.APPLICATION, new Pair<>(20, 1));//
		
		// 生成本次的设置 难度 0.7  容忍度0.24，总分100
		Config config = 
		Config.build(0.7F, chaps, sec, 4, 0.25F, SystemConfig.getDefault());
		System.out.println(config.getTotalScore());
		field.setConfig(config);
		field.prepare();
		field.train();
		List<Group> groups = field.getSortedResult();

		//输出最佳的一组
		for (Group group : groups) {
			System.out.println(group.summary.adaptability);
		}
		
		/**
		 * 导出为doc和html
		 */
		QuestionService questionService = AppContext.beanAssembler.getBean("questionService");
		TestPaper paper = questionService.toPaper(groups.get(groups.size() - 1));
//
		System.out.println(paper);
//		
//		HtmlGenerator generator = AppContext.beanAssembler.getBean("htmlGenerator");
//		generator.setConfig(config);
//		generator.add(paper);
//		paper = questionService.toPaper(groups.get(groups.size() - 2));
//		generator.add(paper);
//		
//		paper = questionService.toPaper(groups.get(groups.size() - 3));
//		generator.add(paper);
//		
//		//导出为text.html 和 test.doc
//		generator.output("test");

	}

}
