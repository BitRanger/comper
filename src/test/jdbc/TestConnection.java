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

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.blacklancer.comper.XMLBeanAssembler;
import org.blacklancer.comper.config.FileInputStreamProvider;
import org.blacklancer.comper.config.InputStreamCallback;
import org.blacklancer.comper.config.InputStreamSupport;
import org.blacklancer.comper.db.jdbc.JdbcAux;
import org.blacklancer.comper.db.orm.RowMapping;
import org.blacklancer.comper.model.Tag;

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
