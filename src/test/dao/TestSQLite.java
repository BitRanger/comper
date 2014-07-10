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

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchEvent.Modifier;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestSQLite {

	@Before
	public void setUp() throws Exception {
		Class.forName("org.sqlite.JDBC");
		Connection connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
		Statement statement = connection.createStatement();
		String buf = new String(Files.readAllBytes(new File("sql/create_table_sqlite.sql").toPath()));
//		System.out.println(buf);
		statement.execute(buf);
		statement.execute("insert into wk_paper(name)values(\"from sqlite!\")");
		ResultSet rSet = statement.executeQuery("select count(*) from wk_paper");
		
		while (rSet.next()) {
			System.out.println(rSet.getInt(1));
		}
		System.out.println("TestSQLite.setUp()");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() throws SQLException {
	      Connection connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
	      Statement statement = connection.createStatement();
	      statement.setQueryTimeout(10);  // set timeout to 30 sec.

	      statement.executeUpdate("drop table if exists person");
	      statement.executeUpdate("create table person (id integer, name string)");
	      statement.executeUpdate("insert into person values(1, 'leo')");
	      statement.executeUpdate("insert into person values(2, 'yui')");
	      ResultSet rs = statement.executeQuery("select * from person");
	      while(rs.next())
	      {
	        // read the result set
	        System.out.println("name = " + rs.getString("name"));
	        System.out.println("id = " + rs.getInt("id"));
	      }
	}

}
