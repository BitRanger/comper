package test.comper.dao;

import org.junit.After;
import org.junit.Test;
import org.wangk.comper.model.WKQuestionContent;

import test.comper.jdbc.DBConnection;

public class Gen extends DBConnection {

//	@Before
	public void setUp() throws Exception {
//		connect();
//		RandomGenerator rand = new RandomGenerator();
//		List<WKPaper> papers = daoPaper.getAll();
//		List<WKChapter> chapters = daoChapter.getAll();
//		List<WKQuestionMeta> questionMetas = daoQuestion.getAll();
//		System.out.println(papers.size());
//		System.out.println(chapters.size());
//		System.out.println(questionMetas.size());

//		System.out.println(questionMetas.size());
//		for (WKChapter wkChapter : chapters) {
//			System.out.println(wkChapter);
//		}
		
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
//		Random random = new Random();
//
//		WKQuestionMeta questionMeta = null;
//		for (int i = 0; i < 120; i++) {
//			questionMeta = new WKQuestionMeta();
//			questionMeta.type = QuestionType.TRUE_FALSE;
//			questionMeta.score = 2;
//			questionMeta.difficulty = random.nextFloat();
//			questionMeta.id_paper = rand.pickSingle(papers).id;
//			questionMeta.id_chapter = rand.pickSingle(chapters).id;
//			daoQuestion.save(questionMeta);
//			System.out.println(i);
//		}
		

		
//		jdbc.execute("delete from wk_question_meta");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
//		connect();
//		WKQuestionContent qContent = new WKQuestionContent();
//		qContent.content = "fuck GFW";
//		qContent.answer = "fuck GFW";
//		qContent.comment = "fuck GFW";
//		daoQuestion.saveQuestionContent(451, qContent);
	}

}
