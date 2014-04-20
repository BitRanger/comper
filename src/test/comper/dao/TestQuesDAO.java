package test.comper.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.wangk.comper.feature.model.QuestionType;
import org.wangk.comper.model.WKQuestionMeta;

import test.comper.jdbc.DBConnection;

public class TestQuesDAO extends DBConnection {

	@Before
	public void setUp() throws Exception {
		connect();
	}
	@Test
	public void test() {
		WKQuestionMeta meta = new WKQuestionMeta();
		meta.id_chapter = daoChapter.getAll().get(0).id;
		meta.id_paper = daoChapter.getAll().get(0).id;
		meta.score = 5;
		meta.type = QuestionType.EXPLAINATION;
		meta.difficulty = 0.56F;
		daoQuestion.save(meta);
	}
	
	@After
	public void tearDown() throws Exception {
	}



}
