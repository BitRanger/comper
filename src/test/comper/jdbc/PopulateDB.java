package test.comper.jdbc;

import java.sql.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.wangk.comper.model.WKPaper;

public class PopulateDB extends DBConnection {

	@Before
	public void setUp() throws Exception {
		connect();
		WKPaper paper = new WKPaper();
		paper.setDifficulty(9.5F);
		paper.setName("test paper 1");
		paper.setName_publisher("test publisher");
		paper.setDescription("test description");
		
//		public String			name_publisher;
//		
//		public String			name;
//		public String			description;
//		public int 				score;
//		paper.chapterList
	}
	
	@Test
	public void test() {
	}
	
	@After
	public void tearDown() throws Exception {
	}



}