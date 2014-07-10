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

import org.blacklancer.comper.AppContext;
import org.blacklancer.comper.dao.DAOPaper;
import org.blacklancer.comper.dao.QuestionService;
import org.blacklancer.comper.feature.Config;
import org.blacklancer.comper.feature.SystemConfig;
import org.blacklancer.comper.feature.TrainingField;
import org.blacklancer.comper.feature.builder.HtmlGenerator;
import org.blacklancer.comper.feature.model.Group;
import org.blacklancer.comper.feature.model.QuestionType;
import org.blacklancer.comper.feature.model.TestPaper;
import org.blacklancer.comper.model.WKPaper;
import org.blacklancer.comper.util.Pair;

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
