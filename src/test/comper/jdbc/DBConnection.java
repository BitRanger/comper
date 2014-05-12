package test.comper.jdbc;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.wangk.comper.context.XMLBeanAssembler;
import org.wangk.comper.context.config.FileInputStreamProvider;
import org.wangk.comper.context.config.InputStreamCallback;
import org.wangk.comper.context.config.InputStreamSupport;
import org.wangk.comper.dao.DAOChapter;
import org.wangk.comper.dao.DAOPaper;
import org.wangk.comper.dao.DAOQuestion;
import org.wangk.comper.dao.QuestionService;
import org.wangk.comper.db.jdbc.JdbcAux;

public class DBConnection {

	public JdbcAux jdbc;
	public DAOPaper daoPaper;
	public DAOChapter daoChapter;
	public DAOQuestion daoQuestion;
	public QuestionService questionService;
	
	public void connect() {
		
		final XMLBeanAssembler assembler = XMLBeanAssembler.getInstance();
		assembler.setClassLoader(TestConnection.class.getClassLoader());
		InputStreamSupport streamSupport = new InputStreamSupport();
		streamSupport.setStreamProvider(new FileInputStreamProvider());
		streamSupport.doInStream("src/context.xml", new InputStreamCallback() {
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
		
		String buf = "";
		try {
			buf = new String(Files.readAllBytes(new File("sql/create_table_sqlite.sql").toPath()));
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (String  s : buf.split(";")) {
//			System.out.println(s);
			if (s.length() > 10) {
				jdbc.execute(s);
			}
		}
//		<bean id="daoQuestion" class="org.wangk.comper.service.impl.DAOQuestion">
//	<bean id="daoPaper" class="org.wangk.comper.dao.DAOPaper">
//		<bean id="daoChapter" class="org.wangk.comper.dao.DAOChapter">
	
	}
	
//	public static void main(String...a) throws ParseException {
//		new DBConnection().connect();
//		System.out.println("DBConnection.main()");
//	}
}
