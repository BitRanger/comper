/*******************************************************************************
 * Copyright (c) 2014 WangKang.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *    WangKang. - initial API and implementation
 ******************************************************************************/
package test.comper.jdbc;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.wangk.comper.context.XMLBeanAssembler;
import org.wangk.comper.context.config.FileInputStreamProvider;
import org.wangk.comper.context.config.InputStreamCallback;
import org.wangk.comper.context.config.InputStreamSupport;
import org.wangk.comper.db.jdbc.JdbcAux;
import org.wangk.comper.db.jdbc.stmt.UpdateStatementCreator;
import org.wangk.comper.db.orm.RowMapping;
import org.wangk.comper.model.Tag;

public class TestConnection {
	
//	@Test
	public static void main(String...a) throws Exception {
		
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
		DataSource dataSource = assembler.getBean("datasource");
//		INSERT INTO bw_tag(num_tagged,content) VALUES(19,'new put()')
		Connection connection = dataSource.getConnection();
		Statement statement = connection.createStatement();
		statement.equals("INSERT INTO bw_tag(num_tagged,content) VALUES(19,'new put()')");
		statement.close();
		connection.close();
		
		JdbcAux jdbc = assembler.getBean("jdbcAux");
//		jdbc.execute("insert into bw_tag(id,num_tagged,content) values(?,?,?)", 111, 234, "new test");
		Tag tag = jdbc.queryForObject("select * from bw_tag where id = 111",
				new RowMapping<Tag>() {
					@Override
					public Tag rowToObjec(ResultSet rs) throws SQLException {
						Tag tag = new Tag();
						tag.id = rs.getInt(1);
						tag.num_tagged = rs.getInt(2);
						tag.content = rs.getString(3);
						return tag;
					}
				});
		
		System.out.println(tag.content);
		System.out.println("TestConnection.main()");
	}



}
