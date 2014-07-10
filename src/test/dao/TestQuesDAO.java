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
package test.dao;

import org.blacklancer.comper.feature.model.QuestionType;
import org.blacklancer.comper.model.WKQuestionMeta;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import test.jdbc.DBConnection;

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
