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

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.wangk.comper.model.WKChapter;
import org.wangk.comper.model.WKPaper;

import test.comper.jdbc.DBConnection;

public class TestPaperDAO extends DBConnection {

	@Before
	public void setUp() throws Exception {
		connect();
	}
//	@Test
	public void test() {
		
		WKPaper paper = new WKPaper();
		paper.setDifficulty(9.5F);
		paper.setName("test paper 3");
		paper.setDescription("3 test description");
		paper.setScore(120);
		paper.setName_publisher(" 3 test publisher");

		this.daoPaper.save(paper);
		
		WKChapter chapter = new WKChapter();
		chapter.description = "2 test description";
		chapter.name = "test chapter 2";
		daoChapter.save(chapter);
		
		List<WKPaper> lsP = daoPaper.getAll();
		List<WKChapter> lsC = daoChapter.getAll();
		System.out.println(lsP.size());
		System.out.println(lsC.size());

		daoPaper.addChapter(lsP.get(0).id, 
				lsC
				.get(0)
				.id);
	}
	
	@Test
	public void name() {
		System.out.println(daoChapter.getByPaper(1));
	}
	
	@After
	public void tearDown() throws Exception {
	}



}
