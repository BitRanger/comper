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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import test.jdbc.DBConnection;

public class TestSQLite2 extends DBConnection {

	@Before
	public void setUp() throws Exception {
		connect();

		Connection connection = jdbc.getConnection();
		Statement statement = connection.createStatement();
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
	public void test() {
	}

}
