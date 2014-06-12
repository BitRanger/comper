/*******************************************************************************
 * Copyright 2014 Cai Bowen Zhou Liangpeng
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package test.comper.dao;

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

import test.comper.jdbc.DBConnection;

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
