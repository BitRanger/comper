package test.comper.jdbc;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Statement;

import javax.sql.DataSource;

import org.wangk.comper.context.XMLBeanAssembler;
import org.wangk.comper.context.config.FileInputStreamProvider;
import org.wangk.comper.context.config.InputStreamCallback;
import org.wangk.comper.context.config.InputStreamSupport;
import org.wangk.comper.db.jdbc.JdbcAux;

public class DBConnection {

	JdbcAux jdbc;
	
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
	}
}
