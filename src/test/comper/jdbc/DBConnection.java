package test.comper.jdbc;

import java.io.InputStream;
import org.wangk.comper.context.XMLBeanAssembler;
import org.wangk.comper.context.config.FileInputStreamProvider;
import org.wangk.comper.context.config.InputStreamCallback;
import org.wangk.comper.context.config.InputStreamSupport;
import org.wangk.comper.dao.DAOChapter;
import org.wangk.comper.dao.DAOPaper;
import org.wangk.comper.dao.DAOQuestion;
import org.wangk.comper.db.jdbc.JdbcAux;

public class DBConnection {

	public JdbcAux jdbc;
	public DAOPaper daoPaper;
	public DAOChapter daoChapter;
	public DAOQuestion daoQuestion;
	
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
//		<bean id="daoQuestion" class="org.wangk.comper.service.impl.DAOQuestion">
//	<bean id="daoPaper" class="org.wangk.comper.dao.DAOPaper">
//		<bean id="daoChapter" class="org.wangk.comper.dao.DAOChapter">
	
	}
}
