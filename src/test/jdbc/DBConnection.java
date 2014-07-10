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
package test.jdbc;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.blacklancer.comper.IBeanAssembler;
import org.blacklancer.comper.XMLBeanAssembler;
import org.blacklancer.comper.config.ClassLoaderInputStreamProvider;
import org.blacklancer.comper.config.FileInputStreamProvider;
import org.blacklancer.comper.config.InputStreamCallback;
import org.blacklancer.comper.config.InputStreamSupport;
import org.blacklancer.comper.dao.DAOChapter;
import org.blacklancer.comper.dao.DAOPaper;
import org.blacklancer.comper.dao.DAOQuestion;
import org.blacklancer.comper.dao.QuestionService;
import org.blacklancer.comper.db.jdbc.JdbcAux;

public class DBConnection {

	public static JdbcAux jdbc;
	public static DAOPaper daoPaper;
	public static DAOChapter daoChapter;
	public static DAOQuestion daoQuestion;
	public static QuestionService questionService;
	
	public void connect() {
		
		final IBeanAssembler assembler = XMLBeanAssembler.getInstance();
		assembler.setClassLoader(TestConnection.class.getClassLoader());
		InputStreamSupport streamSupport = new InputStreamSupport();
		streamSupport.setStreamProvider(new ClassLoaderInputStreamProvider(this.getClass().getClassLoader()));
		streamSupport.doInStream("context.xml", new InputStreamCallback() {
			@Override
			public void doWithStream(InputStream stream) throws Exception {
				assembler.assemble(stream);
			}
		});
		
		this.jdbc = assembler.getBean("jdbcAux");
		this.daoChapter = assembler.getBean("daoChapter");
		this.daoPaper = assembler.getBean("daoPaper");
		this.daoQuestion = assembler.getBean("daoQuestion");
		this.questionService = assembler.getBean("questionService");
		
//		jdbc.execute("DROP TABLE IF EXISTS wk_paper");
//		jdbc.execute("DROP TABLE IF EXISTS wk_chapter");
//		jdbc.execute("DROP TABLE IF EXISTS wk_question_meta");
//		jdbc.execute("DROP TABLE IF EXISTS wk_question");
		final StringBuilder builder = new StringBuilder(1024);
		streamSupport.doInStream("create_table_sqlite.sql", new InputStreamCallback() {
			@Override
			public void doWithStream(InputStream stream) throws Exception {
				InputStreamReader reader = new InputStreamReader(stream);
				BufferedReader bReader = new BufferedReader(reader);
				String buf;
				while (null != (buf = bReader.readLine())) {
					builder.append(buf);
				}
			}
		});

		for (String  s : builder.toString().split(";")) {
			if (s.length() > 10) {
				jdbc.execute(s);
			}
		}
	}
	
//	public static void main(String...a) throws ParseException {
//		new DBConnection().connect();
//		System.out.println("DBConnection.main()");
//	}
}
