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
