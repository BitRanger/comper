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

import java.util.List;
import java.util.Random;

import org.blacklancer.comper.AppContext;
import org.blacklancer.comper.dao.DAOChapter;
import org.blacklancer.comper.dao.DAOPaper;
import org.blacklancer.comper.dao.DAOQuestion;
import org.blacklancer.comper.db.jdbc.JdbcAux;
import org.blacklancer.comper.feature.impl.RandomGenerator;
import org.blacklancer.comper.feature.model.QuestionType;
import org.blacklancer.comper.model.WKChapter;
import org.blacklancer.comper.model.WKPaper;
import org.blacklancer.comper.model.WKQuestionMeta;
import org.blacklancer.comper.util.Str.date;
import org.junit.Before;
import org.junit.Test;

import test.jdbc.DBConnection;

public class Gen {

	RandomGenerator rand = new RandomGenerator();
	Random random = new Random();

	List<WKPaper> papers;
	List<WKChapter> chapters;
	JdbcAux jdbc;

	DAOPaper daoPaper;
	DAOQuestion daoQuestion;
	DAOChapter daoChapter;
	@Before
	public void setUp() throws Exception {
		AppContext.setUp();
		jdbc = AppContext.beanAssembler.getBean("jdbcAux");

		daoPaper = AppContext.beanAssembler.getBean("daoPaper");
		daoQuestion = AppContext.beanAssembler.getBean("daoQuestion");
		daoChapter = AppContext.beanAssembler.getBean("daoChapter");
	}


//	@Test
	public void populateQContent() {
		jdbc.execute("delete from wk_question");
		List<WKQuestionMeta> ms = daoQuestion.getAll();
		int i = 0;
		for (WKQuestionMeta m : ms) {
			jdbc.execute(
		"insert into wk_question(id_meta, content, answer, comment)values(?,?,?,?)",
					m.id,
					" question content " + m.type.getZhName(),
					" answer to" + m.id,
					"no comment");
			System.out.println(i++);
		}
		System.out.println("Gen.populateQContent()");
	}

	/**
	 * plan
	 * 
	 * 20 paper
	 * 15 chapter
	 * 
	 * MULTI_CHOICE 2 score * 12 * 20 = 2 * 240
	 * FILL_BLANKS  1 score * 10 * 20 = 1 * 200
	 * TRUE_FALSE   2 score * 6 * 20 = 2 * 120
	 * EXPLAINATION 3 score * 5 * 20 = 3 * 100
	 * SIMPLE_QA    5 score * 3 * 20 = 5 * 60
	 * 
	 * APPLICATION 16 score 1 * 20
	 * 
	 * APPLICATION 20 score 1 * 20
	 *  
	 */
//	@Test
	public void go() {
		
		for (int i = 0; i < 20; i++) {
			WKPaper paper = new WKPaper();
			paper.difficulty = random.nextFloat();
			paper.name = "paper " + i;
			daoPaper.save(paper);
		}

		for (int i = 0; i < 15; i++) {
			WKChapter paper = new WKChapter();
			paper.name = "WKChapter " + i;
			daoChapter.save(paper);
		}
		
		papers = daoPaper.getAll();
		chapters = daoChapter.getAll();
		System.out.println(papers.size());
		System.out.println(chapters.size());
		
		System.out.println("populating DB WKQuestionMeta... ");
		populateDB(QuestionType.MULTI_CHOICE, 240, 2);
		populateDB(QuestionType.FILL_BLANKS, 200, 1);
		populateDB(QuestionType.TRUE_FALSE, 120, 2);
		populateDB(QuestionType.EXPLAINATION, 100, 5);
		populateDB(QuestionType.SIMPLE_QA, 60, 5);
		
		populateDB(QuestionType.APPLICATION, 2, 16);
		populateDB(QuestionType.APPLICATION, 1, 28);
		populateDB(QuestionType.APPLICATION, 2, 20);

		/**
		 * 调整随机生成的题目难度值，避免极化数据
		 */
		jdbc.execute("update wk_question_meta set difficulty = difficulty + 0.1 where difficulty < 0.15 ");
		jdbc.execute("update wk_question_meta set difficulty = difficulty - 0.1 where difficulty > 0.95 ");
		
		List<WKQuestionMeta> questionMetas = daoQuestion.getAll();

		System.out.println(questionMetas.size());
	}
	
	
	public void populateDB(QuestionType type, int number, int score) {
		
		WKQuestionMeta questionMeta = null;
		for (int i = 0; i < number; i++) {
			questionMeta = new WKQuestionMeta();
			questionMeta.type = type;
			questionMeta.score = score;
			questionMeta.difficulty = random.nextFloat();
			questionMeta.id_paper = rand.pickSingle(papers).id;
			questionMeta.id_chapter = rand.pickSingle(chapters).id;
			daoQuestion.save(questionMeta);
		}

	}
	
	
}
