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

import java.util.List;

import org.blacklancer.comper.model.WKChapter;
import org.blacklancer.comper.model.WKPaper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import test.jdbc.DBConnection;

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
